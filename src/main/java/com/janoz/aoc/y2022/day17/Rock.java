package com.janoz.aoc.y2022.day17;

import com.janoz.aoc.geo.Grid;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

enum Rock {

    R1("####"),
    R2(".#.", "###", ".#."),
    R3("###", "..#", "..#"),
    R4("#", "#", "#", "#"),
    R5("##", "##");

    private final String[] shape;


    private static int currentRock = -1;


    Rock(String... shape) {
        this.shape = shape;
    }

    static void dropNext(GrowingGrid<Boolean> field) {
        currentRock = (currentRock + 1) % Rock.values().length;
        Rock.values()[currentRock].dropRock(field);
    }

    private boolean fits(GrowingGrid<Boolean> field, int x, int y) {
        if (x < 0 || x + shape[0].length() > 7) return false;
        if (y >= field.getHeight()) return true;
        if (y < 0) return false;
        //start fitting
        Point position = new Point(x, y);
        return streamShape().map(position::fromOrigin).noneMatch(field::peek);
    }

    private void place(Grid<Boolean> field, int xPos, int yPos) {
        Point pos = new Point(xPos, yPos);
        streamShape().forEach(p -> field.put(p.fromOrigin(pos), true));
    }

    private Stream<Point> streamShape() {
        return IntStream.range(0, shape.length)
                .mapToObj(y -> IntStream.range(0, shape[0].length()).mapToObj(x -> new Point(x, y)))
                .flatMap(Function.identity())
                .filter(p -> shape[p.y].charAt(p.x) == '#');
    }

    void dropRock(GrowingGrid<Boolean> field) {
        Rock rock = this;
        int curVer = field.getHeight() + 3;
        int curHor = 2;

        do {
            int nextHor = curHor + Day17.commands.next();
            if (rock.fits(field, nextHor, curVer)) {
                curHor = nextHor;
            }

            if (!rock.fits(field, curHor, curVer - 1)) {
                break;
            }
            curVer--;

        } while (true);
        rock.place(field, curHor, curVer);
    }
}
