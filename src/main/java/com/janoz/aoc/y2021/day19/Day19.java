package com.janoz.aoc.y2021.day19;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day19 {

    public static void main(String[] args) throws IOException{
        StopWatch.start();
        List<TranslatedScanner> scanners = placeScanners("inputs/day19.txt");
        System.out.println(scanners.stream().map(TranslatedScanner::getBeacons).reduce(new HashSet<>(), (s1,s2) -> {s1.addAll(s2);return s1;}).size());
        System.out.println(findLongestDistance(scanners.stream().map(TranslatedScanner::getMovement).collect(Collectors.toList())));
        StopWatch.stopPrint();
    }



    static List<TranslatedScanner> placeScanners(String input) throws IOException {
        BufferedReader reader = InputProcessor.getReaderFromResource(input);
        Scanner start = readScanner(reader);
        Scanner s;
        Queue<Scanner> scannerQueue = new ArrayDeque<>();
        while (null != (s = readScanner(reader))) {
            scannerQueue.offer(s);
        }

        List<TranslatedScanner> results = new ArrayList<>();
        results.add(new TranslatedScanner(Point3D.ORIGIN,0,start));
        Set<Point3D> beacons = new HashSet<>(start.getBeacons());
        while (!scannerQueue.isEmpty()) {
            Scanner candidate = scannerQueue.poll();
            TranslatedScanner possibility = findMatch(beacons, candidate);
            if (possibility == null) {
                scannerQueue.offer(candidate);
            } else {
                beacons.addAll(possibility.getBeacons());
                results.add(possibility);
            }
        }
        return results;
    }


    static long findLongestDistance(List<Point3D> points) {
        long result = 0;
        for (Point3D p1:points) {
            for (Point3D p2:points) {
                result = Math.max(result, p1.manhattanDistance(p2));
            }
        }
        return result;
    }

    static Scanner readScanner(BufferedReader reader) throws IOException {
        String name = reader.readLine();
        if (name==null) return null;
        Set<Point3D> beacons = new InputProcessor<>(reader,Point3D::parse).stream().collect(Collectors.toSet());
        return new Scanner(name,beacons);
    }







    static TranslatedScanner findMatch(Set<Point3D> actualBeacons, Scanner candidate) {
        for (int r=0; r<24; r++) {
            List<Point3D> candidateBeacons = rotateBeacons(candidate, r);
            for (Point3D actualBeacon:actualBeacons) {
                for (Point3D candidateBeacon:candidateBeacons) {
                    Point3D translation = findTranslation(actualBeacon,candidateBeacon);
                    TranslatedScanner result = new TranslatedScanner(translation,r,candidate);
                    Set<Point3D> translatedBeacons = new HashSet<>(result.getBeacons());
                    translatedBeacons.retainAll(actualBeacons);
                    if (translatedBeacons.size() >= 2) {
                        if (translatedBeacons.size() >= 12) {
                            return result;
                        }
                    }
                }
            }

        }
        return null;
    }

    private static List<Point3D> rotateBeacons(Scanner candidate, int r) {
        return candidate.getBeacons().stream().map(p -> p.rotate(r)).collect(Collectors.toList());
    }


    static Point3D findTranslation(Point3D source, Point3D target) {
        return new Point3D(
                source.x-target.x,
                source.y-target.y,
                source.z-target.z
        );
    }





}
