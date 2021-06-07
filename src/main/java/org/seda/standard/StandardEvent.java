package org.seda.standard;

import org.seda.EventData;
import org.seda.EventKey;

public interface StandardEvent {

  EventData await(EventKey key) throws InterruptedException;

  void signal(EventData data);
}
