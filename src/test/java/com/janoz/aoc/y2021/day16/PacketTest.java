package com.janoz.aoc.y2021.day16;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class PacketTest {

    @Test
    void testParseLiteral() {
        Packet p = Packet.fromHex("D2FE28");
        assertThat(p.version, equalTo(6));
        assertThat(p.type, equalTo(4));
        assertThat(((LiteralPacket)p).literal, equalTo(BigInteger.valueOf(2021)));
    }

    @Test
    void testParseOperatorLabel0() {
        Packet p = Packet.fromHex("38006F45291200");
        assertThat(p.version, equalTo(1));
        assertThat(p.type, equalTo(6));
        OperatorPacket op = (OperatorPacket) p;
        assertThat(((LiteralPacket)op.subPackets.get(0)).literal, equalTo(BigInteger.valueOf(10)));
        assertThat(((LiteralPacket)op.subPackets.get(1)).literal, equalTo(BigInteger.valueOf(20)));
    }

    @Test
    void testParseOperatorLabel1() {
        Packet p = Packet.fromHex("EE00D40C823060");
        assertThat(p.version, equalTo(7));
        assertThat(p.type, equalTo(3));
        OperatorPacket op = (OperatorPacket) p;
        assertThat(op.subPackets.size(), equalTo(3));
        assertThat(((LiteralPacket)op.subPackets.get(0)).literal, equalTo(BigInteger.valueOf(1)));
        assertThat(((LiteralPacket)op.subPackets.get(1)).literal, equalTo(BigInteger.valueOf(2)));
        assertThat(((LiteralPacket)op.subPackets.get(2)).literal, equalTo(BigInteger.valueOf(3)));
    }

    @Test
    void testVersionSum() {
        assertThat(Packet.fromHex("8A004A801A8002F478").versionSum(), equalTo(16L));
        assertThat(Packet.fromHex("620080001611562C8802118E34").versionSum(), equalTo(12L));
        assertThat(Packet.fromHex("C0015000016115A2E0802F182340").versionSum(), equalTo(23L));
        assertThat(Packet.fromHex("A0016C880162017C3686B18A3D4780").versionSum(), equalTo(31L));
    }

    @Test
    void testCalculation() {
        assertThat(Packet.fromHex("C200B40A82").getResult(), equalTo(BigInteger.valueOf(3)));
        assertThat(Packet.fromHex("04005AC33890").getResult(), equalTo(BigInteger.valueOf(54)));
        assertThat(Packet.fromHex("880086C3E88112").getResult(), equalTo(BigInteger.valueOf(7)));
        assertThat(Packet.fromHex("CE00C43D881120").getResult(), equalTo(BigInteger.valueOf(9)));
        assertThat(Packet.fromHex("D8005AC2A8F0").getResult(), equalTo(BigInteger.valueOf(1)));
        assertThat(Packet.fromHex("F600BC2D8F").getResult(), equalTo(BigInteger.valueOf(0)));
        assertThat(Packet.fromHex("9C005AC2F8F0").getResult(), equalTo(BigInteger.valueOf(0)));
        assertThat(Packet.fromHex("9C0141080250320F1802104A08").getResult(), equalTo(BigInteger.valueOf(1)));
    }
}