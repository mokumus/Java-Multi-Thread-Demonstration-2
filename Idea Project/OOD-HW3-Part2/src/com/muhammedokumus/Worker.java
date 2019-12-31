package com.muhammedokumus;

public class Worker implements Runnable {
    private final MatrixTS m1;
    private final MatrixTS m2;
    private final int rowStart;
    private final int workAmount;
    boolean done = false;

    public Worker(MatrixTS m1, MatrixTS m2, int rowStart, int workAmount) {
        this.m1 = m1;
        this.m2 = m2;
        this.rowStart = rowStart;
        this.workAmount = workAmount;
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
        synchronized (m1) {
            while(!done){
                m1.done = false;
                for(int j = rowStart; j < rowStart + workAmount; j++){
                    for(int k = 0; k < m1.getColSize(); k++){
                        m1.get()[j][k] += m2.get()[j][k];
                        System.out.println("========================");
                        System.out.println("Worker Name- " + Thread.currentThread().getName());
                        //m1.display();
                    }
                }
                m1.done = true;
                done = true;
            }
            m1.notify();
        }
    }
}
