package wator.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wator.simple.SimpleTorus;

import java.io.IOException;

@Service
public class Timer {

    @Autowired
    private WorldEngine worldEngine;
    @Autowired
    private SimpleTorus torus;

    private int count;

    @Scheduled(fixedDelay = 1000)
    void tick() throws IOException {
        count++;
        worldEngine.tick();
        torus.dump(count);
    }
}
