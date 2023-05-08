package oreilly.patterns.refactor;

public class EntityManager {

    public void persist(Item item) {
        System.out.println(item.name() +" persisted");
    }
}
