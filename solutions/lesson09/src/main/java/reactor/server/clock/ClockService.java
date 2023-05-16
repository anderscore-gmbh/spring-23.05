package reactor.server.clock;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@EnableScheduling
public class ClockService {
    private List<ClockEventListener> listeners = Collections.synchronizedList(new LinkedList<>());

    @Scheduled(fixedRate = 1000)
    public void tick() {
        ClockEvent event = renderTime(Instant.now());
        publish(event);
    }

    public void addListener(ClockEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ClockEventListener listener) {
        listeners.remove(listener);
    }

    private void publish(ClockEvent event) {
        listeners.forEach(l -> l.handleClockEvent(event));
    }

    private ClockEvent renderTime(Instant instant) {
        ZonedDateTime time = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
        String formattedTime = formatter.format(time);
        ClockEvent clockEvent = new ClockEvent(instant, formattedTime);
        return clockEvent;
    }
}
