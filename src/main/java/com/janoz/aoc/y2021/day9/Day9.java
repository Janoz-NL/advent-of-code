package com.janoz.aoc.y2021.day9;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.collections.MergingMap;
import com.janoz.aoc.geo.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day9 {

    public static void main(String[] args) {
        Day9 d = fromFile("inputs/day9.txt");
        d.initValeys();
        System.out.println(d.scorePart1());
        d.createBasinIds();
        System.out.println(d.scorePart2());

        d = fromFile("inputs/day9.txt");
        d.createBasinIds2();
        System.out.println(d.scorePart2b());
    }


    Set<Point> valeys;
    int basinAmount;
    int width;
    int height;
    int[][] field;
    int[][] basinIds;

    Day9(int[][] field) {
        this.width = field[0].length;
        this.height= field.length;
        this.field = field;
    }



    public int scorePart1() {
        int answer = 0;
        for (Point p : valeys) {
            answer += get(p.x,p.y)+1;
        }
        return answer;
    }

    public int scorePart2() {
        int[] histogram = new int[basinAmount+1];
        Arrays.stream(basinIds).flatMapToInt(Arrays::stream).forEach(i -> histogram[i]++);
        Arrays.sort(histogram);
        return histogram[basinAmount-1] * histogram[basinAmount-2] * histogram[basinAmount-3];
    }

    public int scorePart2b() {
        int[] histogram = new int[basinAmount+1]; // way to big
        Arrays.stream(basinIds).flatMapToInt(Arrays::stream).map(mm::getActual).forEach(i -> histogram[i]++);
        Arrays.sort(histogram);
        return histogram[basinAmount-1] * histogram[basinAmount-2] * histogram[basinAmount-3];
    }

    public int[][] createBasinIds() {
        basinIds = createMap(width,height);
        int basinId = 0;
        for (Point p:valeys) {
            if (basinIds[p.y][p.x] == 0) {
                fill(p.x,p.y, ++basinId);
            }
        }
        basinAmount = basinId;
        return basinIds;
    }

    MergingMap mm = new MergingMap();

    public int[][] createBasinIds2() {
        basinIds = createMap(width,height);
        basinAmount = 0;
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                if (get(x,y) != 9) {
                    int top = get(x,y-1);
                    int left = get(x-1,y);
                    if (top == 9 && left == 9) {
                        basinIds[y][x] = ++basinAmount;
                    } else {
                        if (left != 9) {
                            basinIds[y][x] = basinIds[y][x-1];
                            if (top != 9) {
                                mm.addMapping(basinIds[y][x],basinIds[y-1][x]);
                            }
                        } else {
                            basinIds[y][x] = basinIds[y-1][x];
                        }
                    }
                }
            }
        }
        return basinIds;
    }

    public Set<Point> initValeys() {
        valeys = new HashSet<>();
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                if (isLocalMinimum(x,y)) {
                    valeys.add(new Point(x,y));
                }
            }
        }
        return valeys;
    }

    private void fill(int x, int y, int basinId) {
        if (x<0 || x>= width || y<0 || y>=height) return;
        if (basinIds[y][x] == basinId) return;
        if (field[y][x] == 9) return;
        basinIds[y][x] = basinId;
        fill(x+1,y,basinId);
        fill(x-1,y,basinId);
        fill(x,y+1,basinId);
        fill(x,y-1,basinId);
    }

    boolean isLocalMinimum(int x, int y) {
        int current = get(x,y);
        return
                get(x-1,y) > current &&
                get(x+1,y) > current &&
                get(x,y-1) > current &&
                get(x,y+1) > current;
    }

    int get(int x, int y) {
        if (x<0 || x>= width || y<0 || y>=height) return 9;
        return field[y][x];
    }


    static Day9 fromFile(String input) {
        InputProcessor<int[]> ip = new InputProcessor<>(input, line -> line.chars().map(c -> c - '0').toArray());
        int[][] grid = ip.stream().toArray(int[][]::new);
        return new Day9(grid);
    }

    static int[][] createMap(int width, int height) {
        int[][] result = new int[height][];
        for (int y=0;y<height; y++) {
            result[y]=new int[width];
        }
        return result;
    }
}
