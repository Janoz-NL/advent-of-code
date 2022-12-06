package com.janoz.aoc.y2022.day6;

import com.github.luben.zstd.ZstdInputStream;
import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Disabled
class Day06StreamingTest {

    @Test
    void bigFileSmallBuffer() throws IOException {
        StopWatch.start();
        InputStream inputStream = Objects.requireNonNull(InputProcessor.class.getClassLoader().getResourceAsStream("inputs/2022/bigsets/aoc22d6xxl.txt"));
        new Day06Streaming(inputStream,4);
        StopWatch.stopPrint();
    }

    @Test
    void bigFileBigBuffer() throws IOException {
        StopWatch.start();
        InputStream inputStream = Objects.requireNonNull(InputProcessor.class.getClassLoader().getResourceAsStream("inputs/2022/bigsets/aoc22d6xxl.txt"));
        new Day06Streaming(inputStream,14);
        StopWatch.stopPrint();
    }

    @Test
    void bigZstFileBigBuffer() throws IOException {
        StopWatch.start();
        InputStream inputStream = Objects.requireNonNull(InputProcessor.class.getClassLoader().getResourceAsStream("inputs/2022/bigsets/aoc22d6xxl.txt.zst"));
        new Day06Streaming(new ZstdInputStream(inputStream),14);
        StopWatch.stopPrint();
    }


    @Test
    void whyNotWorking() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("atiaticunproblematicunproblematicundiscoverablyundiscoverably".getBytes());
        new Day06Streaming(inputStream,14);
    }



}