
/**
 * Created by ming on 2017-01-12.
 */
public class Simulator {
    int ticks = 100000; //1 secs
    int W = 1000000;
    int N;
    int A;
    int[] busyUntil;
    int[] busyStart;
    int processingTick;
    One_Queue[] queues;
    boolean[] collision;
    int transmitting;
    int packetsThrough;
    double soujournDelay;
    int dropped;

    public Simulator(int A, int N) {
        this.N = N;
        this. A = A;
        busyUntil = new int[N];
        busyStart = new int[N];
        queues = new One_Queue[N];
        collision = new boolean[N];
        for (int i = 0; i < N; i++) {
            collision[i] = false;
            busyStart[i] = Integer.MAX_VALUE;
            busyUntil[i] = Integer.MIN_VALUE;
            queues[i] = new One_Queue();
        }
        processingTick = (int) ((double)8000 / (double) W * 100000);
        transmitting = 0;
        packetsThrough = 0;
        soujournDelay = 0;
        dropped = 0;
    }

    public void simulate() {
        for (One_Queue q: queues) {
            int i = q.randomGen(A);
            q.packetTick = i;
        }
        for (int i = 0; i < ticks; i++) {
            for (int j = 0; j < N; j++) {
                if (queues[j].isTransmitting()) {
                    transmitting++;
                }
            }
            if (transmitting > 1) {
                for (int j = 0; j < N; j++) {
                    if (queues[j].isTransmitting()) {
                        collision[j] = true;
                    }
                }
            }
            for (int j = 0; j < N; j++) {
                if (i == queues[j].packetTick) {
                    queues[j].generatePacket(i);
                    queues[j].packetTick = queues[j].randomGen(A) + i;
                }

                if (i > busyUntil[j]) {
                    busyStart[j] = Integer.MAX_VALUE;
                    busyUntil[j] = Integer.MIN_VALUE;
                }

                if (queues[j].queue.isEmpty()) {
                    continue;
                }

                if (collision[j]) {
                    dropped += queues[j].backOff(i);
                }

                if (queues[j].isTransmitting()) {
                    if (i >= queues[j].transmittingUntil) {
                        soujournDelay += queues[j].transmitFinish(i);
                        packetsThrough++;
                    }
                }

                if (queues[j].backingOff) {
                    if (queues[j].backOffTick <= i) {
                        queues[j].stopBackOff();
                    }
                }

                if (queues[j].transmitReady() && !queues[j].sensed) {
                    for (int k = 0; k < N; k++) {
                        if (j != k) {
                            if (i > busyStart[k] && i < busyUntil[k]) {
                                queues[j].waitTick(i + 1);
                                break;
                            }
                        }
                    }
                    if (!queues[j].backingOff) {
                        queues[j].sensed = true;
                    }
                } else if (queues[j].sensed && queues[j].transmitReady()) {
                    queues[j].transmit(i + processingTick);
                    busyStart[j] = i + 3;
                    busyUntil[j] = i + processingTick + 3;
                }
            }
            for (int j = 0; j < N; j++) {
                collision[j] = false;
            }
            transmitting = 0;
        }
        System.out.println(packetsThrough +", " + soujournDelay/(double)packetsThrough + ", " + dropped);
    }

    public String print() {
        return "";
    }
}
