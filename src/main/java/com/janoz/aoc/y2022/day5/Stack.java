package com.janoz.aoc.y2022.day5;

public class Stack {

    Crate top;
    Crate bottom = null;

    void add(Crate crate) {
        if (bottom == null) {
            bottom = crate;
        }
        crate.setNext(top);
        top = crate;
    }

    void append(Crate crate) {
        if (bottom == null) {
            add(crate);
        } else {
            bottom.setNext(crate);
            bottom = crate;
        }
    }

    void reverse() {
        Crate c = top;
        top = bottom;
        bottom = c;
        do {
            c.reverse = !c.reverse;
        } while ((c = c.getPrevious())!=null);
    }

    /**
     * @param stack non empty stack
     */
    void add(Stack stack) {
        stack.bottom.setNext(top);
        top = stack.top;
    }

    Crate get() {
        Crate result = top;
        top = result.getNext();
        return result;
    }

    Stack get(int amount) {
        Stack result = new Stack();
        result.top = top;
        result.bottom = top;
        for (int i=1; i<amount; i++) {
            result.bottom = result.bottom.getNext();
        }
        top = result.bottom.getNext();
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Crate c = top;
        while (c!= null) {
            sb.append(c.c);
            c = c.getNext();
        }
        return sb.toString();
    }
}
