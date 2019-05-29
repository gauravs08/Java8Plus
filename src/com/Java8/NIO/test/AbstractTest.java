package com.Java8.NIO.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractTest {

    protected static final String SRC = "D:/Workspace/tmp/input.tar.gz";
    protected static final String TARGET = "D:/Workspace/tmp/output";
    protected static final long AWAIT_TEST_COMPLETE = 20000l;

    protected File srcFile;
    protected File targetFile;

    @BeforeClass
    public static void init() {
        Thread.currentThread().getContextClassLoader().setDefaultAssertionStatus(true);
    }

    @AfterClass
    public static void destroy() {
        Thread.currentThread().getContextClassLoader().setDefaultAssertionStatus(false);
    }

    @Before
    public void setUp() throws IOException {
        this.srcFile = new File(SRC);
        this.targetFile = new File(TARGET + "/" + this.srcFile.getName());

        Files.deleteIfExists(this.targetFile.toPath());
    }

    protected final void compare() {
        assertEquals("file did not copy completely", this.srcFile.length(), this.targetFile.length());
    }
}
