package oreilly.locks;

import java.util.concurrent.Semaphore;

public class SemaphoreMain {
    public static int THREAD_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        var sem = new Semaphore(3);

        Thread[] ts = new Thread[THREAD_COUNT];
        for (var i=0; i < THREAD_COUNT; i += 1) {
            ts[i] = new Thread(new SemaExample(sem));
            ts[i].start();
            Thread.yield();
        }
        for (var i=0; i < THREAD_COUNT; i += 1) {
            ts[i].join();
        }
        System.out.println(sem.availablePermits());
    }

}
