package com.janoz.aoc.y2015.day4;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Predicate;

public class Day4 {
    public static final String INPUT = "yzbqklnj";
    static MessageDigest md5;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        md5 = MessageDigest.getInstance("MD5");
        System.out.println("Part 1 :" + find(INPUT, Day4::validPart1));
        System.out.println("Part 1 :" + find(INPUT, Day4::validPart2));
    }

    static int find(String input, Predicate<byte[]> valid) {
        int i=0;
        byte[] hash;
        do {
            i++;
            hash = md5.digest((input + i).getBytes(StandardCharsets.UTF_8));
        } while (!valid.test(hash));
        return i;
    }

    static boolean validPart1(byte[] hash) {
        return hash[0] == 0 && hash[1] == 0 && hash[2]>=0 && hash[2]<16;
    }

    static boolean validPart2(byte[] hash) {
        return hash[0] == 0 && hash[1] == 0 && hash[2] == 0;
    }
}
