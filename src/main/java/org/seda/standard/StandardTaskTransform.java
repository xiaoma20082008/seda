package org.seda.standard;

import lombok.extern.slf4j.Slf4j;
import org.seda.EventData;
import org.seda.EventKey;

@Slf4j
public class StandardTaskTransform extends StandardTask {

  public StandardTaskTransform(EventKey key) {
    super(key);
  }

  @Override
  public void run() {
    running = true;

    while (running) {
      try {
        EventData data = eventService.transformEvent().await(key);
        log.info("Transform await");
        executor.submit(() -> {
          randomSleep();
          log.info("Transform signal");
          eventService.transformEvent().signal(data);
        });
      } catch (InterruptedException ex) {
        log.info("TransformTask Interrupted");
        break;
      }
    }
  }

}
