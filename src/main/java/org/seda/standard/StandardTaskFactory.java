package org.seda.standard;

import org.seda.EventKey;
import org.seda.Task;
import org.seda.TaskFactory;

public class StandardTaskFactory implements TaskFactory {

  @Override
  public Task createSelectTask(EventKey key) {
    return new StandardTaskSelect(key);
  }

  @Override
  public Task createExtractTask(EventKey key) {
    return new StandardTaskExtract(key);
  }

  @Override
  public Task createTransformTask(EventKey key) {
    return new StandardTaskTransform(key);
  }

  @Override
  public Task createLoadTask(EventKey key) {
    return new StandardTaskLoad(key);
  }

}
