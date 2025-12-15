package com.janoz.aoc.y2025.day12;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.input.AocInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Day12 {

    static Shapes[] shapes = new Shapes[6];

    public static void main(String[] args) {
        StopWatch.start();
        long result = solve(AocInput.of(2025,12));
        StopWatch.stopPrint();
        printResult(result);
    }

    static long solve(AocInput<String> input) {
        Iterator<String> it = input.iterator();
        parseShapes(it);

        int count = 0;
        while (it.hasNext()) {
            if (solve(it.next())) count ++;
        }

        return count;
    }

    static void printResult(long result) {
        System.out.printf("Part 1 : %d\n",result);
    }

    static boolean solve(String line) {
        String[] items = line.split(":? +");
        Field f = new Field(items[0]);
        int fits = (f.width/3) * (f.height/3);
        List<Shapes> shapesToFit = new ArrayList<>();
        int totalArea = 0;
        for (int i=1;i<items.length;i++) {
            for (int j=0;j<Integer.parseInt(items[i]);j++) {
                shapesToFit.add(shapes[i-1]);
                totalArea += shapes[i-1].area;
            }
        }
        if (f.width * f.height < totalArea) return false;
        if (shapesToFit.size() <= fits ) return true;
        return fits(shapesToFit,0,f);
    }


    static boolean fits(List<Shapes> shapesToFit, int posInList, Field field) {
        if (posInList == shapesToFit.size()) return true;
        for (Shape shape : shapesToFit.get(posInList).shapes) {
            for (Field newField : field.fit(shape)) {
                if (fits(shapesToFit,posInList+1,newField)) return true;
            }
        }
        return false;
    }

    static void parseShapes(Iterator<String> it) {
        for (int i=0;i<=5;i++) {
            it.next();
            shapes[i] = new Shapes(new Shape(it.next(),it.next(),it.next()));
            it.next();
        }
    }

    static class Shapes {
        Set<Shape> shapes = new HashSet<>();
        int area ;

        Shapes(Shape shape) {
            area = shape.area;
            shapes.add(shape);
            Shape r1 = shape.rotate();
            Shape m1 = shape.mirrorX();
            Shape m2 = r1.mirrorX();
            for (int i=0; i<4; i++) {
                shapes.add(r1);
                shapes.add(m1);
                shapes.add(m2);
                r1 = r1.rotate();
                m1 = m1.rotate();
                m2 = m2.rotate();
            }
        }
    }

    static class Shape {
        final boolean[] places;
        final int area;

        Shape(String l1, String l2, String l3) {
            places = new boolean[9];
            for (int x = -1;x<=1;x++) {
                places[pos(x,-1)] = l1.charAt(x+1) == '#';
                places[pos(x,0)] = l2.charAt(x+1) == '#';
                places[pos(x,1)] = l3.charAt(x+1) == '#';
            }
            int size = 0;
            for (int i=0; i<9; i++) {
                if (places[i]) size++;
            }
            area = size;
        }

        private Shape(boolean[] shape, int area) {
            this.places = shape;
            this.area = area;
        }

        private int pos(int x, int y) {
            return (y+1)*3+(x+1);
        }

        boolean isOn(int x, int y) {
            return places[pos(x,y)];
        }

        Shape rotate() {
            boolean[] newShapes = new boolean[9];
            for (int y=-1;y<=1;y++) {
                for (int x=-1;x<=1;x++) {
                    newShapes[pos(x,y)]=places[pos(y*-1,x)];
                }
            }
            return new Shape(newShapes, area);
        }

        Shape mirrorX() {
            boolean[] newShapes = new boolean[9];
            for (int y=-1;y<=1;y++) {
                for (int x=-1;x<=1;x++) {
                    newShapes[pos(x,y)]=places[pos(x,y*-1)];
                }
            }
            return new Shape(newShapes, area);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Shape shape = (Shape) o;
            return Arrays.equals(places, shape.places);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(places);
        }
    }

    static class Field {
        final int width;
        final int height;
        final boolean[] field;

        Field(String input) {
            String[] parts = input.split("x");
            width = Integer.parseInt(parts[0]);
            height = Integer.parseInt(parts[1]);
            field = new boolean[width*height];
        }

        Field fit(Shape shape, int x, int y) {
            boolean[] field = Arrays.copyOf(this.field,this.field.length);
            for (int dx=-1;dx<=1;dx++) {
                for (int dy=-1;dy<=1;dy++) {
                    if (shape.isOn(dx,dy)) {
                        if (field[pos(x+dx,y+dy)]) {
                            return null;
                        }
                        field[pos(x+dx,y+dy)] = true;
                    }
                }
            }
            return new Field(width,height,field);
        }

        Field(int width, int height, boolean[] field) {
            this.width = width;
            this.height = height;
            this.field = field;
        }

        private int pos(int x, int y) {
            return y*width+x;
        }

        List<Field> fit(Shape shape) {
            List<Field> result = new ArrayList<>();
            for (int x=1;x<width-1;x++) {
                for (int y=1;y<height-1;y++) {
                    Field f = fit(shape,x,y);
                    if (f != null) result.add(f);
                }
            }
            return result;
        }
    }
}
