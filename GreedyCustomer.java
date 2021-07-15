package cs2030.simulator;

/**
 * GreedyCustomer class, extending Customer, representing greedy customer in the simulator.
 */
public class GreedyCustomer extends Customer {
    // constructor
    /**
     * Constructs a new greedy customer with an identifier 
     * id indexed from 1, in the order Customers (of all types) are created.
     * @param arrivalTime customer's time of arrival
     * @param state State of the customer (State.ARRIVES/SERVED/WAITS/LEAVES/DONE)
     */
    GreedyCustomer(double arrivalTime, State state) {
        super(arrivalTime, state);
    }

    private GreedyCustomer(double arrivalTime, int id, State state) {
        super(arrivalTime, id, state);
    }

    @Override
    public GreedyCustomer setState(State state) {
        return new GreedyCustomer(arrivalTime, id, state);
    }

    @Override
    public boolean isGreedy() {
        return true;
    }

    @Override
    public boolean isTypical() {
        return false;
    }

    @Override
    public String toString() {
        return id + "(greedy)";
    }
}
