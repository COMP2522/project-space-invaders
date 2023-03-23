package org.space.invader;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

//import jeu.Main;
//import ressources.Audio;
//import ressources.Chrono;
//import ressources.Constantes;

public class MissileInvader extends Sprite {
  /**** VARIABLES ****/

//  Random hasard = new Random();
  Random rand = new Random();


  /**** Constructor ****/

//  public MissileInvader(int[] tabPositionAlien) {
  public MissileInvader(int[] arrayPositionInvader) {

    // Initialisation des variables de la super classe
    // Initialization of variables in the superclass
    super.xPos = arrayPositionInvader[0] + Constant.INVADER_SIZE / 2 - 1;
    super.yPos = arrayPositionInvader[1] + Constant.INVADER_HEIGHT;
    super.size = Constant.SIZE_MISSILE_INVADER;
    super.height = Constant.HEIGHT_MISSILE_INVADER;
    super.dx = 0;
    super.dy = Constant.DY_MISSILE_INVADER;
    // Adresse des images du vaisseau
    super.strImg1 = "/tirAlien1.png";  // I'll change it later
    super.strImg2 = "/tirAlien2.png";  // I'll change it later
    super.strImg3 = "";
    // Chargement de l'image du tir de l'alien
    if (rand.nextInt(2) == 0) {
      super.ico = new ImageIcon(getClass().getResource(super.strImg1));
    } else {
      super.ico = new ImageIcon(getClass().getResource(super.strImg2));
    }
    super.img = this.ico.getImage();
  }


  /**** METHODES ****/

//  public int deplacementTirAlien() {
  public int movMissileInvader() {
    if (Stopwatch.count % 4 == 0) {
      if (this.yPos < 600) {
        this.yPos = this.yPos + Constant.DY_MISSILE_INVADER;
      }
    }
    return yPos;
  }

  //  public void dessinTirAlien(Graphics g) {
  public void drawInvaderMissile(Graphics g) {
    g.drawImage(this.img, this.xPos, this.movMissileInvader(), null);
  }

  //  private boolean tirAlienAHauteurDeChateau() {
  private boolean missileInvaderFireAtBarrier() {
    // Renvoie vrai si le tir du vaisseau est   hauteur des ch teaux
    if (this.yPos < Constant.Y_POS_BARRIER + Constant.HEIGHT_BARRIER && this.yPos + this.height > Constant.Y_POS_BARRIER) {
      return true;
    } else {
      return false;
    }
  }

  //  private int chateauProche() {
  private int numberBarrier() {
    // Renvoie le num ro du ch teau (0,1,2 ou 3) dans la zone de tir du vaisseau
    int numBarrier = -1;
    int column = -1;
    while (numBarrier == -1 && column < 4) {
      column++;
      if (this.xPos + this.size - 1 > Constant.WINDOW_MARGIN + Constant.X_POS_INIT_BARRIER + column * (Constant.SIZE_BARRIER +
          Constant.GAP_BARRIER)
          && this.xPos + 1 < Constant.WINDOW_MARGIN + Constant.X_POS_INIT_BARRIER + Constant.SIZE_BARRIER +
          column * (Constant.SIZE_BARRIER + Constant.GAP_BARRIER)) {
        numBarrier = column;
      }
    }
    return numBarrier;
  }

  //  private int abscisseContactTirAlienChateau(Chateau chateau) {
  private int xContactMisInvaderBarrier(Barrier barrier) {
    int xPosTirAlien = -1;
    if (this.xPos + this.size > barrier.getxPos() && this.xPos < barrier.getxPos() + Constant.SIZE_BARRIER) {
      xPosTirAlien = this.xPos;
    }
    return xPosTirAlien;
  }

  //  public int[] tirAlienToucheChateau() { // Renvoie num ro ch teau touch  et abscisse du tir
  public int[] missileInvaderTouchBarrier() { // Renvoie num ro ch teau touch  et abscisse du tir
    int[] tabRep = {-1, -1};
    if (this.missileInvaderFireAtBarrier() == true) { // Le tir alien est   hauteur du ch teau
      tabRep[0] = this.numberBarrier(); // enregistre le num ro du ch teau touch  dans tabRep[0]
      if (tabRep[0] != -1) {
        tabRep[1] = this.xContactMisInvaderBarrier(
            Window.window.BarrierArray[tabRep[0]]);
      }
    }
    return tabRep;
  }

  //  public void TirAlienDetruitChateau(Chateau tabChateaux[]) {
  public void misInvaderDestroyBarrier(Barrier arrayBarriers[]) {
    int[] array = this.missileInvaderTouchBarrier(); // Contient (-1,-1) ou le num ro du ch teau touch  et l'abscisse du tir
    if (array[0] != -1) { // Un ch teau est touch
      if (arrayBarriers[array[0]].findTopBrick(arrayBarriers[array[0]].findBarrierColumn(array[1])) != -1
          && arrayBarriers[array[0]].findTopBrick(arrayBarriers[array[0]].findBarrierColumn(array[1])) != 27) {
        arrayBarriers[array[0]].breakTopBricks(array[1]); // D truit les briques du ch teau touch
        this.yPos = 700; // On tue le tir de l'alien
      }
    }
  }

  //  public boolean toucheVaisseau(Vaisseau vaisseau) {
  public boolean touchPlayer(Player player) {
    // Renvoie vrai si un tirAlien touche le vaisseau
    if (this.yPos < player.getyPos() + player.getHeight() && this.yPos + this.height > player.getyPos()
        && this.xPos + this.size > player.getxPos() && this.xPos < player.getxPos() + player.getSize()) {
      this.yPos = 700;
//      Audio.playSound("/sons/sonDestructionVaisseau.wav");
      return true;
    } else {
      return false;
    }
  }
}

