package cs2030.simulator;

/**
 * Statistics class representing the statistics to collect.
 */
public class Statistics {
    private final int numServed;
    private final int numLeft;
    private final double totalWaitingTime;

    /**
     * Constructs a new statistics object where data initialised to 0.
     */
    Statistics() {
        this.numServed = 0;
        this.numLeft = 0;
        this.totalWaitingTime = 0;
    }

    /**
     * Constructs a new statistics object where data is initialised according
     * to the given arguments.
     * @param numServed number of customers served
     * @param numLeft number of customers who left without getting served
     * @param totalWaitingTime summation of all waiting times of customers who waited
     */
    Statistics(int numServed, int numLeft, double totalWaitingTime) {
        this.numServed = numServed;
        this.numLeft = numLeft;
        this.totalWaitingTime = totalWaitingTime;
    }

    Statistics addTotalWaitingTime(double time) {
        return new Statistics(numServed, numLeft, totalWaitingTime + time);
    }

    Statistics addNumServed() {
        return new Statistics(numServed + 1, numLeft, totalWaitingTime);
    }

    Statistics addNumLeft() {
        return new Statistics(numServed, numLeft + 1, totalWaitingTime);
    }

    /**
     * Computes and returns the average waiting time of each customer served.
     * @return total waiting time of all customers served / number of customers served
     */
    double getAvgWaitingTime() {
        if (numServed == 0) {
            return 0;
        } else {
            return totalWaitingTime / numServed;
        }
    }

    @Override
    public String toString() {
        return "[" + String.format("%.3f", getAvgWaitingTime()) + " " + 
                numServed + " " + numLeft + "]";
    }
}

    
