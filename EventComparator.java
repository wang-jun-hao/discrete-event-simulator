package cs2030.simulator;

import java.util.Comparator;

/**
 * EventComparator class implements the logic of comparing 2 events by time 
 * (smaller time given priority), if both events have the same time, they are compared by 
 * customer id (smaller id given priority).
 */
public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event e1, Event e2) {
        if (e1.getTime() != e2.getTime()) {
            // the 2 events have different time -> sort by time
            return ((Double) e1.getTime()).compareTo(e2.getTime());
        } else {
            // the 2 events share the same time -> sort by customer id
            return ((Integer) e1.getCustomer().getId()).compareTo(e2.getCustomer().getId());
        }
    }

}
