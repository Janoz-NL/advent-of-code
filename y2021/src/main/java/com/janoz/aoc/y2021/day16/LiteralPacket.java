package com.janoz.aoc.y2021.day16;

import java.math.BigInteger;

public class LiteralPacket extends Packet{
    static final BigInteger BI_16 = BigInteger.valueOf(16);

    BigInteger literal;
    int end;

    public LiteralPacket(String bits, int version, int start) {
        super(version, 4);
        this.end = start;
        literal = parseLiteral(bits);
    }

    int posAfter() {
        return end;
    }

    BigInteger getResult() {
        return literal;
    }

    private BigInteger parseLiteral(String bits) {
        BigInteger result = BigInteger.ZERO;
        do {
            result = result.multiply(BI_16)
                    .add(BigInteger.valueOf(Integer.parseInt(bits.substring(end + 1, end + 5), 2)));
            end = end + 5;
        } while (bits.charAt(end-5) == '1');
        return result;
    }
}
