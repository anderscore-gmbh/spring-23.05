package reactor.server.clock;

import java.time.Instant;
import java.util.Objects;

public class ClockEvent {
    private Instant createdAt;
    private String formattedTime;

    // default constructor required to (de-)serialize
    public ClockEvent() {
    }

    public ClockEvent(Instant createdAt, String formattedTime) {
        this.createdAt = createdAt;
        this.formattedTime = formattedTime;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClockEvent message = (ClockEvent) o;
        return Objects.equals(createdAt, message.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt);
    }

    @Override
    public String toString() {
        return formattedTime;
    }
}
