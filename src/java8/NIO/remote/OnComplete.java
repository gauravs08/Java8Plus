package java8.NIO.remote;

@FunctionalInterface
public interface OnComplete {

    void onComplete(FileWriterProxy fileWriter);
}
