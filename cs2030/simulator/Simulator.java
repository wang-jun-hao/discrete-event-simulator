package cs2030.simulator;

import java.util.Queue;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;

/**
 * Simulator class encompassing the logic of the simulator program, where each
 * Simulator has its own queue of events, list of servers, random generator and statistics.
 */
public class Simulator {
    private final Queue<Event> eventQ;
    private final List<Server> servers;
    private Statistics statistics;
    private RandomGenerator randomGenerator;
    private final double restProb;
    private final double greedyProb;
    private int numOfHumanServers;
    private int numOfSelfCheckOuts;

    /**
     * Constructs a new simulator with the given properties.
     * @param baseSeed base seed of RandomGenerator to be used for this simulator
     * @param numOfHumanServers number of HumanServers in this simulator
     * @param numOfSelfCheckOuts number of SelfCheckOuts in this simulator
     * @param maxQLength length of customer queue each Server can have 
     *     (exclusive of customer being served)
     * @param numOfCustomers number of Customers that will arrive in this simulator
     * @param arrivalRate arrival rate of RandomGenerator
     * @param serviceRate service rate of RandomGenerator
     * @param restingRate resting rate of RandomGenerator
     * @param restProb probability of a server resting after it finishes serving a customer
     * @param greedyProb probability of an arriving customer being a greedy customer
     */
    // constructor
    public Simulator(int baseSeed, int numOfHumanServers, int numOfSelfCheckOuts, 
        int maxQLength, int numOfCustomers, double arrivalRate, double serviceRate, 
        double restingRate, double restProb, double greedyProb) {

        this.statistics = new Statistics();
        this.randomGenerator = new RandomGenerator(baseSeed, arrivalRate, 
            serviceRate, restingRate);
        this.restProb = restProb;
        this.greedyProb = greedyProb;
        this.numOfHumanServers = numOfHumanServers;
        this.numOfSelfCheckOuts = numOfSelfCheckOuts;

        // set up servers
        this.servers = new ArrayList<>();
        SelfCheckOut mainSelfCheckOut = null;
        for (int i = 0; i < numOfHumanServers; i++) {
            servers.add(new HumanServer(maxQLength)); 
            // initialise a new idle HumanServer with queue size of maxQLength + 1 (inclusive
            // of customer being served) and add to the list of servers
        }
        for (int i = 0; i < numOfSelfCheckOuts; i++) {
            if (i == 0) {
                mainSelfCheckOut = new SelfCheckOut(maxQLength);
                servers.add(mainSelfCheckOut);
            } else {
                servers.add(new SelfCheckOut(maxQLength, mainSelfCheckOut));
            }
        }

        // set up arrival events
        this.eventQ = new PriorityQueue<>(new EventComparator());
        double arrTimeStamp = 0;
        // instantiate priority queue of events sorted by time
        // in the event of same time, events are sorted by customer id
        for (int i = 0; i < numOfCustomers; i++) {
            // initialise eventQ with random arrival events
            if (randomGenerator.genCustomerType() < greedyProb) {
                this.eventQ.add(
                    new Event(arrTimeStamp, 
                        new GreedyCustomer(arrTimeStamp, State.ARRIVES), null)); 
                arrTimeStamp += this.randomGenerator.genInterArrivalTime();
            } else {
                this.eventQ.add(
                    new Event(arrTimeStamp, 
                        new TypicalCustomer(arrTimeStamp, State.ARRIVES), null)); 
                arrTimeStamp += this.randomGenerator.genInterArrivalTime();
            }
        }
    }

    // methods
    /**
     * Returns true if simulator still has event in the event queue.
     * @return true if simulator still has event in the event queue
     */
    public boolean hasEvent() {
        return (eventQ.peek() != null);
    }

    /**
     * Returns the current event.
     * @return current event
     */
    public Event getEvent() {
        return eventQ.peek();
    }

    /**
     * Returns the statistics of the simulator.
     * @return statistics of simulator
     */
    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * Triggers the processing of the current event in the Simulator, which may 
     * result in more events added to the event queue.
     */
    public void processEvent() {
        Event e = eventQ.poll();

        if (e.isRestEvent()) {
            // processing a rest event -> let server rest and add subsequent back event
            Server server = e.getServer();
            server.rest();
            eventQ.add(new BackEvent(e.getTime() + randomGenerator.genRestPeriod(), server));
        } else if (e.isBackEvent()) {
            // processing a back event -> check for any waiting customer and resume serving
            // check server for waitlisted customer
            Server server = e.getServer();
            server.back();
            if (server.hasWait()) {
                // server has waitlisted customer -> serve waitlisted customer
                Customer nextCustomer = server.getNextCustomer();
                server.serveNextCustomer();
                eventQ.add(e.createNewEvent(nextCustomer, State.SERVED));
            } else {
                // server has no waitlisted customer -> return to idle by previous .clearCurrent()
                // no new events created
            }
        } else {
            Customer customer = e.getCustomer();
            if (customer.getState() == State.ARRIVES) {
                boolean haveServer = false;
                // processing an arrival event
                // check Server
                for (Server server : servers) {
                    if (server.canServe()) {
                        // Server is idle and can serve immediately -> add served event
                        server.serve(customer);
                        eventQ.add(e.createNewEvent(State.SERVED, server));
                        statistics = statistics.addNumServed();
                        haveServer = true;
                        break;
                    }
                }
                if (!haveServer) {
                    if (customer.isTypical()) {
                        for (int i = 0; 
                            i < (numOfSelfCheckOuts > 0 
                                ? numOfHumanServers + 1 : numOfHumanServers); 
                            i++) {
                            Server server = servers.get(i);
                            if (server.canWaitList()) {
                                server.waitList(customer);
                                eventQ.add(e.createNewEvent(State.WAITS, server));
                                statistics = statistics.addNumServed();
                                haveServer = true;
                                break;
                            } 
                        }
                    } else {
                        Server shortestServer = null;
                        for (int i = 0; 
                            i < (numOfSelfCheckOuts > 0 
                                ? numOfHumanServers + 1 : numOfHumanServers); 
                            i++) {
                            Server server = servers.get(i);
                            if (server.canWaitList()) {
                                if (shortestServer == null) {
                                    shortestServer = server;
                                } else {
                                    shortestServer = 
                                        (server.hasShorterQThan(shortestServer) 
                                            ? server : shortestServer);
                                } 
                                haveServer = true;
                            }
                        }
                        if (haveServer) {
                            shortestServer.waitList(customer);
                            eventQ.add(e.createNewEvent(State.WAITS, shortestServer));
                            statistics = statistics.addNumServed();
                        }
                    }
                }
                if (!haveServer) {
                    eventQ.add(e.createNewEvent(State.LEAVES, null));
                    statistics = statistics.addNumLeft();
                }
            } else if (customer.getState() == State.SERVED) {
                Server server = e.getServer();
                // processing a serving event -> add done event
                eventQ.add(e.createNewEvent(randomGenerator.genServiceTime(), State.DONE));
                statistics = statistics.addTotalWaitingTime(
                    e.getWaitingTime(customer));
            } else if (customer.getState() == State.WAITS) {
                // processing a waiting event
                // no new events created -> handled by server's current customer's done event
            } else if (customer.getState() == State.LEAVES) {
                // processing a leaving event -> do nothing
            } else if (customer.getState() == State.DONE) {
                // processing a done event
                // clear server's current customer only
                Server server = e.getServer();
                server.clearCurrent(); 
                
                // simulate random resting event occurence FOR HUMAN SERVER ONLY
                if (server.isHumanServer() && randomGenerator.genRandomRest() < restProb) {
                    // server rests -> add rest event
                    eventQ.add(new RestEvent(e.getTime(), server));
                    // waiting customer's serve event handled by subsequent BackEvent
                } else {
                    // server does not rest -> standard code
                    // check server for waitlisted customer
                    if (server.hasWait()) {
                        // server has waitlisted customer -> serve waitlisted customer
                        Customer nextCustomer = server.getNextCustomer();
                        server.serveNextCustomer();
                        eventQ.add(e.createNewEvent(nextCustomer, State.SERVED));
                    } else {
                        // server has no waitlisted customer -> return to idle by .clearCurrent()
                        // no new events created
                    }
                }
            }
        }
    }
}

