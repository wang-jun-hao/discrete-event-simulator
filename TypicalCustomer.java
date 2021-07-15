package cs2030.simulator;

/**
 * TypicalCustomer class, extending Customer, representing typical customer in the simulator.
 */
public class TypicalCustomer extends Customer {
    // constructor
    /**
     * Constructs a new typical customer with an identifier 
     * id indexed from 1, in the order Customers (of all types) are created.
     * @param arrivalTime customer's time of arrival
     * @param state State of the customer (State.ARRIVES/SERVED/WAITS/LEAVES/DONE)
     */
    TypicalCustomer(double arrivalTime, State state) {
        super(arrivalTime, state);
    }

    TypicalCustomer(double arrivalTime, int id, State state) {
        super(arrivalTime, id, state);
    }

    /**
     * Constructs a place-holder customer of id 0 for use by Rest and Back events 
     * for correct priority order of events.
     */
    TypicalCustomer() {
        super();
    }

    @Override
    public TypicalCustomer setState(State state) {
        return new TypicalCustomer(arrivalTime, id, state);
    }

    @Override
    public boolean isGreedy() {
        return false;
    }

    @Override
    public boolean isTypical() {
        return true;
    }

    @Override
    public String toString() {
        return "" + id;
    }
}

