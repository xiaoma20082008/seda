package org.seda.disruptor;

import org.seda.EventKey;

public class DisruptorTaskLoad extends DisruptorTask {

  public DisruptorTaskLoad(EventKey key) {
    super(key);
  }

  @Override
  public void doCall() throws InterruptedException {

  }

}
