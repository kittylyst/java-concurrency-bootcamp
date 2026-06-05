package oreilly.patterns;

public class FailToFinishMain {

    void main() throws InterruptedException {
        Thread t;
        try (var f2f = new FailToFinish()) {
            t = new Thread(f2f);
            t.start();
            Thread.sleep(500);
        } // f2f.close() is called from the main thread
        t.join();
    }

}
