package com.janoz.aoc.graphics;

import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ConsumingAnimationWriter {

    private final FileImageOutputStream outputStream;
    private final int fps;
    private GifSequenceWriter gcw;

    private final BufferedImage[] lastImageContainer = new BufferedImage[1];

    public ConsumingAnimationWriter(String filename, int fps) {
        try {
            this.outputStream = new FileImageOutputStream(new File(filename));
            this.fps = fps;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Consumer<BufferedImage> imageConsumer(){
        return image -> {
            try {
                if (gcw == null) {
                    gcw = new GifSequenceWriter(outputStream, image.getType(), fps);
                }
                gcw.writeToSequence(image);
                lastImageContainer[0] = image;
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        };
    }

    public void addPauze(int frames) {
        if (lastImageContainer[0] == null) {
            System.err.println("Can't pauze on first image..");
            return;
        }
        BufferedImage bi = new BufferedImage(lastImageContainer[0].getWidth(),lastImageContainer[0].getHeight(), BufferedImage.TYPE_INT_ARGB);
        Consumer<BufferedImage> imageConsumer = imageConsumer();
        for (int i=0;i<frames; i++) {
            imageConsumer.accept(bi);
        }
    }

    public void close() {
        try {
            gcw.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}