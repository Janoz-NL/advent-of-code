package com.janoz.aoc.y2023.day2;

import java.util.Arrays;

class Draw {
    int red;
    int green;
    int blue;

    public Draw(String input) {
        String[] cubes = input.split(",");
        Arrays.stream(cubes).forEach(c -> {
            String[] parts = c.trim().split(" ");
            switch (parts[1]) {
                case "red":
                    red = Integer.parseInt(parts[0]);
                    break;
                case "green":
                    green = Integer.parseInt(parts[0]);
                    break;
                case "blue":
                    blue = Integer.parseInt(parts[0]);
                    break;
            }
        });
    }

    public boolean isValid() {
        return ! (red > 12 || green > 13 || blue > 14);
    }


    public String toString() {
        return "b" + blue + ",g" + green + ",r" + red;
    }


}
