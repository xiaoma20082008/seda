package org.seda.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.seda.EventData;
import org.seda.EventKey;
import org.seda.SetlPipelineBase;

@Slf4j
public class DisruptorSetlPipeline extends SetlPipelineBase {

  private Disruptor<EventData> disruptor;
  private int idx;

  public DisruptorSetlPipeline(EventKey key) {
    super(key);
  }

  @Override
  public void start() {
    EventFactory<EventData> factory = new EventFactory<EventData>() {
      @Override
      public EventData newInstance() {
        log.debug("select");
        EventData eventData = new EventData();
        eventData.setKey(key);
        eventData.setId(0);
        return eventData;
      }
    };
    // 1. select stage 单线程
    // 2. extract stage 多线程
    // 3. transform stage 多线程
    // 4. load stage 单线程
    WorkHandler<EventData>[] extractPool = new WorkHandler[16];
    for (int i = 0; i < 16; i++) {
      extractPool[i] = new WorkHandler<EventData>() {
        @Override
        public void onEvent(EventData event) throws Exception {
          log.debug("extract");
        }
      };
    }
    WorkHandler<EventData>[] transformPool = new WorkHandler[16];
    for (int i = 0; i < 16; i++) {
      transformPool[i] = new WorkHandler<EventData>() {
        @Override
        public void onEvent(EventData event) throws Exception {
          log.debug("transform");
        }
      };
    }

    this.disruptor = new Disruptor<>(factory, 1024, new ThreadFactory() {

      @Override
      public Thread newThread(Runnable r) {
        return null;
      }
    }, ProducerType.SINGLE, new BlockingWaitStrategy());
    this.disruptor.handleEventsWithWorkerPool(extractPool)
        .thenHandleEventsWithWorkerPool(transformPool)
        .then(new EventHandler<EventData>() {
          @Override
          public void onEvent(EventData event, long sequence, boolean endOfBatch) throws Exception {
            log.debug("load");
          }
        });
    this.disruptor.start();

    while (true) {
      RingBuffer<EventData> ringBuffer = this.disruptor.getRingBuffer();
      long seq = ringBuffer.next();
      try {
        EventData eventData = ringBuffer.get(seq);
        eventData.setId(idx++);
      } finally {
        ringBuffer.publish(seq);
      }
    }
  }

  @Override
  public void close() {
    this.disruptor.shutdown();
  }

}
