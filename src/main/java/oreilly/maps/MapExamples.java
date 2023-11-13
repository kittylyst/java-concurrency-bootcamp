package oreilly.maps;

import java.util.Map;

public class MapExamples {
    public static void main(String[] args) {
        Map<String, String> map = new SimpleDict();
        int SIZE = 100;

        Runnable r1 = () -> {
          for (int i = 0; i < SIZE; i = i + 1) {
              map.put("t1" + i, "0");
              if (i % 10_000 == 0) {
                  System.out.println("t1: " + i);
              }
          }
          System.out.println("Thread 1 done");
        };
        Runnable r2 = () -> {
            for (int i = 0; i < SIZE; i = i + 1) {
                map.put("t2" + i, "0");
                if (i % 10_000 == 0) {
                    System.out.println("t2: " + i);
                }
            }
            System.out.println("Thread 2 done");
        };
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException __) {
            System.err.println("Threads interrupted");
        }
        System.out.println("Lost Updates: "+ (2 * SIZE - map.size()));
    }
}
