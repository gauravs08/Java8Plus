package com.Java8.NIO.test.local;

import java.io.IOException;

import org.junit.Test;

import com.Java8.NIO.local.FileCopy;
import com.Java8.NIO.test.AbstractTest;

public class FileCopyTest extends AbstractTest {

    @Test
    public void copyLargeFile() throws IOException {
        FileCopy.copy(SRC, TARGET);

        compare();
    }
}
