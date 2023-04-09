package org.space.invader;

import javax.swing.*;
import org.bson.Document;

/**
 * The Invader class represents an alien invader in the Space Invaders game.
 * It extends the Sprite class and inherits its properties and methods.
 */

public class Invader extends Sprite {
  public static final int INVADER_SIZE = 33;
  public static final int INVADER_HEIGHT = 25;

  /**
   * Constructor for creating a new Invader object.
   *
   * @param xPos    The x-coordinate of the invader's position.
   * @param yPos    The y-coordinate of the invader's position.
   * @param strImg1 The filename of the image for the invader in its first position.
   * @param strImg2 The filename of the image for the invader in its second position.
   */
  public Invader(int xPos, int yPos, String strImg1, String strImg2) {
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
    //strImg3 should be the image of dead alien
    super.strImg3 = "/dead.png";
    super.ico = new ImageIcon(getClass().getResource(super.strImg1));
    super.img = this.ico.getImage();
    super.alive = true;
  }

  /**
   * This method changes the image of the invader according to the sound status and position.
   *
   * @param pos1 A boolean indicating whether the invader is in its first position.
   */
  public void chooseImage(boolean pos1) {
    //Method of changing the image of invader according to the sound status and position(1,2)
    if (this.alive == true) {
      if (pos1 == true) {
        super.ico = new ImageIcon(getClass().getResource(strImg1));
      } else {
        super.ico = new ImageIcon(getClass().getResource(strImg2));
      }
    } else {
      super.ico = new ImageIcon(getClass().getResource(strImg3));
    }
    super.img = this.ico.getImage(); // Change of image
  }

  /**
   * This method returns the state of the invader as a Document object.
   *
   * @return state as Document
   */
  public Document getState() {
    Document state = new Document();
    state.put("xPos", xPos);
    state.put("yPos", yPos);
    state.put("alive", alive);
    return state;
  }

  /**
   * This method loads the state of the invader from a Document object.
   *
   * @param document Document object
   */
  public void loadState(Document document) {
  }
}
