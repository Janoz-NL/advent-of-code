package com.janoz.aoc.y2021.day21;

import java.util.Objects;

public class Player{

    final int position;
    final int score;

    public Player(int startPosition) {
        position = startPosition -1;
        score = 0;
    }

    public Player(int position, int score) {
        this.position = position;
        this.score = score;
    }

    Player moved(int amount) {
        int newPosition = (position + amount) % 10;
        return new Player(newPosition, score + newPosition + 1);
    }

    int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return position == player.position && score == player.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, score);
    }
}

