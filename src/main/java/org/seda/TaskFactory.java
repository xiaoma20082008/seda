package org.seda;

public interface TaskFactory {

  Task createSelectTask(EventKey key);

  Task createExtractTask(EventKey key);

  Task createTransformTask(EventKey key);

  Task createLoadTask(EventKey key);

}
