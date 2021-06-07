package org.seda.standard;

import org.seda.EventData;
import org.seda.EventKey;
import org.seda.Stage;

public interface StageController {

  EventData await(Stage stage, EventKey key) throws InterruptedException;

  boolean signal(Stage stage, EventData data);
}
