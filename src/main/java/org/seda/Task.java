package org.seda;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
public abstract class Task extends Thread implements AutoCloseable {

  protected final Logger logger = LoggerFactory.getLogger(getClass());
  protected final EventKey key;
  protected ExecutorService executor;
  protected int coreSize = Runtime.getRuntime().availableProcessors();
  protected int maxSize = coreSize << 1;
  protected int waitingBuffer = 10240;
  protected int keepaliveTime = 60 * 1000;

  protected volatile boolean running;

  public Task(EventKey key) {
    this.key = key;
    setName(getClass().getSimpleName());
    this.executor = new ThreadPoolExecutor(coreSize, maxSize, keepaliveTime, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<>(10240), new ThreadFactory() {
      private final AtomicInteger index = new AtomicInteger(1);

      @Override
      public Thread newThread(Runnable r) {
        String name = getName() + "-Executor-" + index.getAndIncrement();
        Thread thread = new Thread(r, name);
        thread.setDaemon(false);
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
      }
    }, new ThreadPoolExecutor.CallerRunsPolicy());
  }

  @Override
  public void close() {
    this.running = false;
    this.executor.shutdown();
  }

  protected void doSomething() {
    int times = (int) (Math.random() * 10 + 10);
    String oldName = Thread.currentThread().getName();
    Thread.currentThread().setName(getClass().getSimpleName().substring("StandardTask".length()));
    try {
      Thread.sleep(times);
      logger.info("execute task cost {}ms", times);
    } catch (InterruptedException ignore) {
    } finally {
      Thread.currentThread().setName(oldName);
    }
  }

}
