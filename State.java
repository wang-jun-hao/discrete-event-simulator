package cs2030.simulator;

/**
 * State class representing the 5 different states each customer can be in.
 */
enum State {
    ARRIVES("arrives"),
    SERVED("served by"),
    WAITS("waits to be served by"),
    LEAVES("leaves"),
    DONE("done serving by");

    private final String output;

    State(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return output;
    }
}
