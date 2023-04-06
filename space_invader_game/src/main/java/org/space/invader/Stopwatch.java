package org.space.invader;

/**
 *  A Stopwatch class that implements the Runnable interface
 *  for timing and updating the game window.
 */
public class Stopwatch implements Runnable {

  /** The pause time between each loop iteration. */
  int pause = 5;

  /** The count of time elapsed. */
  public static int count = 0;

  /**
   *  Runs the Stopwatch in a loop while the game is running.
   *  Increments the count and repaints the window with each iteration.
   */
  @Override
  public void run() {
    while (Window.game) {
      count++;
      Window.window.repaint(); // Call the PaintComponent method of the window object
      try {
        Thread.sleep(pause); // Pause the thread execution for 5ms.
        // It's used to control the frame rate of the game loop.
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
