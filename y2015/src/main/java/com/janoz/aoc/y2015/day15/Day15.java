package com.janoz.aoc.y2015.day15;

import com.janoz.aoc.input.AocInput;

import java.util.List;

public class Day15 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,15));
    }

    static List<Ingredient> ingredients;

    static void solve(AocInput<String> input) {
        ingredients = input.addMapper(Ingredient::new).asList();
        System.out.println("Part 1: " + mix(0,100,new int[5], false));
        System.out.println("Part 2: " + mix(0,100,new int[5], true));
    }

    static int mix(int ingredientIndex, int spoonsLeft, int[] scores, boolean caloriesCount) {
        if (ingredientIndex == ingredients.size() -1 || spoonsLeft == 0) {
            int[] finalScores = add(scores, ingredients.get(ingredientIndex).get(spoonsLeft));
            if (finalScores[4] == 500 || !caloriesCount) {
                return
                        Math.max(0, finalScores[0]) *
                                Math.max(0, finalScores[1]) *
                                Math.max(0, finalScores[2]) *
                                Math.max(0, finalScores[3]);
            } else return 0;
        }
        int score = 0;
        for (int i=0; i<spoonsLeft; i++) {
            int[] newScore = add(scores, ingredients.get(ingredientIndex).get(i));
            if (caloriesCount && (newScore[4] > 500)) continue;
            score = Math.max(score, mix(ingredientIndex+1, spoonsLeft-i, newScore, caloriesCount));
        }
        return score;
    }

    static int[] add(int[] a, int[] b) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] + b[i];
        }
        return result;
    }

    static class Ingredient {
        int[] ingredients = new int[5];

        Ingredient(String input) {
            String[] parts = input.split("[, ]");
            ingredients[0] = Integer.parseInt(parts[2]);
            ingredients[1] = Integer.parseInt(parts[5]);
            ingredients[2] = Integer.parseInt(parts[8]);
            ingredients[3] = Integer.parseInt(parts[11]);
            ingredients[4] = Integer.parseInt(parts[14]);
        }

        int[] get(int teaspoons) {
            int[] result = new int[5];
            for (int i = 0; i < 5; i++) {
                result[i] = ingredients[i] * teaspoons;
            }
            return result;
        }
    }
}
