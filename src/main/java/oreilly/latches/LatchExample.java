package oreilly.latches;

import java.util.concurrent.CountDownLatch;

public class LatchExample implements Runnable {
    private final CountDownLatch latch;

    public LatchExample(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        //Call an API
        try {
            Thread.sleep((long)(1000 * Math.random()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Thread.currentThread().getName() + " Done API Call");
        try {
            latch.countDown();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " Continue processing");
    }
}
