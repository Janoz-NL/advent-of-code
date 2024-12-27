package com.janoz.aoc.y2021.day25;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.Point;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Field {

    List<char[]> readField = new ArrayList<>();
    char[][] field;
    int width;
    int height;

    void addRow(String row) {
        readField.add(row.toCharArray());
    }



    int moveCucumbers() {
        int moved = 0;
        Set<Point> moving = new HashSet<>();
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                if (field[y][x] == '>' && field[y][(x + 1) % width] == '.') {
                    moving.add(new Point(x,y));
                }
            }
        }
        moved += moving.size();
        for (Point p: moving) {
            field[p.y][p.x] = '.';
            field[p.y][(p.x + 1) % width] = '>';
        }
        moving.clear();
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                if (field[y][x] == 'v' && field[(y + 1) % height][x] == '.') {
                    moving.add(new Point(x,y));
                }
            }
        }
        moved += moving.size();
        for (Point p: moving) {
            field[p.y][p.x] = '.';
            field[(p.y + 1) % height][p.x] = 'v';
        }
        return moved;
    }



    int moveUntilStopped() {
        int moves = 1;
        while (moveCucumbers() != 0) {
            moves++;
        }
        return moves;
    }

    void constructField() {
        field = readField.toArray(new char[readField.size()][]);
    }

    void readField(String file) {
        new InputProcessor<String>(file, s -> s).forEach(this::addRow);
        constructField();
        height = field.length;
        width = field[0].length;
    }


    void print() {
        for (char[] row: field) {
            System.out.println(row);
        }
    }
}
