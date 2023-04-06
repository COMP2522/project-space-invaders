package org.space.invader;

import java.awt.*;
import javax.swing.*;

/**
 * The class of Game Restart screen.
 */
public class GameRestart extends JPanel {

  /** The font size of Restart button. */
  final int buttonTextSize = 30;

  /** The format of the text for restart button. */
  public Font buttonText = new Font("Arial", Font.PLAIN, buttonTextSize);

  /**
   * Draws the 'Play Again' button.
   *
   * @param g Graphics
   */
  public void drawRestart(Graphics g) {
    int ypos = 300;
    // Add play again button
    g.setFont(buttonText);
    g.setColor(Color.BLUE);
    g.fillRect(300, ypos, 160, 60); // create button box
    g.setColor(Color.WHITE);
    g.drawString("RESTART", 310, ypos + 40); // add text to button
  }

  /**
   * Restarts the game.
   *
   * @param g Graphics
   */
  public void restartGame(Graphics g, Window w) {
    // Stop the game loop
    w.gameLoop.stop();
    w.gameLoop = null;
    w.game = false;
    Stopwatch.count = 0;

    // Reset the score to 0
    w.score = 0;

    // Reset the player and invader objects
    w.player = new Player(Window.window.playerName);
    w.groupInvaders = new InvaderManager();

    // Reset the player and invader missile objects
    w.missilePlayer = new MissilePlayer();
    w.missileInvader1 = null;
    w.missileInvader2 = null;
    w.missileInvader3 = null;

    // Reset the barrier objects
    for (int column = 0; column < w.numberBarrier; column++) {
      w.barrierArray[column] = new Barrier(Window.WINDOW_MARGIN
          + Barrier.X_POS_INIT_BARRIER + column * (Barrier.SIZE_BARRIER + Barrier.GAP_BARRIER));
    }

    // Reset the game over flag and handle flag
    Window.game = true;
    w.isGameOverHandled = false;

    // Start the game loop
    w.gameLoop = new Timer(1000 / 60, e -> repaint());
    w.gameLoop.start();
    Thread stopwatch = new Thread(new Stopwatch());
    stopwatch.start();

    paintComponent(g);
  }
}
