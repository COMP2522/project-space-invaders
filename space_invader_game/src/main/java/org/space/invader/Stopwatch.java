package org.space.invader;

/**
 *  A Stopwatch class that implements the Runnable interface for timing and updating the game window.
 */
public class Stopwatch implements Runnable {

  /** The pause time between each loop iteration. */
  private final int PAUSE = 5;

  /** The count of time elapsed. */
  public static int count = 0;

  /** The window object that the stopwatch is updating. */
  Window window;

  /**
   *  Runs the Stopwatch in a loop while the game is running. Increments the count and repaints the window
   *  with each iteration.
   */
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
