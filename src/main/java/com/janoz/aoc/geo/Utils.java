package com.janoz.aoc.geo;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class Utils {

    /**
     * wraps a predicate for a 'from -> to' test with a boundscheck for 'to'
     *
     * @param width width of bounds
     * @param height height of bounds
     * @param routePredicate predicate to check if 'to' a legal move from 'from' regardless of bounds
     * @return predicate that checks routePredicate and bounds
     */
    public static BiFunction<Point, Point, Boolean> boundsCheckWrapperForTo(int width, int height, BiFunction<Point, Point, Boolean> routePredicate) {
        return boundsCheckWrapperForTo(Point.boundsPredicate(width,height), routePredicate);
    }

    /**
     * wraps a predicate for a 'from -> to' test with a boundscheck for 'to'
     *
     * @param boundsCheck predicate to check if 'to' is within bounds
     * @param routePredicate predicate to check if 'to' is a legal move from 'from' regardless of bounds
     * @return predicate that checks routePredicate and bounds
     */
    public static BiFunction<Point, Point, Boolean> boundsCheckWrapperForTo(Predicate<Point> boundsCheck, BiFunction<Point, Point, Boolean> routePredicate) {
        return (from,to) -> boundsCheck.test(to) && routePredicate.apply(from,to);
    }
}
