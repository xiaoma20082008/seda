package org.seda;

public interface SetlPipeline extends AutoCloseable {

  void start();

  @Override
  void close();
}
