package com.janoz.aoc.y2021.day23;


import org.junit.jupiter.api.Test;

import static com.janoz.aoc.y2021.day23.Amphiod.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomTest {

    @Test
    void testRoomCreation() {
        Room r;
        r = new Room(AMBER, null,null,null,null);
        assertThat(r.stepsInto(), equalTo(4));
        assertThat(r.enterState, equalTo(true));
        assertThat(r.done, equalTo(false));

        r = new Room(AMBER, null,null,AMBER,AMBER);
        assertThat(r.stepsInto(), equalTo(2));
        assertThat(r.stepsOutof(), equalTo(3));
        assertThat(r.enterState, equalTo(true));
        assertThat(r.done, equalTo(false));
        assertThat(r.top(), equalTo(AMBER));

        r = new Room(AMBER, AMBER,AMBER,AMBER,AMBER);
        assertThat(r.enterState, equalTo(false));
        assertThat(r.done, equalTo(true));
        assertThat(r.top(), equalTo(AMBER));

        r = new Room(DESSERT, AMBER,AMBER,AMBER,AMBER);
        assertThat(r.enterState, equalTo(false));
        assertThat(r.done, equalTo(false));
        assertThat(r.top(), equalTo(AMBER));

        r = new Room(DESSERT, null,null,AMBER,AMBER);
        assertThat(r.enterState, equalTo(false));
        assertThat(r.done, equalTo(false));
        assertThat(r.top(), equalTo(AMBER));
    }

    @Test
    void roomEnter() {
        Room r;
        r = new Room(AMBER, null,null,null,null).enter();
        assertThat(r.stepsInto(), equalTo(3));
        assertThat(r.stepsOutof(), equalTo(4));
        assertThat(r.enterState, equalTo(true));
        assertThat(r.done, equalTo(false));

        r = r.enter();
        assertThat(r.stepsInto(), equalTo(2));
        assertThat(r.stepsOutof(), equalTo(3));
        assertThat(r.enterState, equalTo(true));
        assertThat(r.done, equalTo(false));

        r = r.enter();
        assertThat(r.stepsInto(), equalTo(1));
        assertThat(r.stepsOutof(), equalTo(2));
        assertThat(r.enterState, equalTo(true));
        assertThat(r.done, equalTo(false));

        r = r.enter();
        assertThat(r.stepsInto(), equalTo(0));
        assertThat(r.stepsOutof(), equalTo(1));
        assertThat(r.enterState, equalTo(false));
        assertThat(r.done, equalTo(true));
    }

    @Test
    void roomLeave() {
        Room r = new Room(DESSERT, DESSERT,COPPER,BRONZE,AMBER);
        assertThat(r.enterState, equalTo(false));
        assertThat(r.stepsOutof(), equalTo(1));
        assertThat(r.top(), equalTo(DESSERT));

        r = r.leave();
        assertThat(r.enterState, equalTo(false));
        assertThat(r.stepsOutof(), equalTo(2));
        assertThat(r.top(), equalTo(COPPER));

        r = r.leave();
        assertThat(r.enterState, equalTo(false));
        assertThat(r.stepsOutof(), equalTo(3));
        assertThat(r.top(), equalTo(BRONZE));

        r = r.leave();
        assertThat(r.enterState, equalTo(false));
        assertThat(r.stepsOutof(), equalTo(4));
        assertThat(r.top(), equalTo(AMBER));

        r = r.leave();
        assertThat(r.enterState, equalTo(true));
        assertThat(r.stepsInto(), equalTo(4));
    }

}