package java8.NIO.remote;

import java8.NIO.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

final class FileReceiverAsync {

    private final AsynchronousServerSocketChannel server;
    private final AsynchronousChannelGroup group;
    private final String path;
    private final OnComplete onFileComplete;

    FileReceiverAsync(final int port, final int poolSize, final String path, final OnComplete onFileComplete) {
        assert !Objects.isNull(path);

        this.path = path;
        this.onFileComplete = onFileComplete;

        try {
            this.group = AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(poolSize));
            this.server = AsynchronousServerSocketChannel.open(this.group).bind(new InetSocketAddress(port));
        } catch (IOException e) {
            throw new IllegalStateException("unable to start FileReceiver", e);
        }
    }

    void start() {
        accept();
    }

    void stop(long wait) {

        try {
            this.group.shutdown();
            this.group.awaitTermination(wait, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("unable to stop FileReceiver", e);
        }
    }

    private void read(final AsynchronousSocketChannel channel, final FileWriterProxy proxy) {
        assert !Objects.isNull(channel) && !Objects.isNull(proxy);

        final ByteBuffer buffer = ByteBuffer.allocate(Constants.BUFFER_SIZE);
        channel.read(buffer, proxy, new CompletionHandler<Integer, FileWriterProxy>() {

            @Override
            public void completed(final Integer result, final FileWriterProxy attachment) {
                if (result >= 0) {
                    if (result > 0) {
                        writeToFile(channel, buffer, attachment);
                    }

                    buffer.clear();
                    channel.read(buffer, attachment, this);
                } else if (result < 0 || attachment.done()) {
                    onComplete(attachment);
                    close(channel, attachment);
                }
            }

            @Override
            public void failed(final Throwable exc, final FileWriterProxy attachment) {
                throw new RuntimeException("unable to read data", exc);
            }
        });
    }

    private void onComplete(final FileWriterProxy proxy) {
        assert !Objects.isNull(proxy);

        this.onFileComplete.onComplete(proxy);
    }

    private void meta(final AsynchronousSocketChannel channel) {
        assert !Objects.isNull(channel);

        final ByteBuffer buffer = ByteBuffer.allocate(Constants.BUFFER_SIZE);
        channel.read(buffer, new StringBuffer(), new CompletionHandler<Integer, StringBuffer>() {

            @Override
            public void completed(final Integer result, final StringBuffer attachment) {
                if (result < 0) {
                    close(channel, null);
                } else {

                    if (result > 0) {
                        attachment.append(new String(buffer.array()).trim());
                    }

                    if (attachment.toString().contains(Constants.END_MESSAGE_MARKER)) {
                        final FileMetaData metaData = FileMetaData.from(attachment.toString());
                        FileWriterProxy fileWriterProxy;

                        try {
                            fileWriterProxy = new FileWriterProxy(FileReceiverAsync.this.path, metaData);
                            confirm(channel, fileWriterProxy);
                        } catch (IOException e) {
                            close(channel, null);
                            throw new RuntimeException("unable to create file writer proxy", e);
                        }
                    } else {
                        buffer.clear();
                        channel.read(buffer, attachment, this);
                    }
                }
            }

            @Override
            public void failed(final Throwable exc, final StringBuffer attachment) {
                close(channel, null);
                throw new RuntimeException("unable to read meta data", exc);
            }
        });
    }

    private void confirm(final AsynchronousSocketChannel channel, final FileWriterProxy proxy) {
        assert !Objects.isNull(channel) && !Objects.isNull(proxy);

        final ByteBuffer buffer = ByteBuffer.wrap(Constants.CONFIRMATION.getBytes());
        channel.write(buffer, null, new CompletionHandler<Integer, Void>() {

            @Override
            public void completed(final Integer result, final Void attachment) {
                while (buffer.hasRemaining()) {
                    channel.write(buffer, null, this);
                }

                read(channel, proxy);
            }

            @Override
            public void failed(final Throwable exc, final Void attachment) {
                close(channel, null);
                throw new RuntimeException("unable to confirm", exc);
            }

        });
    }

    private void accept() {
        this.server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            public void completed(final AsynchronousSocketChannel channel, final Void attachment) {

                // Delegate off to another thread for the next connection.
                accept();

                // Delegate off to another thread to handle this connection.
                meta(channel);
            }

            public void failed(final Throwable exc, final Void attachment) {
                throw new RuntimeException("unable to accept new connection", exc);
            }
        });
    }

    private void writeToFile(final AsynchronousSocketChannel channel, final ByteBuffer buffer, final FileWriterProxy proxy) {
        assert !Objects.isNull(buffer) && !Objects.isNull(proxy) && !Objects.isNull(channel);

        try {
            buffer.flip();

            final long bytesWritten = proxy.getFileWriter().write(buffer, proxy.getPosition().get());
            proxy.getPosition().addAndGet(bytesWritten);
        } catch (IOException e) {
            close(channel, proxy);
            throw new RuntimeException("unable to write bytes to file", e);
        }
    }

    private void close(final AsynchronousSocketChannel channel, final FileWriterProxy proxy) {
        assert !Objects.isNull(channel);

        try {

            if (!Objects.isNull(proxy)) {
                proxy.getFileWriter().close();
            }
            channel.close();
        } catch (IOException e) {
            throw new RuntimeException("unable to close channel and FileWriter", e);
        }
    }
}
