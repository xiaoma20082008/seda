package org.seda.standard;

import lombok.extern.slf4j.Slf4j;
import org.seda.EventData;
import org.seda.EventKey;

@Slf4j
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
        log.info("Extract await");
        executor.submit(() -> {
          randomSleep();
          eventService.extractEvent().signal(data);
          log.info("Extract signal");
        });
      } catch (InterruptedException ex) {
        log.info("ExtractTask Interrupted");
        break;
      }

    }
  }

}
