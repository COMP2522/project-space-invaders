package org.space.invader;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *  Keyboard class that implements KeyListener interface to handle user keyboard input.
 */
public class Keyboard implements KeyListener {

//  public Keyboard(Window window) {
//    this.window = window;
//  }

  /**
   * Overrides the keyPressed method from KeyListener interface to handle user input.
   *
   * @param e The KeyEvent object representing the user input.
   */
  @Override
  public void keyPressed(KeyEvent e) {
//    if(e.getKeyCode()== KeyEvent.VK_RIGHT && Window.player.xPos<=LIMIT_RIGHT_PLAYER){
//      Window.player.xPos+=5;
//      System.out.println(Window.player.getxPos());
//      Window.window.repaint();
//    } else if(e.getKeyCode() == KeyEvent.VK_LEFT && Window.player.xPos>=LIMIT_LEFT_PLAYER){
//      Window.player.xPos-=5;
//      System.out.println(Window.player.getxPos());
//      Window.window.repaint();
//    }
    if (Window.player.isAlive() == true) {
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        Window.player.setDx(Constant.DX_PLAYER);
      } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        Window.player.setDx(-Constant.DX_PLAYER);
      } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        if (Window.missilePlayer.isPlayerShoot() == false) {
//          Audio.playSound("/sons/sonTirVaisseau.wav");
          Window.missilePlayer.setyPos(Constant.Y_POS_PLAYER - Constant.HEIGHT_MISSILE_PLAYER);
          Window.missilePlayer.setxPos(Window.player.getxPos() + Constant.PLAYER_SIZE / 2 - 1);
          Window.missilePlayer.setPlayerShoot(true);
        }
      }
    }
  }

  /**
   *  Overrides the keyReleased method from KeyListener interface to set player's horizontal speed to 0 when the key is released.
   *
   *  @param e The KeyEvent object representing the user input.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    Window.player.setDx(0);
  }

  /**
   *  Overrides the keyTyped method from KeyListener interface, but does nothing.
   *
   *  @param arg0 The KeyEvent object representing the user input.
   */
  @Override
  public void keyTyped(KeyEvent arg0) {

  }
}
