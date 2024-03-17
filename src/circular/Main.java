package circular;

import circular.buffer.CircularBuffer;
import circular.buffer.Consumer;
import circular.buffer.Producer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        CircularBuffer buffer = new CircularBuffer(BUFFER_SIZE);
        BufferedImage image = ImageIO.read(new File("image.jpg"));

        Runnable producer = new Producer(buffer, image);
        Runnable consumer = new Consumer(buffer, image);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
