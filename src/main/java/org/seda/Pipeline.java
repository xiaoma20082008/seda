package org.seda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Setter;

@Setter
public class Pipeline {

  private final Map<EventKey, List<Task>> taskMap = new HashMap<>();
  private TaskFactory taskFactory;

  public void start(EventKey key) {
    List<Task> tasks = new ArrayList<>();
    Task selectTask = taskFactory.createSelectTask(key);
    Task extractTask = taskFactory.createExtractTask(key);
    Task transformTask = taskFactory.createTransformTask(key);
    Task loadTask = taskFactory.createLoadTask(key);
    tasks.add(selectTask);
    tasks.add(extractTask);
    tasks.add(transformTask);
    tasks.add(loadTask);
    tasks.forEach(Task::start);
    taskMap.put(key, tasks);
  }

  public void close(EventKey key) {
    List<Task> tasks = taskMap.remove(key);
    if (tasks == null) {
      return;
    }
    tasks.forEach(Task::close);
  }

}
