package org.space.invader;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.space.invader.Constant.LIMIT_LEFT_PLAYER;
import static org.space.invader.Constant.LIMIT_RIGHT_VAISSEAU;
import static org.space.invader.Player.player;

public class Keyboard implements KeyListener {

//  private Window window;
//  Window window = new Window();

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode()== KeyEvent.VK_RIGHT && Window.player.xPos<=LIMIT_RIGHT_VAISSEAU){
      Window.player.xPos+=5;
      System.out.println(Window.player.getxPos());
      Window.window.repaint();
    } else if(e.getKeyCode() == KeyEvent.VK_LEFT && Window.player.xPos>=LIMIT_LEFT_PLAYER){
      Window.player.xPos-=5;
      System.out.println(Window.player.getxPos());
      Window.window.repaint();
    }
  }
  @Override
  public void keyTyped(KeyEvent e) {
    Window.player.setDx(0);

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
