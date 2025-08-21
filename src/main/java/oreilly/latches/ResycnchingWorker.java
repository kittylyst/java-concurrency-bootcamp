package oreilly.latches;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public final class ResycnchingWorker implements Runnable {
    private final CyclicBarrier barrier;

    public ResycnchingWorker(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        //Call an API

        System.out.println(System.currentTimeMillis() +": "+ Thread.currentThread().getName() + " Done API Call");
        try {
            barrier.await();
            try {
                Thread.sleep((long)(1000 * Math.random()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        System.out.println(System.currentTimeMillis() +": "+Thread.currentThread().getName() + " Continue processing");
    }
}
