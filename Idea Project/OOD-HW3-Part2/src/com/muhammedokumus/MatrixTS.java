package com.muhammedokumus;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MatrixTS implements Runnable {
    private final Matrix m = new Matrix();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();
    boolean done = false;

    public MatrixTS(float[][] m) {
        r.lock();
        try {
            this.m.reInit(m);
        }
        finally { r.unlock(); }
    }

    public MatrixTS() {

    }

    public float[][] get() {
        r.lock();
        try {
            System.out.println("Current Thread Name- " + Thread.currentThread().getName() + " | Operation: get()");
            return m.getM();
        }
        finally { r.unlock(); }
    }

    public int getRowSize() {
        r.lock();
        try {
            System.out.println("Current Thread Name- " + Thread.currentThread().getName() + " | Operation: getRowSize()");
            return m.getRowSize();
        }
        finally { r.unlock(); }
    }

    public int getColSize() {
        r.lock();
        try {
            System.out.println("Current Thread Name- " + Thread.currentThread().getName() + " | Operation: getColSize()");
            return m.getColSize();
        }
        finally { r.unlock(); }
    }

    public void display() {
        r.lock();
        try {
            System.out.println("Current Thread Name- " + Thread.currentThread().getName() + " | Operation: display()");
            m.display();
        }
        finally { r.unlock(); }
    }


    public void reInit(float[][] m){

        r.lock();
        try {
            System.out.println("Current Thread Name- " + Thread.currentThread().getName() + " | Operation: reInit()");
            this.m.reInit(m);
        }
        finally { r.unlock(); }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        // Getting thread's name
        System.out.println("Current Thread Name- " + Thread.currentThread().getName());
        // Getting thread's ID
        System.out.println("Current Thread ID- " + Thread.currentThread().getId() + " For Thread- " + Thread.currentThread().getName());
    }
}
