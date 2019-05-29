package com.Java8.NIO.remote;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;

final class FileWriterProxy {

    private final FileWriter fileWriter;
    private final AtomicLong position;
    private final long size;
    private final String fileName;

    FileWriterProxy(final String path, final FileMetaData metaData) throws IOException {
        assert !Objects.isNull(metaData) && StringUtils.isNotEmpty(path);

        this.fileWriter = new FileWriter(path + "/" + metaData.getFileName());
        this.position = new AtomicLong(0l);
        this.size = metaData.getSize();
        this.fileName = metaData.getFileName();
    }
    
    String getFileName() {
        return this.fileName;
    }

    FileWriter getFileWriter() {
        return this.fileWriter;
    }
    
    AtomicLong getPosition() {
        return this.position;
    }
    
    boolean done() {
        return this.position.get() == this.size;
    }
}
