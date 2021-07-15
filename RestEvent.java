package cs2030.simulator;

/**
 * RestEvent class that extends Event and represents the event whereby a server
 * temporarily pauses its service after finishing serving a customer.
 */
class RestEvent extends Event {
    // constructor
    /**
     * Constructs a RestEvent at given time involving given server.
     * @param time time of event of server taking a break
     * @param server the server taking break
     */
    RestEvent(double time, Server server) {
        super(time, new TypicalCustomer(), server);
    }

    @Override
    public boolean isRestEvent() {
        return true;
    }

    @Override
    public boolean isBackEvent() {
        return false;
    }

    @Override 
    public String toString() {
        return "";
    }
}
