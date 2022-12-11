package com.janoz.aoc.y2022.day11;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

public class Monkey {

    private long itemsInspected = 0;
    private Deque<Item> items = new LinkedList<>();
    private List<Monkey> monkeys;
    private LongFunction<Long> duringInspection;
    private LongFunction<Long> operation;
    private LongToIntFunction targetter;


    public void doInspections() {
        while (!items.isEmpty()) {
            Item i = items.poll();
            monkeys.get(inspectItem(i)).items.add(i);
        }
    }
    public void postParse(List<Monkey> monkeys, LongFunction<Long> duringInspection) {
        this.monkeys = monkeys;
        this.duringInspection = duringInspection;
    }

    public long getItemsInspected() {
        return itemsInspected;
    }

    private int inspectItem(Item item) {
        itemsInspected++;
        return targetter.applyAsInt(
                item.worryLevel =
                        duringInspection.apply(operation.apply(item.worryLevel)));
    }

    private void parseOperation(String operation) {
        assert(operation.startsWith("Operation: new = old "));
        if ("old".equals(operation.substring(23))) {
            this.operation = l->l*l;
        } else {
            char op = operation.charAt(21);
            long opVal = Long.parseLong(operation.substring(23));
            if ('+' == op) {
                this.operation = l->l+opVal;
            } else {
                this.operation = l->l*opVal;
            }
        }
    }

    private void parseTest(Iterator<String> i) {
        long divVal = Long.parseLong(i.next().substring(19));
        int trueTarget = Integer.parseInt(i.next().substring(25));
        int falseTarget = Integer.parseInt(i.next().substring(26));
        targetter = l -> (l % divVal == 0)? trueTarget: falseTarget;
    }

    public static Monkey parse(Iterator<String> it) {
        Monkey m = new Monkey();
        it.next();
        m.items.addAll(Item.parseItems(it.next()));
        m.parseOperation(it.next());
        m.parseTest(it);
        if (it.hasNext()) it.next(); // empty line
        return m;
    }
}
