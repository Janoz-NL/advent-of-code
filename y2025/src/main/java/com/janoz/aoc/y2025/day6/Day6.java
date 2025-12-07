package com.janoz.aoc.y2025.day6;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.input.AocInput;

import java.util.ArrayList;
import java.util.List;

public class Day6 {

    public static void main(String[] args) {
        StopWatch.start();
        long result1 = solve1(AocInput.of(2025,6));
        StopWatch.stopPrint();
        StopWatch.start();
        long result2 = solve2(AocInput.of(2025,6,s->s));
        StopWatch.stopPrint();
        printResult(new LongTuple(result1,result2));
    }



    static long solve2(AocInput<String> input) {
        List<String> data = input.asList();
        String operators = data.get(data.size()-1);
        int length = data.stream().mapToInt(String::length).max().getAsInt();
        long total = 0;
        int curpos = 0;
        int nextpos = gnextPos(operators,0);
        while(true) {
            total += action(curpos,nextpos-1, data);
            curpos = nextpos;
            nextpos = gnextPos(operators,curpos+1);
            if (nextpos == -1) {
                total += action(curpos,length, data);
                break;
            }
        }
        return total;
    }

    static long action(int start, int end, List<String> data) {
        List<Long> numbers = new ArrayList<>();
        for (int i = start; i < end; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < data.size()-1; j++) {
                sb.append(getChar(data.get(j),i));
            }
            String s = sb.toString().trim();
            if (!s.isEmpty()) {
                numbers.add(Long.parseLong(s));
            }
        }
        if (data.get(data.size()-1).charAt(start) == '+')
            return add(numbers);
        else
            return mul(numbers);
    }

    static int gnextPos(String s, int i) {
        int mul = s.indexOf('*',i);
        int add = s.indexOf('+',i);
        if (mul>0 && add>0) return Math.min(mul,add);
        return Math.max(mul,add);
    }

    static char getChar(String s, int i) {
        if (i>=s.length()) return ' ';
        return s.charAt(i);
    }

    static long solve1(AocInput<String> input) {
        List<String[]> data = input.addMapper(s -> s.split("\\s+")).asList();
        long total =0;


        for (int i =0; i<data.get(0).length; i++) {
            List<Long> l = new ArrayList<>();
            for (int j =-0; j < data.size()-1; j++) {
                l.add(Long.parseLong(data.get(j)[i]));
            }
            if ("*".equals(data.get(data.size()-1)[i])) {
                total += mul(l);
            } else {
                total += add(l);
            }
        }
        return total;
    }

    static long add(List<Long> numbers) {
        return numbers.stream().reduce(0L,Long::sum);
    }
    static long mul(List<Long> numbers) {
        return numbers.stream().reduce(1L,(a,b) -> a*b);
    }

    static void printResult(LongTuple results) {
        System.out.printf("Part 1 : %d\nPart 2 : %d",results.getLeft(), results.getRight());
    }
}
