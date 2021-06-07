package org.seda;

import java.io.Serializable;
import lombok.Data;

@Data
public class EventData implements Serializable {

  private static final long serialVersionUID = 5702927600948447443L;

  private EventKey key;
  private long id;

}
