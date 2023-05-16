package reactor.server.clock;

public interface ClockEventListener {

    void handleClockEvent(ClockEvent event);
}
