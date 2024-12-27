package com.janoz.aoc.y2024.day7;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;

public class Day7 {

    public static void main(String[] args) {
        StopWatch.start();
        long answer = InputProcessor.asStream("inputs/2024/aoc-2024-day-07-challenge-3.txt").mapToLong( s -> {
            String[] elements = s.split(":");
            List<Long> vals = Arrays.stream(elements[1].trim().split("\\s+")).map(Long::parseLong).collect(Collectors.toList());
            long result = Long.parseLong(elements[0]);
            if (trySolve(result, vals)) return result;
            return 0;
        }).sum();
        StopWatch.stopPrint();
        System.out.print(answer);
    }

    static boolean trySolve(long result, List<Long> values) {
        long tail = values.get(values.size()-1);
        List<Long> headList = values.subList(0, values.size()-1);
        if (values.size() == 1) return tail == result;
        if (tail == 0 && result == 0)  return true;
        if ((tail != 0)  && (result % tail == 0) && trySolve(result/tail,headList)) return true;
        if ((result >= tail) && trySolve(result-tail,headList)) return true;

        String resultString = String.valueOf(result);
        String tailString = String.valueOf(tail);
        if (resultString.length() > tailString.length() && resultString.endsWith(tailString)) {
            resultString = resultString.substring(0, resultString.length() - tailString.length());
            return trySolve(Long.parseLong(resultString), headList);
        }
        return false;
    }
}
