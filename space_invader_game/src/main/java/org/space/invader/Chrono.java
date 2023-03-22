package org.space.invader;


public class Chrono implements Runnable {
  private static final int PAUSE = 5;
  private boolean needsRepaint = false;

  @Override
  public void run() {
    while (true) {
      if (needsRepaint) {
        Window.window.repaint();
        needsRepaint = false;
      }
      try {
        Thread.sleep(PAUSE);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void setNeedsRepaint(boolean value) {
    this.needsRepaint = value;
  }
}

