package oreilly.vthread;


import java.util.concurrent.atomic.AtomicInteger;

public class ExampleScopedRebind implements Runnable {
    private enum SecurityLevel { USER, ADMIN }

    private static final ScopedValue<SecurityLevel> securitySV = ScopedValue.newInstance();
    private static final ScopedValue<Integer> requestSV = ScopedValue.newInstance();

    private final AtomicInteger req = new AtomicInteger();

    public void run() {
        ScopedValue.where(securitySV, level())
                .where(requestSV, req.getAndIncrement())
                .run(() -> process());
    }

    private void process() {
        if (!securitySV.isBound()) {
            throw new RuntimeException("ScopedValue not bound - this should not happen");
        }

        var level = securitySV.get();
        if (level == SecurityLevel.USER) {
            System.out.println("User privileges granted for "+ requestSV.get() +" on: "+ Thread.currentThread());
        } else {
            System.out.println("Admin privileges requested for "+ requestSV.get() +" on: "+ Thread.currentThread());
            System.out.println("System is in lockdown. Falling back to user privileges");
            ScopedValue.where(securitySV, SecurityLevel.USER)
                    .run(() -> process());
        }

    }

    public SecurityLevel level() {
        if (Math.random() < 0.5) {
            return SecurityLevel.ADMIN;
        }
        return SecurityLevel.USER;
    }

    public static void main(String[] args) throws InterruptedException {
        var example = new ExampleScopedRebind();
        Thread t1 = new Thread(example);
        Thread t2 = new Thread(example);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Exiting");
    }
}
