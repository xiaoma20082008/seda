package org.seda.disruptor;

import org.seda.EventKey;
import org.seda.Task;
import org.seda.TaskFactory;

public class DisruptorTaskFactory implements TaskFactory {

  @Override
  public Task createSelectTask(EventKey key) {
    return new DisruptorTaskSelect(key);
  }

  @Override
  public Task createExtractTask(EventKey key) {
    return new DisruptorTaskExtract(key);
  }

  @Override
  public Task createTransformTask(EventKey key) {
    return new DisruptorTaskTransform(key);
  }

  @Override
  public Task createLoadTask(EventKey key) {
    return new DisruptorTaskLoad(key);
  }

}
