package com.Java8.NIO.remote;

@FunctionalInterface
public interface OnComplete {

    void onComplete(FileWriterProxy fileWriter);
}
