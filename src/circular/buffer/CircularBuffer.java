package circular.buffer;

import static java.lang.System.out;

public class CircularBuffer {
    private final byte[] buffer;
    private final Object lock = new Object();
    private int head = 0;
    private int tail = 0;
    private int count = 0;

    public CircularBuffer(int size) {
        buffer = new byte[size];
    }

    /*
     * Method that puts a byte into the buffer.
     * If the buffer is full, the producer thread waits until the consumer thread reads a byte from the buffer.
     */
    public void put(byte value) throws InterruptedException {
        synchronized (lock) {
            while (count == buffer.length) {
                out.println("Buferis pilnas, Producer laukia...");
                lock.wait();
            }
            buffer[tail] = value;
            tail = (tail + 1) % buffer.length;
            count++;
            out.println("Producer įrašė duomenį. Buferio užpildymas: " + count);
            lock.notifyAll();
        }
    }

    /*
     * Method that reads a byte from the buffer.
     * If the buffer is empty, the consumer thread waits until the producer thread puts a byte into the buffer.
     */
    public byte get() throws InterruptedException {
        synchronized (lock) {
            while (count == 0) {
                out.println("Buferis tuščias, Consumer laukia...");
                lock.wait();
            }
            byte value = buffer[head];
            head = (head + 1) % buffer.length;
            count--;
            out.println("Consumer nuskaito duomenis. Buferio užpildymas: " + count);
            lock.notifyAll();
            return value;
        }
    }
}
