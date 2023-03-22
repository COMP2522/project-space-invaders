package org.space.invader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

import org.space.invader.*;
public class Window extends JPanel {

  static Player player = new Player();
  public InvaderManager groupInvaders = new InvaderManager();

  public Barrier BarrierArray[] = new Barrier[4];
  public static final int WINDOW_SIZE = 600;
  public static final int WINDOW_HEIGHT = 600;
  public static final int WINDOW_MARGIN = 50;

  public static boolean check = true;
  public Window(){
    super();

    this.setFocusable(true);
    this.requestFocusInWindow();
    this.addKeyListener(new Keyboard());

    // Instantiation of Barrier Array
    for (int column = 0; column < 4; column++) {
      this.BarrierArray[column] = new Barrier(Constant.WINDOW_MARGIN +
          Constant.X_POS_INIT_BARRIER + column * (Constant.SIZE_BARRIER + Constant.GAP_BARRIER));
    }

  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics g2 = (Graphics2D)g;

    //Draw the window frame
    g2.setColor(Color.BLACK);
    g2.fillRect(0,0, WINDOW_SIZE, WINDOW_HEIGHT);

    //Draw the green line on the bottom of the window
    g2.setColor(Color.GREEN);
    g2.fillRect(30, 530, 535, 5);

    //Draw the invaders
    this.groupInvaders.drawInvader(g2);

    // draw player
    g2.drawImage(this.player.getImg(),this.player.getxPos(),this.player.getyPos(),null);

    // Draw the barriers
    for (int column = 0; column < 4; column++) {
      this.BarrierArray[column].drawBarrier(g2);
    }


  }
  public static void main(String[] args) {
    JFrame frame = new JFrame("Space Invaders");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Window window = new Window();
    window.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_HEIGHT));
    frame.add(window);
    frame.pack();
    frame.setVisible(true);
  }
}
