package com.janoz.aoc.y2022.day5;

import java.util.Optional;

public class Crate {
    char c;
    private Crate next;
    private Crate prev;
    boolean reverse = false;

    Crate(char c) {
        this.c = c;
    }


    Crate getNext() {
        return reverse?prev:next;
    }

    Crate getPrevious() {
        return reverse?next:prev;
    }

    void setNext(Crate next) {
        Optional.ofNullable(next).ifPresent(c-> c.setPrevious(this));
        if (reverse) {
            prev = next;
        } else {
            this.next = next;
        }
    }


    private void setPrevious(Crate previous) {
        if (reverse) {
            next = previous;
        } else {
            prev = previous;
        }
    }



}
