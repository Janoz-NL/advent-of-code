package com.janoz.aoc.y2021.day23;

import org.junit.jupiter.api.Test;

import static com.janoz.aoc.y2021.day23.Amphiod.*;
import static com.janoz.aoc.y2021.day23.Amphiod.COPPER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HallTest {

    @Test
    void testRoom2HallRoutes() {
        Hall hall;
        hall = new Hall(null, new Amphiod[]{null, null, null, null, null, null, null},0);
        assertThat(hall.routeAvailableR2H(0, 6), equalTo(true));
        hall = new Hall(null, new Amphiod[]{null, null, AMBER, null, null, null, null},0);
        assertThat(hall.routeAvailableR2H(0, 6), equalTo(false));
        assertThat(hall.routeAvailableR2H(0, 2), equalTo(false));
        assertThat(hall.routeAvailableR2H(0, 1), equalTo(true));
        hall = new Hall(null, new Amphiod[]{null, AMBER, null, null, null, null, null},0);
        assertThat(hall.routeAvailableR2H(0, 6), equalTo(true));
        assertThat(hall.routeAvailableR2H(0, 1), equalTo(false));
    }


    @Test
    void testHall2RoomRoutes() {
        Hall hall;
        hall = new Hall(null, new Amphiod[]{null, null, AMBER, null, DESSERT, null, null},0);
        assertThat(hall.routeAvailableH2R(0, 0), equalTo(true));
        assertThat(hall.routeAvailableH2R(2, 1), equalTo(true));
        assertThat(hall.routeAvailableH2R(2, 2), equalTo(true));
        assertThat(hall.routeAvailableH2R(2, 3), equalTo(false));
    }


    @Test
    void testRoom2RoomRoutes() {
        Hall hall;
        hall = new Hall(null, new Amphiod[]{null, AMBER, null, null, null, null, null},0);
        assertThat(hall.routeAvailableR2R(0, 3), equalTo(true));
        assertThat(hall.routeAvailableR2R(3, 0), equalTo(true));
        assertThat(hall.routeAvailableR2R(0, 1), equalTo(true));
        assertThat(hall.routeAvailableR2R(1, 0), equalTo(true));
        assertThat(hall.routeAvailableR2R(2, 3), equalTo(true));
        assertThat(hall.routeAvailableR2R(3, 2), equalTo(true));

        hall = new Hall(null, new Amphiod[]{null, null, AMBER, null, null, null, null},0);
        assertThat(hall.routeAvailableR2R(0, 3), equalTo(false));
        assertThat(hall.routeAvailableR2R(3, 0), equalTo(false));
        assertThat(hall.routeAvailableR2R(0, 1), equalTo(false));
        assertThat(hall.routeAvailableR2R(1, 0), equalTo(false));
        assertThat(hall.routeAvailableR2R(2, 3), equalTo(true));
        assertThat(hall.routeAvailableR2R(3, 2), equalTo(true));
    }

    @Test
    void testExample1() {
        Hall hall = new Hall(new Room[]{
                Room.initialRoom(AMBER, 1, BRONZE, AMBER),
                Room.initialRoom(BRONZE, 1, COPPER, DESSERT),
                Room.initialRoom(COPPER, 1, BRONZE, COPPER),
                Room.initialRoom(DESSERT, 1, DESSERT, AMBER)
        });
        hall = hall.nextR2H(2,2).get();
        assertThat(hall.score, equalTo(40));
        assertThat(hall.isDone(), equalTo(false));

        hall = hall.nextR2R(1).get();
        assertThat(hall.score, equalTo(440));
        assertThat(hall.isDone(), equalTo(false));

        hall = hall.nextR2H(1,3).get();
        assertThat(hall.score, equalTo(3440));
        assertThat(hall.isDone(), equalTo(false));

        hall = hall.nextH2R(2).get();
        assertThat(hall.score, equalTo(3470));
        assertThat(hall.isDone(), equalTo(false));

        hall = hall.nextR2R(0).get();
        assertThat(hall.score, equalTo(3510));
        assertThat(hall.isDone(), equalTo(false));


        hall = hall.nextR2H(3,4).get();
        assertThat(hall.isDone(), equalTo(false));

        hall = hall.nextR2H(3,5).get();
        assertThat(hall.score, equalTo(5513));
        assertThat(hall.isDone(), equalTo(false));

        hall = hall.nextH2R(4).get();
        assertThat(hall.isDone(), equalTo(false));

        hall = hall.nextH2R(3).get();
        assertThat(hall.score, equalTo(12513));
        assertThat(hall.isDone(), equalTo(false));

        hall = hall.nextH2R(5).get();
        assertThat(hall.score, equalTo(12521));
        assertThat(hall.isDone(), equalTo(true));
    }
}