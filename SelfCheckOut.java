package cs2030.simulator;

/**
 * SelfCheckOut class representing a self-checkout counter in the simulator.
 */
class SelfCheckOut extends Server {
    private final SelfCheckOut mainSelfCheckOut;

    // constructor
    /**
     *Constructor for the first self-checkout counter in the simulator.
     *@param size maximum length of customer queue that can be handled by this counter
     */
    SelfCheckOut(int size) {
        super(size);
        this.mainSelfCheckOut = this;
    }

    // constructor for subsequent SelfCheckOuts
    /**
     * Constructor for subsequent self-checkout counters in the simulator.
     * @param size maximum length of customer queue that can be handled by this counter
     * @param mainSelfCheckOut first self-checkout counter created in the simulator 
     *     whose queue is the common shared queue for all self-checkout counters
     */
    SelfCheckOut(int size, SelfCheckOut mainSelfCheckOut) {
        super(size);
        this.mainSelfCheckOut = mainSelfCheckOut;
    }

    @Override
    public void serveNextCustomer() {
        currCustomer = mainSelfCheckOut.customerQ.poll();
    }

    @Override
    public boolean canWaitList() {
        return mainSelfCheckOut.customerQ.size() < maxQLength;
    }

    @Override
    public void waitList(Customer customer) {
        mainSelfCheckOut.customerQ.add(customer);
    }

    @Override
    public boolean hasWait() {
        return mainSelfCheckOut.customerQ.size() > 0;
    }

    @Override
    public Customer getNextCustomer() {
        return mainSelfCheckOut.customerQ.peek();
    }

    @Override
    public boolean isHumanServer() {
        return false;
    }

    @Override
    public boolean isSelfCheckOut() {
        return true;
    }

    @Override
    public String toString() {
        return "self-check " + id;
    }
}

