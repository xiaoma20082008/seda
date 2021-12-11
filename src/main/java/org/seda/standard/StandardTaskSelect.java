package org.seda.standard;

import java.util.concurrent.atomic.AtomicInteger;
import org.seda.EventData;
import org.seda.EventKey;

public class StandardTaskSelect extends StandardTask {

  private final AtomicInteger selectCount = new AtomicInteger();

  public StandardTaskSelect(EventKey key) {
    super(key);
  }

  @Override
  public void run() {
    running = true;

    while (running) {
      try {
        EventData data = eventService.selectEvent().await(key);
        selectCount.incrementAndGet();
        executor.submit(() -> {
          doSomething();
          eventService.selectEvent().signal(data);
        });
      } catch (InterruptedException ex) {
        logger.info("SelectTask Interrupted");
        break;
      }
    }

  }

  public int getCount() {
    return selectCount.get();
  }

}
