package com.janoz.aoc;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Strings {


    static String reverse(String in) {
        StringBuilder sb = new StringBuilder(in);
        sb.reverse();
        return sb.toString();
    }

    static String replaceFirst(String in, Map<String,String> replacements) {
        return null;
    }


    private static final Pattern SPLITTER_COMMA = Pattern.compile(",");

    public static Stream<String> streamCSV(String csv) {
        return SPLITTER_COMMA.splitAsStream(csv);
    }

}
