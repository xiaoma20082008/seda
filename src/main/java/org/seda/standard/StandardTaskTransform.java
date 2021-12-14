package org.seda.standard;

import org.seda.EventData;
import org.seda.EventKey;

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
        executor.submit(() -> {
          doSomething(data);
          eventService.transformEvent().signal(data);
        });
      } catch (InterruptedException ex) {
        logger.info("TransformTask Interrupted");
        break;
      }
    }
  }

}
