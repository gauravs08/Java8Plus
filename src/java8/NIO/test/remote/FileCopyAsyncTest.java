package java8.NIO.test.remote;

import java8.NIO.Constants;
import java8.NIO.test.AbstractTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

public class FileCopyAsyncTest extends AbstractTest {

    private static final int PORT = 9999;

    private static final String LARGE_FILE_ONE = "/tmp/input1.tar.gz";
    private static final String LARGE_FILE_TWO = "/tmp/input2.tar.gz";

    @Before
    @Override
    public void setUp() throws IOException {
        final File fileOne = new File(LARGE_FILE_ONE);
        final File fileTwo = new File(LARGE_FILE_TWO);

        final File targetFileOne = new File(TARGET + "/" + fileOne.getName());
        final File targetFileTwo = new File(TARGET + "/" + fileTwo.getName());

        Files.deleteIfExists(targetFileOne.toPath());
        Files.deleteIfExists(targetFileTwo.toPath());
    }


    @Test
    public void copyTwoLargeFilesConcurrently() throws IOException, InterruptedException {
        final CountDownLatch serverReady = new CountDownLatch(1);
        final CountDownLatch jobsLatch = new CountDownLatch(2);
        final AtomicBoolean pass = new AtomicBoolean(Boolean.FALSE);

        final FileReceiverAsync fileReceiverAsync = new FileReceiverAsync(PORT, 2, TARGET, new OnComplete() {

            @Override
            public void onComplete(final FileWriterProxy fileWriter) {
                pass.set((fileWriter.done() && (LARGE_FILE_ONE.contains(fileWriter.getFileName()) || LARGE_FILE_TWO.contains(fileWriter.getFileName()))));
                jobsLatch.countDown();
            }
        });

        new Thread() {
            public void run() {
                fileReceiverAsync.start();
                serverReady.countDown();
            }
        }.start();

        serverReady.await();

        runClient(LARGE_FILE_ONE);
        runClient(LARGE_FILE_TWO);

        jobsLatch.await();
        fileReceiverAsync.stop(0l);

        assertTrue(pass.get());
    }

    private void runClient(final String srcPath) {
        new Thread() {
            public void run() {
                try {
                    final File srcFile = new File(srcPath);

                    final FileSender sender = new FileSender(PORT);
                    final FileReader reader = new FileReader(sender, srcPath);
                    final TestAsyncClient helper = new TestAsyncClient(sender, reader, srcFile.getName(), srcFile.length());
                    helper.run();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }

    private final class TestAsyncClient implements Runnable {

        private final FileSender sender;
        private final FileReader reader;
        private final long size;
        private final String fileName;

        private TestAsyncClient(final FileSender sender, final FileReader reader, final String fileName, final long size) {
            assert !Objects.isNull(sender) && !Objects.isNull(reader) && !Objects.isNull(fileName);

            this.sender = sender;
            this.reader = reader;
            this.size = size;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try {
                negotiate();
                confirmAndGo(StringUtils.EMPTY);
            } catch (IOException e) {
                throw new RuntimeException("unable to run", e);
            } finally {
                try {
                    this.reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("unable to close reader", e);
                }
            }
        }

        private void negotiate() throws IOException {
            final String message = this.fileName + Constants.MESSAGE_DELIMITTER + String.valueOf(this.size) + Constants.END_MESSAGE_MARKER;
            final ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());

            while (buffer.hasRemaining()) {
                this.sender.getChannel().write(buffer);
            }
        }

        private void confirmAndGo(final String response) throws IOException {

            if (!response.contains(Constants.CONFIRMATION)) {
                final ByteBuffer buffer = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                final long bytesRead = this.sender.getChannel().read(buffer);

                if (bytesRead > 0) {
                    confirmAndGo(response + new String(buffer.array()));
                } else if (bytesRead < 0) {
                    this.reader.close();
                }
            } else {
                this.reader.read();
            }
        }
    }
}
