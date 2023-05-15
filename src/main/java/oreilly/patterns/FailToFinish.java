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
        var i = 0;
        while (!shutdown) {
            System.out.println("Looping... "+ i);
            i += 1;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                shutdown = true;
            }
        }
    }
}
