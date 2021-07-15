package cs2030.simulator;

/**
 * BackEvent class that extends Event and represents the event whereby a server
 * returns back to service and resumes serving.
 */
class BackEvent extends Event {
    // constructor
    /**
     * Constructs a BackEvent at given time involving given server.
     * @param time time of event of server returning back to service
     * @param server the server returning back to service
     */
    BackEvent(double time, Server server) {
        super(time, new TypicalCustomer(), server);
    }

    @Override
    public boolean isRestEvent() {
        return false;
    }

    @Override
    public boolean isBackEvent() {
        return true;
    }

    @Override 
    public String toString() {
        return "";
    }
}
