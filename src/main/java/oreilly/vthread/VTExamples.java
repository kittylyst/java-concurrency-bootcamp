package oreilly.vthread;

public class VTExamples {

    public static final int REPS = 10_000_000;

    void main() {
        Thread.Builder tb = Thread.ofVirtual();
        tb.name("MyVThread");
//        tb.factory();
        Thread t = tb.unstarted(() -> IO.println(Thread.currentThread()+ ": Hello World!"));
        IO.println(t); // new state
        t.start();
        try {
            t.join();
        } catch (InterruptedException _) {

        }
    }

}
