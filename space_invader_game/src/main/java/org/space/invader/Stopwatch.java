package org.space.invader;

public class Stopwatch implements Runnable {
  /**** VARIABLES ****/

  private final int PAUSE = 5; // time between each loop iteration: 5 ms
  public static int count = 0;

  Window window;

  /**** METHODS ****/
  @Override
  public void run() {
    while (window.game == true) {
      count++;
      Window.window.repaint(); // Call the PaintComponent method of the window object
      try {
        Thread.sleep(PAUSE);
      } // pause for some time (5 ms)
      catch (InterruptedException e) {
      }
    }
  }
}
