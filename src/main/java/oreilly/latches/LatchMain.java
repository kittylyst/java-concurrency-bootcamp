package oreilly.latches;

import java.util.concurrent.CountDownLatch;

public class LatchMain {
    public static int THREAD_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        var latch = new CountDownLatch(THREAD_COUNT);

        Thread[] ts = new Thread[THREAD_COUNT];
        for (var i=0; i < THREAD_COUNT; i += 1) {
            ts[i] = new Thread(new LatchExample(latch));
            ts[i].start();
            Thread.yield();
        }

        latch.await();
        System.out.println("Joining");
        for (var i=0; i < THREAD_COUNT; i += 1) {
            ts[i].join();
        }
        System.out.println("Completed");
    }

}
