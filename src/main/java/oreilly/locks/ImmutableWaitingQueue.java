package oreilly.locks;

import java.util.LinkedList;

public final class ImmutableWaitingQueue<E> {
    LinkedList<E> q = new LinkedList<>(); // storage

    public synchronized ImmutableWaitingQueue<E> put(E o) {
        q.add(o);         // Append the object to the end of the list
        this.notifyAll();// Tell waiting threads that data is ready
        return this;
    }

    public synchronized E head() {
        while(q.size() == 0) {
            try { this.wait(); }
            catch (InterruptedException ignore) {}
        }
        return q.getFirst(); // Return the head of the queue
    }

    public synchronized ImmutableWaitingQueue<E> tail() {
        while(q.size() == 0) {
            try { this.wait(); }
            catch (InterruptedException ignore) {}
        }
        q.removeFirst();
        return this;
    }
}