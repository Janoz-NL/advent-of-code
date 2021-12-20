package com.janoz.aoc.y2021.day19;

import java.util.Collections;
import java.util.Set;

public class Scanner {


    private final Set<Point3D> beacons;
    private final String name;

    public Scanner(String name, Set<Point3D> beacons) {
        this.name = name;
        this.beacons = Collections.unmodifiableSet(beacons);
    }

    public boolean inRange(Point3D beacon) {
        return
                beacon.x <= 1000 && beacon.x > -1000 &&
                beacon.y <= 1000 && beacon.y > -1000 &&
                beacon.z <= 1000 && beacon.z > -1000;
    }

    public Set<Point3D> getBeacons() {
        return beacons;
    }

    public String getName() {
        return name;
    }
}
