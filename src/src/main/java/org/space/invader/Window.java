package org.space.invader;
import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {
  public InvaderManager groupInvaders = new InvaderManager();

  public static final int WINDOW_SIZE = 600;
  public static final int WINDOW_HEIGHT = 600;
  public static final int WINDOW_MARGIN = 50;

  public Window(){
    super();
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
//    this.groupInvaders.drawInvader(g2);

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
