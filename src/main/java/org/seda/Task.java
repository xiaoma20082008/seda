package org.seda;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Setter;

@Setter
public abstract class Task extends Thread implements AutoCloseable {

  protected final EventKey key;
  protected final ThreadPoolExecutor executor;
  protected volatile boolean started;

  protected int coreSize = 64;
  protected int maxSize = 128;
  protected int waitingBuffer = 10240;
  protected int keepaliveTime = 60 * 1000;

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
  public final void run() {
    while (started) {
      try {
        doCall();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void close() {
    this.started = true;
  }

  @Override
  public final synchronized void start() {
    this.started = true;
    super.start();
  }

  protected abstract void doCall() throws InterruptedException;

  protected void randomSleep() {
    int times = (int) (Math.random() * 1000 + 1000);
    try {
      Thread.sleep(times);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
