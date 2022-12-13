package com.janoz.aoc.algorithms;

import java.util.Collection;
import java.util.Collections;

public interface PathFindingAlgorithm<NODE> {

    default long calculate(NODE start) {
        return calculate(Collections.singletonList(start));
    }

    long calculate(Collection<NODE> starts);

    long getDistance(NODE node);

}
