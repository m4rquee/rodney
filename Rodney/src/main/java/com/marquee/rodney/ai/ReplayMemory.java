package com.marquee.rodney.ai;

import java.util.*;
import java.util.stream.*;

public class ReplayMemory {

    private final int capacity;
    private int position;

    private final ArrayList<Transition> transitions;

    public ReplayMemory(int capacity) throws IllegalArgumentException {
        this.transitions = new ArrayList(capacity);
        this.capacity = capacity;
        this.position = 0;
    }

    public void push(Transition transition) {
        if (this.transitions.size() < this.capacity) {
            this.transitions.add(transition);
        } else {
            this.transitions.set(this.position, transition);
        }

        this.position = ++this.position % this.capacity;
    }

    public ArrayList<Transition> sample(int batchSize) throws IllegalArgumentException {
        if (batchSize < 0 || batchSize > this.capacity) {
            throw new IllegalArgumentException("Illegal size: " + batchSize);
        }

        Random random = new Random();

        return IntStream
                .generate(() -> random.nextInt(this.transitions.size()))
                .distinct()
                .limit(batchSize)
                .mapToObj(this.transitions::get)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int len() {
        return this.transitions.size();
    }
}
