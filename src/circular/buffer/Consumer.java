package circular.buffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
 * Consumer class that reads pixels from the buffer and processes them.
 */
public class Consumer extends Thread {
    private final CircularBuffer buffer;
    private final BufferedImage processedImage;

    public Consumer(CircularBuffer buffer, BufferedImage image) {
        this.buffer = buffer;
        this.processedImage = image;
    }

    @Override
    public void run() {
        try {
            for (int y = 0; y < processedImage.getHeight(); y++) {
                for (int x = 0; x < processedImage.getWidth(); x++) {
                    byte pixel = buffer.get();
                    // Invert the color
                    int invertedPixel = ~pixel & 0xFF;
                    processedImage.setRGB(x, y, invertedPixel);
                }
            }
            ImageIO.write(processedImage, "jpg", new File("processed_image.jpg"));
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
