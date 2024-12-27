package com.janoz.aoc.y2022.day25;

import com.janoz.aoc.InputProcessor;

public class Day25 {

    public static void main(String[] args) {
        String file = "inputs/2022/day25.txt";
        System.out.println(toSnafu(InputProcessor.asStream(file).mapToLong(Day25::fromSnafu).sum()));
    }

    static String toSnafu(long l){
        long[] digits = Long.toUnsignedString(l, 5).chars().map(i -> i - '0').mapToLong(i -> i).toArray();
        long reminder = 0;
        for (int i=digits.length-1; i>=0; i--) {
            digits[i] += reminder;
            reminder = 0;
            while (digits[i] > 2) {
                digits[i] -= 5;
                reminder += 1;
            }
        }
        StringBuilder sb = new StringBuilder();
        if (reminder > 0) {
            sb.append(toSnafu(reminder));
        }
        for (long digit : digits) {
            sb.append(digitToSnafu((int) digit));
        }
        return sb.toString();
    }

    static long fromSnafu(String in) {
        long result = 0;
        for (int i = 0; i< in.length(); i++ ){
            result = result * 5;
            result += fromSnafu(in.charAt(i));
        }
        return result;
    }

    private static long fromSnafu(char c) {
        switch (c) {
            case '2' : return 2;
            case '1' : return 1;
            case '0' : return 0;
            case '-' : return -1;
            case '=' : return -2;
            default: throw new NumberFormatException("Unknown char " + c);
        }
    }

    private static char digitToSnafu(int l) {
        switch (l) {
            case 2 : return '2';
            case 1 : return '1';
            case 0 : return '0';
            case -1 : return '-';
            case -2 : return '=';
            default: throw new NumberFormatException("Unexpected digit " + l);
        }
    }

}
