package org.space.invader;

import java.awt.Graphics;

import javax.swing.ImageIcon;
//
////import jeu.Main;
////import ressources.Audio;
////import ressources.Constantes;
//

// TirVaisseau = MissilePlayer
public class MissilePlayer extends Sprite {
  /**** VARIABLES ****/

  // vaisseauTire = playerShoot
  private boolean playerShoot = false;
  Window window;


  /**** CONSTRUCTOR ****/

  public MissilePlayer() {

    // Initialisation des variables de la super classe
    super.xPos = 0;
    super.yPos = Constant.Y_POS_PLAYER - Constant.HEIGHT_MISSILE_PLAYER;
    super.size = Constant.SIZE_MISSILE_PLAYER;
    super.height = Constant.HEIGHT_MISSILE_PLAYER;
    super.dx = 0;
    super.dy = Constant.DY_MISSILE_PLAYER;
    // Adresse des images du vaisseau
    super.strImg1 = "/tirVaisseau.png";  // I'll change it later
    super.strImg2 = "";
    super.strImg3 = "";
    // Chargement de l'image du vaisseau
    super.ico = new ImageIcon(getClass().getResource(super.strImg1));
    super.img = this.ico.getImage();
  }

  /**** METHODES ****/
  // isVaisseauTire = isPlayerShoot
  public boolean isPlayerShoot() {
    return playerShoot;
  }

  // setVaisseauTire = setPlayerShoot
  public void setPlayerShoot(boolean playerShoot) {
    this.playerShoot = playerShoot;
  }

  // deplacementTirVaisseau = movement of the spaceship's shot
  // deplacementTirVaisseau = MovMissilePlayer
  public int movMissilePlayer() {
    if (this.playerShoot == true) {
      if (this.yPos > 0) {
        this.yPos = this.yPos - Constant.DY_MISSILE_PLAYER;
      } else {
        this.playerShoot = false;
      }
    }
    return yPos;
  }

  // dessinTirVaisseau = drawing of the spaceship firing
  // dessinTirVaisseau = drawInvaderMissile
  public void drawPlayerMissile(Graphics g) {
    if (this.playerShoot == true) {
      g.drawImage(this.img, this.xPos, this.movMissilePlayer(), null);
    }
  }

  // tueAlien = killAlien
  // tueAlien = killInvader
  public boolean killInvader(Invader invader) {
    // le tir du vaisseau d truit un alien
    // the spaceship's shot destroys an alien
    if (this.yPos < invader.getyPos() + invader.getHeight()
        && this.yPos + this.height > invader.getyPos()
        && this.xPos + this.size > invader.getxPos()
        && this.xPos < invader.getxPos() + invader.getSize()) {
      Audio.playSound("/sons/sonAlienMeurt.wav");
      return true;
    } else {
      return false;
    }
  }

  // tirVaisseauAHauteurDeChateau = shipFiresAtCastleHeight ??
//  private boolean tirVaisseauAHauteurDeChateau() {
  private boolean missilePlayerFireAtBarrier() {
    // Renvoie vrai si le tir du vaisseau est   hauteur des ch teaux
    // Returns true if the ship's shot is at the height of the barriers
    if (this.yPos < Constant.Y_POS_BARRIER + Constant.HEIGHT_BARRIER && this.yPos + this.height > Constant.Y_POS_BARRIER) {
      return true;
    } else {
      return false;
    }
  }

  // chateauProche = near castle
  private int numberBarrier() {
    // Renvoie le num ro du ch teau (0,1,2 ou 3) dans la zone de tir du vaisseau
    // Returns the number of the barrier (0,1,2, or 3) in the firing zone of the player
    int numBarrier = -1;
    int column = -1;
    while (numBarrier == -1 && column < 4) {
      column++;
      if (this.xPos + this.size > Constant.WINDOW_MARGIN + Constant.X_POS_INIT_BARRIER + column *
          (Constant.SIZE_BARRIER + Constant.GAP_BARRIER)
          && this.xPos < Constant.WINDOW_MARGIN + Constant.X_POS_INIT_BARRIER + Constant.SIZE_BARRIER + column *
          (Constant.SIZE_BARRIER + Constant.GAP_BARRIER)) {
        numBarrier = column;
      }
    }
    return numBarrier;
  }

  // abscisseContactTirChateau = abscissa of contact with castle sho
  // Chateau = "castle" or "mansion"
//  private int abscisseContactTirChateau(Barrier barrier) {
  private int xContactMisBarrier(Barrier barrier) {
    // Renvoie l'abscisse du tir du vaisseau lors du contact avec un ch teau
    // Returns the player's shot upon contact with a barrier.
//    int xPosTirVaisseau = -1;
    int xPosMisPlayer = -1;
    if (this.xPos + this.size > barrier.getxPos() && this.xPos < barrier.getxPos() + Constant.SIZE_BARRIER) {
      xPosMisPlayer = this.xPos;
    }
    return xPosMisPlayer;
  }

  //  public int[] tirVaisseauToucheChateau() {
  public int[] misPlayerTouchBarrier() {
    // Renvoie num ro ch teau touch  et abscisse du tir
    int[] arrayRep = {-1, -1};
    if (this.missilePlayerFireAtBarrier() == true) { // Le tir du vaisseau est   hauteur du ch teau
      arrayRep[0] = this.numberBarrier(); // enregistre le num ro du ch teau touch  dans tabRep[0]
      if (arrayRep[0] != -1) {
        //enregistre l'abscisse du tir du vaisseau lors du contact avec le ch teau dans tabRep[1]
        arrayRep[1] = this.xContactMisBarrier(window.BarrierArray[arrayRep[0]]);
      }
    }
    return arrayRep;
  }

  //  public void tirVaisseauDetruitChateau(Chateau tabChateaux[]) {
  // Ship Shot Destroys barrier.
  public void misPlayerDestroyBarrier(Barrier BarrierArray[]) {
//    int[] array = this.misPlayerTouchBarrier(); // original
    int[] array = this.misPlayerTouchBarrier(); // Contient (-1,-1) ou le num ro du ch teau touch  et l'abscisse du contact tirVaisseau et ch teau
    if (array[0] != -1) { // Un ch teau est touch
      if (BarrierArray[array[0]].findBrick(BarrierArray[array[0]].findBarrierColumn(array[1])) != -1) {
        BarrierArray[array[0]].breakBricks(array[1]); // D truit les briques du ch teau touch
        this.yPos = -1; // On tue le tir et on r active le canon du vaisseau
      }
    }
  }
}