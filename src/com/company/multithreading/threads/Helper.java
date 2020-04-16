package com.company.multithreading.threads;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Helper extends Thread {

    private String longestRepeatedSeq;
    private String content;
    private String fileName;
    private MainThread mainThread;

    public Helper(String file, MainThread mainThread) {
        this.fileName = file;
        this.mainThread = mainThread;
    }

    public synchronized void setFileName(String fileName) {
        this.fileName = fileName;
        notify();
    }

    public String getContent() {
        return content;
    }

    public String getLongestRepeatedSeq() {
        return longestRepeatedSeq;
    }

    private static byte[] readByteFromFile(File name) throws IOException {
        return Files.readAllBytes(name.toPath());
    }

    private static String largestCommonPrefix(String s, String t) {
        int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i)) {
                return s.substring(0, i);
            }
        }
        return s.substring(0, n);
    }

    private static String longestRepeatingSequence(String string, MainThread mainThread) {
        String lrs = "";
        int n = string.length();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                //Checks for the largest common factors in every substring
                String x = largestCommonPrefix(string.substring(i, n), string.substring(j, n));
//                System.out.println("Current sequence is: " + x);
                //If the current prefix is greater than previous one
                //then it takes the current one as longest repeating sequence
                if (x.length() > lrs.length()) {
                    lrs = x;
//                    System.out.println("index = " + i);
                    mainThread.setCurrentLength(lrs.length());
                    System.out.println("Helper thread: lrs = " + lrs);
                }
            }
        }
        return lrs;
    }

    @Override
    public void run() {
        while (true) {
            File file = new File(fileName);
            try {
                content = new String(readByteFromFile(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            longestRepeatedSeq = longestRepeatingSequence(content, mainThread);
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
