package org.seda.standard;

import org.seda.EventData;
import org.seda.EventKey;

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
        executor.submit(() -> {
          doSomething();
          eventService.loadEvent().signal(data);
        });
      } catch (InterruptedException ex) {
        logger.info("LoadTask Interrupted");
        break;
      }
    }
  }

}
