package java8.NIO.remote;

import java8.NIO.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

final class FileSender {

    private final InetSocketAddress hostAddress;
    private SocketChannel client;

    FileSender(final int port) throws IOException {
        this.hostAddress = new InetSocketAddress(port);
        this.client = SocketChannel.open(this.hostAddress);
    }

    void transfer(final FileChannel channel, long position, long size) throws IOException {
        assert !Objects.isNull(channel);

        while (position < size) {
            position += channel.transferTo(position, Constants.TRANSFER_MAX_SIZE, this.client);
        }
    }

    SocketChannel getChannel() {
        return this.client;
    }

    void close() throws IOException {
        this.client.close();
    }
}
