package com.janoz.aoc.y2021.day18;

public class Tokenizer {

    int pos;
    String content;

    public Tokenizer(String content) {
        this.content = content;
        this.pos = 0;
    }

    boolean hasNext() {
        return pos < content.length();
    }

    char peek() {
        return content.charAt(pos);
    }

    char get() {
        return content.charAt(pos++);
    }
}
