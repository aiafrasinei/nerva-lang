package org.nerva.model.containers;

import org.nerva.model.Block;

import java.util.ArrayDeque;
import java.util.Deque;

public class Blocks {
    private final Deque<Block> blockStack = new ArrayDeque<>();

    public void push(Block block) {
        blockStack.push(block);
    }

    public void pop() {
        blockStack.pop();
    }

    public Block getFirst() {
        return blockStack.peekFirst();
    }

    public Block getLast() {
        return blockStack.peekLast();
    }
}
