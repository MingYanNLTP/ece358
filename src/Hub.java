/**
 * Created by ming on 2017-02-24.
 */
public class Hub {
    boolean busy;
    int transmitting;
    public Hub() {
        busy = false;
        transmitting = 0;
    }

    public void processPacket() {
        busy = true;
    }

    public void finishProcess() {
        busy = false;
    }

    public boolean isBusy() {
        return busy;
    }
}
