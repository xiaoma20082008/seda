package org.seda.standard;

import lombok.Setter;
import org.seda.EventData;
import org.seda.EventKey;
import org.seda.Stage;

@Setter
public class MemoryEventSelect implements StandardEventSelect {

  private StageController stageController;
  private long idx = 0;

  @Override
  public EventData await(EventKey key) {
    EventData data = new EventData();
    data.setKey(key);
    data.setId(++idx);
    return data;
  }

  @Override
  public void signal(EventData data) {
    stageController.signal(Stage.SELECT, data);
  }

}
