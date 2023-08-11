package java8.NIO.remote;

import java8.NIO.Constants;
import org.apache.commons.lang3.StringUtils;

final class FileMetaData {

    private final String fileName;
    private final long size;

    private FileMetaData(final String fileName, final long size) {
        assert StringUtils.isNotEmpty(fileName);

        this.fileName = fileName;
        this.size = size;
    }

    static FileMetaData from(final String request) {
        assert StringUtils.isNotEmpty(request);

        final String[] contents = request.replace(Constants.END_MESSAGE_MARKER, StringUtils.EMPTY).split(Constants.MESSAGE_DELIMITTER);
        return new FileMetaData(contents[0], Long.valueOf(contents[1]));
    }

    String getFileName() {
        return this.fileName;
    }

    long getSize() {
        return this.size;
    }
}
