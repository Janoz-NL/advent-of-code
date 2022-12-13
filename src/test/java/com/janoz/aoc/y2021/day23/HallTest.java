package com.janoz.aoc.y2021.day23;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.janoz.aoc.y2021.day23.Amphiod.*;
import static com.janoz.aoc.y2021.day23.Amphiod.COPPER;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class HallTest {

    @Test
    void testRoom2HallRoutes() {
        Hall hall;
        hall = new Hall(null, new Amphiod[]{null, null, null, null, null, null, null},0);
        assertThat(hall.routeAvailableR2H(0, 6)).isTrue();
        hall = new Hall(null, new Amphiod[]{null, null, AMBER, null, null, null, null},0);
        assertThat(hall.routeAvailableR2H(0, 6)).isFalse();
        assertThat(hall.routeAvailableR2H(0, 2)).isFalse();
        assertThat(hall.routeAvailableR2H(0, 1)).isTrue();
        hall = new Hall(null, new Amphiod[]{null, AMBER, null, null, null, null, null},0);
        assertThat(hall.routeAvailableR2H(0, 6)).isTrue();
        assertThat(hall.routeAvailableR2H(0, 1)).isFalse();
    }


    @Test
    void testHall2RoomRoutes() {
        Hall hall;
        hall = new Hall(null, new Amphiod[]{null, null, AMBER, null, DESSERT, null, null},0);
        assertThat(hall.routeAvailableH2R(0, 0)).isTrue();
        assertThat(hall.routeAvailableH2R(2, 1)).isTrue();
        assertThat(hall.routeAvailableH2R(2, 2)).isTrue();
        assertThat(hall.routeAvailableH2R(2, 3)).isFalse();
    }


    @Test
    void testRoom2RoomRoutes() {
        Hall hall;
        hall = new Hall(null, new Amphiod[]{null, AMBER, null, null, null, null, null},0);
        assertThat(hall.routeAvailableR2R(0, 3)).isTrue();
        assertThat(hall.routeAvailableR2R(3, 0)).isTrue();
        assertThat(hall.routeAvailableR2R(0, 1)).isTrue();
        assertThat(hall.routeAvailableR2R(1, 0)).isTrue();
        assertThat(hall.routeAvailableR2R(2, 3)).isTrue();
        assertThat(hall.routeAvailableR2R(3, 2)).isTrue();

        hall = new Hall(null, new Amphiod[]{null, null, AMBER, null, null, null, null},0);
        assertThat(hall.routeAvailableR2R(0, 3)).isFalse();
        assertThat(hall.routeAvailableR2R(3, 0)).isFalse();
        assertThat(hall.routeAvailableR2R(0, 1)).isFalse();
        assertThat(hall.routeAvailableR2R(1, 0)).isFalse();
        assertThat(hall.routeAvailableR2R(2, 3)).isTrue();
        assertThat(hall.routeAvailableR2R(3, 2)).isTrue();
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
        assertThat(hall.score).isEqualTo(40);
        assertThat(hall.isDone()).isFalse();

        hall = hall.nextR2R(1).get();
        assertThat(hall.score).isEqualTo(440);
        assertThat(hall.isDone()).isFalse();

        hall = hall.nextR2H(1,3).get();
        assertThat(hall.score).isEqualTo(3440);
        assertThat(hall.isDone()).isFalse();

        hall = hall.nextH2R(2).get();
        assertThat(hall.score).isEqualTo(3470);
        assertThat(hall.isDone()).isFalse();

        hall = hall.nextR2R(0).get();
        assertThat(hall.score).isEqualTo(3510);
        assertThat(hall.isDone()).isFalse();


        hall = hall.nextR2H(3,4).get();
        assertThat(hall.isDone()).isFalse();

        hall = hall.nextR2H(3,5).get();
        assertThat(hall.score).isEqualTo(5513);
        assertThat(hall.isDone()).isFalse();

        hall = hall.nextH2R(4).get();
        assertThat(hall.isDone()).isFalse();

        hall = hall.nextH2R(3).get();
        assertThat(hall.score).isEqualTo(12513);
        assertThat(hall.isDone()).isFalse();

        hall = hall.nextH2R(5).get();
        assertThat(hall.score).isEqualTo(12521);
        assertThat(hall.isDone()).isTrue();
    }
}