package org.seda;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class EventKey implements Serializable {

  private static final long serialVersionUID = 5665732162602631370L;

  private final long id;

  public EventKey(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return id + "";
  }

}
