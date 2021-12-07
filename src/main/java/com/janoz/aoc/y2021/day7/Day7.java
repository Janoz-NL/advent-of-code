package com.janoz.aoc.y2021.day7;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

public class Day7 {

    private static final String INPUT = "1101,1,29,67,1102,0,1,65,1008,65,35,66,1005,66,28,1,67,65,20,4,0,1001,65,1,65,1106,0,8,99,35,67,101,99,105,32,110,39,101,115,116,32,112,97,115,32,117,110,101,32,105,110,116,99,111,100,101,32,112,114,111,103,114,97,109,10,760,1085,275,960,23,133,190,86,999,298,714,247,509,704,122,1109,713,51,41,1028,59,10,251,0,600,201,103,176,482,204,747,540,57,33,133,90,724,793,294,1618,762,65,1579,4,603,1182,25,12,718,30,1534,614,1021,1175,20,647,201,65,136,798,526,1,1060,70,329,194,54,747,423,349,261,604,133,32,1074,148,177,997,597,703,158,1265,472,277,52,320,467,899,333,750,40,588,311,456,1298,1511,33,1037,946,199,12,1751,221,14,1046,686,552,288,231,926,747,67,105,537,1264,654,539,211,549,294,381,662,6,523,239,48,487,6,575,553,218,1404,160,1196,330,336,1690,215,134,1312,186,1502,377,52,2,479,649,523,330,737,112,40,846,171,102,1614,39,514,438,932,143,443,1270,339,548,230,430,420,521,431,83,517,463,12,517,173,72,45,806,65,280,559,1076,332,162,50,606,1468,15,128,34,77,533,211,1157,789,111,67,308,462,147,1106,215,801,1294,203,98,833,136,136,1363,539,114,365,690,1378,266,1,212,537,283,327,55,96,377,57,899,37,1397,747,341,4,555,72,283,356,70,1410,33,311,1255,382,1076,50,98,314,214,49,281,33,1143,11,1270,396,477,265,156,763,86,595,1182,139,1085,499,1,3,7,90,408,1062,37,1175,56,925,1118,463,93,198,678,839,507,511,151,1081,146,1,553,292,208,384,787,395,360,1587,400,981,22,852,109,342,52,173,439,980,1058,11,282,117,558,652,370,86,81,178,531,309,691,254,183,324,495,511,26,57,1473,19,243,1290,392,362,1533,837,397,207,251,1250,584,700,431,1084,204,89,4,1439,48,1163,100,149,73,426,107,882,868,145,352,434,1445,354,74,1134,166,118,792,722,198,228,157,119,1178,789,947,670,1247,726,28,474,35,137,24,328,152,270,429,368,1113,132,364,32,122,12,1314,227,513,215,96,235,142,230,100,1112,119,308,1590,509,297,494,316,916,816,791,1204,42,660,1207,1170,257,663,120,12,18,1579,1164,110,432,601,397,323,376,656,128,34,215,1572,744,156,1081,330,1084,245,83,620,409,463,1029,1178,952,334,1344,963,109,8,462,174,302,1441,12,16,701,466,1794,620,442,227,165,894,1542,94,261,419,962,1047,1294,400,3,355,394,125,25,674,774,44,22,492,384,44,457,121,188,132,1226,185,991,822,1351,1126,638,258,134,349,204,72,330,1006,124,969,981,586,61,670,0,158,316,794,835,1086,160,506,293,798,77,44,1337,106,602,1459,665,85,364,1328,363,32,796,344,1894,178,742,347,626,267,304,909,130,82,189,284,745,14,50,494,36,113,632,527,140,817,136,1707,1227,792,1774,4,159,1233,85,486,515,917,16,200,333,335,328,640,347,311,1297,1489,1047,653,1,56,157,833,257,1294,237,759,124,453,1205,447,734,976,364,315,656,19,336,42,566,61,73,212,107,747,1033,130,1896,1283,1028,877,336,325,127,762,887,644,965,955,25,562,1042,975,410,346,387,1432,1303,1,257,87,814,1101,1399,38,204,1753,69,201,1347,442,169,272,1593,136,21,1821,200,60,99,76,6,88,1657,1825,539,92,705,1402,297,1309,316,614,84,403,204,45,805,119,67,149,384,179,188,1712,68,226,1430,1137,0,561,515,1233,9,450,657,216,516,197,829,413,53,792,792,432,397,97,120,876,596,287,44,125,37,70,225,27,5,122,1936,14,492,151,1072,464,62,478,1393,35,747,510,100,1242,10,1608,212,281,15,905,81,49,198,318,278,751,219,211,561,33,1787,64,419,1,809,410,228,196,333,261,1,454,1364,637,654,224,107,1573,907,245,129,346,619,111,392,40,273,256,1,807,1594,51,766,1113,21,31,745,1510,204,25,125,124,434,608,546,0,251,81,116,957,973,76,1129,320,368,851,302,711,612,84,218,809,858,1460,818,136,886,1160,1284,531,1617,122,1091,539,231,318,616,148,1366,291,537,1606,1004,317,43,1424,469,1193,500,479,431,470,1316,32,953,593,1162,803,761,60,255,369,1250,275,1534,312,258,36,114,308,672,94,698,231,34,213,168,64,1170,44,1547,246,1607,733,479,87,554,101,68,631,673,231,177,392,627,464,405,415,148,1478,396,1309,445,298,445,428,208,510,371,788,597,635,1230,111,325,121,1173,21,157,576,5,365,319,858,722,259,129,198,555,83,160,1125,467,784,100,706,155,209,446,821,379,732,160,233,114,644,565,106,656,863,354,1246,266,437,41,154";


    private long[] positions;

    public static void main(String[] args) {
        Day7 day7 = new Day7();
        day7.initPositions(INPUT);
        System.out.println(day7.part1());
        day7.resetCalc();
        System.out.print(day7.part2a());
        System.out.println(" " + day7.calc);
        day7.resetCalc();
        System.out.print(day7.part2b());
        System.out.println(" " + day7.calc);
        day7.resetCalc();
        System.out.print(day7.part2c());
        System.out.println(" " + day7.calc);
    }

    long part1() {
        return calcScore(p -> cost1(p,positions[positions.length / 2]));
    }


    long part2a() {
        return findLocalMinimum(p -> calcScore(i -> cost2(i, p)), positions[positions.length / 2]);
    }

    long part2b() {
        return findLocalMinimum2(p -> calcScore(i -> cost2(i, p)), positions[0], positions[positions.length - 1]);
    }

    long part2c() {
        return findLocalMinimum2(cached(p -> calcScore(i -> cost2(i, p))), positions[0], positions[positions.length - 1]);
    }


    private long findLocalMinimum(LongUnaryOperator scoreFucntion, long start) {
        long leftScore = scoreFucntion.applyAsLong(start);
        long rightScore = scoreFucntion.applyAsLong(start + 1);
        if (leftScore < rightScore) return findLocalMinimum(scoreFucntion, start, leftScore, -1);
        else return findLocalMinimum(scoreFucntion, start + 1, rightScore, 1);
    }

    private long findLocalMinimum(LongUnaryOperator scoreFucntion, long current, long currentScore, int direction) {
        long newScore = scoreFucntion.applyAsLong(current + direction);
        if (newScore > currentScore) {
            return currentScore;
        } else {
            return findLocalMinimum(scoreFucntion, current+direction, newScore, direction);
        }
    }


    private long findLocalMinimum2(LongUnaryOperator scoreFucntion, long bottom, long top) {
        if (top - bottom < 4) return LongStream.rangeClosed(bottom,top).map(scoreFucntion).min().getAsLong();
        long thirdWidth = (top - bottom) / 3;
        long i1=bottom + thirdWidth;
        long i2=top - thirdWidth;
        long score1 = scoreFucntion.applyAsLong(i1);
        long score2 = scoreFucntion.applyAsLong(i2);

        if (score1 < score2) {
            return findLocalMinimum2(scoreFucntion,bottom,i2);
        } else if (score1 > score2) {
            return findLocalMinimum2(scoreFucntion, i1, top);
        } else {
            return findLocalMinimum2(scoreFucntion,i1,i2);
        }
    }


    int calc;
    Map<Long,Long> cache = new HashMap<>();
    void resetCalc() {
        calc = 0;
        cache.clear();
    }

    LongUnaryOperator cached(LongUnaryOperator actual) {
        return l -> {
            if (!cache.containsKey(l)) {
                cache.put(l,actual.applyAsLong(l));
            }
            return cache.get(l);
        };
    }

    long calcScore(LongUnaryOperator scoreFunction) {
        calc++;
        return Arrays.stream(positions).map(scoreFunction).sum();
    }

    long cost1(long position, long target) {
        return Math.abs(position-target);
    }

    long cost2(long position, long target) {
        long cost1 = cost1(position, target);
        return (cost1 * (cost1 + 1)) >> 1;
    }

    void initPositions(String input) {
        positions = Arrays.stream(input.split(",")).mapToLong(Long::parseLong).sorted().toArray();
    }

    void initPositions(long[] positions) {
        this.positions=positions;
        Arrays.sort(positions);
    }
}
