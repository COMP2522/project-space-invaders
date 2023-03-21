package org.space.invader;

import javax.swing.*;

public class Invader extends Sprite {



public Invader(int xPos, int yPos, String strImg1, String strImg2 ){
  //Initialize the variables of the super class
  super.xPos = xPos;
  super.yPos = yPos;
  super.size = Constant.INVADER_SIZE;
  super.height = Constant.INVADER_HEIGHT;
  super.dx = 0;
  super.dy = 0;
  //Address of the images
  super.strImg1 = strImg1;
  super.strImg2 = strImg2;
//  super.strImg3 = strImg3;
  super.ico = new ImageIcon(getClass().getResource(super.strImg1));
  super.img = this.ico.getImage();



}


}
