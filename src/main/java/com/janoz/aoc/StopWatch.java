package com.janoz.aoc;

public class StopWatch {
    static private long ms;

    public static void start() {
        ms = System.currentTimeMillis();
    }

    public static long stop() {
        return System.currentTimeMillis() - ms;
    }

    public static void stopPrint() {
        System.out.println(stop());
    }

}
