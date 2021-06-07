package org.seda.standard;

import org.seda.EventData;
import org.seda.EventKey;

public class StandardTaskSelect extends StandardTask {

  public StandardTaskSelect(EventKey key) {
    super(key);
  }

  @Override
  public void doCall() throws InterruptedException {
    EventData data = eventService.selectEvent().await(key);
    randomSleep();
    System.out.println("SelectTask doing...");
    eventService.selectEvent().signal(data);
  }


}
