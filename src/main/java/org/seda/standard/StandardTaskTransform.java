package org.seda.standard;

import org.seda.EventData;
import org.seda.EventKey;

public class StandardTaskTransform extends StandardTask {

  public StandardTaskTransform(EventKey key) {
    super(key);
  }

  @Override
  public void doCall() throws InterruptedException {
    while (started) {
      EventData data = eventService.transformEvent().await(key);
      randomSleep();
      System.out.println("TransformTask doing...");
      eventService.transformEvent().signal(data);
    }
  }

}
