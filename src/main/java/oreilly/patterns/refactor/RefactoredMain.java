package oreilly.patterns.refactor;

import java.util.concurrent.Executors;

public class RefactoredMain {

    public static void main(String[] args) {
        var executor = Executors.newSingleThreadExecutor();
        var em = new EntityManager();

        for (var i = 0; i < 1000; i = i + 1) {
            var item = new Item("item"+ i);
            Runnable r = () -> em.persist(item);
            executor.submit(r);
        }
        executor.shutdown();
    }
}
