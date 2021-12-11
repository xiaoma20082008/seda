package org.seda.standard;

import org.seda.EventData;
import org.seda.EventKey;

public class StandardTaskExtract extends StandardTask {

  public StandardTaskExtract(EventKey key) {
    super(key);
  }

  @Override
  public void run() {
    running = true;

    while (running) {
      try {
        EventData data = eventService.extractEvent().await(key);
        executor.submit(() -> {
          doSomething();
          eventService.extractEvent().signal(data);
        });
      } catch (InterruptedException ex) {
        logger.info("ExtractTask Interrupted");
        break;
      }

    }
  }

}
