package org.space.invader;
import javax.swing.*;
import java.awt.Image;
public abstract class Sprite {
  /****instance Variables****/

  protected int size, height, xPos, yPos, dx, dy;
  protected boolean alive;
  protected String strImg1, strImg2, strImg3;
  protected ImageIcon ico;
  protected Image img;



  /****Methods****/
  public int getSize(){
    return size;
  }
  public void setSize(int size){
    this.size = size;
  }
  public int getHeight(){
    return height;
  }
  public void setHeight(int height){
    this.height = height;
  }
  public int getxPos(){
    return xPos;
  }
  public void setxPos(int xPos){
    this.xPos = xPos;
  }
  public int getyPos(){
    return yPos;
  }
  public void setyPos(int yPos){ this.yPos = yPos; }

  public int getDx(){
    return dx;
  }
  public void setDx(int dx){
    this.dx = dx;
  }
  public int getDy(){
    return dx;
  }
  public void setDy(int dy){ this.dy = dy; }
 public boolean isAlive(){
    return alive;
 }
 public void setAlive(boolean alive){
    this.alive = alive;
 }
 public String getStrImg1(){
    return strImg1;
 }
 public void setStringImg1(){
    this.strImg1 = strImg1;
 }
  public String getStrImg2(){
    return strImg2;
  }
  public void setStringImg2(){
    this.strImg2 = strImg2;
  }
  public String getStrImg3(){
    return strImg3;
  }
  public void setStringImg3(){
    this.strImg3 = strImg3;
  }

  public ImageIcon getIco(){
    return ico;
  }
  public void setIco(ImageIcon ico){
    this.ico = ico;
  }
  public Image getImg(){
    return img;
  }

  public void setImg(Image img){
    this.img = img;
  }







}
