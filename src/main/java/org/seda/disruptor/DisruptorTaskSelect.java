package org.seda.disruptor;

import org.seda.EventKey;

public class DisruptorTaskSelect extends DisruptorTask {

  public DisruptorTaskSelect(EventKey key) {
    super(key);
  }

  @Override
  public void doCall() throws InterruptedException {

  }


}
