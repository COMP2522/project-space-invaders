package org.space.invader;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * MissileInvader class represents the missile fired by an invader alien.
 * It extends the Sprite class.
 */
public class MissileInvader extends Sprite {
  final int INVALID = -1;
  final int ZERO = 0;
  final int ONE = 1;
  final int TWO = 2;
  final int WINDOW_HEIGHT = 600;
  final int NUMBER_COLUMN = 4;
  final int SCREEN_HEIGHT = 700;
  final int BARRIER_HEIGHT = 27;
  final int MISSILE_MOVE_INTERVAL = 4;

  /**** VARIABLES ****/
  Random rand = new Random();

  /**
   * Constructor that initializes a new MissileInvader object with the given
   * array of invader position coordinates.
   *
   * @param arrayPositionInvader an array of integers representing the x and y
   *                             coordinates of the invader that fired the missile.
   */
  public MissileInvader(int[] arrayPositionInvader) {

    // Initialization of variables in the superclass
    super.xPos = arrayPositionInvader[ZERO] + Constant.INVADER_SIZE / TWO - ONE;
    super.yPos = arrayPositionInvader[ONE] + Constant.INVADER_HEIGHT;
    super.size = Constant.SIZE_MISSILE_INVADER;
    super.height = Constant.HEIGHT_MISSILE_INVADER;
    super.dx = ZERO;
    super.dy = Constant.DY_MISSILE_INVADER;
    super.strImg1 = "/missileInvader1.png";
    super.strImg2 = "/missileInvader2.png";
//    super.strImg3 = "";
    if (rand.nextInt(TWO) == ZERO) {
      super.ico = new ImageIcon(getClass().getResource(super.strImg1));
    } else {
      super.ico = new ImageIcon(getClass().getResource(super.strImg2));
    }
    super.img = this.ico.getImage();
  }

  /**
   * Moves the missile downwards on the game screen.
   *
   * @return the new y-coordinate of the missile after moving it.
   */
  public int moveMissileInvader() {
    if (Stopwatch.count % MISSILE_MOVE_INTERVAL == ZERO) {
      if (this.yPos < WINDOW_HEIGHT) {
        this.yPos = this.yPos + Constant.DY_MISSILE_INVADER;
      }
    }
    return yPos;
  }

  /**
   * Draws the invader missile icon on the game screen at the current missile position.
   *
   * @param g the Graphics object used to draw the missile icon.
   */
  public void drawInvaderMissile(Graphics g) {
    g.drawImage(this.img, this.xPos, this.moveMissileInvader(), null);
  }

  /**
   * Checks if the invader missile is at the same height as a barrier.
   *
   * @return true if the missile is at the same height as a barrier, false otherwise.
   */
  private boolean missileInvaderFireAtBarrier() {
    return this.yPos < Constant.Y_POS_BARRIER + Constant.HEIGHT_BARRIER
        && this.yPos + this.height > Constant.Y_POS_BARRIER;
  }

  /**
   * Determines the number of the barrier that is closest to the invader missile based on its x-coordinate.
   *
   *  @return the number of the barrier closest to the missile, or -1 if there is no barrier close enough.
   */
  private int numberBarrier() {
    int numBarrier = INVALID;
    int column = INVALID;
    while (numBarrier == INVALID && column < NUMBER_COLUMN) {
      column++;
      if (this.xPos + this.size - ONE > Constant.WINDOW_MARGIN + Constant.X_POS_INIT_BARRIER + column * (Constant.SIZE_BARRIER +
          Constant.GAP_BARRIER)
          && this.xPos + ONE < Constant.WINDOW_MARGIN + Constant.X_POS_INIT_BARRIER + Constant.SIZE_BARRIER +
          column * (Constant.SIZE_BARRIER + Constant.GAP_BARRIER)) {
        numBarrier = column;
      }
    }
    return numBarrier;
  }

  /**
   * Returns the x-coordinate of the point where the invader missile touches the barrier.
   * If there is no contact between the missile and the barrier, -1 is returned.
   *
   * @param barrier the Barrier object to check for collision
   * @return the x-coordinate of the contact point, or -1 if there is no contact
   */
  private int xContactMisInvaderBarrier(Barrier barrier) {
    int xPosTirAlien = INVALID;
    if (this.xPos + this.size > barrier.getxPos()
            && this.xPos < barrier.getxPos() + Constant.SIZE_BARRIER) {
      xPosTirAlien = this.xPos;
    }
    return xPosTirAlien;
  }

  /**
   * Returns an integer array with two values: the index of the barrier that the invader missile touches,
   * and the x-coordinate of the point where the missile touches the barrier. If the missile does not touch
   * any barrier, both values in the array are set to -1.
   *
   * @return an integer array with two values representing the index of the barrier and the x-coordinate of
   * the point where the missile touches the barrier, or {-1, -1} if there is no contact
   */
  public int[] missileInvaderTouchBarrier() {
    int[] tabRep = {INVALID, INVALID};    // tabRep[0] = the number of Barrier, tabRep[1] = x position
    if (this.missileInvaderFireAtBarrier()) {
      tabRep[ZERO] = this.numberBarrier();
      if (tabRep[ZERO] != INVALID) {
        tabRep[ONE] = this.xContactMisInvaderBarrier(
            Window.window.BarrierArray[tabRep[ZERO]]);
      }
    }
    return tabRep;
  }

  /**
   * Checks if the invader missile touches any barrier, and breaks the top brick of the corresponding
   * barrier column if it does. Also updates the y-coordinate of the missile to move it off the screen.
   *
   * @param arrayBarriers an array of Barrier objects to check for collision
   */
  public void misInvaderDestroyBarrier(Barrier arrayBarriers[]) {
    int[] array = this.missileInvaderTouchBarrier();
    if (array[ZERO] != INVALID) {
      if (arrayBarriers[array[ZERO]].findTopBrick(arrayBarriers[array[ZERO]].findBarrierColumn(array[ONE])) != INVALID
          && arrayBarriers[array[ZERO]].findTopBrick(arrayBarriers[array[ZERO]].findBarrierColumn(array[ONE])) != BARRIER_HEIGHT) {
        arrayBarriers[array[ZERO]].breakTopBricks(array[ONE]);
        this.yPos = SCREEN_HEIGHT;
      }
    }
  }

  /**
   * Checks if the invader missile touches the player's ship, and returns true if it does. Also updates
   * the y-coordinate of the missile to move it off the screen.
   *
   * @param player the Player object representing the player's ship
   * @return true if the missile touches the player's ship, false otherwise
   */
  public boolean touchPlayer(Player player) {
    if (this.yPos < player.getyPos() + player.getHeight() && this.yPos + this.height > player.getyPos()
        && this.xPos + this.size > player.getxPos() && this.xPos < player.getxPos() + player.getSize()) {
      this.yPos = SCREEN_HEIGHT;
      Audio.playSound("/player_dead.wav");
      return true;
    } else {
      return false;
    }
  }
}

