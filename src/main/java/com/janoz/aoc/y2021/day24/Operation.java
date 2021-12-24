package com.janoz.aoc.y2021.day24;

import java.util.function.BiConsumer;

public class Operation {

    static final BiConsumer<Parameter,Parameter> ADD = (a,b) -> a.put(a.get() + b.get());
    static final BiConsumer<Parameter,Parameter> MUL = (a,b) -> a.put(a.get() * b.get());
    static final BiConsumer<Parameter,Parameter> DIV = (a,b) -> a.put(a.get() / b.get());
    static final BiConsumer<Parameter,Parameter> MOD = (a,b) -> {
        long val = a.get();
        if (val < 0) throw new ArithmeticException("mod of negative");
        a.put(val % b.get());
    };
    static final BiConsumer<Parameter,Parameter> EQL = (a,b) -> a.put(a.get() == b.get()?1:0);

    static final BiConsumer<Parameter,Parameter> INP = (a,b) -> a.put(b.get());



    private BiConsumer<Parameter, Parameter> op;
    private Parameter parA;
    private Parameter parB;

    void execute() {
        op.accept(parA,parB);
    }

    Operation(ALU alu, String input) {
        String[] parts = input.split(" ");
        parA = Parameter.parse(parts[1], alu);
        if (parts[0].equals("inp")) {
            op = INP;
            parB = Parameter.input(alu);
        } else {
            op = getOp(parts[0]);
            parB = Parameter.parse(parts[2], alu);
        }
    }

    private static BiConsumer<Parameter,Parameter> getOp(String op) {
        switch (op) {
            case "add": return ADD;
            case "mul": return MUL;
            case "div": return DIV;
            case "mod": return MOD;
            case "eql": return EQL;
        }
        throw new RuntimeException("Unknown operation " + op);
    }



}
