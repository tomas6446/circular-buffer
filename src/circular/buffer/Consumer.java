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
    private int currentX = 0;
    private int currentY = 0;

    public Consumer(CircularBuffer buffer, BufferedImage image) {
        this.buffer = buffer;
        this.processedImage = image;
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte pixel = buffer.get();
                // Invert the color
                int invertedPixel = ~pixel & 0xFF;
                processedImage.setRGB(currentX, currentY, invertedPixel);

                // Update the current position
                currentX++;
                if (currentX >= processedImage.getWidth()) {
                    currentX = 0;
                    currentY++;
                }

                if (currentY >= processedImage.getHeight()) {
                    ImageIO.write(processedImage, "jpg", new File("processed_image.jpg"));
                    break;
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
