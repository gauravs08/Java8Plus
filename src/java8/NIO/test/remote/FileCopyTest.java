package java8.NIO.test.remote;

import java8.NIO.test.AbstractTest;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class FileCopyTest extends AbstractTest {

    private static final int PORT = 9999;

    @Test
    public void copyLargeFile() throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final FileReceiver receiver = new FileReceiver(PORT, new FileWriter(TARGET + "/" + super.srcFile.getName()), super.srcFile.length());

        new Thread() {
            public void run() {
                try {
                    receiver.receive();
                } catch (IOException e) {

                } finally {
                    latch.countDown();
                }
            }
        }.start();

        final FileReader reader = new FileReader(new FileSender(PORT), SRC);
        reader.read();

        latch.await();
        compare();
    }
}
