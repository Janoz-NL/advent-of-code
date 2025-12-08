package com.janoz.aoc.y2021.day14;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.collections.Histogram;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {

    public static void main(String[] args) throws IOException {
        System.out.println(replace("inputs/2021/day14.txt", 10));
        System.out.println(replace("inputs/2021/day14.txt", 40));

    }

    static BigInteger replace(String input, int times) throws IOException {
        Histogram<String, BigInteger> histogram = read(input);
        for(int i=0; i<times; i++) {
            histogram = next(histogram);
        }
        return calcScore(histogram);
    }

    static BigInteger calcScore(Histogram<String, BigInteger> patternHistoram) {
        Histogram<Character, BigInteger> histogram = Histogram.hugeHistogram();
        histogram.inc(startPattern.charAt(startPattern.length()-1));
        patternHistoram.entrySet().forEach(e -> histogram.inc(e.getKey().charAt(0), e.getValue()));
        List<Character> sorted = histogram.sorted(Character::compareTo);
        BigInteger max = histogram.get(sorted.get(0));
        BigInteger min = histogram.get(sorted.get(sorted.size()-1));
        return max.add(min.negate());
    }

    static String startPattern;

    static  Histogram<String, BigInteger>  next(Histogram<String, BigInteger>  histogram) {
        Histogram<String, BigInteger> newHistogram = Histogram.hugeHistogram();
        histogram.entrySet().forEach( entry -> {
            String[] replacements = replacementsMap.get(entry.getKey());
            newHistogram.inc(replacements[0], entry.getValue());
            newHistogram.inc(replacements[1], entry.getValue());
        } );
        return newHistogram;
    }

    static  Histogram<String, BigInteger>  read(String input) throws IOException {
        Histogram<String, BigInteger> histogram = Histogram.hugeHistogram();
        BufferedReader reader = InputProcessor.getReaderFromResource(input);
        startPattern = reader.readLine();
        for (int i=0; i < startPattern.length()-1;i++) {
            histogram.inc(startPattern.substring(i,i+2));
        }
        reader.readLine();
        new InputProcessor<>(reader, s -> s ).forEach(Day14::parseReplacement);
        return histogram;
    }

    private static final Map<String, String[]> replacementsMap = new HashMap<>();

    static void parseReplacement(String s) {
        replacementsMap.put(s.substring(0,2),
                new String[]{
                        new String(new char[]{s.charAt(0), s.charAt(6)}),
                        new String(new char[]{s.charAt(6), s.charAt(1)}),
                }
        );
    }

}
