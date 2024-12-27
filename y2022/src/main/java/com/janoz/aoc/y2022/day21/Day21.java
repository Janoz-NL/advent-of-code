package com.janoz.aoc.y2022.day21;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.collections.AlwaysHashMap;

import java.util.Map;
import java.util.function.LongBinaryOperator;

public class Day21 {

    static Map<String, Monkey> monkeys = new AlwaysHashMap<>(Monkey::new);

    public static void main(String[] args) throws Exception {
        String file = "inputs/2022/day21.txt";
        InputProcessor.asStream(file).forEach(Monkey::parse);
        Monkey root = monkeys.get("root");

        System.out.println("part 1 " + root.getValPart1());

        root.fillValPart2();
        if (root.valLeft == null) {
            System.out.println("part 2 " + root.left.withWantedAnswer(root.valRight));
        } else {
            System.out.println("part 2 " + root.right.withWantedAnswer(root.valLeft));
        }
    }

    static class Monkey {
        private static final String HUMAN = "humn";

        private final String name;

        private Long val;
        Monkey left;
        Monkey right;

        private LongBinaryOperator operator;
        private LongBinaryOperator operatorMakingLeft;
        private LongBinaryOperator operatorMakingRight;

        private Long valLeft;
        private Long valRight;

        Monkey(String name) {
            this.name = name;
        }

        long getValPart1() {
            if (operator != null)
                return operator.applyAsLong(
                        left.getValPart1(),
                        right.getValPart1());
            return val;
        }

        Long fillValPart2() {
            if (HUMAN.equals(name)) return null;
            if (val!=null) return val;
            valLeft = left.fillValPart2();
            valRight = right.fillValPart2();
            if (valLeft != null && valRight != null) return operator.applyAsLong(valLeft, valRight);
            return null;
        }

        Long withWantedAnswer(Long answer) {
            if (HUMAN.equals(name)) return answer;
            if (valLeft == null) {
                return left.withWantedAnswer(operatorMakingLeft.applyAsLong(valRight, answer));
            } else {
                return right.withWantedAnswer(operatorMakingRight.applyAsLong(valLeft, answer));
            }
        }

        static Monkey parse(String s) {
            Monkey result = monkeys.get(s.substring(0,4));
            if (s.length() == 17) {
                result.left = monkeys.get(s.substring(6,10));
                result.right = monkeys.get(s.substring(13));
                switch (s.charAt(11)) {
                    case '+': // a + b = c
                        result.operator =  (a,b) -> a + b;
                        result.operatorMakingLeft = (b,c) -> c - b;
                        result.operatorMakingRight = (a,c) -> c - a;
                        break;
                    case '-': // a - b = c
                        result.operator =  (a,b) -> a - b;
                        result.operatorMakingLeft = (b,c) -> b + c;
                        result.operatorMakingRight = (a,c) -> -c + a;
                        break;
                    case '*': // a * b = c
                        result.operator =  (a,b) -> a * b;
                        result.operatorMakingLeft = (b,c) -> c / b;
                        result.operatorMakingRight = (a,c) -> c / a;
                        break;
                    case '/': // a / b = c
                        result.operator =  (a,b) -> a / b;
                        result.operatorMakingLeft = (b,c) -> b * c;
                        result.operatorMakingRight = (a,c) -> a / c;
                        break;
                    default: throw new RuntimeException("unknown char");
                }
            } else {
                result.val = Long.parseLong(s.substring(6));
            }
            return result;
        }

        @Override
        public String toString() {
            return "Monkey{" +
                    "name='" + name + '\'' +
                    ", val=" + val +
                    ", left='" + left.name + '\'' +
                    ", right='" + right.name + '\'' +
                    '}';
        }
    }
}
