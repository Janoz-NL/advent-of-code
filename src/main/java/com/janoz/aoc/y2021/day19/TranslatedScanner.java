package com.janoz.aoc.y2021.day19;

import com.janoz.aoc.geo.Point3D;

import java.util.Set;
import java.util.stream.Collectors;

public class TranslatedScanner {


    private final Point3D movement;
    private final int rotation;

    private final Scanner scanner;

    private final Set<Point3D> beacons;

    public TranslatedScanner(Point3D position, int rotation, Scanner scanner) {
        this.movement = position.reverse();
        this.rotation = rotation;
        this.scanner = scanner;
        beacons = scanner.getBeacons().stream().map(p -> p.rotate(rotation)).map(p -> p.translate(position)).collect(Collectors.toSet());
    }

    public boolean inRange(Point3D position) {
        return scanner.inRange(position.translate(movement));
    }

    public Set<Point3D> getBeacons() {
        return beacons;
    }

    public Point3D getMovement() {
        return movement;
    }

    public int getRotation() {
        return rotation;
    }
}
