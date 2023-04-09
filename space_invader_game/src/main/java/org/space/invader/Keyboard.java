package org.space.invader;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Keyboard class that implements KeyListener interface to handle user keyboard input.
 */
public class Keyboard implements KeyListener {
  private final Window window;

  public Keyboard(Window window) {
    this.window = window;
  }

  /**
   * Overrides the keyPressed method from KeyListener interface to handle user input.
   *
   * @param e The KeyEvent object representing the user input.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_P) {
      window.togglePause();
      return;
    }
    if (!window.isPaused && window.player != null && window.player.isAlive()) {
      handlePlayerKeys(keyCode);
      handleMissileKey(keyCode);
    }
  }

  /**
   * Handles the player's movement keys.
   *
   * @param keyCode as integer
   */
  private void handlePlayerKeys(int keyCode) {
    if (keyCode == KeyEvent.VK_RIGHT) {
      window.player.setXspeed(Player.DX_PLAYER);
    } else if (keyCode == KeyEvent.VK_LEFT) {
      window.player.setXspeed(-Player.DX_PLAYER);
    }
  }

  /**
   * Handles the player's missile key.
   *
   * @param keyCode as integer
   */
  private void handleMissileKey(int keyCode) {
    if (keyCode == KeyEvent.VK_SPACE && !Window.missilePlayer.isPlayerShoot()) {
      try {
        Audio audio = Audio.getInstance();
        audio.playMissilePlayer();
      } catch (Exception a) {
        a.printStackTrace();
      }
      Window.missilePlayer.setyPos(Player.Y_POS_PLAYER - MissilePlayer.HEIGHT_MISSILE_PLAYER);
      Window.missilePlayer.setxPos(window.player.getxPos() + Player.PLAYER_SIZE / 2 - 1);
      Window.missilePlayer.setPlayerShoot(true);
    }
  }



  /**
   * Overrides the keyReleased method from KeyListener interface to set player's horizontal
   * speed to 0 when the key is released.
   *
   * @param e The KeyEvent object representing the user input.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (window.player != null && window.player.isAlive()) {
      if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
        window.player.setXspeed(0);
      }
    }
  }

  /**
   * Overrides the keyTyped method from KeyListener interface, but does nothing.
   *
   * @param arg0 The KeyEvent object representing the user input.
   */
  @Override
  public void keyTyped(KeyEvent arg0) {
  }
}
