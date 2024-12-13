package com.janoz.aoc.graphics;

import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ConsumingAnimationWriter {

    private final FileImageOutputStream outputStream;
    private final boolean loop;
    private GifSequenceWriter gcw;

    public ConsumingAnimationWriter(String filename, boolean loop) {
        try {
            this.outputStream = new FileImageOutputStream(new File(filename));
            this.loop = loop;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Consumer<BufferedImage> imageConsumer(){
        return image -> {
            try {
                if (gcw == null) {
                    gcw = new GifSequenceWriter(outputStream, image.getType(), loop);
                }
                gcw.writeToSequence(image);
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        };
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