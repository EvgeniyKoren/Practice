package com.company.multithreading;

import com.company.multithreading.threads.MainThread;

public class ByteSequence {

    public static void main(String[] args) {
        MainThread mainThread = new MainThread();
        mainThread.start();
    }
}
