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
