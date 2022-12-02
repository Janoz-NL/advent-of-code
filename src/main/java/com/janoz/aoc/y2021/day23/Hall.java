package com.janoz.aoc.y2021.day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Hall implements Comparable<Hall> {

    final Room[] rooms;
    final Amphiod[] hall;
    final int score;
    final int potentialScore;


    public Hall(Room[] rooms) {
        this(rooms, new Amphiod[7],0);
    }

    public Hall(Room[] rooms, Amphiod[] hall, int score) {
        this.rooms = rooms;
        this.hall = hall;
        this.score = score;
        this.potentialScore = score + potentialCost();
    }

    static int[][] r2hSteps = new int[][] {
            { 2, 1, 1, 3, 5, 7, 8},
            { 4, 3, 1, 1, 3, 5, 6},
            { 6, 5, 3, 1, 1, 3, 4},
            { 8, 7, 5, 3, 1, 1, 2}
    };

    static int[][] r2rSteps = new int[][] {
            { 0, 2, 4, 6},
            { 2, 0, 2, 4},
            { 4, 2, 0, 2},
            { 6, 4, 2, 0}
    };

    boolean isDone() {
        for (int r=0; r<4;r++) {
            if (!rooms[r].done) return false;
        }
        return true;
    }

    boolean routeAvailableR2R(int start, int end) {
        int min = Math.min(start,end);
        int max = Math.max(start,end);
        return routeAvailableH2H(2+min, 1+max, true);
    }

    boolean routeAvailableR2H(int room, int end) {
        int hallStart = 1+room;
        if (end > hallStart) hallStart++;
        return routeAvailableH2H(hallStart, end, true);
    }

    boolean routeAvailableH2R(int start, int room) {
        int hallStart = 1+room;
        if (start > hallStart) hallStart++;
        return routeAvailableH2H(start, hallStart, false);
    }

    boolean routeAvailableH2H(int start, int end, boolean startMustBeEmpty) {
        int min = Math.min(start,end);
        int max = Math.max(start,end);
        if (startMustBeEmpty) start = -1;
        for (int i=min; i<=max; i++) {
            if (hall[i] != null && i != start) return false;
        }
        return true;
    }


    void print() {
        System.out.println(" --> " + score);
        System.out.println(" --> " + potentialScore);
        System.out.println();
    }


    List<Hall> nextStates() {
        List<Hall> next = new ArrayList<>();
        for (int from=0; from <4; from++) {
            nextR2R(from).ifPresent(next::add);
        }
        for (int from=0; from<7; from++) {
            nextH2R(from).ifPresent(next::add);
        }
        for (int from=0; from<4; from++) {
            for (int to=0; to <7; to++) {
                nextR2H(from,to).ifPresent(next::add);
            }
        }
        return next;
    }




    Optional<Hall> nextR2R(int fromRoom) {
        if (rooms[fromRoom].enterState) return Optional.empty(); //cant leave fromroom
        Amphiod mover = rooms[fromRoom].top();
        int toRoom = mover.index;
        if (!rooms[toRoom].enterState) return Optional.empty(); //cant enter fromroom
        if (toRoom == fromRoom) return Optional.empty(); //can only move to this room
        if (!routeAvailableR2R(fromRoom, toRoom)) return Optional.empty(); //something in the way
        Room[] newRooms = new Room[4];
        for (int i=0; i<4; i++) {
            if (i == fromRoom) newRooms[i] = rooms[i].leave();
            else if (i == toRoom) newRooms[i] = rooms[i].enter();
            else newRooms[i] = rooms[i];
        }
        int steps = rooms[fromRoom].stepsOutof() + r2rSteps[fromRoom][toRoom] + rooms[toRoom].stepsInto();
        return Optional.of(new Hall(newRooms, hall, score + (mover.stepcost * steps)));
    }

    Optional<Hall> nextR2H(int fromRoom, int to) {
        if (rooms[fromRoom].enterState) return Optional.empty(); //cant leave fromroom
        if (!routeAvailableR2H(fromRoom, to)) return Optional.empty(); //no route
        Room[] newRooms = new Room[4];
        for (int i=0; i<4; i++) {
            if (i == fromRoom) newRooms[i] = rooms[i].leave();
            else newRooms[i] = rooms[i];
        }
        Amphiod mover = rooms[fromRoom].top();
        Amphiod[] newHall = Arrays.copyOf(hall,7);
        newHall[to] = mover;
        int steps = rooms[fromRoom].stepsOutof() + r2hSteps[fromRoom][to];
        return Optional.of(new Hall(newRooms, newHall, score + (mover.stepcost * steps)));
    }

    Optional<Hall> nextH2R(int from) {
        Amphiod mover = hall[from];
        if (mover == null) return Optional.empty(); //nothing to move
        int toRoom = mover.index;
        if (!rooms[toRoom].enterState) return Optional.empty(); //cant enter fromroom
        if (!routeAvailableH2R(from, toRoom)) return Optional.empty(); //no route
        Room[] newRooms = new Room[4];
        for (int i=0; i<4; i++) {
            if (i == toRoom) newRooms[i] = rooms[i].enter();
            else newRooms[i] = rooms[i];
        }
        Amphiod[] newHall = Arrays.copyOf(hall,7);
        newHall[from] = null;
        int steps = r2hSteps[toRoom][from] + rooms[toRoom].stepsInto();
        return Optional.of(new Hall(newRooms, newHall, score + (mover.stepcost * steps)));
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall otherHall = (Hall) o;
        return score == otherHall.score && Arrays.equals(rooms, otherHall.rooms) && Arrays.equals(hall, otherHall.hall);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(score);
        result = 31 * result + Arrays.hashCode(rooms);
        result = 31 * result + Arrays.hashCode(hall);
        return result;
    }

    public int hallHash() {
        int result =  Arrays.hashCode(rooms);
        result = 31 * result + Arrays.hashCode(hall);
        return result;
    }

    @Override
    public int compareTo(Hall o) {
        return potentialScore - o.potentialScore;
    }


    private int potentialCost() {
        int result = 0;
        for (int i=0; i<7; i++) {
            if (hall[i] != null) result += hall[i].stepcost * (r2hSteps[hall[i].index][i]);
        }
        for (int i=0; i<4; i++) {
            result += rooms[i].potentialCost();
        }
        return result;
    }
}
