package com.janoz.aoc.y2021.day16;

import java.math.BigInteger;

public abstract class Packet {

    int version;
    int type;

    protected Packet(int version, int type) {
        this.version = version;
        this.type = type;
    }

    long versionSum() {
        return version;
    }

    abstract int posAfter();

    abstract BigInteger getResult();

    static Packet fromHex(String hex) {
        StringBuilder sb = new StringBuilder();
        for (char c:hex.toCharArray()) {
            switch (c) {
                case '0' : sb.append("0000"); break;
                case '1' : sb.append("0001"); break;
                case '2' : sb.append("0010"); break;
                case '3' : sb.append("0011"); break;
                case '4' : sb.append("0100"); break;
                case '5' : sb.append("0101"); break;
                case '6' : sb.append("0110"); break;
                case '7' : sb.append("0111"); break;
                case '8' : sb.append("1000"); break;
                case '9' : sb.append("1001"); break;
                case 'A' : sb.append("1010"); break;
                case 'B' : sb.append("1011"); break;
                case 'C' : sb.append("1100"); break;
                case 'D' : sb.append("1101"); break;
                case 'E' : sb.append("1110"); break;
                case 'F' : sb.append("1111"); break;
            }
        }
        String bits = sb.toString();
        return parsePacket(bits,0);
    }

    static Packet parsePacket(String bits, int start) {
        int version = Integer.parseInt(bits.substring(start,start + 3),2);
        int type = Integer.parseInt(bits.substring(start + 3,start + 6),2);
        if (type == 4) {
            return new LiteralPacket(bits, version, start + 6);
        }
        return new OperatorPacket(bits, version, type, start + 6);
    }
}
