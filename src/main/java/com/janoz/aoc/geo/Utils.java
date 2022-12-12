package com.janoz.aoc.geo;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class Utils {

    /**
     * wraps a predicate for a 'from -> to' test with a boundscheck for 'to'
     *
     * @param width width of bounds
     * @param height height of bounds
     * @param routePredicate predicate to check if 'to' is within bounds
     * @return predicate that checks bounds of to
     */
    public static BiFunction<Point, Point, Boolean> boundsCheckWrapperForTo(int width, int height, BiFunction<Point, Point, Boolean> routePredicate) {
        Predicate<Point> pr = Point.boundsPredicate(width,height);
        return (from,to) -> pr.test(to) && routePredicate.apply(from,to);
    }
}
