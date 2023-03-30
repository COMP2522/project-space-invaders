package org.space.invader;

/**
 * Constant class contains constant values used in the Space Invader game.
 */
public class Constant {

//window
  public static final int WINDOW_SIZE = 600;
  public static final int WINDOW_HEIGHT = 600;
  public static final int WINDOW_MARGIN = 40;

  /************************************* PLAYER *************************************/
  public static final int PLAYER_SIZE = 39;
  public static final int PLAYER_HEIGHT = 24;
  public final static int X_POS_INIT_PLAYER = (WINDOW_SIZE - PLAYER_SIZE)/ 2;
  public final static int Y_POS_PLAYER = 490;
  public final static int DX_PLAYER = 1;
  public final static int LIMIT_LEFT_PLAYER = 60;
  public final static int LIMIT_RIGHT_PLAYER = 500;


  /************************************* INVADER ***************************************/

  //Invader size
  public static final int INVADER_SIZE = 33;
  public static final int INVADER_HEIGHT = 25;


  //Invader position
  public final static int ALT_INIT_INVADER = 120;
  public final static int XPOS_INIT_INVADER = 29 + WINDOW_MARGIN;
  public final static int SPACE_ROW_INVADER = 40;
  public final static int SPACE_COLOMN_INVADER = 10;

  //Invader movement unit
  public final static int DX_INVADER= 2;
  public final static int DY_INVADER= 20;

  public final static int INVADER_SPEED = 4;

  // Total number of aliens
  public final static int NUMBER_INVADER = 50;

  /************************************ MISSLE PLAYER **********************************/
  public static final int SIZE_MISSILE_PLAYER = 3;
  public static final int HEIGHT_MISSILE_PLAYER = 13;
  public final static int DY_MISSILE_PLAYER = 2;

  /************************************* BARRIER *************************************/
  public static final int DIMENSION_BARRIER = 2;
  public static final int SIZE_BARRIER = 90;
  public static final int HEIGHT_BARRIER = 46;
  public final static int Y_POS_BARRIER = 420;
  public final static int X_POS_INIT_BARRIER = 0;
  public final static int GAP_BARRIER = 50;

  /************************************ MISSILE INVADER ************************************/
  public static final int SIZE_MISSILE_INVADER = 5;
  public static final int HEIGHT_MISSILE_INVADER = 15;
  public final static int DY_MISSILE_INVADER = 3;


  /************************************* POINTS *************************************/
  public static final int HIGH_VALUE_INVADER = 50;
  public static final int MIDDLE_VALUE_INVADER = 40;
  public static final int LOW_VALUE_INVADER = 20;

}
