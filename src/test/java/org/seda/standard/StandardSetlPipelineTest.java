package org.seda.standard;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.seda.EventKey;

@Slf4j
public class StandardSetlPipelineTest {

  @Test
  public void test_StandardSetlPipeline() {
    StandardSetlPipeline pipeline = new StandardSetlPipeline(new EventKey(101));
    pipeline.start();
    int count;
    int index;
    do {
      count = pipeline.getSelectCount();
      index = pipeline.getSelectIndex();
      try {
        Thread.sleep(100);
      } catch (InterruptedException ignore) {
      }
      log.info("{}:{}", index, count);
    } while (count < 200);
    pipeline.close();
  }
}
