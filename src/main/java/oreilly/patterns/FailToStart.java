package oreilly.patterns;

import java.util.concurrent.CountDownLatch;

public class FailToStart {
    void main() throws InterruptedException {
        var cdl = new CountDownLatch(1);
        Runnable r = () -> {
            int i = 0;
            while(true){
                IO.println("Thread: "+
                        Thread.currentThread() +" "+ i++);
                if (i > 1000) {
                    cdl.countDown();
                    break;
                }
            }
        };
        var t = new Thread(r);
        t.setDaemon(true);
        t.start();
        cdl.await();
        IO.println("Done");
//        Thread.sleep(2);
    }
}
