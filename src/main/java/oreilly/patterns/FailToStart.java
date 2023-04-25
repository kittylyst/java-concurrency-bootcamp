package oreilly.patterns;

public class FailToStart {
    public static void main(String[] args) {
        Runnable r = () -> {
            while(true){
                System.out.println("Thread: "+
                        Thread.currentThread());
            }
        };
        var t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }
}
