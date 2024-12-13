package com.janoz.aoc.graphics;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class Graphics {


    public static final float BRIGHTNESS = 0.8f;
    public static final float SATURATION = 0.7f;

    public static void writeAniGif(List<BufferedImage> images, String filename) {
        try (FileImageOutputStream outputStream = new FileImageOutputStream(new File(filename))) {
            GifSequenceWriter gsw = new GifSequenceWriter(outputStream, images.get(0).getType(), true);
            for (BufferedImage image : images) {
                gsw.writeToSequence(image);
            }
            gsw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writePng(RenderedImage image, String filename) {
        try {
            ImageIO.write(image, "png", new FileOutputStream(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Function<Integer, Color> constructMapper(Set<Integer> inputs) {
        Integer[] values = inputs.toArray(new Integer[inputs.size()]);
        Arrays.sort(values);
        Map<Integer, Color> colorMap = new HashMap<>();
        for (int i=0; i<values.length; i++) {
            colorMap.put(values[i], new Color((float)i/(float) values.length, SATURATION, BRIGHTNESS));
        }
        return colorMap::get;
    }

    public static Function<Character, Color> constructMapper(char bottom, char top) {
        return c -> Color.getHSBColor((c - bottom) / (float)(top-bottom), SATURATION, BRIGHTNESS);
    }

    public static Function<Integer, Color> constructMapper(int amount) {
        return i -> Color.getHSBColor((i / (float)amount), SATURATION, BRIGHTNESS);
    }
}
