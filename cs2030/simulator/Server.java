package cs2030.simulator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * An abstract class representing a Server object that can serve customers in the simulator.
 */
public abstract class Server {
    protected Customer currCustomer;
    protected final Queue<Customer> customerQ;
    protected final int maxQLength;
    protected final int id;
    private static int count = 0;
    private boolean resting = false;

    /**
     *Constructor for an indexed (from 1) idle server who can serve and waitlist customers up 
     *to (maxQLength) number of waiting customers, for use by concrete sub-classes.
     *@param maxQLength maximum length of customer queue that can be handled by this server
     */
    // constructor
    protected Server(int maxQLength) {       
        // idle server
        this.currCustomer = null;
        this.maxQLength = maxQLength;
        this.customerQ = new LinkedList<>();
        count++;
        this.id = count;
    }

    /**
     *Returns true if server is not resting and is able to serve a new customer
     *immediately (no customer queue), false otherwise.
     *@return true if able to serve a customer immediately, false otherwise
     */
    public boolean canServe() {
        return !resting && currCustomer == null;
    }

    /**
     *Returns true if server (which may be resting) is able to have a customer on waitlist,
     *(customer queue still has vacancy), false otherwise.
     *@return true if able to waitlist a customer, false otherwise
     */
    public boolean canWaitList() {
        return customerQ.size() < maxQLength;
        // customer queue not maxed out yet
    }

    /**
     *Serve given customer immediately.
     *@param customer customer to be served
     */
    public void serve(Customer customer) {
        currCustomer = customer;
    }

    /**
     *Serve the next customer in the customer queue.
     */
    public void serveNextCustomer() {
        currCustomer = customerQ.poll();
    }

    /**
    *Add given customer as waitlisted customer by adding customer to customer queue.
    *@param customer customer to be waitlisted (added to the queue)
    */
    public void waitList(Customer customer) {
        customerQ.add(customer);
    }

    /**
     *Clears the spot for a new customer to be served by server, invoked when server
     *finishes serving a customer.
     */
    public void clearCurrent() {
        currCustomer = null;
    }

    /**
     *Returns true if there are customers in the queue waiting
     *to be served.
     *@return true if there are customers waiting in the queue
     */
    public boolean hasWait() {
        return customerQ.size() > 0;    // still have customer in queue
    }

    /**
     *Retrieve the next customer in the customer queue.
     *@return next customer in the customer queue to be served
     */
    public Customer getNextCustomer() {
        return customerQ.peek();
    }

    /**
     *Puts Server to rest.
     */
    public void rest() {
        resting = true;
    }

    /**
     *Puts Server back in service.
     */
    public void back() {
        resting = false;
    }

    /**
     *Checks if this server has a shorter customer queue than given server.
     *@param other server to be compared against
     *@return true if this server has a shorter customer queue than given server
     */
    public boolean hasShorterQThan(Server other) {
        return this.customerQ.size() < other.customerQ.size();
    }

    abstract boolean isHumanServer();

    abstract boolean isSelfCheckOut();

    @Override
    public abstract String toString();

}
