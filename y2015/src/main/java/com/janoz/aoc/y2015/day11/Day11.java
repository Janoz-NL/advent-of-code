package com.janoz.aoc.y2015.day11;

import com.janoz.aoc.input.AocInput;

public class Day11 {
    public static void main(String[] args) {
        solve(AocInput.of(2015,11));
    }

    static void solve(AocInput<String> input) {
        char[] password = input.iterator().next().toCharArray();
        password = nextValid(password);
        System.out.println("Part 1: " + new String(password));
        password = nextValid(password);
        System.out.println("Part 2: " + new String(password));
    }

    static char[] nextValid(char[] password) {
        do {
            password = next(password);
        } while (!isValid(password));
        return password;
    }

    static char[] next(char[] password) {
        int carry = 1;
        char[] result = new char[password.length];
        for (int i = password.length-1; i >= 0; i--) {
            char c = (char) (password[i] + carry);
            carry = 0;
            if (c > 'z') {
                carry = 1;
                c = (char) (c-26);
            }
            result[i]=c;
        }
        return result;
    }

    static boolean isValid(char[] password) {
        char previous = password[0];
        int pairs = 0;
        int straight = 0;
        boolean containsStraight = false;
        boolean previousWasPair = false;
        for (int i = 1; i < password.length; i++) {
            char current = password[i];
            if (current == 'i') return false;
            if (current == 'o') return false;
            if (current == 'l') return false;
            if (previous +1 == current) {
                straight++;
                if (straight == 2) containsStraight = true;
            } else {
                straight = 0;
            }
            if (!previousWasPair && current == previous) {
                pairs++;
                previousWasPair = true;
            } else {
                previousWasPair = false;
            }
            previous = current;
        }
        return pairs >= 2 && containsStraight;
    }
}
