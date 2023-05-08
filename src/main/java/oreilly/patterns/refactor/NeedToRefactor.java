package oreilly.patterns.refactor;

import java.io.Closeable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class NeedToRefactor implements Closeable, Runnable {

    private final BlockingQueue<Item> tasks;
    private volatile boolean shutdown = false;

    private final EntityManager em = new EntityManager(); // or injected

    public NeedToRefactor(BlockingQueue<Item> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void close() {
        shutdown = true;
    }

    @Override
    public void run() {
        while (!shutdown) {
            try {
                var item = tasks.poll(1, TimeUnit.SECONDS);
                if (item != null) {
                    em.persist(item);
                }
            } catch (InterruptedException e) {
                shutdown = true;
            }
        }
    }


}
