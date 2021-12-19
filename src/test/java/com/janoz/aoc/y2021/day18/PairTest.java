package com.janoz.aoc.y2021.day18;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class PairTest {


    @Test
    void testParse() {
        assertThat(Pair.parse("[1,2]").toString(), equalTo("[1,2]"));
        assertThat(Pair.parse("[[[[1,2],[3,4]],[[5,6],[7,8]]],9]").toString(), equalTo("[[[[1,2],[3,4]],[[5,6],[7,8]]],9]"));
    }

    @Test
    void testExplode() {
        Pair p;
        p = Pair.parse("[[[[[9,8],1],2],3],4]");
        assertThat(p.explode(), equalTo(true));
        assertThat(p.toString(),equalTo("[[[[0,9],2],3],4]"));
        p = Pair.parse("[7,[6,[5,[4,[3,2]]]]]");
        assertThat(p.explode(), equalTo(true));
        assertThat(p.toString(),equalTo("[7,[6,[5,[7,0]]]]"));
        p = Pair.parse("[[6,[5,[4,[3,2]]]],1]");
        assertThat(p.explode(), equalTo(true));
        assertThat(p.toString(),equalTo("[[6,[5,[7,0]]],3]"));
        p = Pair.parse("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
        assertThat(p.explode(), equalTo(true));
        assertThat(p.toString(),equalTo("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"));
        p = Pair.parse("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
        assertThat(p.explode(), equalTo(true));
        assertThat(p.toString(),equalTo("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"));


        p = Pair.parse("[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]");
        assertThat(p.explode(), equalTo(true));
        assertThat(p.toString(),equalTo("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"));

    }

    @Test
    void testSplit() {
        Pair p = new Pair(10);
        assertThat(p.split(), equalTo(true));
        assertThat(p.toString(), equalTo("[5,5]"));
        p = new Pair(11);
        assertThat(p.split(), equalTo(true));
        assertThat(p.toString(), equalTo("[5,6]"));
        p = new Pair(12);
        assertThat(p.split(), equalTo(true));
        assertThat(p.toString(), equalTo("[6,6]"));

        p = Pair.parse(("[1,1]"));
        p.left.val=11;
        p.right.val=11;
        assertThat(p.split(), equalTo(true));
        assertThat(p.toString(), equalTo("[[5,6],11]"));
    }

    @Test
    void testReduce() {
        Pair left = Pair.parse("[[[[4,3],4],4],[7,[[8,4],9]]]");
        Pair right = Pair.parse("[1,1]");
        Pair p = new Pair(left, right);
        p.reduce();
        assertThat(p.toString(), equalTo("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"));
    }

    @Test
    void testAdd() {
        Pair sum = null;
        sum = Pair.add(sum,Pair.parse("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]"));
        assertThat(sum.toString(), equalTo("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]"));
        sum = Pair.add(sum,Pair.parse("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]"));
        assertThat(sum.toString(), equalTo("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]"));
        sum = Pair.add(sum,Pair.parse("[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]"));
        assertThat(sum.toString(), equalTo("[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]"));
    }

    @Test
    void testMagnitude() {
        assertThat(Pair.parse("[[1,2],[[3,4],5]]").magnitude(), equalTo(143L));
        assertThat(Pair.parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").magnitude(), equalTo(1384L));
        assertThat(Pair.parse("[[[[1,1],[2,2]],[3,3]],[4,4]]").magnitude(), equalTo(445L));
        assertThat(Pair.parse("[[[[3,0],[5,3]],[4,4]],[5,5]]").magnitude(), equalTo(791L));
        assertThat(Pair.parse("[[[[5,0],[7,4]],[5,5]],[6,6]]").magnitude(), equalTo(1137L));
        assertThat(Pair.parse("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").magnitude(), equalTo(3488L));
    }

}