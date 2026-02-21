package oreilly.patterns.refactor;

public class EntityManager {

    public void persist(Item item) {
        IO.println(item.name() +" persisted");
    }
}
