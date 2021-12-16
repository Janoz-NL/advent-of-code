package com.janoz.aoc.y2021.day16;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class OperatorPacket extends Packet{

    int end;
    List<Packet> subPackets = new ArrayList<>();

    public OperatorPacket(String bits, int version, int type, int start) {
        super(bits, version, type);
        end = start;
        if (bits.charAt(end) == '1') {
            int amountOfSubpackages = Integer.parseInt(bits.substring(end + 1, end + 12),2);
            end += 12;
            for (int i=0; i<amountOfSubpackages; i++) {
                Packet p = Packet.parsePacket(bits, end);
                end = p.posAfter();
                subPackets.add(p);
            }
        } else {
            int amountOfBits = Integer.parseInt(bits.substring(end + 1, end + 16),2);
            end += 16;
            int end = this.end + amountOfBits;
            do {
                Packet p = Packet.parsePacket(bits, this.end);
                this.end = p.posAfter();
                subPackets.add(p);
            } while (this.end < end);
        }
    }

    @Override
    BigInteger getResult() {
        switch(type) {
            case 0: //sum
                return subPackets.stream().map(Packet::getResult).reduce(BigInteger.ZERO, BigInteger::add);
            case 1: //product
                return subPackets.stream().map(Packet::getResult).reduce(BigInteger.ONE, BigInteger::multiply);
            case 2: //min
                return subPackets.stream().map(Packet::getResult).min(BigInteger::compareTo).get();
            case 3: //max
                return subPackets.stream().map(Packet::getResult).max(BigInteger::compareTo).get();
            case 5: //greater
                return (subPackets.get(0).getResult().compareTo(subPackets.get(1).getResult()) > 0 )?BigInteger.ONE:BigInteger.ZERO;
            case 6: //smaller
                return (subPackets.get(0).getResult().compareTo(subPackets.get(1).getResult()) < 0 )?BigInteger.ONE:BigInteger.ZERO;
            case 7: //equal
                return (subPackets.get(0).getResult().equals(subPackets.get(1).getResult()))?BigInteger.ONE:BigInteger.ZERO;
            default:
                throw new UnsupportedOperationException("Type " + type + " not supported");
        }
    }

    @Override
    int posAfter() {
        return end;
    }

    @Override
    long versionSum() {
        return version + subPackets.stream().mapToLong(Packet::versionSum).sum();
    }
}
