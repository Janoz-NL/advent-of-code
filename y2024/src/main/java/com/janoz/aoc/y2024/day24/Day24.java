package com.janoz.aoc.y2024.day24;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.input.AocInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day24 {

    static Map<String, BoolNode> nodes = new HashMap<>();
    static Map<String, BoolNode> opNodes = new HashMap<>();
    static BiFunction<Boolean,Boolean,Boolean> AND = (a, b) -> a && b;
    static BiFunction<Boolean,Boolean,Boolean> OR = (a,b) -> a || b;
    static BiFunction<Boolean,Boolean,Boolean> XOR = (a,b) -> a ^ b;

    public static void main(String[] args) {
        AocInput<String> input = AocInput.of(2024,24);
        StopWatch.start();
        readInput(input);
        System.out.println("Part 1 : " + part1());
        System.out.println("Part 2 : " + part2());
        StopWatch.stopPrint();
    }

    static long part1() {
        return toLong(nodes.keySet().stream()
                .filter(x -> x.charAt(0) == 'z')
                .sorted()
                .map(nodes::get)
                .map(BoolNode::getValue)
                .collect(Collectors.toList()));
    }

    static long toLong(List<Boolean> bits) {
        long result = 0;
        for (int i=bits.size()-1; i>=0; i--) {
            result <<= 1;
            if (bits.get(i)) result++;
        }
        return result;
    }

    static String part2() {
        List<String> errors = new ArrayList<>();
        String carry;
        try {
            carry = findFirstCarry();
        } catch (RecoverableException r) {
            errors.add(r.output1);
            errors.add(r.output2);
            fixError(r.output1, r.output2);
            carry = findFirstCarry();
        }
        int i=1;
        while (carry != null) {
            try {
                carry = findNextCarry(i, carry);
                i++;
            } catch (RecoverableException r) {
                errors.add(r.output1);
                errors.add(r.output2);
                fixError(r.output1, r.output2);
            }
        }
        return errors.stream().sorted().collect(Collectors.joining(","));
    }

    /*
     * X00 ^ Y00 = Z00
     * X00 & Y00 = C01
     */
    static String findFirstCarry() {
        BoolNode z00a = findByLogic(XOR, "x00", "y00").orElseThrow(() -> new NonRecoverableException("X00 ^ Y00 not found"));
        BoolNode z00b = nodes.get("z00");
        if (!z00a.name.equals(z00b.name)) throw new RecoverableException(z00a.name, z00b.name);
        BoolNode carry = findByLogic(AND, "x00", "y00").orElseThrow(() -> new NonRecoverableException("Error at first carry"));
        return carry.name;
    }

    /*
     * Xx ^ Yx = Ax
     * Cx ^ Ax = Zx
     * Ax & Cx = Bx
     * Xx & Yx = Dx
     * Bx | Dx = C(x+1)
     */
    static String findNextCarry(int bit, String previousCarry) {
        String nr = String.format("%02d", bit);
        BoolNode zNode = nodes.get("z" + nr);
        String a = null;
        String b = null;
        String c = null;
        String d = null;
        String z = null;
        try {
            if (previousCarry.equals(zNode.name)) {
                //DONE
                return null;
            }
            a = findByLogic(XOR,"x" + nr, "y" + nr).orElseThrow(() -> new NonRecoverableException("Xx ^ Yx not found")).name;
            d = findByLogic(AND, "x" + nr, "y" + nr).orElseThrow(() -> new NonRecoverableException("Xx & Yx not found")).name;
            z = findByLogic(XOR, a, previousCarry).map(n -> n.name).orElse(null);
            if (z == null) {
                throw new RecoverableException(
                        a,
                        otherInput(zNode,previousCarry)
                                .orElseThrow(() -> new NonRecoverableException("Cx ^ Ax = Zx not found")));
            }
            if (!z.equals(zNode.name)) throw new RecoverableException(z, zNode.name);
            b = findByLogic(AND, a, previousCarry).orElseThrow(() -> new NonRecoverableException("Ax & Cx not found")).name;
            c = findByLogic(OR, b, d).orElseThrow(() -> new NonRecoverableException("Bx | Dx not found")).name;
            return c;
        } catch (NonRecoverableException r) {

            System.out.println("Invalid construction at bit " + bit + " : " + r.getMessage());
            System.out.println("c" + bit + " was " + previousCarry);
            System.out.println("a" + bit + " is " + a);
            System.out.println("b" + bit + " is " + b);
            System.out.println("d" + bit + " is " + d);
            System.out.println("c" + (bit + 1) + " is " + c);
            System.out.println("z" + bit + " is " + z);

            List<String> ns = List.of("x" + nr, "y" + nr, "z" + nr, previousCarry);
            opNodes.values().stream().filter(n -> {
                List<String> l = new ArrayList<>(ns);
                l.retainAll(n.inputs);
                return !l.isEmpty();
            }).forEach(System.out::println);

            return null;
        }
    }

    static Optional<String> otherInput(BoolNode node, String input) {
        return node.inputs.stream().filter(n -> !n.equals(input)).findFirst();
    }

    static Optional<BoolNode> findByLogic(BiFunction<Boolean,Boolean,Boolean> logic, String... inputs) {
        List<String> l = Arrays.asList(inputs);
        return opNodes.values().stream()
                .filter(n -> n.op == logic)
                .filter(n -> n.inputs.containsAll(l))
                .findAny();
    }

    static void fixError(String s1, String s2) {
        BoolNode n1 = nodes.get(s1);
        BoolNode n2 = nodes.get(s2);
        n1.name = s2;
        n2.name = s1;
        nodes.put(s1, n2);
        nodes.put(s2, n1);
    }

    static void readInput(AocInput<String> input) {
        Iterator<String> it = input.iterator();
        while (it.hasNext()) {
            BoolNode node = BoolNode.fromValue(it.next());
            nodes.put(node.name,node);
        }
        it = input.iterator();
        while (it.hasNext()) {
            BoolNode node = BoolNode.fromOp(it.next());
            nodes.put(node.name,node);
            opNodes.put(node.name,node);
        }
    }

    static class BoolNode {
        String name;
        Boolean value;
        BiFunction<Boolean, Boolean, Boolean> op;
        Set<String> inputs;
        Boolean getValue() {
            if (value == null) {
                Iterator<String> it = inputs.iterator();
                value = op.apply(nodes.get(it.next()).getValue(), nodes.get(it.next()).getValue());
            }
            return value;
        }

        static BoolNode fromValue(String line) {
            BoolNode node = new BoolNode();
            node.name = line.substring(0, 3);
            node.value = line.charAt(5) == '1';
            return node;
        }

        static BoolNode fromOp(String line) {
            BoolNode node = new BoolNode();
            int i;
            if (line.length() == 17) {
                i=0;
                node.op = OR;
            } else {
                i=1;
                node.op = line.charAt(4) == 'X' ? XOR : AND;
            }
            node.name = line.substring(14+i);
            node.inputs = new HashSet<>();
            node.inputs.add(line.substring(0, 3));
            node.inputs.add(line.substring(7+i, 10+i));
            return node;
        }

        public String toString() {
            if (op == null) {
                return name;
            }
            StringBuilder sb = new StringBuilder();
            Iterator<String> it = inputs.iterator();
            String i1 = it.next();
            String i2 = it.next();
            sb.append(name);
            sb.append(" = ");
            sb.append(i1);
            if (op == AND) sb.append(" & " );
            if (op == OR) sb.append(" | " );
            if (op == XOR) sb.append(" ^ " );
            sb.append(i2);
            return sb.toString();
        }
    }

    static class RecoverableException extends RuntimeException {
        String output1;
        String output2;

        public RecoverableException(String output1, String output2) {
            this.output1 = output1;
            this.output2 = output2;
        }
    }

    static class NonRecoverableException extends RuntimeException {
        public NonRecoverableException(String message) {
            super(message);
        }
    }

}