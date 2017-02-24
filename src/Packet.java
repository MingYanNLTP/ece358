/**
 * Created by ming on 2017-01-12.
 */
public class Packet {
    int generated;
    int size;
    int timeInQ;
    public Packet(int tick, int size){
        generated = tick;
        this.size = size;
    }
    public void process(int i) {
        timeInQ = i - generated;
    }
}
