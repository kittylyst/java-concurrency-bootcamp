package oreilly.patterns.refactor;

import java.util.concurrent.LinkedBlockingQueue;

public class NTRMain {
    public static void main(String[] args) {
        var bq = new LinkedBlockingQueue<Item>();
        for (var i = 0; i < 1000; i = i + 1) {
            var item = new Item("item"+ i);
            bq.add(item);
        }

        var ntr = new NeedToRefactor(bq);
        var processor = new Thread(ntr);
        processor.start();
        try {
            while (bq.size() > 0) {
                Thread.sleep(1);
            }
            ntr.close();
            processor.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
