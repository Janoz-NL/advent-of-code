package com.janoz.aoc.y2024.day24;

public class CodeGenerator {
    public static void main(String[] args) {
        for (int i=0; i<46; i++) {
            String s = String.format("%02d",i);
        System.out.println("        //"+s+"\n" +
                "        rename.put(\"a"+s+"\",\"a"+s+"\");\n" +
                "        rename.put(\"b"+s+"\",\"b"+s+"\");\n" +
                "        rename.put(\"c"+s+"\",\"c"+s+"\");\n" +
                "        rename.put(\"d"+s+"\",\"d"+s+"\");    \n");}
    }
}
