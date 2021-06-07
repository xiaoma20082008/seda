package org.seda.standard;

import lombok.extern.slf4j.Slf4j;
import org.seda.EventData;
import org.seda.EventKey;

@Slf4j
public class StandardTaskLoad extends StandardTask {

  public StandardTaskLoad(EventKey key) {
    super(key);
  }

  @Override
  public void run() {
    running = true;

    while (running) {
      try {
        EventData data = eventService.loadEvent().await(key);
        log.info("Load await");
        executor.submit(() -> {
          randomSleep();
          log.info("Load signal");
          eventService.loadEvent().signal(data);
        });
      } catch (InterruptedException ex) {
        log.info("LoadTask Interrupted");
        break;
      }
    }
  }

}
