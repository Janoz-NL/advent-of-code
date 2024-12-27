package com.janoz.aoc.y2022.day5;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StackTest {


    @Test
    void testAdd(){
        Stack stack = new Stack();
        stack.add(new Crate('A'));
        stack.add(new Crate('B'));
        stack.add(new Crate('C'));
        assertThat(stack.toString()).isEqualTo("CBA");
    }

    @Test
    void testAddStack(){
        Stack stack1 = new Stack();
        stack1.add(new Crate('D'));
        stack1.add(new Crate('C'));

        Stack stack2 = new Stack();
        stack2.add(new Crate('B'));
        stack2.add(new Crate('A'));

        stack1.add(stack2);
        assertThat(stack1.toString()).isEqualTo("ABCD");
    }


    @Test
    void testAppend(){
        Stack stack = new Stack();
        stack.append(new Crate('A'));
        stack.append(new Crate('B'));
        stack.append(new Crate('C'));
        assertThat(stack.toString()).isEqualTo("ABC");
    }


    @Test
    void testReverse(){
        Stack stack = new Stack();
        stack.add(new Crate('A'));
        stack.add(new Crate('B'));
        stack.add(new Crate('C'));
        stack.reverse();
        assertThat(stack.toString()).isEqualTo("ABC");
    }



}