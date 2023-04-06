package org.space.invader;

import org.bson.Document;
import javax.swing.*;
import java.awt.*;

/**
 * Player class extends the Sprite class.
 * It represents a player object in a 2D space invader game.
 */
public class Player extends Sprite {
  public static final int PLAYER_SIZE = 39;
  public static final int PLAYER_HEIGHT = 24;
  public final static int X_POS_INIT_PLAYER = (Window.WINDOW_SIZE - PLAYER_SIZE) / 2;
  public final static int Y_POS_PLAYER = 490;
  public final static int DX_PLAYER = 1;
  public final static int LIMIT_LEFT_PLAYER = 60;
  public final static int LIMIT_RIGHT_PLAYER = 500;
  /**
   * An integer that counts the number of times a player is destroyed.
   */
  private int counter = 0;
  final int PLAYER_DESTRUCTION_DURATION = 300;
  final int EVEN_IMAGE_INDEX = 2;
  final int ZERO = 0;

  // Add a new instance variable for the player's name
  private String name;
  private int score;

  /**
   * A constructor that sets the initial values for the player object.
   * It sets the position, size, height, direction, and image of the player.
   */
  public Player(String name) {
    this.name = name;
    this.score = 0;
    super.xPos = X_POS_INIT_PLAYER;
    super.yPos = Y_POS_PLAYER;
    super.size = PLAYER_SIZE;
    super.height = PLAYER_HEIGHT;
    super.dx = 0;
    super.dy = 0;

    this.strImg1 = "/spaceship2.png";
    this.strImg2 = "/playerDes1.png";
    this.strImg3 = "/playerDes2.png";

    this.ico = new ImageIcon(getClass().getResource(super.strImg1));
    this.img = this.ico.getImage();
    this.alive = true;
  }

  /**
   * Limits the movement of the player to within the left and right boundaries
   * of the game window.
   *
   * @return xPos x position of the player.
   */
  public int playerLimit() {
    if (this.dx < ZERO) {
      if (this.xPos > LIMIT_LEFT_PLAYER) {
        this.xPos = this.xPos + this.dx;
      }
    } else if (this.dx > ZERO) {
      if (this.xPos + this.dx < LIMIT_RIGHT_PLAYER) {
        this.xPos = this.xPos + this.dx;
      }
    }
    return this.xPos;
  }

  /**
   * Draws the player object on the screen. If the player is not alive,
   * it calls the destructionPlayer() method to animate the player's destruction.
   *
   * @param g the Graphics object used for drawing
   */
  public void drawPlayer(Graphics g) {
    if (!this.alive) {
      this.destructPlayer();
    }
    g.drawImage(this.img, this.playerLimit(), this.yPos, null);
  }

  /**
   * Animates the player's destruction. It switches between two images of an exploding
   * player to create a blinking effect. Once the animation is complete, it sets the
   * game variable to 'false' to end the game.
   */
  public void destructPlayer() {
    if (counter < PLAYER_DESTRUCTION_DURATION) {
      if (Stopwatch.count % EVEN_IMAGE_INDEX == ZERO) {
        super.ico = new ImageIcon(getClass().getResource(super.strImg2));
      } else {
        super.ico = new ImageIcon(getClass().getResource(super.strImg3));
      }
      counter++;
    } else {
      Window.game = false;
    }
    super.img = this.ico.getImage();
  }

  // Getter and setter methods
  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getXspeed() {
    return dx;
  }

  public void setDx(int i) {
    this.dx = i;
  }

  public void setXPos(int i) {
    this.xPos = i;
  }

  public Document getState() {
    Document state = new Document();
    state.put("xPos", xPos);
    state.put("yPos", yPos);
    state.put("alive", alive);
    return state;
  }

  public void loadState(Document playerState) {
    if (playerState != null) {
      xPos = playerState.getInteger("xPos");
      yPos = playerState.getInteger("yPos");
      alive = playerState.getBoolean("alive");
    }
  }
}
