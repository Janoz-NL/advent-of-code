package com.janoz.aoc.y2021.day23;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.janoz.aoc.y2021.day23.Amphiod.*;

public class Room {


    final Amphiod roomOwners;
    final Amphiod[] spots;
    final boolean done;
    final boolean enterState;
    final int top;

    Room(Amphiod owner, Amphiod... initialFilling){
        this.roomOwners = owner;
        this.spots = initialFilling;
        this.done = isDone(initialFilling,owner);
        this.enterState = !done && isEnter(initialFilling,owner);
        this.top = findTop(initialFilling);
    }

    Room(Amphiod owner, int amount) {
        this.roomOwners = owner;
        this.done = amount == 4;
        this.top = 4 - amount;
        this.enterState = true;
        spots = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        if (roomOwners != room.roomOwners) return false;
        if (enterState)
            return room.enterState && room.top==top;
        else
            return Arrays.equals(spots, room.spots);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(roomOwners);
        if (enterState) {
            result = 31 * result + top;
        } else {
            result = 31 * result + Arrays.hashCode(spots);
        }
        return result;
    }

    Amphiod top() {
        if (enterState) {
            throw new RuntimeException("No top available when in enter state");
        }
        return spots[top];
    }

    int stepsInto() {
        return top;
    }

    int stepsOutof() {
        return top+1;
    }


    static private int findTop(Amphiod[] spots) {
        for (int i=3;i>=0;i--) {
            if (spots[i]==null) return i+1;
        }
        return 0;
    }

    static private boolean isDone(Amphiod[] spots, Amphiod owner) {
        for(Amphiod a: spots) {
            if (a != owner) return false;
        }
        return true;
    }

    static private boolean isEnter(Amphiod[] spots, Amphiod owner) {
        for(Amphiod a: spots) {
            if (a != null && a != owner) return false;
        }
        return true;
    }


    Room enter() {
        if (!enterState) {
            throw new RuntimeException("Tried to enter while room not in enterstate");
        }
        return new Room(roomOwners, 5-top);
    }

    Room leave() {
        if (enterState) {
            throw new RuntimeException("Tried to leave while room in enterstate");
        }
        Amphiod[] newSpots = new Amphiod[4];
        for (int i=top+1; i<4; i++) {
            newSpots[i]=spots[i];
        }
        return new Room(roomOwners, newSpots);
    }

    static Room initialRoom(Amphiod owners, int part, Amphiod fill1, Amphiod fill2) {
        switch(owners) {
            case AMBER:
                if (part == 1) return new Room(AMBER,fill1,fill2,AMBER,AMBER);
                    return new Room(AMBER, fill1, DESSERT, DESSERT, fill2);
            case BRONZE:
                if (part == 1) return new Room(BRONZE,fill1,fill2,BRONZE,BRONZE);
                return new Room(AMBER, fill1, COPPER, BRONZE, fill2);
            case COPPER:
                if (part == 1) return new Room(COPPER,fill1,fill2,COPPER,COPPER);
                return new Room(AMBER, fill1, BRONZE, AMBER, fill2);
            case DESSERT:
                if (part == 1) return new Room(DESSERT,fill1,fill2,DESSERT,DESSERT);
                return new Room(AMBER, fill1, AMBER, COPPER, fill2);
        }
        throw new RuntimeException("Unknown owner");
    }

    int potentialCost() {
        if (enterState) return 0;
        return IntStream.range(0,4).map(i -> costOfSpot(spots[i],i)).sum();
    }

    private int costOfSpot(Amphiod a, int depth) {
        if (a == null) return 0;
        int me = roomOwners.index;
        int distance = Math.abs(me - a.index) + depth;
        return distance * a.stepcost;
    }
}
