package org.space.invader;

public class Constant {

//window
  public static final int WINDOW_SIZE = 600;
  public static final int WINDOW_HEIGHT = 600;
  public static final int WINDOW_MARGIN = 50;

  /************************************* VAISSEAU *************************************/
  /************************************* PLAYER *************************************/
  // vaisseau = player
  // Dimensions du vaisseau
//  public static final int LARGEUR_VAISSEAU = 39;
  public static final int PLAYER_SIZE = 39;
//  public static final int HAUTEUR_VAISSEAU = 24;
  public static final int PLAYER_HEIGHT = 24;

  // Position initiale du vaisseau
//  public final static int X_POS_INIT_VAISSEAU = (LARGEUR_FENETRE - LARGEUR_VAISSEAU)/ 2;
  public final static int X_POS_INIT_PLAYER = (WINDOW_SIZE - PLAYER_SIZE)/ 2;
//  public final static int Y_POS_VAISSEAU = 490;
  public final static int Y_POS_PLAYER = 490;

  // Unit?de d?lacement du vaisseau
//  public final static int DX_VAISSEAU = 1;
  public final static int DX_PLAYER = 1;

  // Limite de d?lacement du vaisseau
//  public final static int LIMITE_GAUCHE_VAISSEAU = 60;
  public final static int LIMIT_LEFT_PLAYER = 60;
//  public final static int LIMITE_DROITE_VAISSEAU = 500;
  public final static int LIMIT_RIGHT_VAISSEAU = 500;




  /************************************* ALIEN ***************************************/
  /************************************* INVADER ***************************************/

  //Invader size
  public static final int INVADER_SIZE = 33;
  public static final int INVADER_HEIGHT = 25;


  //Invader position
  public final static int ALT_INIT_INVADER = 120;
  public final static int XPOS_INIT_INVADER = 29 + Window.WINDOW_MARGIN;
  public final static int SPACE_ROW_INVADER = 40;
  public final static int SPACE_COLOMN_INVADER = 10;

  //Invader movement unit

  public final static int DX_INVADER= 2;
  public final static int DY_INVADER= 20;

//  public final static int VITESSE_ALIEN = 1;
  public final static int INVADER_SPEED = 1;

  // Nombre total d'aliens
  // Total number of aliens
//  public final static int NOMBRE_ALIENS = 50;
  public final static int NUMBER_INVADER = 50;

  /************************************ TIR VAISSEAU **********************************/
  /************************************ MISSLE PLAYER **********************************/
  // Dimensions du tir
  // Dimensions of the shot
//  public static final int LARGEUR_TIR_VAISSEAU = 3;
  public static final int SIZE_MISSILE_PLAYER = 3;
//  public static final int HAUTEUR_TIR_VAISSEAU = 13;
  public static final int HEIGHT_MISSILE_PLAYER = 13;

  // Unit?de d?lacement du tir
  // Unit of movement of the shot
//  public final static int DY_TIR_VAISSEAU = 2;
  public final static int DY_MISSILE_PLAYER = 2;

  /************************************* CHATEAU *************************************/
  /************************************* BARRIER *************************************/
  // Dimensions de la brique
  // Dimensions of the barrier
//  public static final int DIMENSION_BRIQUE = 2;
  public static final int DIMENSION_BARRIER = 2;

  // Dimensions du ch?eau (multiples des dimensions de la brique)
  // Dimensions of the castle (multiples of the dimensions of the brick)
//  public static final int LARGEUR_CHATEAU = 72;
  public static final int SIZE_BARRIER = 72;
//  public static final int HAUTEUR_CHATEAU = 54;
  public static final int HEIGHT_BARRIER = 54;

  // Param?res de position des ch?eaux
  // Position parameters of the barriers
//  public final static int Y_POS_CHATEAU = 400;
  public final static int Y_POS_BARRIER = 400;
//  public final static int X_POS_INIT_CHATEAU = 39;
  public final static int X_POS_INIT_BARRIER = 39;
//  public final static int ECART_CHATEAU = 42;
  public final static int GAP_BARRIER = 42;

  /************************************ TIR ALIEN ************************************/
  /************************************ MISSILE INVADER ************************************/
  // Dimensions du tir
  // Dimensions of the shot
//  public static final int LARGEUR_TIR_ALIEN = 5;
  public static final int SIZE_MISSILE_INVADER = 5;
//  public static final int HAUTEUR_TIR_ALIEN = 15;
  public static final int HEIGHT_MISSILE_INVADER = 15;

  // Unit?de d?lacement du tir
  // Unit of movement of the shot
//  public final static int DY_TIR_ALIEN = 3;
  public final static int DY_MISSILE_INVADER = 3;

//  /************************************* SOUCOUPE *************************************/
//  // Dimensions de la soucoupe
//  public static final int LARGEUR_SOUCOUPE = 42;
//  public static final int HAUTEUR_SOUCOUPE = 22;
//
//  // Position initiale de la soucoupe
//  public final static int X_POS_INIT_SOUCOUPE = LARGEUR_FENETRE;
//  public final static int Y_POS_SOUCOUPE = 50;
//
//  // Unit?de d?lacement de la soucoupe
//  public final static int DX_SOUCOUPE = 1;

  /************************************* POINTS *************************************/
  // Points attribu? pour la destruction des aliens
  // Points awarded for the destruction of invaders.
//  public static final int VALEUR_ALIEN_HAUT = 50;
  public static final int HIGH_VALUE_INVADER = 50;
//  public static final int VALEUR_ALIEN_MILIEU = 40;
  public static final int MIDDLE_VALUE_INVADER = 40;
//  public static final int VALEUR_ALIEN_BAS = 20;
  public static final int LOW_VALUE_INVADER = 20;
//  public static final int VALEUR_SOUCOUPE = 100;

}
