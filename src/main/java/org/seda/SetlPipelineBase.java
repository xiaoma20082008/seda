package org.seda;

public abstract class SetlPipelineBase implements SetlPipeline {

  protected final EventKey key;

  public SetlPipelineBase(EventKey key) {
    this.key = key;
  }

}
