package org.seda.standard;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.seda.EventData;
import org.seda.EventKey;

@Slf4j
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
        log.info("Select await. Id={}", data.getId());
        selectCount.incrementAndGet();
        executor.submit(() -> {
          randomSleep();
          eventService.selectEvent().signal(data);
          log.info("Select signal");
        });
      } catch (InterruptedException ex) {
        log.info("SelectTask Interrupted");
        break;
      }
    }

  }

  public int getCount() {
    return selectCount.get();
  }

}
