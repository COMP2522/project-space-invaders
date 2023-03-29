package org.space.invader;
import javax.swing.*;
import java.awt.Image;
public abstract class Sprite {

  /** The size of the sprite. */
  protected int size;

  /** The height of the sprite. */
  protected int height;

  /** The x position of the sprite. */
  protected int xPos;

  /** The y position of the sprite. */
  protected int yPos;

  /** The change in x position of the sprite. */
  protected int dx;

  /** The change in y position of the sprite. */
  protected int dy;

  /** The state of whether the sprite is alive or not. */
  protected boolean alive;

  /** The file path of the first image of the sprite. */
  protected String strImg1;

  /** The file path of the second image of the sprite. */
  protected String strImg2;

  /** The file path of the third image of the sprite. */
  protected String strImg3;

  /** The ImageIcon of the sprite. */
  protected ImageIcon ico;

  /** The Image of the sprite. */
  protected Image img;


  /** Returns the size of the sprite.
   *
   * @return The size of the sprite.
   */
  public int getSize(){
    return size;
  }

  /**
   * Sets the size of the sprite.
   *
   * @param size the new size of the sprite
   */
//  public void setSize(int size){
//    this.size = size;
//  }
  /**
   *  Returns the height of the sprite.
   *
   *  @return the height of the sprite
   */
  public int getHeight(){
    return height;
  }

  /**
   * Sets the height of the sprite.
   *
   * @param height the new height of the sprite
   */

//  public void setHeight(int height){
//    this.height = height;
//  }

  /**
   * Returns the x position of the sprite.
   *
   * @return the x position of the sprite
   */
  public int getxPos(){
    return xPos;
  }

  /**
   * Sets the x position of the sprite.
   *
   * @param xPos the new x position of the sprite
   */
  public void setxPos(int xPos){
    this.xPos = xPos;
  }

  /**
   * Returns the y position of the sprite.
   *
   * @return the y position of the sprite
   */
  public int getyPos(){
    return yPos;
  }

  /**
   * Sets the y position of the sprite.
   *
   * @param yPos the new y position of the sprite
   */
  public void setyPos(int yPos){ this.yPos = yPos; }

  /**
   * Returns the horizontal speed of the sprite.
   *
   * @return the horizontal speed of the sprite
   */
//  public int getDx(){ return dx; }

  /**
   * Sets the horizontal speed of the sprite.
   *
   * @param dx the new horizontal speed of the sprite
   */
  public void setDx(int dx){
    this.dx = dx;
  }

  /**
   * Returns the vertical speed of the sprite.
   *
   * @return the vertical speed of the sprite
   */
//  public int getDy(){ return dx; }

  /**
   * Sets the vertical speed of the sprite.
   *
   * @param dy the new vertical speed of the sprite
   */
//  public void setDy(int dy){ this.dy = dy; }

  /**
   * Returns whether the sprite is alive or not.
   *
   * @return true if the sprite is alive, false otherwise
   */
 public boolean isAlive(){
    return alive;
 }

  /**
   * Sets the status of the sprite to alive or dead.
   *
   * @param alive the status of the sprite, true for alive and false for dead.
   */
 public void setAlive(boolean alive){
    this.alive = alive;
 }

  /**
   * Gets the string representation of the first image of the sprite.
   *
   * @return the string representation of the first image of the sprite.
   */
// public String getStrImg1(){
//    return strImg1;
// }

  /**
   * Sets the string representation of the first image of the sprite.
   *
   * @param strImg1 the string representation of the first image of the sprite.
   */
// public void setStringImg1(String strImg1){
//    this.strImg1 = strImg1;
// }

  /**
   * Gets the string representation of the second image of the sprite.
   *
   * @return the string representation of the second image of the sprite.
   */
//  public String getStrImg2(){
//    return strImg2;
//  }

  /**
   * Sets the string representation of the second image of the sprite.
   *
   * @param strImg2 the string representation of the second image of the sprite.
   */
//  public void setStringImg2(String strImg2){
//    this.strImg2 = strImg2;
//  }

  /**
   * Gets the string representation of the third image of the sprite.
   *
   * @return the string representation of the third image of the sprite.
   */
//  public String getStrImg3(){
//    return strImg3;
//  }

  /**
   * Sets the string representation of the third image of the sprite.
   *
   * @param strImg3 the string representation of the third image of the sprite.
   */
//  public void setStringImg3(String strImg3){
//    this.strImg3 = strImg3;
//  }

  /**
   * Gets the image icon of the sprite.
   *
   * @return the image icon of the sprite.
   */
//  public ImageIcon getIco(){
//    return ico;
//  }

  /**
   * Sets the image icon of the sprite.
   *
   * @param ico the image icon of the sprite.
   */
//  public void setIco(ImageIcon ico){
//    this.ico = ico;
//  }

  /**
   * Gets the image of the sprite.
   *
   * @return the image of the sprite.
   */
  public Image getImg(){
    return img;
  }

  /**
   * Sets the image of the sprite.
   *
   * @param img the image of the sprite.
   */
//  public void setImg(Image img){
//    this.img = img;
//  }



}
