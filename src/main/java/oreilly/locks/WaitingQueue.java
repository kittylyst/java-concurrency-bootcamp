package oreilly.locks;

import java.util.LinkedList;

public final class WaitingQueue implements SimpleQueue {
    LinkedList q = new LinkedList<>(); // storage

    public synchronized void put(Object o) {
        q.add(o);         // Append the object to the end of the list
        this.notifyAll(); // Tell waiting threads that data is ready
    }

    public synchronized Object take() {
        while (q.size() == 0) { // Empty condition
            try { this.wait(); }
            catch (InterruptedException _) {}
        }
        return q.get(q.size() - 1); // Return the head of the queue
    }
}