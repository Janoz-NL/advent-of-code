package com.janoz.aoc.y2024.day17;

import com.janoz.aoc.StopWatch;

public class Day17 {

    static int[] outputs = {2,4,1,1,7,5,1,5,4,3,5,5,0,3,3,0};
    static ChronospatialComputer cc = new ChronospatialComputer(0,0,0, outputs);

    public static void main(String[] args) {
        StopWatch.start();
        System.out.println(findA(outputs.length-1,0));
        StopWatch.stopPrint();
    }

    public static long findA(int pointer, long a) {
        if (pointer == -1) {
            return a;
        }
        a = a * 8;
        for (int i=0; i<8; i++) {
            cc.reset(a+i,0,0);
            cc.run();
            if (tailEqual(outputs,cc.getOutput())) {
                long candidate = findA(pointer -1, a+i);
                if (candidate >= 0) return candidate;
            }
        }
        return -1;
    }

    public static boolean tailEqual(int[] a, int[] b) {
        int d = a.length - b.length;
        for (int i=0; i<b.length; i++) {
            if (a[d+i] != b[i]) {
                return false;
            }
        }
        return true;
    }

}
