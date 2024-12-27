package com.janoz.aoc.y2024.day24;

import com.janoz.aoc.InputProcessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day24Manual {




    static BiFunction<Boolean,Boolean,Boolean> AND = (a, b) -> a && b;
    static BiFunction<Boolean,Boolean,Boolean> OR = (a,b) -> a || b;
    static BiFunction<Boolean,Boolean,Boolean> XOR = (a,b) -> a ^ b;

    static Map<String, BoolNode> nodes = new HashMap<>();
    static Set<BoolNode> opNodes = new HashSet<>();

    public static void main(String[] args) {
        Iterator<String> input = InputProcessor.asIterator("inputs/2024/day24.txt");
        String line = input.next();
        while (!line.isEmpty()) {
            BoolNode node = BoolNode.fromValue(line);
            nodes.put(node.name,node);
            line = input.next();
        }
        do {
            line = input.next();
            BoolNode node = BoolNode.fromOp(line);
            nodes.put(node.name,node);
            opNodes.add(node);
        } while (input.hasNext());

        opNodes.forEach(node -> {
            node.left = nodes.get(node.leftName);
            node.right = nodes.get(node.rightName);
        });

        List<Boolean> bits =
        nodes.keySet().stream()
                .filter(x -> x.charAt(0) == 'z')
                .sorted()
                .map(nodes::get)
                .map(BoolNode::getValue)
                .collect(Collectors.toList());
        System.out.println(bits.size());
        System.out.println(toLong(bits));


        System.out.println();

        int i=0;
        String halfAdder;
        String d,d2, candidate;
        do {
            i++;
            halfAdder = printHalfAdder(i);
            d = String.format("%02d", i);
            d2 = String.format("%02d", i + 1);
            candidate = "a"+d+" = x"+d+" ^ y"+d+"\n" +
                    "b"+d+" = a"+d+" & c"+d+"\n" +
                    "d"+d+" = x"+d+" & y"+d+"\n" +
                    "z"+d+" = a"+d+" ^ c"+d+"\n" +
                    "c"+d2+" = b"+d+" | d"+d+"\n";
        } while (halfAdder.equals(candidate));
        System.out.println("Bit " + i);
        System.out.print(halfAdder);
        System.out.println("Candidate Bit " + i);
        System.out.println(printHalfAdderCandidate(i));


        System.out.println(
        errors.entrySet().stream().flatMap(e -> Stream.of(e.getKey(),e.getValue())).sorted().collect(Collectors.joining(",")));
    }

    static String printHalfAdder(int i) {
        String a = rerename(String.format("a%02d", i));
        String b = rerename(String.format("b%02d", i));
        String c = rerename(String.format("c%02d", i+1));
        String d = rerename(String.format("d%02d", i));
        StringBuilder sb = new StringBuilder();
        sb.append(printOutput(a));
        sb.append(printOutput(b));
        sb.append(printOutput(d));
        sb.append(printOutput(String.format("z%02d",i)));
        sb.append(printOutput(c));
        return sb.toString();
    }

    static String printHalfAdderCandidate(int i) {
        String a = rerename(String.format("a%02d", i));
        String b = rerename(String.format("b%02d", i));
        String c1 = rerename(String.format("c%02d", i));
        String c2 = rerename(String.format("c%02d", i+1));
        String d = rerename(String.format("d%02d", i));

        StringBuilder sb = new StringBuilder();
        sb.append(print(String.format("x%02d", i)));
        sb.append(printOutput(a));
        sb.append(print(c1));
        sb.append(printOutput(String.format("z%02d", i)));
        sb.append(printOutput(b));
        sb.append(printOutput(d));
        sb.append(print(b));
        sb.append(print(d));
        sb.append(printOutput(c2));

        sb.append(printOutput(b));
        sb.append(printOutput(d));
        return sb.toString();
    }

    static Map<String,String> errors;
    static {
        errors = new HashMap<>();
        errors.put("djg", "z12");
        errors.put("sbg", "z19");
        errors.put("mcq", "hjm");
        errors.put("dsd", "z37");
    }

    static Map<String,String> rename;
    static {
        rename = new HashMap<>();
        //1
        rename.put("rvb","a01");
        rename.put("kmb","b01");
        rename.put("kgp","d01");
        rename.put("ktt","c01");
        //02
        rename.put("ssq","a02");
        rename.put("vsc","b02");
        rename.put("rkn","c02");
        rename.put("kwm","d02");
        //03
        rename.put("fbk","a03");
        rename.put("dps","b03");
        rename.put("ntj","c03");
        rename.put("jmr","d03");
        //04
        rename.put("jjc","a04");
        rename.put("gvt","b04");
        rename.put("mpf","c04");
        rename.put("csm","d04");
        //05
        rename.put("kdm","a05");
        rename.put("sch","b05");
        rename.put("cgt","c05");
        rename.put("fjd","d05");

        //06
        rename.put("mhv","a06");
        rename.put("njj","b06");
        rename.put("ftg","c06");
        rename.put("kfb","d06");

        //07
        rename.put("dts","a07");
        rename.put("whm","b07");
        rename.put("gpv","c07");
        rename.put("qss","d07");

        //08
        rename.put("wqw","a08");
        rename.put("rdt","b08");
        rename.put("mtm","c08");
        rename.put("wvw","d08");

        //09
        rename.put("pmb","a09");
        rename.put("pvb","b09");
        rename.put("trw","c09");
        rename.put("fhf","d09");

        //10
        rename.put("hcd","a10");
        rename.put("sqt","b10");
        rename.put("rqp","c10");
        rename.put("wnv","d10");

        //11
        rename.put("fkc","a11");
        rename.put("dcc","b11");
        rename.put("hww","c11");
        rename.put("wkn","d11");

        //12
        rename.put("jsb","a12");
        rename.put("djg","b12");
        rename.put("njf","c12");
        rename.put("djr","d12");

        //13
        rename.put("fnc","a13");
        rename.put("mwj","b13");
        rename.put("nbf","c13");
        rename.put("ntm","d13");

        //14
        rename.put("nbp","a14");
        rename.put("cpr","b14");
        rename.put("tnq","c14");
        rename.put("tmw","d14");

        //15
        rename.put("ktv","a15");
        rename.put("pks","b15");
        rename.put("dhb","c15");
        rename.put("ptm","d15");

        //16
        rename.put("nqd","a16");
        rename.put("stm","b16");
        rename.put("dnd","c16");
        rename.put("mgr","d16");

        //17
        rename.put("wjh","a17");
        rename.put("jtp","b17");
        rename.put("cpv","c17");
        rename.put("bpg","d17");

        //18
        rename.put("qcp","a18");
        rename.put("bcq","b18");
        rename.put("pft","c18");
        rename.put("fsc","d18");

        //19
        rename.put("kbs","a19");
        rename.put("bnh","b19");
        rename.put("qjc","c19");
        rename.put("sbg","d19");

        //20
        rename.put("ckt","a20");
        rename.put("bgs","b20");
        rename.put("jwg","c20");
        rename.put("rpc","d20");

        //21
        rename.put("hgv","a21");
        rename.put("mdk","b21");
        rename.put("nqq","c21");
        rename.put("nqk","d21");

        //22
        rename.put("kvp","a22");
        rename.put("njd","b22");
        rename.put("tbs","c22");
        rename.put("bsr","d22");

        //23
        rename.put("tkb","a23");
        rename.put("jqs","b23");
        rename.put("cpq","c23");
        rename.put("mks","d23");

        //24
        rename.put("hjm","a24");
        rename.put("rpj","b24");
        rename.put("jrr","c24");
        rename.put("mcq","d24");

        //25
        rename.put("wpd","a25");
        rename.put("tpj","b25");
        rename.put("sqr","c25");
        rename.put("ffg","d25");

        //26
        rename.put("tmk","a26");
        rename.put("wcc","b26");
        rename.put("jrb","c26");
        rename.put("jjp","d26");

        //27
        rename.put("qdg","a27");
        rename.put("trd","b27");
        rename.put("fqp","c27");
        rename.put("jqg","d27");

        //28
        rename.put("gkk","a28");
        rename.put("smk","b28");
        rename.put("nrd","c28");
        rename.put("kgm","d28");

        //29
        rename.put("hhm","a29");
        rename.put("ktc","b29");
        rename.put("rmg","c29");
        rename.put("rwk","d29");

        //30
        rename.put("rkw","a30");
        rename.put("gwm","b30");
        rename.put("dmn","c30");
        rename.put("bfn","d30");

        //31
        rename.put("qvw","a31");
        rename.put("dkq","b31");
        rename.put("smd","c31");
        rename.put("jsf","d31");

        //32
        rename.put("pgs","a32");
        rename.put("vfq","b32");
        rename.put("frt","c32");
        rename.put("psb","d32");

        //33
        rename.put("jkm","a33");
        rename.put("vsk","b33");
        rename.put("wrg","c33");
        rename.put("jpc","d33");

        //34
        rename.put("bcf","a34");
        rename.put("cwc","b34");
        rename.put("hqv","c34");
        rename.put("gbk","d34");

        //35
        rename.put("kbm","a35");
        rename.put("vsb","b35");
        rename.put("dgv","c35");
        rename.put("mwp","d35");

        //36
        rename.put("qmj","a36");
        rename.put("vwj","b36");
        rename.put("jqc","c36");
        rename.put("hjs","d36");

        //37
        rename.put("tjm","a37");
        rename.put("spj","b37");
        rename.put("hhp","c37");
        rename.put("gqf","d37");

        //38
        rename.put("a38","a38");
        rename.put("b38","b38");
        rename.put("dsd","c38");
        rename.put("d38","d38");

        //39
        rename.put("a39","a39");
        rename.put("b39","b39");
        rename.put("c39","c39");
        rename.put("d39","d39");

        //40
        rename.put("a40","a40");
        rename.put("b40","b40");
        rename.put("c40","c40");
        rename.put("d40","d40");

        //41
        rename.put("a41","a41");
        rename.put("b41","b41");
        rename.put("c41","c41");
        rename.put("d41","d41");

        //42
        rename.put("a42","a42");
        rename.put("b42","b42");
        rename.put("c42","c42");
        rename.put("d42","d42");

        //43
        rename.put("a43","a43");
        rename.put("b43","b43");
        rename.put("c43","c43");
        rename.put("d43","d43");

        //44
        rename.put("a44","a44");
        rename.put("b44","b44");
        rename.put("c44","c44");
        rename.put("d44","d44");

        //45
        rename.put("a45","a45");
        rename.put("b45","b45");
        rename.put("c45","c45");
        rename.put("d45","d45");

    }

    static String printOutput(String o) {
        for (BoolNode node : opNodes) {
            if (node.name.equals(o)) {
                return node + "\n";
            }
        }
        return "";
    }


    static String print(String name) {
        StringBuilder sb = new StringBuilder();
        for (BoolNode node : opNodes) {
            if (node.leftName.equals(name) || node.rightName.equals(name)) {
                sb.append(node);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    static long toLong(List<Boolean> bits) {
        long result = 0;
        for (int i=bits.size()-1; i>=0; i--) {
            result <<= 1;
            if (bits.get(i)) result++;
        }
        return result;
    }


    static class BoolNode {
        String name;
        Boolean value;
        BiFunction<Boolean,Boolean,Boolean> op;
        String leftName;
        BoolNode left;
        String rightName;
        BoolNode right;

        Boolean getValue() {
            if (value==null) {
                return op.apply(left.getValue(),right.getValue());
            }
            return value;
        }

        static BoolNode fromValue(String line) {
            BoolNode node = new BoolNode();
            node.name = line.substring(0,3);
            node.value = line.charAt(5) == '1';
            return node;
        }

        static BoolNode fromOp(String line) {
            BoolNode node = new BoolNode();
            node.leftName = (line.substring(0,3));
            if (line.length() == 17) {
                node.rightName = (line.substring(7,10));
                node.name = fixError(line.substring(14));
                node.op = OR;
            } else {
                node.rightName = (line.substring(8,11));
                node.name = fixError(line.substring(15));
                node.op = line.charAt(4) == 'X'?XOR:AND;
            }
            return node;
        }

        public String toString() {
            if (value != null) {
                return name;
            }
            StringBuilder sb = new StringBuilder();
            String i1 = rename(leftName);
            String i2 = rename(rightName);
            if (i1.compareTo(i2) > 0) {
                i1 = i2;
                i2 = rename(leftName);
            }
            sb.append(rename(name));
            sb.append(" = ");
            sb.append(i1);
            if (op == AND) sb.append(" & " );
            if (op == OR) sb.append(" | " );
            if (op == XOR) sb.append(" ^ " );
            sb.append(i2);
            return sb.toString();
        }
    }

    static  String rename(String s) {
        if (rename.containsKey(s))
            return rename.get(s);
        return s;
    }

    static  String rerename(String s) {
        return rename.entrySet().stream().filter(e -> e.getValue().equals(s)).map(Map.Entry::getKey).findAny().orElse(s);
    }


    static String fixError(String s) {
        for (Map.Entry<String,String> e:errors.entrySet()) {
            if (e.getValue().equals(s))  return e.getKey();
            if (e.getKey().equals(s))  return e.getValue();
        }
        return s;
    }

}
