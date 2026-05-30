package oreilly.patterns.refactor;

public final class EntityManager {

    public void persist(Item item) {
        IO.println(item.name() +" persisted");
    }
}
