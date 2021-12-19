package com.janoz.aoc.y2021.day18;

import java.util.Optional;

public class Pair {

    Pair parent;
    Pair left;
    Pair right;
    int val;

    Pair(int val) {
        this(val,null);
    }

    Pair(int val, Pair parent) {
        this.val = val;
        this.parent = parent;
    }

    Pair(Pair left, Pair right) {
        this.left = left;
        this.left.parent = this;
        this.right = right;
        this.right.parent = this;
    }


    public long magnitude() {
        if (isLeaf()) return val;
        return 3 * left.magnitude() + 2 * right.magnitude();
    }

    void reduce() {
        boolean done=false;
        while (!done) {
            if (!explode()) done = !split();
        }
    }

    boolean explode() {
        return explode(0);
    }

    boolean split() {
        if (isLeaf()) {
            if (val > 9) {
                left = new Pair(val / 2, this);
                right = new Pair(val - left.val, this);
                val = 0;
                return true;
            } else {
                return false;
            }
        } else {
            return left.split() || right.split();
        }
    }

    boolean isLeaf() {
        return this.left == null;
    }

    private boolean explode(int depth) {
        if (!isLeaf()) {
            if (depth == 4) {
                travelLeft().ifPresent(r -> r.val += left.val);
                travelRight().ifPresent(r -> r.val += right.val);

                this.left = null;
                this.right = null;
                this.val = 0;
                return true;
            } else {
                return left.explode(depth + 1) || right.explode(depth + 1);
            }
        }
        return false;
    }

    private Optional<Pair> travelRight() {
        Pair result = this;
        while (result.isRight()) {
            result = result.parent;
        }
        result = result.parent;
        if (result == null) return Optional.empty();
        return Optional.of(result.right.travelDownLeft());
    }

    private Optional<Pair> travelLeft() {
        Pair result = this;
        while (result.isLeft()) {
            result = result.parent;
        }
        result = result.parent;
        if (result == null) return Optional.empty();
        return Optional.of(result.left.travelDownRight());
    }

    private Pair travelDownRight() {
        Pair result = this;
        while (result.right != null) {
            result = result.right;
        }
        return result;
    }

    private Pair travelDownLeft() {
        Pair result = this;
        while (result.left != null) {
            result = result.left;
        }
        return result;
    }

    boolean isRight() {
        return parent != null && this.parent.right == this;
    }

    boolean isLeft() {
        return parent != null && this.parent.left == this;
    }

    @Override
    public String toString() {
        if (left == null) {
            return "" + val;
        }
        return "[" + left + "," + right + "]";
    }

    @Override
    public Pair clone() {
        return parse(this.toString());
    }

    static Pair parse(String input) {
        return parse(new Tokenizer(input));
    }

    static Pair parse(Tokenizer input) {
        if (Character.isDigit(input.peek())) {
            return new Pair(input.get() - '0');
        } else {
            input.get(); // [
            Pair left = parse(input);
            input.get(); // ,
            Pair right = parse(input);
            input.get(); // ]
            return new Pair(left,right);
        }
    }

    static Pair add(Pair l, Pair r) {
        if (l == null) return r;
        Pair result = new Pair(l,r);
        result.reduce();
        return result;
    }
}
