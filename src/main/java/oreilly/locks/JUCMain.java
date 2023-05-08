package oreilly.locks;

public class JUCMain {

    public static void main(String[] args) {
        SimpleBoundedQueue q = new JUCQueue();
        Runnable source = () -> {
            for (var i = 0; i < 5000; i += 1) {
                try {
                    q.put("Item"+ i);
                } catch (InterruptedException __) {
                }
            }
        };

        Runnable sink = () -> {
            for (var i = 0; i < 5000; i += 1) {
                try {
                    Object o = q.take();
                    System.out.println("Collected: "+ o);
                } catch (InterruptedException __) {
                }
            }
        };

    }

}
