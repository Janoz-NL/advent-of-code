package com.janoz.aoc;

public class StopWatch {
    static private long ms;

    public static void start() {
        ms = System.nanoTime();
    }

    public static long stop() {
        return System.nanoTime() - ms;
    }

    public static void stopPrint() {
        System.out.println("In " + formatDuration(stop()));
    }

    private static String formatDuration(long nano) {
        StringBuilder result = new StringBuilder();

        result.insert(0, "ns");
        result.insert(0, nano % 1000000L);
        nano = nano / 1000000L;
        result.insert(0, "ms ");
        result.insert(0, nano % 1000L);

        nano = nano / 1000L;
        result.insert(0, "s ");
        result.insert(0, nano % 60);

        nano = nano / 60L;
        result.insert(0, "m ");
        result.insert(0, nano );

        return result.toString();
    }
}
