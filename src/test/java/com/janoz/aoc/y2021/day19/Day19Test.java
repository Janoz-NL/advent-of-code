package com.janoz.aoc.y2021.day19;


import com.janoz.aoc.InputProcessor;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class Day19Test {

    @Test
    void testPart1() throws IOException {
        assertThat(Day19.placeScanners("inputs/day19example.txt"), equalTo(79));
    }

    @Test
    void testMatching() throws Exception {
        BufferedReader input = InputProcessor.getReaderFromResource("inputs/day19exampleSmall.txt");
        Scanner s0 = Day19.readScanner(input);
        Scanner s1 = Day19.readScanner(input);
        TranslatedScanner result = Day19.findMatch(s0.getBeacons(), s1);
        assertThat(result.getRotation(), equalTo(6));
    }

    @Test
    void testMappingTranslated() {
        Scanner s1 = new Scanner("Scan 1", Collections.singleton(new Point3D(10,20,30)));
        Scanner s2 = new Scanner("Scan 2", Collections.singleton(new Point3D(-10,-20,-30)));
        Point3D translation = Day19.findTranslation(
                s1.getBeacons().iterator().next(),
                s2.getBeacons().iterator().next());
        TranslatedScanner translated = new TranslatedScanner(translation, 0, s2);
        Set<Point3D> overlap = new HashSet<>(translated.getBeacons());
        overlap.retainAll(s1.getBeacons());
        assertThat(overlap.size(), equalTo(1));
    }

    @Test
    void testMappingRotated() {
        Scanner s1 = new Scanner("Scan 1", Collections.singleton(new Point3D(10,20,30)));
        Scanner s2 = new Scanner("Scan 2", Collections.singleton(new Point3D(-10,-20,-30)));

        TranslatedScanner s2rot = new TranslatedScanner(Point3D.ORIGIN,1,s2);
        Point3D translation = Day19.findTranslation(
                s1.getBeacons().iterator().next(),
                s2rot.getBeacons().iterator().next());
        TranslatedScanner translated = new TranslatedScanner(translation, 1, s2);
        Set<Point3D> overlap = new HashSet<>(translated.getBeacons());
        overlap.retainAll(s1.getBeacons());
        assertThat(overlap.size(), equalTo(1));
    }
}