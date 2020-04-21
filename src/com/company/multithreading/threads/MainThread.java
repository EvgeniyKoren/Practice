package com.company.multithreading.threads;

import java.util.Scanner;

public class MainThread extends Thread {

    private boolean flag = true;
    //    public List<Integer> lengths = new ArrayList<>();
    private int currentLength;

    public synchronized void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
        notify();
    }

    public String getFileName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file name:");
        String fileName = null;
        if(scanner.hasNextLine()) {
            fileName = scanner.nextLine();
        }
        if ("stop".equalsIgnoreCase(fileName)) {
            this.interrupt();
            flag = false;
        }
        return fileName;
    }

    @Override
    public void run() {
        String fileName = getFileName();
        Helper helper = new Helper(fileName, this);
        helper.setDaemon(true);
        helper.start();
        while(flag) {
           if (helper.getState() == State.RUNNABLE) {
               System.out.println("Current length of sequence is: " + currentLength);
           } else {
               System.out.println("The longest repeating sequence is " + helper.getLongestRepeatedSeq());
//               fileName = getFileName();
               helper.setFileName(getFileName());
           }

        }
    }
}
