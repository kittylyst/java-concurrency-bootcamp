package oreilly.patterns;

import java.util.concurrent.CountDownLatch;

public class FailToStart {
    public static void main(String[] args) throws InterruptedException {
        var cdl = new CountDownLatch(1);
        Runnable r = () -> {
            int i = 0;
            while(true){
                System.out.println("Thread: "+
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
        System.out.println("Done");
//        t.yield();
//        Thread.sleep(2);
    }
}
