package org.seda.standard;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.seda.EventData;
import org.seda.EventKey;
import org.seda.Message;

public class StandardTaskSelect extends StandardTask {

  private final AtomicInteger selectCount = new AtomicInteger();
  private final AtomicInteger idGenerator = new AtomicInteger();

  public StandardTaskSelect(EventKey key) {
    super(key);
  }

  @Override
  public void run() {
    running = true;

    while (running) {
      try {
        Message message = pollMsgFromSwh(); // poll message from somewhere
        if (message.getData() == null) {
          continue;
        }
        EventData data = eventService.selectEvent().await(key);
        selectCount.incrementAndGet();
        executor.submit(() -> {
          doSomething(data);
          eventService.selectEvent().signal(data);
        });
      } catch (InterruptedException ex) {
        logger.info("SelectTask Interrupted");
        break;
      }
    }

  }

  public int getCount() {
    return selectCount.get();
  }

  public int getIndex() {
    return idGenerator.get();
  }

  public Message pollMsgFromSwh() throws InterruptedException {
    int times = (int) (Math.random() * 5 + 10);
    Thread.sleep(times);
    Message message = new Message();
    message.setId(idGenerator.incrementAndGet());
    if (times % 5 == 0) {
      message.setData(null);
    } else {
      message.setData(UUID.randomUUID().toString());
    }
    return message;
  }
}
