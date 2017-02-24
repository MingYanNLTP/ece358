/**
 * Created by ming on 2017-01-12.
 */
public class Main {
    public static void main(String args[]) {
        runq1();
    }
    public static void runq1() {
        Simulator sim = new Simulator("100000, 150, 2000, 1000000");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 200, 2000, 1000000");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 250, 2000, 1000000");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 300, 2000, 1000000");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 350, 2000, 1000000");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 400, 2000, 1000000");
        sim.simulate();
        System.out.println(sim.print());
    }

    public static void runq2() {
        Simulator sim = new Simulator("100000, 300, 2000, 1000000, 50");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 350, 2000, 1000000, 50");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 400, 2000, 1000000, 50");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 450, 2000, 1000000, 50");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 500, 2000, 1000000, 50");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 550, 2000, 1000000, 50");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000,600, 2000, 1000000, 50");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 650, 2000, 1000000, 50");
        sim.simulate();
        System.out.println(sim.print());
        sim = new Simulator("100000, 700, 2000, 1000000, 50");
        sim.simulate();
        System.out.println(sim.print());
    }
}
