package oreilly.locks;

import java.util.LinkedList;

public final class WaitingQueue {
    LinkedList q = new LinkedList<Object>(); // storage

    public synchronized void put(Object o) {
        q.add(o);         // Append the object to the end of the list
        this.notifyAll(); // Tell waiting threads that data is ready
    }

    public synchronized Object take() {
        while(q.size() == 0) {
            try { this.wait(); }
            catch (InterruptedException ignore) {}
        }
        return q.remove(); // Return the head of the queue
    }
}