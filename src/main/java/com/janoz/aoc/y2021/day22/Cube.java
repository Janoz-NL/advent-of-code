package com.janoz.aoc.y2021.day22;

import com.janoz.aoc.geo.Point3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Cube {

    final Point3D min;
    final Point3D max;

    public Cube(Point3D min, Point3D max) {
        this.min = min;
        this.max = max;
    }

    public Cube(long xs, long ys, long zs, long xe, long ye, long ze) {
        this(new Point3D(xs,ys,zs), new Point3D(xe,ye,ze));
    }

    public long volumne() {
        Point3D size = min.findTranslationTo(max);
        return size.x * size.y * size.z;
    }

    public boolean isValid() {
        return
                min.x <= max.x &&
                min.y <= max.y &&
                min.z <= max.z;
    }

    public boolean intersects(Cube other) {
        return
            min.isBelow(other.max) && max.isAbove(other.min) &&
            min.isLeft(other.max) && max.isRight(other.min) &&
            min.isAfter(other.max) && max.isBefore(other.min);
    }

    public boolean contains(Cube other) {
        return
            !other.min.isBelow(min) && !other.min.isLeft(min)  && !other.min.isAfter(min) &
                    !other.max.isAbove(max) && !other.max.isRight(max) && !other.max.isBefore(max);

    }

    private List<Long> toPlanes(Long... longs) {
        SortedSet<Long> result = new TreeSet<>(Arrays.asList(longs));
        return new ArrayList<>(result);
    }

    /**
     *
     * @param other other cube
     * @return [0] this, [2] other
     */
    public Set<Cube>[] cut(Cube other) {
        List<Long> xs = toPlanes(this.min.x, this.max.x, other.min.x, other.max.x);
        List<Long> ys = toPlanes(this.min.y, this.max.y, other.min.y, other.max.y);
        List<Long> zs = toPlanes(this.min.z, this.max.z, other.min.z, other.max.z);
        Cube c;
        Set<Cube> forThis = new HashSet<>();
        Set<Cube> forOther = new HashSet<>();
        for (int x=1; x<xs.size(); x++) {
            for (int y=1; y<ys.size(); y++) {
                for (int z = 1; z < zs.size(); z++) {
                    c = new Cube(xs.get(x - 1), ys.get(y - 1), zs.get(z - 1), xs.get(x), ys.get(y), zs.get(z));
                    if (c.isValid()) {
                        if (this.contains(c)) forThis.add(c);
                        if (other.contains(c)) forOther.add(c);
                    }
                }
            }
        }
        return new Set[]{forThis, forOther};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return Objects.equals(min, cube.min) && Objects.equals(max, cube.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}
