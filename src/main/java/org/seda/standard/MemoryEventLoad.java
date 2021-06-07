package org.seda.standard;

import lombok.Setter;
import org.seda.EventData;
import org.seda.EventKey;
import org.seda.Stage;

@Setter
public class MemoryEventLoad implements StandardEventLoad {

  private StageController stageController;

  @Override
  public EventData await(EventKey key) throws InterruptedException {
    return stageController.await(Stage.LOAD, key);
  }

  @Override
  public void signal(EventData data) {
    stageController.signal(Stage.LOAD, data);
  }
}
