package cs2030.simulator;

/**
 * Event class representing events(actions) of the system, where each event
 * has a Customer, time and a Server.
 */
public class Event {
    private final Customer customer;
    private final double time;
    private final Server server;

    // constructor
    /**
     * Constructs event of given time involving given customer amd given server.
     * @param time time of event
     * @param customer customer involved in event
     * @param server server involed in event, null if event concerns no server
     */ 
    Event(double time, Customer customer, Server server) {
        this.time = time;
        this.customer = customer;
        this.server = server;
    }

    // getters
    public Customer getCustomer() {
        return customer;
    }

    public double getTime() {
        return time;
    }

    public Server getServer() {
        return server;
    }

    // sub-type checkers
    public boolean isRestEvent() {
        return false;
    }

    public boolean isBackEvent() {
        return false;
    }

    // methods
    /**
     * Returns a new event involving the same customer in the given state handled by given server
     * at the same time as reference event.
     * @param state new state of customer
     * @param server server handling the customer
     * @return event at the same time involving the same customer in a different state handled
     *     by the given server
     */
    public Event createNewEvent(State state, Server server) {
        return new Event(time, customer.setState(state), server);
    }

    /**
     * Returns a new event involving the same server and same customer in the 
     * given state at time = serviceTime after reference event's time.
     * @param serviceTime service time of server
     * @param state new state of customer at time of new event
     * @return event at serviceTime after reference event's time involving the same customer
     *     in a different state and the same server
     */
    public Event createNewEvent(double serviceTime, State state) {
        return new Event(time + serviceTime, customer.setState(state), this.server);
    }

    /**
     * Returns a new event involving the given customer with the given state at
     * the same time as the reference event with the same server.
     * @param customer customer involved in the event
     * @param state new state of customer
     * @return event with same server and at same time as referenced event involving given customer
     *     with a given state
     */
    public Event createNewEvent(Customer customer, State state) {
        return new Event(time, customer.setState(state), this.server);
    }

    /**
     * Returns amount of time the given customer has waited since arrival until event's time.
     * @param customer subject customer
     * @return amount of time the given customer has waited until served
     */
    public double getWaitingTime(Customer customer) {
        return time - customer.getArrivalTime();
    }

    @Override
    public String toString() {
        if (server == null) {
            return String.format("%.3f", time) + " " + customer
                    + " " + customer.getState();
        } else {
            return String.format("%.3f", time) + " " + customer
                    + " " + customer.getState() + " " + server;
        }
    }
}
        
