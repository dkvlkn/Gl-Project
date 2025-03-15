package data.astar;

import java.util.PriorityQueue;

public class Queue {
    private PriorityQueue<ACell> queue;

    public Queue() {
        queue = new PriorityQueue<>((a, b) -> Double.compare(a.getCost(), b.getCost()));
    }

    public ACell handle() {
        return queue.poll();
    }

    public PriorityQueue<ACell> getQueue() {
        return queue;
    }
}