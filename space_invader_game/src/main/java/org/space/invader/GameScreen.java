//package org.space.invader;
//
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.ArrayList;
//
//import java.util.List;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//
//
//public class GameScreen extends JPanel {
//
//  /**
//   * The manager for the invaders.
//   */
//  public InvaderManager groupInvaders = new InvaderManager();
//  /**
//   * An array of barrier objects.
//   */
//  public static Barrier[] BarrierArray = new Barrier[4];
//
//  public GameScreen() {
//
//  }
//  public void drawMissile1(Window w, Graphics g) {
//    MissileInvader missileInvader1;
//    if (Stopwatch.count % 500 == 0) {
//      missileInvader1 = new MissileInvader(w.groupInvaders.chooseInvaderToDraw());
//    }
//    if (w.missileInvader1 != null) {
//      w.missileInvader1.drawInvaderMissile(g);
//      w.missileInvader1.misInvaderDestroyBarrier(BarrierArray);
//      if (w.missileInvader1.touchPlayer(w.player) == true) {
//        w.player.setAlive(false);
//      }
//    }
//  }
//
//  public void drawMissile2(Window w, Graphics g) {
//    MissileInvader missileInvader2;
//    if (Stopwatch.count % 750 == 0) {
//      missileInvader2 = new MissileInvader(w.groupInvaders.chooseInvaderToDraw());
//    }
//    if (w.missileInvader2 != null) {
//      Graphics g2 = null;
//      w.missileInvader2.drawInvaderMissile(g);
//      w.missileInvader2.misInvaderDestroyBarrier(BarrierArray);
//      if (w.missileInvader2.touchPlayer(w.player) == true) {
//        w.player.setAlive(false);
//      }
//    }
//  }
//
//  public void drawMissile3(Window w, Graphics g) {
//    MissileInvader missileInvader3;
//    if (Stopwatch.count % 750 == 0) {
//      missileInvader3 = new MissileInvader(w.groupInvaders.chooseInvaderToDraw());
//    }
//    if (w.missileInvader3 != null) {
//      Graphics g2 = null;
//      w.missileInvader3.drawInvaderMissile(g);
//      w.missileInvader3.misInvaderDestroyBarrier(BarrierArray);
//      if (w.missileInvader3.touchPlayer(w.player) == true) {
//        w.player.setAlive(false);
//      }
//    }
//  }
//}


//  public void drawMissile1(Graphics g) {
//    if (Stopwatch.count % 500 == 0) {
//      missileInvader1 = new MissileInvader(groupInvaders.chooseInvaderToDraw());
//    }
//    if (missileInvader1 != null) {
//      missileInvader1.drawInvaderMissile(g);
//      missileInvader1.misInvaderDestroyBarrier(BarrierArray);
//      if (missileInvader1.touchPlayer(player) == true) {
//        player.setAlive(false);
//      }
//    }
//  }
//
//  public void drawMissile2(Graphics g) {
//    if (Stopwatch.count % 750 == 0) {
//      missileInvader2 = new MissileInvader(groupInvaders.chooseInvaderToDraw());
//    }
//    if (missileInvader2 != null) {
//      missileInvader2.drawInvaderMissile(g);
//      missileInvader2.misInvaderDestroyBarrier(BarrierArray);
//      if (missileInvader2.touchPlayer(player) == true) {
//        player.setAlive(false);
//      }
//    }
//  }
//
//  public void drawMissile3(Graphics g) {
//    if (Stopwatch.count % 750 == 0) {
//      missileInvader3 = new MissileInvader(groupInvaders.chooseInvaderToDraw());
//    }
//    if (missileInvader3 != null) {
//      missileInvader3.drawInvaderMissile(g);
//      missileInvader3.misInvaderDestroyBarrier(BarrierArray);
//      if (missileInvader3.touchPlayer(player) == true) {
//        player.setAlive(false);
//      }
//    }
//  }
