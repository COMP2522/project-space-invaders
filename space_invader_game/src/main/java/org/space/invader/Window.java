package org.space.invader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

import org.space.invader.*;
public class Window extends JPanel {

  static Window window;
  public static boolean game = true;


  static Player player = new Player();
  public InvaderManager groupInvaders = new InvaderManager();
//  public TirVaisseau tirVaisseau = new TirVaisseau();
  public static MissilePlayer missilePlayer = new MissilePlayer();

  public MissileInvader missileInvader1, missileInvader2, missileInvader3;

  public Barrier BarrierArray[] = new Barrier[4];
//  public static final int WINDOW_SIZE = 600;
//  public static final int WINDOW_HEIGHT = 600;
//  public static final int WINDOW_MARGIN = 50;
//  public static boolean check = true;

  private Font DisplayScore = new Font("Arial", Font.PLAIN, 20);
  private Font Displaytext = new Font("Arial", Font.PLAIN, 80);

  // original: public int score = 0;
  public static int score = 0;

  public Window(){
    super();

    this.setFocusable(true);
    this.requestFocusInWindow();
    this.addKeyListener(new Keyboard());

//    Thread thread = new Thread(new Stopwatch());
//    thread.start();


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
    g2.fillRect(0,0, Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT);

    //Draw the green line on the bottom of the window
    g2.setColor(Color.GREEN);
    g2.fillRect(30, 530, 535, 5);

    // Display the screen
    g.setFont(DisplayScore);
    g.drawString("SCORE : " + score, 400, 25);

    // Draw the player
    this.player.drawPlayer(g2);

    //Draw the invaders
    this.groupInvaders.drawInvader(g2);

    // Dessin du tir vaisseau
    // Drawing of the spaceship shot
    // this.tirVaisseau.dessinTirVaisseau(g2);
    this.missilePlayer.drawPlayerMissile(g2);

//    // draw player
//    g2.drawImage(this.player.getImg(),this.player.getxPos(),this.player.getyPos(),null);

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

    // D?ection contact tirVaisseau avec ch?eau
    // Direction of spaceship's contact with the castle
//    this.tirVaisseau.tirVaisseauDetruitChateau(tabChateaux);
    this.missilePlayer.misPlayerDestroyBarrier(BarrierArray);

    // Dessin des tirs des aliens
    // Drawing of the aliens' Missile`
    if(Stopwatch.count % 500 == 0) {
      missileInvader1 = new MissileInvader(this.groupInvaders.chooseInvaderToDraw());}
    if(this.missileInvader1 != null) {
      this.missileInvader1.drawInvaderMissile(g2);
      this.missileInvader1.misInvaderDestroyBarrier(BarrierArray); // D?ection contact tirAlien1 avec ch?eau
      if(this.missileInvader1.touchPlayer(player) == true) {this.player.setAlive(false);}
    }
    if(Stopwatch.count % 750 == 0) {
      missileInvader2 = new MissileInvader(this.groupInvaders.chooseInvaderToDraw());}
    if(this.missileInvader2 != null) {
      this.missileInvader2.drawInvaderMissile(g2);
      this.missileInvader2.misInvaderDestroyBarrier(BarrierArray); // D?ection contact tirAlien2 avec ch?eau
      if(this.missileInvader2.touchPlayer(player) == true) {this.player.setAlive(false);}
    }
    if(Stopwatch.count % 900 == 0) {
      missileInvader3 = new MissileInvader(this.groupInvaders.chooseInvaderToDraw());}
    if(this.missileInvader3 != null) {
      this.missileInvader3.drawInvaderMissile(g2);
      this.missileInvader3.misInvaderDestroyBarrier(BarrierArray); // D?ection contact tirAlien3 avec ch?eau
      if(this.missileInvader3.touchPlayer(player) == true) {this.player.setAlive(false);}
    }

    // we don't have to write the below method
//    // Dessin de la soucoupe
//    if(Chrono.compteTours % 2500 == 0) {soucoupe = new Soucoupe();}
//    if(this.soucoupe != null) {
//      if(this.soucoupe.getxPos()>0) {
//        // D?ection contact tir vaisseau avec soucoupe
//        if(this.tirVaisseau.detruitSoucoupe(this.soucoupe) == true) {
//          if(this.soucoupe.getDx() != 0) {this.score = this.score + Constantes.VALEUR_SOUCOUPE;}
//          this.soucoupe.setDx(0);
//          this.soucoupe.setVivant(false);
//          this.soucoupe.musiqueSoucoupe.stop();
//          this.soucoupe.musiqueDestructionSoucoupe.play();
//        }
//        this.soucoupe.dessinSoucoupe(g2);
//      }else {this.soucoupe = null;}
//    }

    if(this.groupInvaders.getInvaderNum() == 0) {groupInvaders = new InvaderManager();}

    if(this.groupInvaders.positionInvaderLowest() > Constant.Y_POS_PLAYER) {this.player.destructionPlayer();}
  }

//}
  public static void main(String[] args) {
    JFrame frame = new JFrame("Space Invaders");
    frame.setSize(Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window = new Window();
    window.setPreferredSize(new Dimension(Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT));
    frame.add(window);
    frame.pack();
    frame.setVisible(true);
  }
}
