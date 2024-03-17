package circular.buffer;

import java.awt.image.BufferedImage;

/*
 * Producer class that reads an image file and puts its pixels into the buffer.
 */
public class Producer extends Thread {
    private final CircularBuffer buffer;
    private final BufferedImage image;

    public Producer(CircularBuffer buffer, BufferedImage image) {
        this.buffer = buffer;
        this.image = image;
    }

    @Override
    public void run() {
        try {
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getRGB(x, y);
                    // Invert the color
                    int invertedPixel = ~pixel & 0xFF;
                    buffer.put((byte) invertedPixel);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
