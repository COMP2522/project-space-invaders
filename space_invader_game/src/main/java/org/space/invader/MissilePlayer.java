package org.space.invader;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.Objects;
import javax.swing.ImageIcon;
import org.bson.Document;

/**
 * MissilePlayer class that handles the player's missile.
 */
public class MissilePlayer extends Sprite {
  public static final int SIZE_MISSILE_PLAYER = 3;
  public static final int HEIGHT_MISSILE_PLAYER = 13;
  public static final int DY_MISSILE_PLAYER = 2;
  final int INVALID = -1;
  final int NUMBER_COLUMN = 4;
  final int ZERO = 0;
  final int ONE = 1;
  final ImageObserver NONE = null;
  private boolean playerShoot = false;

  /**
   * Constructor for the MissilePlayer class.
   * Sets the initial values for the missile's position, size, and movement speed.
   * Also sets the image for the missile.
   */
  public MissilePlayer() {
    this.xPos = ZERO;
    this.yPos = Player.Y_POS_PLAYER - HEIGHT_MISSILE_PLAYER;
    this.size = SIZE_MISSILE_PLAYER;
    this.height = HEIGHT_MISSILE_PLAYER;
    this.dx = ZERO;
    this.dy = DY_MISSILE_PLAYER;  // the speed of missile
    this.strImg1 = "/misPlayer.png";
    this.ico = new ImageIcon(Objects.requireNonNull(getClass().getResource(this.strImg1)));
    this.img = this.ico.getImage();
  }

  /**
   * Returns whether or not the player has fired a missile.
   *
   * @return true if the player has fired a missile, false otherwise.
   */
  public boolean isPlayerShoot() {
    return playerShoot;
  }

  /**
   * Sets whether or not the player has fired a missile.
   *
   * @param playerShoot true if the player has fired a missile, false otherwise.
   */
  public void setPlayerShoot(boolean playerShoot) {
    this.playerShoot = playerShoot;
  }

  /**
   * Moves the player's missile up the screen.
   *
   * @return the new y-position of the missile.
   */
  public int moveMissilePlayer() {
    if (this.playerShoot) {
      if (this.yPos > ZERO) {
        this.yPos = this.yPos - DY_MISSILE_PLAYER;
      } else {
        this.playerShoot = false;
      }
    }
    return yPos;
  }

  /**
   * Draws the player's missile on the screen.
   *
   * @param g the Graphics object to use for drawing.
   */
  public void drawPlayerMissile(Graphics g, boolean isPaused) {
    if (isPaused) {
      return;
    }
    if (this.playerShoot) {
      g.drawImage(this.img, this.xPos, this.moveMissilePlayer(), NONE);
    }
  }

  /**
   * Checks whether the current spaceship's shot is hitting
   * the given invader object, and if so, it returns true.
   * Otherwise, it returns false.
   *
   * @param invader Invader object
   * @return boolean
   */
  public boolean killInvader(Invader invader) {
    // the player's shot destroys an alien
    if (this.yPos < invader.getyPos() + invader.getHeight()
            && this.yPos + this.height > invader.getyPos()
            && this.xPos + this.size > invader.getxPos()
            && this.xPos < invader.getxPos() + invader.getSize()) {
      try {
        Audio audio = Audio.getInstance();
        audio.playDeadInvader();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks whether the current spaceship's shot is at the height
   * of the barriers, and if so, it returns true. Otherwise, it returns false.
   *
   * @return boolean
   */
  private boolean missilePlayerFireAtBarrier() {
    // Returns true if the ship's shot is at the height of the barriers
    return this.yPos < Barrier.Y_POS_BARRIER + Barrier.HEIGHT_BARRIER
            && this.yPos + this.height > Barrier.Y_POS_BARRIER;
  }

  /**
   * Calculates and returns the number of the barrier (0, 1, 2, or 3) in the
   * firing zone of the player.
   *
   * @return number of the barrier
   */
  private int numberBarrier() {
    int numBarrier = INVALID;
    int column = INVALID;
    while (numBarrier == INVALID && column < NUMBER_COLUMN) {
      column++;
      if (this.xPos + this.size > Window.WINDOW_MARGIN + Barrier.X_POS_INIT_BARRIER + column
              * (Barrier.SIZE_BARRIER + Barrier.GAP_BARRIER)
              && this.xPos < Window.WINDOW_MARGIN + Barrier.X_POS_INIT_BARRIER
              + Barrier.SIZE_BARRIER + column
              * (Barrier.SIZE_BARRIER + Barrier.GAP_BARRIER)) {
        numBarrier = column;
      }
    }
    return numBarrier;
  }

  /**
   * Calculates and returns the player's shot's coordinates upon contact
   * with the given barrier.
   *
   * @param barrier Barrier object
   * @return the x coordinate of missile contacted with the barrier
   */
  private int xContactMisBarrier(Barrier barrier) {
    int xPosMisPlayer = INVALID;
    if (this.xPos + this.size > barrier.getxPos() && this.xPos
            < barrier.getxPos() + Barrier.SIZE_BARRIER) {
      xPosMisPlayer = this.xPos;
    }
    return xPosMisPlayer;
  }

  /**
   * Checks whether the missile fired by the player hits a barrier or not.
   * If the missile hits the barrier, it returns an array with two values.
   *
   * @return arrayRep[2].
   * arrauRep[0] = the index of the barrier that the missile hits.
   * arrayRep[1] = the x-coordinate of the collision point.
   */
  public int[] misPlayerTouchBarrier() {
    int[] arrayRep = {INVALID, INVALID};
    if (this.missilePlayerFireAtBarrier()) {
      arrayRep[ZERO] = this.numberBarrier();
      if (arrayRep[ZERO] != INVALID) {
        arrayRep[ONE] = this.xContactMisBarrier(Window.barrierArray[arrayRep[ZERO]]);
      }
    }
    return arrayRep;
  }

  /**
   * Calls the misPlayerTouchBarrier() method to get the index of the barrier
   * and the x-coordinate of the collision point. If a barrier is hit by the
   * missile, it checks whether the barrier has any bricks in the same column
   * as the collision point. If there are bricks in that column, it destroys
   * those bricks using the breakBricks() method of the Barrier class. Finally,
   * it sets the yPos variable to -1, which indicates that the missile is
   * destroyed, and it activates the player's cannon to fire again.
   *
   * @param BarrierArray array of 'Barrier' objects
   */
  public void misPlayerDestroyBarrier(Barrier[] BarrierArray) {
    int[] array = this.misPlayerTouchBarrier();
    if (array[ZERO] != INVALID) {
      if (BarrierArray[array[ZERO]].findBrick(BarrierArray[array[ZERO]]
              .findBarrierColumn(array[ONE])) != INVALID) {
        BarrierArray[array[ZERO]].breakBricks(array[ONE]);
        this.yPos = INVALID;
        try {
          Audio audio = Audio.getInstance();
          audio.playBarrier();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Returns a document containing the current state of the missile.
   *
   * @return a document with the missile's x and y positions
   */
  public Document getState() {
    // create a new document to hold the missile state
    Document missileState = new Document();

    // add the current x and y positions to the document
    missileState.put("xPos", xPos);
    missileState.put("yPos", yPos);

    // return the document
    return missileState;
  }

  /**
   * Loads the missile's state from the given document.
   *
   * @param missilePlayerState a document containing the missile's x and y positions
   */
  public void loadState(Document missilePlayerState) {
    // check that the document is not null
    if (missilePlayerState != null) {
      // set the missile's x and y positions from the document
      xPos = missilePlayerState.getInteger("xPos");
      yPos = missilePlayerState.getInteger("yPos");
    }
  }
}