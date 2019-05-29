package com.Java8.NIO.remote;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

final class FileReader {

    private final FileChannel channel;
    private final FileSender sender;

    FileReader(final FileSender sender, final String path) throws IOException {
        if (Objects.isNull(sender) || StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("sender and path required");
        }

        this.sender = sender;
        this.channel = FileChannel.open(Paths.get(path), StandardOpenOption.READ);
    }

    void read() throws IOException {
        try {
            transfer();
        } finally {
            close();
        }
    }

    void close() throws IOException {
        this.sender.close();
        this.channel.close();
    }

    private void transfer() throws IOException {
        this.sender.transfer(this.channel, 0l, this.channel.size());
    }
}
