package org.space.invader;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
//  Window window = new Window();

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode()== KeyEvent.VK_RIGHT){
      System.out.println("Right arrow pressed");
      Window.player.setxPos(Window.player.getxPos()+Constant.DX_PLAYER);
    }
  }
  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
