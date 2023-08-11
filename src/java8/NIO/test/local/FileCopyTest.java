package java8.NIO.test.local;

import java8.NIO.local.FileCopy;
import java8.NIO.test.AbstractTest;
import org.junit.Test;

import java.io.IOException;

public class FileCopyTest extends AbstractTest {

    @Test
    public void copyLargeFile() throws IOException {
        FileCopy.copy(SRC, TARGET);

        compare();
    }
}
