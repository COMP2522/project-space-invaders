package org.space.invader;

public class Constant {

//window
  public static final int WINDOW_SIZE = 600;
  public static final int WINDOW_HEIGHT = 600;
  public static final int WINDOW_MARGIN = 50;

  /************************************* VAISSEAU *************************************/
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

  //Invader size
  public static final int INVADER_SIZE = 33;
  public static final int INVADER_HEIGHT = 25;


  //Invader position
  public final static int ALT_INIT_INVADER = 120;
  public final static int XPOS_INIT_INVADER = 29 + Window.WINDOW_MARGIN;
  public final static int SPACE_ROW_INVADER = 40;
  public final static int SPACE_COLOMN_INVADER = 10;

  //Invader movement unit

  public final static int DX_INVADER= 5;
  public final static int DY_INVADER= 25;







}
