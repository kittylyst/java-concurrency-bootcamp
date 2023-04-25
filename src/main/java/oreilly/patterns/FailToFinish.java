package oreilly.patterns;

import java.io.Closeable;
import java.io.IOException;

public class FailToFinish implements Closeable, Runnable {
    private boolean shutdown = false;

    @Override
    public void close() {
        shutdown = true;
    }

    @Override
    public void run() {
        while (!shutdown) {
            // do some work....
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                shutdown = true;
            }
        }
    }
}
