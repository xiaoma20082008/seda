package org.seda;

public enum Stage {
  SELECT,
  EXTRACT,
  TRANSFORM,
  LOAD;

  public boolean isSelect() {
    return this.equals(SELECT);
  }

  public boolean isExtract() {
    return this.equals(EXTRACT);
  }

  public boolean isTransform() {
    return this.equals(TRANSFORM);
  }

  public boolean isLoad() {
    return this.equals(LOAD);
  }

}
