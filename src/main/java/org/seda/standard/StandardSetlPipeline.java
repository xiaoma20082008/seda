package org.seda.standard;

import org.seda.EventKey;
import org.seda.SetlPipelineBase;
import org.seda.Task;
import org.seda.TaskFactory;

public class StandardSetlPipeline extends SetlPipelineBase {

  private final TaskFactory factory = new StandardTaskFactory();

  private Task selectTask;
  private Task extractTask;
  private Task transformTask;
  private Task loadTask;

  public StandardSetlPipeline(EventKey key) {
    super(key);
  }

  @Override
  public void start() {
    this.selectTask = factory.createSelectTask(key);
    this.extractTask = factory.createExtractTask(key);
    this.transformTask = factory.createTransformTask(key);
    this.loadTask = factory.createLoadTask(key);

    this.selectTask.start();
    this.extractTask.start();
    this.transformTask.start();
    this.loadTask.start();
  }

  @Override
  public void close() {
    this.selectTask.close();
    this.extractTask.close();
    this.transformTask.close();
    this.loadTask.close();
  }

  public int getSelectCount() {
    return ((StandardTaskSelect) selectTask).getCount();
  }

  public int getSelectIndex() {
    return ((StandardTaskSelect) selectTask).getIndex();
  }
}
