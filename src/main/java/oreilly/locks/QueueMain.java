package oreilly.locks;

public class QueueMain {

    void main() {
        SimpleQueue q = new WaitingQueue();
//        SimpleQueue q = new ClassicQueue();
//        SimpleQueue q = new JUCQueue();
        Runnable source = () -> {
            for (var i = 0; i < 5000; i += 1) {
                try {
                    q.put("Item"+ i);
                } catch (InterruptedException _) {
                }
            }
        };

        Runnable sink = () -> {
            for (var i = 0; i < 5000; i += 1) {
                try {
                    Object o = q.take();
                    IO.println("Collected: "+ o);
                } catch (InterruptedException _) {
                }
            }
        };

        Thread t1 = new Thread(source);
        Thread t2 = new Thread(sink);

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
