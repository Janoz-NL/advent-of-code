package com.janoz.aoc.y2021.day5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Automatically growing 2D field
 * @param <T> type of gridpoints
 */
public class Field<T> {

    int width=0;
    List<List<T>> rows = new ArrayList<>();

    private T emptyValue;

    public Field(T emptyValue) {
        this.emptyValue = emptyValue;
    }

    public void put(Point p, T value) {
        resizeTo(p);
        rows.get(p.y).set(p.x, value);
    }

    public T get(Point p) {
        resizeTo(p);
        return rows.get(p.y).get(p.x);
    }

    private void resizeTo(Point p) {
        if (p.y >= rows.size()) {
            for (int i = rows.size(); i<= p.y; i++) {
                rows.add(new ArrayList<>());
            }
        }
        List<T> col = rows.get(p.y);
        if (p.x >= col.size()) {
            for (int i = col.size(); i<= p.x; i++) {
                col.add(emptyValue);
            }
            width = Math.max(width, p.x+1);
        }
    }

    /**
     * @param successFilter empty fields should never match successfilter
     * @return Return the amount of gridpoints matching the successfilter
     */
    public long getScore(Predicate<T> successFilter) {
        return rows.stream().flatMap(Collection::stream).filter(successFilter).count();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return rows.size();
    }

    public void print(String format) {
        System.out.println();
        rows.forEach(cols -> {
                    cols.forEach(i -> System.out.printf(format,i));
                    for (int i=cols.size(); i<width; i++) System.out.printf(format, emptyValue);
                    System.out.println();
                }
        );
    }

}
