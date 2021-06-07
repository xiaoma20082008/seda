package org.seda.standard;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.seda.EventData;
import org.seda.EventKey;

@Slf4j
public class StandardEventServiceTest {

  @Test
  public void test_StandardEventService() throws InterruptedException {
    EventKey key = new EventKey(1001L);
    StandardEventService eventService = new StandardEventService();

    ExecutorService pool = Executors.newCachedThreadPool();
    CountDownLatch latch = new CountDownLatch(1);

    Thread s = new Thread(() -> {
      try {
        int i = 0;
        while (i++ < 1000) {
          try {
            EventData data = eventService.selectEvent().await(key);
            log.info("Select await-" + i);
            pool.submit(() -> {
              randomSleep();
              eventService.selectEvent().signal(data);
              log.info("Select signal");
            });
          } catch (InterruptedException ex) {
            log.info("SelectTask Interrupted");
            break;
          }
        }
      } finally {
        latch.countDown();
      }
    }, "SelectTask");
    Thread e = new Thread(() -> {
      while (true) {
        try {
          EventData data = eventService.extractEvent().await(key);
          log.info("Extract await");
          pool.submit(() -> {
            randomSleep();
            eventService.extractEvent().signal(data);
            log.info("Extract signal");
          });
        } catch (InterruptedException ex) {
          log.info("ExtractTask Interrupted");
          break;
        }

      }
    }, "ExtractTask");
    Thread t = new Thread(() -> {
      while (true) {
        try {
          EventData data = eventService.transformEvent().await(key);
          log.info("Transform await");
          pool.submit(() -> {
            randomSleep();
            log.info("Transform signal");
            eventService.transformEvent().signal(data);
          });
        } catch (InterruptedException ex) {
          log.info("TransformTask Interrupted");
          break;
        }
      }

    }, "TransformTask");
    Thread l = new Thread(() -> {
      while (true) {
        try {
          EventData data = eventService.loadEvent().await(key);
          log.info("Load await");
          pool.submit(() -> {
            randomSleep();
            log.info("Load signal");
            eventService.loadEvent().signal(data);
          });
        } catch (InterruptedException ex) {
          log.info("LoadTask Interrupted");
          break;
        }
      }
    }, "LoadTask");

    s.start();
    e.start();
    t.start();
    l.start();

    latch.await();
    s.interrupt();
    e.interrupt();
    t.interrupt();
    l.interrupt();

    pool.shutdown();
  }

  private void randomSleep() {
    try {
      Thread.sleep(1);
    } catch (InterruptedException ignore) {
    }
  }
}
