/**
 * Created by ming on 2017-01-12.
 */
public class Server {
    int busy = 0;
    public Server() {

    }

    public void processPacket() {
        busy = 1;
    }

    public void finishProcess() {
        busy = 0;
    }

    public int isBusy() {
        return busy;
    }
}
