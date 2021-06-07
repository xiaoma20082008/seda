package org.seda.standard;

import org.seda.EventData;
import org.seda.EventKey;

public class StandardTaskExtract extends StandardTask {

  public StandardTaskExtract(EventKey key) {
    super(key);
  }

  @Override
  public void doCall() throws InterruptedException {
    EventData data = eventService.extractEvent().await(key);
    randomSleep();
    System.out.println("ExtractTask doing...");
    eventService.extractEvent().signal(data);
  }

}
