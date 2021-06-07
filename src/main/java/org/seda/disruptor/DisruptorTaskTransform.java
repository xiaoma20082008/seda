package org.seda.disruptor;

import org.seda.EventKey;

public class DisruptorTaskTransform extends DisruptorTask {

  public DisruptorTaskTransform(EventKey key) {
    super(key);
  }

  @Override
  public void doCall() throws InterruptedException {

  }

}
