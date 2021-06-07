package org.seda.standard;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import org.seda.EventData;
import org.seda.EventKey;
import org.seda.Stage;

public class MemoryStageController implements StageController {

  private final LoadingCache<Stage, BlockingQueue<EventData>> cache = CacheBuilder.newBuilder()
      .build(new CacheLoader<>() {
        @Override
        public BlockingQueue<EventData> load(Stage stage) {
          return new LinkedBlockingQueue<>();
        }
      });

  @Override
  public EventData await(Stage stage, EventKey key) throws InterruptedException {
    try {
      return cache.get(stage).take();
    } catch (ExecutionException e) {
      throw new InterruptedException(e.getMessage());
    }
  }

  @Override
  public boolean signal(Stage stage, EventData data) {
    try {
      boolean r = false;
      switch (stage) {
        case SELECT: {
          cache.get(Stage.EXTRACT).offer(data);
          r = true;
          break;
        }
        case EXTRACT: {
          cache.get(Stage.TRANSFORM).offer(data);
          r = true;
          break;
        }
        case TRANSFORM: {
          cache.get(Stage.LOAD).offer(data);
          r = true;
          break;
        }
        case LOAD: {
          cache.get(Stage.SELECT).offer(data);
          r = true;
          break;
        }
      }
      return r;
    } catch (ExecutionException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
