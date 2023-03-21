package org.space.invader;

public class Invader extends Sprite {

  //Alien size
  public static final int INVADER_SIZE = 33;
  public static final int INVADER_HEIGHT = 25;


  //Alien position
  public final static int ALT_INIT_INVADER = 120;
  public final static int XPOS_INIT_INVADER = 29 + Window.WINDOW_MARGIN;
  public final static int SPACE_ROW_INVADER = 40;
  public final static int SPACE_COLOMN_INVADER = 10;

  //Alien movement unit

  public final static int DX_INVADER= 5;
  public final static int DY_INVADER= 25;


public Invader(int xPos, int yPos, String strImg1, String strImg2 ){
  //Initialize the variables of the super class
  super.xPos = xPos;
  super.yPos = yPos;
  super.size = INVADER_SIZE;
  super.height = INVADER_HEIGHT;
  super.dx = 0;
  super.dy = 0;
  //Address of the images
  super.strImg1 = strImg1;
  super.strImg2 = strImg2;
//  super.strImg3 = strImg3;




}


}
