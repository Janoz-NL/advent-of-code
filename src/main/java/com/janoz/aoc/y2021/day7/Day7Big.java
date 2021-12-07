package com.janoz.aoc.y2021.day7;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Day7Big {


    public static void main(String[] args) throws IOException {

        Day7Big d = new Day7Big();
        long start = System.currentTimeMillis();
        d.readData(args[0]);
        System.out.println(System.currentTimeMillis() - start);

        BigInteger answer1 = d.scorePart1(d.bag.med());
        System.out.println(System.currentTimeMillis() - start);

        Cache<BigInteger, BigInteger> cache = new Cache<>();
        BigInteger answer2 = d.findLocalMinimum(cache.cached(d::scorePart2),d.bag.min(), d.bag.max());
        System.out.println(System.currentTimeMillis() - start);

        System.out.println("Score for part 1: " + answer1);
        System.out.println("Score for part 2: " + answer2);
    }

    SortedBag<BigInteger,BigInteger> bag = SortedBag.bigIntegerSortedBag();

    private BigInteger findLocalMinimum(Function<BigInteger, BigInteger> scoreFunction, BigInteger bottom, BigInteger top) {
        BigInteger delta = top.add(bottom.negate());
        if (delta.compareTo(BigInteger.valueOf(4)) < 0) {
            return IntStream.rangeClosed(0,delta.intValue()).mapToObj(BigInteger::valueOf).map(bottom::add).map(scoreFunction).reduce(BigInteger::min).get();
        }
        BigInteger thirdWidth = delta.divide(BigInteger.valueOf(3));
        BigInteger i1=bottom.add(thirdWidth);
        BigInteger i2=top.add(thirdWidth.negate());
        BigInteger score1 = scoreFunction.apply(i1);
        BigInteger score2 = scoreFunction.apply(i2);
        if (score1.compareTo(score2) < 0) {
            return findLocalMinimum(scoreFunction,bottom,i2);
        } else if (score1.compareTo(score2) > 0) {
            return findLocalMinimum(scoreFunction, i1, top);
        } else {
            return findLocalMinimum(scoreFunction,i1,i2);
        }
    }

    private BigInteger scorePart2(BigInteger position) {
        BigInteger score = BigInteger.ZERO;
        for (Map.Entry<BigInteger, BigInteger> e : bag.histoEntrySet()) {
            BigInteger pos = e.getKey();
            BigInteger amount = e.getValue();
            BigInteger distance = pos.add(position.negate()).abs();
            BigInteger dPlusOne = distance.add(BigInteger.ONE);
            score = score.add(amount.multiply(distance.multiply(dPlusOne).divide(BigInteger.TWO)));
        }
        return score;
    }

    private BigInteger scorePart1(BigInteger position) {
        BigInteger score = BigInteger.ZERO;
        for (Map.Entry<BigInteger, BigInteger> e : bag.histoEntrySet()) {
            BigInteger pos = e.getKey();
            BigInteger amount = e.getValue();
            BigInteger distance = pos.add(position.negate()).abs();
            score = score.add(amount.multiply(distance));
        }
        return score;
    }

    void readData(String file) throws IOException {
        Scanner scanner = new Scanner(Path.of(URI.create(file)));
        scanner.useDelimiter("[,\\n]");
        while (scanner.hasNext()) {
            bag.put(scanner.nextBigInteger());
        }
    }
}
