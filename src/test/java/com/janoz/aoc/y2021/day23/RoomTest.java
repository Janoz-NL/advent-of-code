package com.janoz.aoc.y2021.day23;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.janoz.aoc.y2021.day23.Amphiod.*;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class RoomTest {

    @Test
    void testRoomCreation() {
        Room r;
        r = new Room(AMBER, null,null,null,null);
        assertThat(r.stepsInto()).isEqualTo(4);
        assertThat(r.enterState).isTrue();
        assertThat(r.done).isFalse();

        r = new Room(AMBER, null,null,AMBER,AMBER);
        assertThat(r.stepsInto()).isEqualTo(2);
        assertThat(r.stepsOutof()).isEqualTo(3);
        assertThat(r.enterState).isTrue();
        assertThat(r.done).isFalse();
        assertThat(r.top()).isEqualTo(AMBER);

        r = new Room(AMBER, AMBER,AMBER,AMBER,AMBER);
        assertThat(r.enterState).isFalse();
        assertThat(r.done).isTrue();
        assertThat(r.top()).isEqualTo(AMBER);

        r = new Room(DESSERT, AMBER,AMBER,AMBER,AMBER);
        assertThat(r.enterState).isFalse();
        assertThat(r.done).isFalse();
        assertThat(r.top()).isEqualTo(AMBER);

        r = new Room(DESSERT, null,null,AMBER,AMBER);
        assertThat(r.enterState).isFalse();
        assertThat(r.done).isFalse();
        assertThat(r.top()).isEqualTo(AMBER);
    }

    @Test
    void roomEnter() {
        Room r;
        r = new Room(AMBER, null,null,null,null).enter();
        assertThat(r.stepsInto()).isEqualTo(3);
        assertThat(r.stepsOutof()).isEqualTo(4);
        assertThat(r.enterState).isTrue();
        assertThat(r.done).isFalse();

        r = r.enter();
        assertThat(r.stepsInto()).isEqualTo(2);
        assertThat(r.stepsOutof()).isEqualTo(3);
        assertThat(r.enterState).isTrue();
        assertThat(r.done).isFalse();

        r = r.enter();
        assertThat(r.stepsInto()).isEqualTo(1);
        assertThat(r.stepsOutof()).isEqualTo(2);
        assertThat(r.enterState).isTrue();
        assertThat(r.done).isFalse();

        r = r.enter();
        assertThat(r.stepsInto()).isEqualTo(0);
        assertThat(r.stepsOutof()).isEqualTo(1);
        assertThat(r.enterState).isFalse();
        assertThat(r.done).isTrue();
    }

    @Test
    void roomLeave() {
        Room r = new Room(DESSERT, DESSERT,COPPER,BRONZE,AMBER);
        assertThat(r.enterState).isFalse();
        assertThat(r.stepsOutof()).isEqualTo(1);
        assertThat(r.top()).isEqualTo(DESSERT);

        r = r.leave();
        assertThat(r.enterState).isFalse();
        assertThat(r.stepsOutof()).isEqualTo(2);
        assertThat(r.top()).isEqualTo(COPPER);

        r = r.leave();
        assertThat(r.enterState).isFalse();
        assertThat(r.stepsOutof()).isEqualTo(3);
        assertThat(r.top()).isEqualTo(BRONZE);

        r = r.leave();
        assertThat(r.enterState).isFalse();
        assertThat(r.stepsOutof()).isEqualTo(4);
        assertThat(r.top()).isEqualTo(AMBER);

        r = r.leave();
        assertThat(r.enterState).isTrue();
        assertThat(r.stepsInto()).isEqualTo(4);
    }

}