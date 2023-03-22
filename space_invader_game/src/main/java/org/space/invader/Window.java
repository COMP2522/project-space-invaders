package org.space.invader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

import org.space.invader.*;
public class Window extends JPanel {

  static Window window;

  static Player player = new Player();
  public InvaderManager groupInvaders = new InvaderManager();
//  public TirVaisseau tirVaisseau = new TirVaisseau();
  public MissilePlayer missilePlayer = new MissilePlayer();

  public MissileInvader missileInvader1, missileInvader2, missileInvader3;

  public Barrier BarrierArray[] = new Barrier[4];
  public static final int WINDOW_SIZE = 600;
  public static final int WINDOW_HEIGHT = 600;
  public static final int WINDOW_MARGIN = 50;
  public static boolean check = true;

  private Font DisplayScore = new Font("Arial", Font.PLAIN, 20);
  private Font Displaytext = new Font("Arial", Font.PLAIN, 80);

  public int score = 0;

  public Window(){
    super();

    this.setFocusable(true);
    this.requestFocusInWindow();
    this.addKeyListener(new Keyboard());

    Thread thread = new Thread(new Chrono());
    thread.start();


    // Instantiation of Barrier Array
    for (int column = 0; column < 4; column++) {
      this.BarrierArray[column] = new Barrier(Constant.WINDOW_MARGIN +
          Constant.X_POS_INIT_BARRIER + column * (Constant.SIZE_BARRIER + Constant.GAP_BARRIER));
    }

    // Instantiation of the stopwatch (at the end of the constructor)
    Thread stopwatch = new Thread(new Stopwatch());
    stopwatch.start();
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

    // Display the screen
    g.setFont(DisplayScore);
    g.drawString("SCORE : " + score, 400, 25);

    //Draw the invaders
    this.groupInvaders.drawInvader(g2);

    // Dessin du tir vaisseau
    // Drawing of the spaceship shot
    // this.tirVaisseau.dessinTirVaisseau(g2);
    this.missilePlayer.drawPlayerMissile(g2);

    // draw player
    g2.drawImage(this.player.getImg(),this.player.getxPos(),this.player.getyPos(),null);

    // Draw the barriers
    for (int column = 0; column < 4; column++) {
      this.BarrierArray[column].drawBarrier(g2);
    }

    // Start message
    if(Stopwatch.count < 500) {
      g.setFont(Displaytext);
      g.drawString("Good luck!", 95, 100);
    }

    // Game over message
    if(this.player.isAlive() == false) {
      g.setFont(Displaytext);
      g.drawString("GAME OVER", 50, 100);
    }

  }
  public static void main(String[] args) {
    JFrame frame = new JFrame("Space Invaders");
    frame.setSize(WINDOW_SIZE, WINDOW_HEIGHT);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window = new Window();
    window.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_HEIGHT));
    frame.add(window);
    frame.pack();
    frame.setVisible(true);
  }
}
