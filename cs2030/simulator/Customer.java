package cs2030.simulator;

/**
 *Abstract Customer class representing customers where each customer has
 *his/her own unique id, state and arrivalTime.
 */
public abstract class Customer {
    protected final int id;
    private static int totalCount = 0;
    protected final State state;
    protected final double arrivalTime;

    /**
     * Constructor for concrete sub-classes to create a new customer with an identifier 
     * id indexed from 1, in the order they are created.
     * @param arrivalTime customer's time of arrival
     * @param state State of the customer (State.ARRIVES/SERVED/WAITS/LEAVES/DONE)
     */ 
    // constructor
    public Customer(double arrivalTime, State state) {
        // new customer
        this.id = totalCount + 1;
        totalCount++;
        this.state = state;
        this.arrivalTime = arrivalTime;
    }

    protected Customer(double arrivalTime, int id, State state) {
        // existing customer changing state -> totalCount stays the same
        this.id = id;
        this.state = state;
        this.arrivalTime = arrivalTime;
    }

    /**
     * Constructor for concrete sub-classes to create a customer object with id = 0 as 
     * a place-holder for Rest and Back Events, does not affect totalCount and subsequent 
     * indexing of customers.
     */
    public Customer() {
        // Customer of id 0 for use by Rest and Back events for correct priority order
        this.id = 0;
        this.state = null;
        this.arrivalTime = 0;
    }


    // getters
    /**
     * Static method for retrieving total number of Customers created.
     * @return total number of Customers created
     */
    public static int getTotalCount() {
        return totalCount;
    }

    public int getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }
    
    /**
     * Returns a new Customer of the same type and id with the given state.
     * @param state customer's new state
     * @return new customer of the same type with same id and given state
     */
    public abstract Customer setState(State state);

    abstract boolean isGreedy();

    abstract boolean isTypical();

    @Override
    public abstract String toString();

}
