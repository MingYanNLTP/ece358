import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by ming on 2017-01-12.
 */
public class One_Queue {
    Server server;
    Queue<Packet> queue;
    int BEB;
    int waitBEB;
    boolean transmitting;
    boolean backingOff;
    int packetTick;
    int backOffTick;
    boolean sensed;
    private final int ticksTime = 100000;
    int transmittingUntil;

    public One_Queue() {
        server = new Server();
        queue = new LinkedList<>();
        BEB = 0;
        waitBEB = 5;
        transmitting = false;
        backingOff = false;
        sensed = false;
    }

    public int randomGen(int lambda) {
        Random random = new Random();
        double urand = random.nextDouble();
        double X = (-1/(double)lambda)*Math.log(1 - urand);
        int result = Math.round(Math.round(X * ticksTime));

        return result;
    }

    public boolean generatePacket(int genTick) {
        queue.add(new Packet(genTick));
        return true;
    }

    public boolean isTransmitting() {
        return transmitting;
    }

    public boolean transmitReady() {
        if (queue.isEmpty() || backingOff || isTransmitting()) {
            return false;
        }
        return true;
    }
    public void waitTick(int tick) {
        Random random = new Random();
        int wait = random.nextInt((int)Math.pow(2, waitBEB));
        backOffTick = tick + 512 * wait;
        backingOff = true;
    }

    public void backOff(int tick) {
        transmitting = false;
        transmittingUntil = 0;
        sensed = false;
        BEB++;
        if (BEB > 10) {
            System.out.println("drop it like its hot");
            queue.remove();
            BEB = 0;
        } else {
            Random random = new Random();
            int wait = random.nextInt((int)Math.pow(2, BEB));
            backOffTick = tick + 512 * wait;
            backingOff = true;
        }
    }

    public void stopBackOff() {
        backingOff = false;
        backOffTick = 0;
    }

    public void transmit(int tick) {
        transmitting = true;
        transmittingUntil = tick;
    }

    public void transmitFinish() {
        transmitting = false;
        sensed = false;
        transmittingUntil = 0;
        BEB = 0;
        queue.remove();
    }

    public void processPacket() {
        server.processPacket();
    }

    public void finishPacket() {
        server.finishProcess();
    }

    @Override
    public String toString() {
        String str = String.format("Queue size: %d", queue.size());
        return str;
    }
}
