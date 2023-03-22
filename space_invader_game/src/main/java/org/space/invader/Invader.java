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

  public void changeImage(boolean pos1) {
  //Method of changing the image of invader according to its status and position

    if(this.isAlive() == true) {
      if(pos1 == true) {super.ico = new ImageIcon(getClass().getResource(strImg1));}
      else {super.ico = new ImageIcon(getClass().getResource(strImg2));}
    }
    else {super.ico = new ImageIcon(getClass().getResource(strImg3));}
    super.img = this.ico.getImage();
  }
}
