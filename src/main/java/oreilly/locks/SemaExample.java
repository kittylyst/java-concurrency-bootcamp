package oreilly.locks;

import java.util.concurrent.Semaphore;

public class SemaExample implements Runnable {
    private final Semaphore sem;

    public SemaExample(Semaphore sem) {
        this.sem = sem;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("Starting on thread: "+ name);
        try {
            sem.acquire();
            System.out.println("Acquired on thread: "+ name);
            Thread.sleep((long) (100 * Math.random()));
            sem.release();
//            sem.release();
        } catch (InterruptedException e) {
            System.out.println("Interrupted on "+ name);
        }
        System.out.println("Finished on "+ name);
    }
}
