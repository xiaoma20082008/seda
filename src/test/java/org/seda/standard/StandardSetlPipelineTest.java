package org.seda.standard;

import org.junit.Test;
import org.seda.EventKey;

public class StandardSetlPipelineTest {

  @Test
  public void test_StandardSetlPipeline() {
    StandardSetlPipeline pipeline = new StandardSetlPipeline(new EventKey(101));
    pipeline.start();
    while (pipeline.getSelectCount() < 200) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException ignore) {
      }
    }
    pipeline.close();
  }
}
