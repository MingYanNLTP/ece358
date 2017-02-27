/**
 * Created by ming on 2017-01-12.
 */
public class Main {
    public static void main(String args[]) {
        for (int i = 4; i < 18; i+=2) {
            Simulator simulator = new Simulator(i, 8);
            simulator.simulate();
        }

    }
}
