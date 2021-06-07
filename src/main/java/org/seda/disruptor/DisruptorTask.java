package org.seda.disruptor;

import org.seda.EventKey;
import org.seda.Task;

public abstract class DisruptorTask extends Task {

  public DisruptorTask(EventKey key) {
    super(key);
  }
}
