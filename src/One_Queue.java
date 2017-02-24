import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by ming on 2017-01-12.
 */
public class One_Queue {
    int queueLimit;
    private boolean limitFlag = false;
    Server server;
    Queue<Packet> queue;
    private final int ticksTime = 1000;
    public One_Queue(String arg) {
        setup(Integer.parseInt(arg));
    }
    public One_Queue() {
        setup(-1);
    }
    public void setup(int queueSize) {
        queueLimit = queueSize;
        if (queueLimit >= 0) {
            limitFlag = true;
        }
        server = new Server();
        queue = new LinkedList<>();
    }

    public int randomGen(int lambda) {
        Random random = new Random();
        double urand = random.nextDouble();
        double X = (-1/(double)lambda)*Math.log(1-urand);
        int result = Math.round(Math.round(X * ticksTime));

        return result;
    }

    public boolean generatePacket(int genTick, int size) {
        if (limitFlag == true) {
            if (queue.size() < queueLimit) {
                queue.add(new Packet(genTick, size));
                return true;
            } else {
                return false;
            }
        }
        queue.add(new Packet(genTick, size));
        return true;
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
