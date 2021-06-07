package org.seda.disruptor;

import org.seda.EventKey;

public class DisruptorTaskExtract extends DisruptorTask {

  public DisruptorTaskExtract(EventKey key) {
    super(key);
  }

  @Override
  public void doCall() throws InterruptedException {

  }

}
