
/**
 * Created by ming on 2017-01-12.
 */
public class Simulator {
    int ticks;
    int lambda;
    int packetLength;
    int serviceTime;
    One_Queue queue;

    int nextPacGen = -1;
    int totalPacGen = -1;
    int nextPacPro = -1;
    int packetGenerated = 0;
    int souj = 0;
    double rho = 0;

    int serverIdle = 0;
    int runTimes = 9;
    int packetDropped = 0;
    int totalGenerated = 0;
    int totalProcessed = 0;
    double percentageDrop = 0;

    double[] avgPac;
    double[] avgSouj;
    double avgavgpac;
    double avgavgsouj;
    public Simulator(String args) {
        args = args.replaceAll("\\s", "");
        String[] split = args.split(",");
        setup(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
        if (split.length == 5) {
            queue = new One_Queue(split[4]);
        } else {
            queue = new One_Queue();
        }
    }
    public void setup(int ticks, int lambda, int L, int C) {
        this.ticks = ticks;
        this.lambda = lambda;
        this.packetLength = L;
        this.serviceTime = C;
        rho = (double)lambda * (double)packetLength / (double)serviceTime;
        avgPac = new double[runTimes];
        avgSouj = new double[runTimes];
    }

    public void simulate() {
        for (int j = 0; j < runTimes; j++) {
            queue.queue.clear();
            queue.server.finishProcess();

            nextPacGen = queue.randomGen(lambda);
            totalPacGen = nextPacGen;

            packetGenerated = 0;
            totalProcessed = 0;
            souj = 0;
            nextPacPro = -1;
            for (int i = 0; i < ticks; i++) {
                if (i == totalPacGen) {
                    boolean b = queue.generatePacket(i, packetLength);
                    if (!b) {
                        packetDropped++;
                    }

                    totalGenerated++;
                    do{
                        nextPacGen = queue.randomGen(lambda);
                    } while (nextPacGen == 0);
                    totalPacGen = nextPacGen + i;
                }

                if (!queue.queue.isEmpty() && queue.server.isBusy() != 1) {
                    queue.processPacket();
                    nextPacPro = i + Math.round(Math.round(Math.ceil((float) (packetLength) / (float) (serviceTime))));
                } else if (queue.queue.isEmpty()) {
                    serverIdle++;
                }

                if (i == nextPacPro) {
                    Packet p = queue.queue.poll();
                    p.process(i);
                    souj += p.timeInQ;
                    queue.finishPacket();
                    totalProcessed++;
                }
                packetGenerated += queue.queue.size();
            }

            if (!queue.queue.isEmpty()) {
                for (Packet p : queue.queue) {
                    souj += ticks - 1 - p.generated;
                }
            }
            avgPac[j] = (double)packetGenerated / (double)ticks;
            avgSouj[j] = (double)souj / (double)totalProcessed;
        }
            serverIdle /= runTimes;
            percentageDrop = (double) packetDropped / (double) totalGenerated * 100;
            for (int i = 0; i < runTimes; i++) {
                avgavgpac += avgPac[i];
                avgavgsouj += avgSouj[i];
            }
            avgavgpac /= runTimes;
            avgavgsouj /= runTimes;
    }

    public String print() {
        String str = "";
        str += String.format("%1$.7f \n", avgavgpac);
        str += String.format("%1$.7f \n", avgavgsouj);
        str += String.format("%d \n", serverIdle);
        str += String.format("%1$.5f\n", percentageDrop);
        return str;
    }
}
