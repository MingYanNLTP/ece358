import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by ming on 2017-01-12.
 */
public class One_Queue {
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
        queue = new LinkedList<>();
        BEB = 0;
        waitBEB = 0;//5;
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

    public int backOff(int tick) {
        transmitting = false;
        transmittingUntil = 0;
        sensed = false;
        BEB++;
        if (BEB > 10) {
            queue.remove();
            BEB = 0;
            return 1;
        } else {
            Random random = new Random();
            int wait = random.nextInt((int)Math.pow(2, BEB));
            backOffTick = tick + 512 * wait;
            backingOff = true;
        }
        return 0;
    }

    public void stopBackOff() {
        backingOff = false;
        backOffTick = 0;
    }

    public void transmit(int tick) {
        transmitting = true;
        transmittingUntil = tick;
    }

    public int transmitFinish(int tick) {
        transmitting = false;
        sensed = false;
        transmittingUntil = 0;
        BEB = 0;
        Packet packet = queue.poll();
        packet.process(tick);
        return packet.timeInQ;
    }


    @Override
    public String toString() {
        String str = String.format("Queue size: %d", queue.size());
        return str;
    }
}
