package com.janoz.aoc.y2022.day13;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class Package implements Comparable<Package> {

    private List<Object> contents;

    public Package(String data) {
        contents = parseList(new CharIterator(data));
    }

    public String asString() {
        return asString(contents);
    }

    private String asString(List<Object> l) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i=0; i<l.size(); i++) {
            if (i!=0) {
                sb.append(',');
            }
            if (l.get(i) instanceof Integer) {
                sb.append((int)l.get(i));
            } else {
                sb.append(asString((List<Object>)l.get(i)));
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private List<Object> parseList(CharIterator it) {
        List<Object> currentList = new ArrayList<>();
        it.pop(); // pop [
        while (it.peek()!=']') {
            if (it.peek() == '[') {
                currentList.add(parseList(it));
            } else {
                currentList.add(it.popInt());
            }
            if (it.peek() == ',') it.pop();
        }
        it.pop();
        return currentList;
    }

    @Override
    public int compareTo(Package o) {
        return inOrder(new ArrayDeque<>((List<Object>) contents), new ArrayDeque<>((List<Object>) o.contents));
    }

    private static int inOrder(Queue<Object> firstQ, Queue<Object> secondQ ) {
        while (!firstQ.isEmpty()) {
            if (secondQ.isEmpty()) {
                return 1;
            }
            if (firstQ.peek() instanceof Integer) {
                int fi = (Integer) firstQ.poll();
                if (secondQ.peek() instanceof Integer) {
                    int si = (Integer) secondQ.poll();
                    if (fi != si) {
                        return fi - si;
                    }
                } else {
                    int result = inOrder(new ArrayDeque<>(Collections.singleton(fi)), new ArrayDeque<>((List<Object>) secondQ.poll()));
                    if (result != 0) return result;
                }
            } else {
                Queue<Object> firstChildQ = new ArrayDeque<>((List<Object>) firstQ.poll());
                if (secondQ.peek() instanceof Integer) {
                    int result = inOrder(firstChildQ, new ArrayDeque<>(Collections.singleton((Integer) secondQ.poll())));
                    if (result != 0) return result;
                } else {
                    int result = inOrder(firstChildQ, new ArrayDeque<>((List<Object>) secondQ.poll()));
                    if (result != 0) return result;
                }
            }
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
}
