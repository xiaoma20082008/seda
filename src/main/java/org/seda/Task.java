package org.seda;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Setter;

@Setter
public abstract class Task extends Thread implements AutoCloseable {

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
        new LinkedBlockingQueue<>(), new ThreadFactory() {
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

  protected void randomSleep() {
    int times = (int) (Math.random() * 10 + 10);
    try {
      Thread.sleep(times);
    } catch (InterruptedException ignore) {
    }
  }

}
