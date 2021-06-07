package org.seda.standard;

import org.seda.EventKey;
import org.seda.Task;
import org.seda.TaskFactory;

public class StandardTaskFactory implements TaskFactory {

  private final StandardEventService eventService;

  public StandardTaskFactory() {
    this.eventService = new StandardEventService();
  }

  @Override
  public Task createSelectTask(EventKey key) {
    StandardTaskSelect taskSelect = new StandardTaskSelect(key);
    taskSelect.setEventService(eventService);
    return taskSelect;
  }

  @Override
  public Task createExtractTask(EventKey key) {
    StandardTaskExtract taskExtract = new StandardTaskExtract(key);
    taskExtract.setEventService(eventService);
    return taskExtract;
  }

  @Override
  public Task createTransformTask(EventKey key) {
    StandardTaskTransform taskTransform = new StandardTaskTransform(key);
    taskTransform.setEventService(eventService);
    return taskTransform;
  }

  @Override
  public Task createLoadTask(EventKey key) {
    StandardTaskLoad taskLoad = new StandardTaskLoad(key);
    taskLoad.setEventService(eventService);
    return taskLoad;
  }

}
