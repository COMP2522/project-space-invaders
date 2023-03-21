package org.space.invader;
import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {

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








  }










}
