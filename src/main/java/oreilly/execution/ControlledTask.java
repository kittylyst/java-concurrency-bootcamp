package oreilly.execution;

import java.io.IOException;

public class ControlledTask implements Runnable, AutoCloseable {

    private volatile boolean shutdown = false;

    public void shutdown() {
        shutdown = true;
    }

    @Override
    public void run() {
        while (!shutdown) {
            doSomeTask();
        }
    }

    void doSomeTask() {
        // Process a work item
    }

    @Override
    public void close() throws IOException {
        shutdown();
    }
}
