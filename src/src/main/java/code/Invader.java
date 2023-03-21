package code;

import javax.swing.*;
import java.net.URL;

public class Invader extends Sprite {



public Invader(int xPos, int yPos, String strImg1, String strImg2 ){
  //Initialize the variables of the super class
  super.xPos = xPos;
  super.yPos = yPos;
  super.size = Constant.INVADER_SIZE;
  super.height = Constant.INVADER_HEIGHT;
  super.dx = 0;
  super.dy = 0;

  //Address of the images of the invaders
  super.strImg1 = strImg1;
  super.strImg2 = strImg2;
  super.strImg3 = "/image/alien1.svg";

  URL imageUrl = getClass().getResource(super.strImg1);
  System.out.println(getClass().getResource("/"));
  if (imageUrl != null) {
    super.ico = new ImageIcon(imageUrl);
    super.img = this.ico.getImage();
  }else{
    System.out.println("It is Null");

  }

//  super.ico = new ImageIcon(getClass().getResource(super.strImg1));
//  super.img = this.ico.getImage();

//  public void chooseImage(boolean pos1) {
//    // M�thode qui charge l'image de l'alien selon son �tat et sa position (1 ou 2)
//    if(this.isAlive() == true) {
//      if(pos1 == true) {super.ico = new ImageIcon(getClass().getResource(strImg1));}
//      else {super.ico = new ImageIcon(getClass().getResource(strImg2));}
//    }
//    else {super.ico = new ImageIcon(getClass().getResource(strImg3));}
//    super.img = this.ico.getImage(); // recharge l'image
//  }

}




}



