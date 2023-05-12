package oreilly.latches;

import java.util.concurrent.CountDownLatch;

public class LatchMain {

    public static void main(String[] args) throws InterruptedException {
        var latch = new CountDownLatch(3);

        Thread[] ts = new Thread[3];
        for (var i=0; i < 3; i += 1) {
            ts[i] = new Thread(new LatchExample(latch));
            ts[i].start();
            Thread.yield();
        }

        latch.await();
        System.out.println("Joining");
        for (var i=0; i < 3; i += 1) {
            ts[i].join();
        }
        System.out.println("Completed");
    }

}
