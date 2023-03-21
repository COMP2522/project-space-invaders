//package org.space.invader;
//
//import java.awt.Graphics;
//
//import javax.swing.ImageIcon;
//
////import jeu.Main;
////import ressources.Audio;
////import ressources.Constantes;
//
//// TirVaisseau = MissilePlayer
//public class MissilePlayer extends Sprite {
//  /**** VARIABLES ****/
//

//  // vaisseauTire = playerShoot
//  private boolean playerShoot = false;
//
//
//  /**** CONSTRUCTEUR ****/
//
//  public MissilePlayer() {
//
//    // Initialisation des variables de la super classe
//    super.xPos = 0;
//    super.yPos = Constant.Y_POS_VAISSEAU - Constant.HAUTEUR_TIR_VAISSEAU;
//    super.size = Constant.LARGEUR_TIR_VAISSEAU;
//    super.height = Constant.HAUTEUR_TIR_VAISSEAU;
//    super.dx = 0;
//    super.dy = Constant.DY_TIR_VAISSEAU;
//    // Adresse des images du vaisseau
//    super.strImg1 = "/images/tirVaisseau.png";
//    super.strImg2 = "";
//    super.strImg3 = "";
//    // Chargement de l'image du vaisseau
//    super.ico = new ImageIcon(getClass().getResource(super.strImg1));
//    super.img = this.ico.getImage();
//  }
//
//  /**** METHODES ****/
//  // isVaisseauTire = isPlayerShoot
//  public boolean isPlayerShoot() {return playerShoot;}
//
//  // setVaisseauTire = setPlayerShoot
//  public void setPlayerShoot(boolean playerShoot) {this.playerShoot = playerShoot;}
//
//  // deplacementTirVaisseau = movement of the spaceship's shot
//  // deplacementTirVaisseau = MovMissilePlayer
//  public int MovMissilePlayer() {
//    if(this.playerShoot == true) {
//      if(this.yPos > 0) {this.yPos = this.yPos - Constant.DY_TIR_VAISSEAU;}
//      else {this.playerShoot = false;}
//    }
//    return yPos;
//  }
//
//  // dessinTirVaisseau = drawing of the spaceship firing
//  // dessinTirVaisseau = drawInvaderMissile
//  public void drawInvaderMissile(Graphics g) {
//    if(this.playerShoot == true) {
//      g.drawImage(this.img, this.xPos, this.MovMissilePlayer(), null);}
//  }
//
//  // tueAlien = killAlien
//  // tueAlien = killInvader
//  public boolean killInvader(Invader invader) {
//    // le tir du vaisseau d truit un alien
//    // the spaceship's shot destroys an alien
//    if(this.yPos < invader.getyPos() + invader.getHauteur()
//        && this.yPos + this.height > invader.getyPos()
//        && this.xPos + this.size > invader.getxPos()
//        && this.xPos < invader.getxPos() + invader.getLargeur()){
//      Audio.playSound("/sons/sonAlienMeurt.wav");
//      return true;
//    }
//    else{return false;}
//  }
//
//  // tirVaisseauAHauteurDeChateau = shipFiresAtCastleHeight ??
//  private boolean tirVaisseauAHauteurDeChateau() {
//    // Renvoie vrai si le tir du vaisseau est   hauteur des ch teaux
//    if(this.yPos < Constant.Y_POS_CHATEAU + Constant.HAUTEUR_CHATEAU && this.yPos + this.height > Constant.Y_POS_CHATEAU) {return true;}
//    else {return false;}
//  }
//
//  // chateauProche = near castle
//  private int chateauProche() {
//    // Renvoie le num ro du ch teau (0,1,2 ou 3) dans la zone de tir du vaisseau
//    int numeroChateau = -1;
//    int colonne = -1;
//    while (numeroChateau == -1 && colonne < 4) {
//      colonne++;
//      if(this.xPos + this.size > Constant.MARGE_FENETRE + Constant.X_POS_INIT_CHATEAU + colonne *
//          (Constant.LARGEUR_CHATEAU + Constant.ECART_CHATEAU)
//          && this.xPos < Constant.MARGE_FENETRE + Constant.X_POS_INIT_CHATEAU + Constant.LARGEUR_CHATEAU + colonne *
//          (Constant.LARGEUR_CHATEAU + Constant.ECART_CHATEAU)) {
//        numeroChateau = colonne;
//      }
//    }
//    return numeroChateau;
//  }
//
//  // abscisseContactTirChateau = abscissa of contact with castle sho
//  // Chateau = "castle" or "mansion"
//  private int abscisseContactTirChateau(Chateau chateau) {
//    // Renvoie l'abscisse du tir du vaisseau lors du contact avec un ch teau
//    int xPosTirVaisseau = -1;
//    if(this.xPos + this.size > chateau.getxPos() && this.xPos < chateau.getxPos() + Constant.LARGEUR_CHATEAU){xPosTirVaisseau = this.xPos;}
//    return xPosTirVaisseau;
//  }
//
//  public int[] tirVaisseauToucheChateau() {
//    // Renvoie num ro ch teau touch  et abscisse du tir
//    int[] tabRep = {-1, -1};
//    if(this.tirVaisseauAHauteurDeChateau() == true) { // Le tir du vaisseau est   hauteur du ch teau
//      tabRep[0] = this.chateauProche(); // enregistre le num ro du ch teau touch  dans tabRep[0]
//      if(tabRep[0] != -1) {
//        //enregistre l'abscisse du tir du vaisseau lors du contact avec le ch teau dans tabRep[1]
//        tabRep[1] = this.abscisseContactTirChateau(Main.scene.tabChateaux[tabRep[0]]);
//      }
//    }
//    return tabRep;
//  }
//
//  public void tirVaisseauDetruitChateau(Chateau tabChateaux[]) {
//    int[] tab = this.tirVaisseauToucheChateau(); // Contient (-1,-1) ou le num ro du ch teau touch  et l'abscisse du contact tirVaisseau et ch teau
//    if(tab[0] != -1) { // Un ch teau est touch
//      if(tabChateaux[tab[0]].trouveBrique(tabChateaux[tab[0]].trouveColonneChateau(tab[1])) != -1) {
//        tabChateaux[tab[0]].casseBriques(tab[1]); // D truit les briques du ch teau touch
//        this.yPos = -1; // On tue le tir et on r active le canon du vaisseau
//      }
//    }
//  }
//
//  public boolean detruitSoucoupe(Soucoupe soucoupe) {
//    // Contact missile avec la soucoupe
//    if(this.yPos < soucoupe.getyPos() + soucoupe.getHauteur() && this.yPos + this.height > soucoupe.getyPos()
//        && this.xPos + this.size > soucoupe.getxPos() && this.xPos < soucoupe.getxPos() + soucoupe.getLargeur()){
//      this.playerShoot = false; // On tue le tir
//      return true;
//    }
//    else{return false;}
//  }
//}