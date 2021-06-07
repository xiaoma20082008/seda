package org.seda.standard;

import org.seda.EventKey;
import org.seda.Task;

public abstract class StandardTask extends Task {

  protected StandardEventService eventService = new StandardEventService();

  public StandardTask(EventKey key) {
    super(key);
  }

}
