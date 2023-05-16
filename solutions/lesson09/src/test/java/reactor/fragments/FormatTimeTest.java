package reactor.fragments;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class FormatTimeTest {

    @Test
    public void test() {
        Instant instant = Instant.now();
        ZonedDateTime time = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
        String formatted = formatter.format(time);
        System.out.println(formatted);
    }
}
