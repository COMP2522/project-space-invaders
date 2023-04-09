package org.space.invader;

import java.awt.Image;
import javax.swing.*;

/**
 * The abstract Sprite class represents a sprite and its properties and methods.
 */
public abstract class Sprite {
  /**
   * The size of the sprite.
   */
  protected int size;
  /**
   * The height of the sprite.
   */
  protected int height;
  /**
   * The x position of the sprite.
   */
  protected int xPos;
  /**
   * The y position of the sprite.
   */
  protected int yPos;
  /**
   * The change in x position of the sprite.
   */
  protected int dx;
  /**
   * The change in y position of the sprite.
   */
  protected int dy;
  /**
   * The state of whether the sprite is alive or not.
   */
  protected boolean alive;
  /**
   * The file path of the first image of the sprite.
   */
  protected String strImg1;
  /**
   * The file path of the second image of the sprite.
   */
  protected String strImg2;
  /**
   * The file path of the third image of the sprite.
   */
  protected String strImg3;
  /**
   * The ImageIcon of the sprite.
   */
  protected ImageIcon ico;
  /**
   * The Image of the sprite.
   */
  protected Image img;

  /**
   * Returns the size of the sprite.
   *
   * @return The size of the sprite.
   */
  public int getSize() {
    return size;
  }

  /**
   * Returns the height of the sprite.
   *
   * @return the height of the sprite
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns the x position of the sprite.
   *
   * @return the x position of the sprite
   */
  public int getxPos() {
    return xPos;
  }

  /**
   * Sets the x position of the sprite.
   *
   * @param xPos the new x position of the sprite
   */
  public void setxPos(int xPos) {
    this.xPos = xPos;
  }

  /**
   * Returns the y position of the sprite.
   *
   * @return the y position of the sprite
   */
  public int getyPos() {
    return yPos;
  }

  /**
   * Sets the y position of the sprite.
   *
   * @param yPos the new y position of the sprite
   */
  public void setyPos(int yPos) {
    this.yPos = yPos;
  }

  /**
   * Sets the horizontal speed of the sprite.
   *
   * @param dx the new horizontal speed of the sprite
   */
  public void setXspeed(int dx) {
    this.dx = dx;
  }

  /**
   * Returns whether the sprite is alive or not.
   *
   * @return true if the sprite is alive, false otherwise
   */
  public boolean isAlive() {
    return alive;
  }

  /**
   * Sets the status of the sprite to alive or dead.
   *
   * @param alive the status of the sprite, true for alive and false for dead.
   */
  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  /**
   * Gets the image of the sprite.
   *
   * @return the image of the sprite.
   */
  public Image getImg() {
    return img;
  }
}
