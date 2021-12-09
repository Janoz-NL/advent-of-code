package com.janoz.aoc.y2021.day8;

import com.janoz.aoc.InputProcessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class Day8 {


    public static void main(String[] args) {
        System.out.println(new InputProcessor<>("inputs/day8.txt",s -> calc(s,Day8::part1)).stream().mapToInt(Integer::intValue).sum());
        System.out.println(new InputProcessor<>("inputs/day8.txt",s -> calc(s,Day8::map)).stream().mapToInt(Integer::intValue).sum());
    }
    static int part1(String[] digits, String[] output) {
        return countInOutput(output,digits[1]) + countInOutput(output,digits[4]) + countInOutput(output,digits[7]) + countInOutput(output,digits[8]);
    }


    static int calc(String line, BiFunction<String[],String[], Integer> answerCalculator) {
        String[] io = line.split("\\|" );
        String[] input = Arrays.stream(io[0].trim().split(" ")).map(Day8::sortChars).toArray(String[]::new);
        String[] output = Arrays.stream(io[1].trim().split(" ")).map(Day8::sortChars).toArray(String[]::new);
        String[] digits = determinDigits(input);
        return answerCalculator.apply(digits,output);
    }

    static int map(String[] digits, String[] values) {
        List<String> lookup = Arrays.asList(digits);
        int answer = 0;
        for (String value:values) {
            answer = answer * 10;
            answer = answer + lookup.indexOf(value);
        }
        return answer;
    }

    static String[] determinDigits(String[] input) {
        String[] digits = new String[10];
        digits[1] = findByLenght(input, 2).iterator().next();
        digits[4] = findByLenght(input, 4).iterator().next();
        digits[7] = findByLenght(input, 3).iterator().next();
        digits[8] = findByLenght(input, 7).iterator().next();

        Set<String> fives = findByLenght(input, 5);
        Set<String> sixes = findByLenght(input, 6);

        digits[3] = getFromSetContaining(fives,digits[7]);
        digits[9] = getFromSetContaining(sixes,digits[3]);
        digits[0] = getFromSetContaining(sixes,digits[1]);
        digits[6] = sixes.iterator().next();
        digits[5] = getFromSetContaining(fives,minus(digits[4],digits[1]));
        digits[2] = fives.iterator().next();

        return digits;
    }

    private static String getFromSetContaining(Set<String> set, String pattern) {
        String result = set.stream().filter(c -> containsAll(c,pattern)).findAny().get();
        set.remove(result);
        return result;
    }


    private static Set<String> findByLenght(String[] input, int lenght) {
        Set<String> result = new HashSet<>();
        for (String s : input) {
            if (s.length() == lenght) {
                result.add(s);
            }
        }
        return result;
    }

    private static int countInOutput(String[] output, String s) {
        int count = 0;
        for (String o : output) {
            if (s.equals(o)) count ++;
        }
        return count;
    }
    
    
    private static String sortChars(String s) {
        char[] result = s.toCharArray();
        Arrays.sort(result);
        return new String(result);
    }


    static boolean containsAll(String s, String m) {
        int sc = 0;
        int mc = 0;
        while(sc < s.length()) {
            if (s.charAt(sc) == m.charAt(mc)) {
                sc++;
                mc++;
            } else if (s.charAt(sc) > m.charAt(mc)) {
                return false;
            } else {
                sc++;
            }
            if (mc == m.length()) {
                return true;
            }
        }
        return false;
    }

    static String minus(String s, String m) {
        int sc = 0;
        int mc = 0;
        int rc = 0;
        char[] result = new char[s.length()];
        while(sc < s.length()) {
            if (s.charAt(sc) == m.charAt(mc)) {
                sc++;
                mc++;
            } else if (s.charAt(sc) > m.charAt(mc)) {
                mc++;
            } else {
                result[rc++] = s.charAt(sc);
                sc++;
            }
            if (mc == m.length()) {
                s.getChars(sc,s.length(),result, rc);
                rc = rc + s.length() - sc;
                break;
            }
        }
        return new String(result,0,rc);
    }


}
