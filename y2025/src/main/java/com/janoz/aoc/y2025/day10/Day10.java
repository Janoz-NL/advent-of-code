package com.janoz.aoc.y2025.day10;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.algorithms.PFABuilder;
import com.janoz.aoc.algorithms.PathFindingAlgorithm;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.input.AocInput;
import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.IntSort;
import com.microsoft.z3.Optimize;
import com.microsoft.z3.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day10 {

    public static void main(String[] args) {
        StopWatch.start();
        LongTuple result = solve(AocInput.of(2025,10));
        StopWatch.stopPrint();
        printResult(result);
    }

    static LongTuple solve(AocInput<String> input) {
        return input
                .addMapper(s -> s.split(" "))
                .stream()
                .map(s -> new LongTuple(solveIndicators(s), solveLevers(s)))
                .reduce(LongTuple::add).orElseThrow();
    }

    static void printResult(LongTuple results) {
        System.out.printf("Part 1 : %d\nPart 2 : %d",results.getLeft(), results.getRight());
    }

    static long solveIndicators(String[] input) {
        int indicator = parseIndicator(input[0]); // bitmap
        int[] buttons = new int[input.length - 2]; // bitmap
        for (int i = 1; i < input.length - 1; i++) {
            buttons[i - 1] = parseButton(input[i]);
        }
        PathFindingAlgorithm<Integer> pfa = PFABuilder
                .forType(Integer.class)
                .withTargetPredicate(i -> i == indicator)
                .withNeighbourProducer(i -> {
                    ArrayList<Integer> result = new ArrayList<>();
                    for (int button : buttons) {
                        result.add(i ^ button);
                    }
                    return result;
                })
                .asDijkstra();
        return pfa.calculate(0);
    }

    /*
     *
     * b0   b1   b2   b3    b4    b5    0 1 2 3
     * (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
     *
     *                     b4 +  b5    = 3
     *      b1 +                 b5    = 5
     *           b2 + b3 + b4          = 4
     * b0 + b1 +      b3               = 7
     *
     * b0 + b1 + b2 + b3 + b4 + b5 = answer
     *
     */
    static long solveLevers(String[] input) {
        int[] joltage = parseJoltage(input[input.length-1]);
        int[][] buttons = new int[input.length-2][];
        for (int i = 1; i < input.length-1; i++) {
            buttons[i-1] = Arrays.stream(input[i].substring(1, input[i].length()-1).split(","))
                    .mapToInt(Integer::parseInt).toArray();
        }
        try (Context ctx = new Context()) {
            Optimize optimize = ctx.mkOptimize();

            //Make list of button press variables
            List<IntExpr> buttonVars = IntStream.range(0,buttons.length)
                    .mapToObj(i -> ctx.mkIntConst("b" + i))
                    .peek(ic -> optimize.Add(ctx.mkGe(ic, ctx.mkInt(0))))
                    .toList();

            //Make expressions bx + by.. = joltage[j] where x,y,.. are buttons that change joltage[j]
            for (var j = 0; j < joltage.length; j++) {

                List<ArithExpr<IntSort>> terms = new ArrayList<>();
                for (var b = 0; b < buttons.length; b++){
                    for (int i = 0; i < buttons[b].length; i++) {
                        if (buttons[b][i] == j) {
                            terms.add(buttonVars.get(b));
                        }
                    }
                }
                ArithExpr<IntSort>[] termArray = terms.toArray(new ArithExpr[terms.size()]);

                var sumExpr = ctx.mkAdd(termArray);
                var tarExpr = ctx.mkInt(joltage[j]);
                optimize.Add(ctx.mkEq(sumExpr, tarExpr));
            }

            //Sum all buttonpresses
            IntExpr answer = ctx.mkIntConst("answer");
            optimize.Add(ctx.mkEq(ctx.mkAdd(buttonVars.toArray(new IntExpr[buttonVars.size()])), answer));

            //optimize for answer
            optimize.MkMinimize(answer);

            //Now solve it
            if (optimize.Check() != Status.SATISFIABLE) {
                throw new RuntimeException("No solution found");
            }
            return Long.parseLong(optimize.getModel().evaluate(answer,false).toString());
        }
    }

    static int parseButton(String s) {
        return Arrays.stream(s.substring(1, s.length()-1).split(","))
                .mapToInt(Integer::parseInt)
                .map(i -> 1 << i)
                .sum();
    }

    public static int parseIndicator(String s) {
        int result = 0;
        for (int i = 1; i < s.length()-1; i++) {
            if (s.charAt(i) == '#') result += 1 << (i-1);
        }
        return result;
    }

    public static int[] parseJoltage(String s) {
        return Arrays.stream(s.substring(1, s.length()-1).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
