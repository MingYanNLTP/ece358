/**
 * Created by ming on 2017-01-12.
 */
public class Packet {
    int generated;
    int size;
    int timeInQ;
    public Packet(int tick){
        generated = tick;
        this.size = 8000;
    }
    public void process(int i) {
    }
}
