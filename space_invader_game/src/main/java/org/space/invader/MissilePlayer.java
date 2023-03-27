package org.space.invader;

import java.awt.Graphics;

import javax.swing.ImageIcon;
public class MissilePlayer extends Sprite {

  private boolean playerShoot = false;

  /**
   * Constructor for the MissilePlayer class.
   * Sets the initial values for the missile's position, size, and movement speed.
   * Also sets the image for the missile.
   */
  public MissilePlayer() {

    super.xPos = 0;
    super.yPos = Constant.Y_POS_PLAYER - Constant.HEIGHT_MISSILE_PLAYER;
    super.size = Constant.SIZE_MISSILE_PLAYER;
    super.height = Constant.HEIGHT_MISSILE_PLAYER;
    super.dx = 0;
    super.dy = Constant.DY_MISSILE_PLAYER;  // the speed of missile
    super.strImg1 = "/misPlayer.png";
    super.strImg2 = "";
    super.strImg3 = "";
    super.ico = new ImageIcon(getClass().getResource(super.strImg1));
    super.img = this.ico.getImage();
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
    if (this.playerShoot == true) {
      if (this.yPos > 0) {
        this.yPos = this.yPos - Constant.DY_MISSILE_PLAYER;
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
  public void drawPlayerMissile(Graphics g) {
    if (this.playerShoot == true) {
      g.drawImage(this.img, this.xPos, this.moveMissilePlayer(), null);
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
      Audio.playSound("/InvaderDead.wav");
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
    if (this.yPos < Constant.Y_POS_BARRIER + Constant.HEIGHT_BARRIER
            && this.yPos + this.height > Constant.Y_POS_BARRIER) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Calculates and returns the number of the barrier (0, 1, 2, or 3) in the
   * firing zone of the player.
   * @return number of the barrier
   */
  private int numberBarrier() {
    int numBarrier = -1;
    int column = -1;
    while (numBarrier == -1 && column < 4) {
      column++;
      if (this.xPos + this.size > Constant.WINDOW_MARGIN + Constant.X_POS_INIT_BARRIER + column *
          (Constant.SIZE_BARRIER + Constant.GAP_BARRIER)
          && this.xPos < Constant.WINDOW_MARGIN + Constant.X_POS_INIT_BARRIER + Constant.SIZE_BARRIER + column *
          (Constant.SIZE_BARRIER + Constant.GAP_BARRIER)) {
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
    int xPosMisPlayer = -1;
    if (this.xPos + this.size > barrier.getxPos() && this.xPos < barrier.getxPos() + Constant.SIZE_BARRIER) {
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
    int[] arrayRep = {-1, -1};
    if (this.missilePlayerFireAtBarrier() == true) {
      arrayRep[0] = this.numberBarrier();
      if (arrayRep[0] != -1) {
        arrayRep[1] = this.xContactMisBarrier(Window.BarrierArray[arrayRep[0]]);
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
  public void misPlayerDestroyBarrier(Barrier BarrierArray[]) {
    int[] array = this.misPlayerTouchBarrier();
    if (array[0] != -1) {
      if (BarrierArray[array[0]].findBrick(BarrierArray[array[0]].findBarrierColumn(array[1])) != -1) {
        BarrierArray[array[0]].breakBricks(array[1]);
        this.yPos = -1;
      }
    }
  }
}