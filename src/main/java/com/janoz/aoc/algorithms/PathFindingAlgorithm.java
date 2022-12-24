package com.janoz.aoc.algorithms;

import java.util.Collection;
import java.util.Collections;

public interface PathFindingAlgorithm<NODE> {

    default Long calculate(NODE start) {
        return calculate(Collections.singletonList(start));
    }

    Long calculate(Collection<NODE> starts);

    Long getDistance(NODE node);

}
