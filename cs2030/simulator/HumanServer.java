package cs2030.simulator;

/**
 * HumanServer class representing a human server in the simulator.
 */
class HumanServer extends Server {
    // constructor
    /**
     *Constructs a human server who can waitlist up to (size) number of customers.
     *@param size maximum length of customer queue that can be handled by this server
     */
    HumanServer(int size) {
        super(size);
    }

    @Override
    public boolean isHumanServer() {
        return true;
    }

    @Override
    public boolean isSelfCheckOut() {
        return false;
    }

    @Override
    public String toString() {
        return "server " + id;
    }
}
