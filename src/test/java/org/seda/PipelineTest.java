package org.seda;

import org.junit.Test;
import org.seda.disruptor.DisruptorTaskFactory;
import org.seda.standard.StandardTaskFactory;

public class PipelineTest {

  @Test
  public void test_standard() {
    Pipeline pipeline = new Pipeline();
    pipeline.setTaskFactory(new StandardTaskFactory());
    pipeline.start(new EventKey(101));
    try {
      Thread.sleep(1000 * 60 * 60);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test_disruptor() {
    Pipeline pipeline = new Pipeline();
    pipeline.setTaskFactory(new DisruptorTaskFactory());
    pipeline.start(new EventKey(101));
  }
}
