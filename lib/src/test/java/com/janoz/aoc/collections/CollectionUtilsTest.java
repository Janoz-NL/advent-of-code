package com.janoz.aoc.collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class CollectionUtilsTest {

    @Test
    void testSuperSet() {
        Collection<Set<Element>> actual = CollectionUtils.superSets(Set.of(Element.A,Element.B,Element.C, Element.D),1,2);
        assertThat(actual).hasSize(10)
                .contains(Set.of(Element.A))
                .contains(Set.of(Element.B))
                .contains(Set.of(Element.C))
                .contains(Set.of(Element.D))
                .contains(Set.of(Element.A, Element.B))
                .contains(Set.of(Element.A, Element.C))
                .contains(Set.of(Element.A, Element.D))
                .contains(Set.of(Element.B, Element.C))
                .contains(Set.of(Element.B, Element.D))
                .contains(Set.of(Element.C, Element.D));
    }

    @Test
    void testSuperSetAgain() {
        Collection<Set<Element>> actual = CollectionUtils.superSets(Set.of(Element.A,Element.B,Element.C, Element.D),2,4);
        assertThat(actual).hasSize(11)
                .contains(Set.of(Element.A, Element.B))
                .contains(Set.of(Element.A, Element.C))
                .contains(Set.of(Element.A, Element.D))
                .contains(Set.of(Element.B, Element.C))
                .contains(Set.of(Element.B, Element.D))
                .contains(Set.of(Element.C, Element.D))
                .contains(Set.of(Element.A, Element.B, Element.C))
                .contains(Set.of(Element.A, Element.B, Element.D))
                .contains(Set.of(Element.A, Element.C, Element.D))
                .contains(Set.of(Element.B, Element.C, Element.D))
                .contains(Set.of(Element.A, Element.B, Element.C, Element.D));
    }

    @Test
    void testSuperSetWithEmptySet() {
        Collection<Set<Element>> actual = CollectionUtils.superSets(Set.of(Element.A,Element.B,Element.C, Element.D),0,2);
        assertThat(actual).hasSize(11)
                .contains(Collections.emptySet());
    }

    @Test
    void testCombinations() {
        assertThat(CollectionUtils.combinations(Set.of(
                Element.A,
                Element.B,
                Element.C),0,1)).containsExactlyInAnyOrder(
                        Collections.emptySet(),
                        Collections.singleton(Element.A),
                        Collections.singleton(Element.B),
                        Collections.singleton(Element.C)
        );

        assertThat(CollectionUtils.combinations(Set.of(
                Element.A,
                Element.B,
                Element.C),0,2)).containsExactlyInAnyOrder(
                        Collections.emptySet(),
                        Collections.singleton(Element.A),
                        Collections.singleton(Element.B),
                        Collections.singleton(Element.C),
                        new HashSet<>(Arrays.asList(Element.B,Element.A)),
                        new HashSet<>(Arrays.asList(Element.C,Element.A)),
                        new HashSet<>(Arrays.asList(Element.B,Element.C))
        );
    }

    private enum Element {
        A,B,C,D,E,F
    }
}