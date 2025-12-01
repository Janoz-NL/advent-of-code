package com.janoz.aoc.y2015.day19;

import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.input.AocInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Day19 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,19));
    }

    static Map<String,List<String>> replacements = new AlwaysHashMap<>(() -> new ArrayList<>());
    static Map<List<Token>,Token> reverse = new HashMap<>();
    static String src;

    static void solve(AocInput<String> input) {
        input.stream().forEach(s -> {
            String[] parts = s.split(" => ");
            replacements.get(parts[0]).add(parts[1]);
            reverse.put(stream(tokenize(parts[1])).toList(),Token.valueOf(parts[0]));
        });
        src = input.asString(s->s);
        for (int i =0; i<src.length(); i++) {
            replace(i);
        }
        System.out.println(molecules.size());


//
//
//        replacements.forEach((k,v) -> {
//            for (String s : v) {
//                System.out.println(k + "\t->\t" + rewrite(s));
//            }
//        });
//
//
//
//        System.out.println(rewrite(src));
//
//
//        breakUp(rewrite(src));
    }

    static Set<String> molecules = new HashSet<>();
    static void replace(int index) {
        String front = src.substring(0,index);
        String back = src.substring(index);
        for (String atom : replacements.keySet()) {
            if (back.startsWith(atom)) {
                String left =back.substring(atom.length());
                replacements.get(atom).forEach(r -> molecules.add(front + r + left));
            }
        }
    }


    static void breakUp(Iterator<Token> tokens) {
        while (tokens.hasNext()) {

        }
    }


//    static String rewrite(String s) {
//        StringBuilder sb = new StringBuilder();
//        int i=0;
//        while (i<s.length()) {
//            if (s.substring(i).startsWith("Y")) {
//                sb.append(",");
//                i++;
//            } else if (s.substring(i).startsWith("Rn")){
//                sb.append("(");
//                i+=2;
//            } else if (s.substring(i).startsWith("Ar")){
//                sb.append(")");
//                i+=2;
//            } else {
//                sb.append(s.charAt(i));
//                i++;
//            }
//        }
//        return sb.toString();
//    }


    static void breakUp(String s) {
        int level = 0;
        int maxLevel = 0;
        List<String> levels = new ArrayList<>();
        levels.add("");
        for (int i=0;i<s.length();i++) {
            if (s.charAt(i) == ')') { level--;}
            if (s.charAt(i) == ',') { level--;}
            for (int j=0;j<=maxLevel;j++) {
                levels.set(j,levels.get(j) + ((level==j)?s.charAt(i):" "));
            }
            if (s.charAt(i) == ',') { level++;}
            if (s.charAt(i) == '(') {
                level++;
                if (level > maxLevel) {
                    maxLevel = level;
                    levels.add(" ".repeat(i+1));
                }
            }
        }
        levels.forEach(System.out::println);
    }


    static <T> Stream<T> stream(Iterator<T> iterator) {
        return StreamSupport.stream(((Iterable<T>) (() -> iterator)).spliterator(),false);
    }



    static Iterator<Token> tokenize(String s) {
        return new Iterator<>() {

            int i=0;

            @Override
            public boolean hasNext() {
                return i < s.length();
            }

            @Override
            public Token next() {
                for (Token t:Token.values()) {
                    String ts = t.toString();
                    if (s.substring(i).startsWith(ts)) {
                        i = i + ts.length();

                        return t;
                    }
                }
                return null;
            }
        };
    }

    enum Token {
        Al,
        Th,
        Ti,
        Si,
        Mg,
        Ca,
        Rn,
        Ar,
        B,
        e,
        F,
        H,
        N,
        O,
        P,
        Y,
        C;
    }
}
