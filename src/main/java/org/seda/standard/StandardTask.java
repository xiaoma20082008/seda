package org.seda.standard;

import lombok.Setter;
import org.seda.EventKey;
import org.seda.Task;

@Setter
public abstract class StandardTask extends Task {

  protected StandardEventService eventService;

  public StandardTask(EventKey key) {
    super(key);
  }

}
