package com.janoz.aoc.y2021.day13;

import java.util.function.Consumer;

public class Move {
    boolean horizontal;
    int position;

    public static Move parse(String s) {
        Move m = new Move();
        m.horizontal = s.charAt(11) == 'y';
        m.position = Integer.parseInt(s.substring(13));
        return m;
    }

    void doMove(Consumer<Integer> horizontalMove, Consumer<Integer> verticalMove) {
        if (horizontal) {
            horizontalMove.accept(position);
        } else {
            verticalMove.accept(position);
        }
    }

    @Override
    public String toString() {
        return "Move{" +
                "horizontal=" + horizontal +
                ", position=" + position +
                '}';
    }
}
