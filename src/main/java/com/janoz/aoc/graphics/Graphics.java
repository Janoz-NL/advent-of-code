package com.janoz.aoc.graphics;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import com.janoz.aoc.geo.Point;
import com.janoz.aoc.geo.ReadOnlyGrid;

public class Graphics {


    public static final float BRIGHTNESS = 0.8f;
    public static final float SATURATION = 0.7f;

    public static void writeAniGif(List<BufferedImage> images, String filename) {
        try (FileImageOutputStream outputStream = new FileImageOutputStream(new File(filename))) {
            GifSequenceWriter gsw = new GifSequenceWriter(outputStream, images.get(0).getType(), 50);
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
        Integer[] values = inputs.toArray(new Integer[0]);
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

    public static BufferedImage gridImage(int width, int height, int blocksize, int bordersize, Color color) {
        BufferedImage image = new BufferedImage(width * blocksize + bordersize,height * blocksize + bordersize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(color);
        for (int x=0;x<=width; x++){
            g2d.fillRect(x * blocksize,0, bordersize,height * blocksize + bordersize);
        }
        for (int y=0;y<=height; y++){
            g2d.fillRect(0,y * blocksize, width * blocksize + bordersize, bordersize);
        }
        return image;
    }

    public static <T> BufferedImage toImage(ReadOnlyGrid<T> grid, Function<T, Color> mapper, int type) {
        return toBigImage(grid, mapper, 1,0,type);
    }

    public static <T> BufferedImage toBigImage(ReadOnlyGrid<T> grid, Function<T, Color> mapper, int blocksize, int bordersize, int type) {
        ToIntFunction<Color> toInt = Color::getRGB;
        BufferedImage image = new BufferedImage(grid.getWidth() * blocksize + bordersize,grid.getHeight() * blocksize + bordersize, type);
        Iterator<Point> it = grid.streamPoints().iterator();
        if (!it.hasNext()) {
            return null;
        }
        while (it.hasNext()) {
            Point p = it.next();
            for (int x = bordersize; x< blocksize; x++){
                for (int y = bordersize; y< blocksize; y++){
                    image.setRGB(p.x * blocksize + x, p.y * blocksize + y, toInt.applyAsInt(mapper.apply(grid.get(p))));
                }
            }
        }
        return image;
    }
}
