package com.janoz.aoc.y2022.day13;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class Package implements Comparable<Package> {

    private final List<ListOrInt> contents;

    public Package(String data) {
        contents = parseList(new CharIterator(data));
    }

    public String asString() {
        return asString(contents);
    }

    private String asString(List<ListOrInt> l) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i=0; i<l.size(); i++) {
            if (i!=0) {
                sb.append(',');
            }
            if (!l.get(i).isList()) {
                sb.append(l.get(i).getInt());
            } else {
                sb.append(asString(l.get(i).getList()));
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private List<ListOrInt> parseList(CharIterator it) {
        List<ListOrInt> currentList = new ArrayList<>();
        it.pop(); // pop [
        while (it.peek()!=']') {
            if (it.peek() == '[') {
                currentList.add(new ListOrInt(parseList(it)));
            } else {
                currentList.add(new ListOrInt(it.popInt()));
            }
            if (it.peek() == ',') it.pop();
        }
        it.pop();
        return currentList;
    }

    @Override
    public int compareTo(Package o) {
        return compare(new ArrayDeque<>(contents), new ArrayDeque<>(o.contents));
    }

    private static int compare(Queue<ListOrInt> firstQ, Queue<ListOrInt> secondQ ) {
        while (!firstQ.isEmpty()) {
            if (secondQ.isEmpty()) {
                return 1;
            }
            ListOrInt first = firstQ.poll();
            ListOrInt second = Objects.requireNonNull(secondQ.poll());
            int result;
            if (first.isList()) {
                result = compare(new ArrayDeque<>(first.getList()), new ArrayDeque<>(second.isList() ? second.getList() : Collections.singleton(second)));
            } else if (second.isList()) {
                result = compare(new ArrayDeque<>(Collections.singleton(first)), new ArrayDeque<>(second.getList()));
            } else {
                result = first.getInt() - second.getInt();
            }
            if (result != 0) return result;
        }
        if (secondQ.isEmpty()) {
            return 0;
        } else {
            return -1;
        }
    }

    private static class CharIterator {
        int curpos;
        String data;

        CharIterator(String data) {
            this.data = data;
            curpos = 0;
        }
        char peek() {
            return data.charAt(curpos);
        }

        char pop() {
            return data.charAt(curpos++);
        }

        int popInt() {
            int i=0;
            while (peek() >= '0' && peek() <= '9') {
                i = i * 10;
                i = i + (pop() - '0');
            }
            return i;
        }
    }

    private static class ListOrInt {
        private int i;
        private List<ListOrInt> l;

        ListOrInt(List<ListOrInt> l) {
            this.l = l;
        }

        ListOrInt(int i) {
            this.i = i;
        }

        boolean isList() {
            return l!=null;
        }

        public int getInt() {
            return i;
        }

        public List<ListOrInt> getList() {
            return l;
        }
    }
}
