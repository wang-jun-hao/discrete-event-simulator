import cs2030.simulator.Simulator;
import cs2030.simulator.Event;
import java.util.Scanner;

/**
 * class Main handles input and output of program.
 */
public class Main {
    /**
     * Entry point into the Simulator program.
     * @param args console argument input
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // extract information from input
        int baseSeed = sc.nextInt();
        int numOfHumanServers = sc.nextInt();
        int numOfSelfCheckOuts = sc.nextInt();
        int maxQLength = sc.nextInt();
        int numOfCustomers = sc.nextInt();
        double arrivalRate = sc.nextDouble();
        double serviceRate = sc.nextDouble();
        double restingRate = sc.nextDouble();
        double restProb = sc.nextDouble();
        double greedyProb = sc.nextDouble();

        sc.close();

        // instantiate Simulator object
        Simulator sim = new Simulator(baseSeed, numOfHumanServers, numOfSelfCheckOuts, maxQLength, 
            numOfCustomers, arrivalRate, serviceRate, restingRate, restProb, greedyProb); 
 
        // process and output events
        while (sim.hasEvent()) {
            // while still have events in queue
            // print event (description of event), skipping SERVER_REST and SERVER_BACK events
            Event e = sim.getEvent();
            if (!e.isRestEvent() && !e.isBackEvent()) {
                System.out.println(e);
            }
            sim.processEvent();
        }

        System.out.println(sim.getStatistics());
    }
}
