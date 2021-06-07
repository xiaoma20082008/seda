package org.seda.standard;

import org.seda.EventData;
import org.seda.EventKey;

public class StandardTaskLoad extends StandardTask {

  public StandardTaskLoad(EventKey key) {
    super(key);
  }

  @Override
  public void doCall() throws InterruptedException {
    while (started) {
      EventData data = eventService.loadEvent().await(key);
      randomSleep();
      System.out.println("LoadTask doing...");
      eventService.loadEvent().signal(data);
    }
  }

}
