package dev.fumaz.particlecreator.util;

import java.util.Stack;

public class SizedStack<T> extends Stack<T> {

    private final int maxSize;

    public SizedStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public T push(T object) {
        // If the stack is too big, remove elements until it's the right size
        while (this.size() >= maxSize) {
            this.remove(0);
        }

        return super.push(object);
    }

    /**
     * @return the maximum possible size of the stack
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * @return the current size of the stack, which may be different from its maximum size
     */
    @Override
    public synchronized int size() {
        return super.size();
    }

}