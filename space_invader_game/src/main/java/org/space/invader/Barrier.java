package org.space.invader;

import org.bson.Document;

import java.awt.*;

/**
 * The Barrier class represents a defensive barrier in the Space Invaders game.
 * It extends the Sprite class and contains methods for initializing the barrier,
 * drawing it on the screen, finding the column of a shot on the barrier,
 * finding the first brick in a column, and removing bricks from a column.
 */

public class Barrier extends Sprite {

  /** The number of rows in the barrier. */
  private static final int NUM_ROWS = Constant.HEIGHT_BARRIER / Constant.DIMENSION_BARRIER;
  /** The number of columns in the barrier */
  private static final int NUM_COLS = Constant.SIZE_BARRIER / Constant.DIMENSION_BARRIER;
  /** An array representing the bricks in the barrier */
  public boolean[][] BarrierArray = new boolean[NUM_ROWS][NUM_COLS];

  /**
   * Constructs a Barrier object with the given x-position.
   *
   *  @param xPos The x-coordinate of the leftmost point of the barrier
   */

  public Barrier(int xPos) {
    super.xPos = xPos; // The leftmost point of the barrier
    super.yPos = Constant.Y_POS_BARRIER; // Ordinate of the top of the barrier

    this.initBarrierArray();
  }


  /**
   * Initializes the BarrierArray with values to represent the undamaged barrier.
   * The top of the barrier is designed to look like a castle.
   */
  public void initBarrierArray() {
    // Fill all cells of the array with true
    for (int row = 0; row < NUM_ROWS; row++) {
      for (int col = 0; col < NUM_COLS; col++) {
        BarrierArray[row][col] = true;
      }
    }

    // Fill all cells without bricks with false
    // Making a shape for the top of the castle
    for (int col = 0; col < 6; col++) {
      for (int row = 0; row < 2; row++) {
        BarrierArray[row][col] = false;
        BarrierArray[row][NUM_COLS - col - 1] = false;
      }
    }
    for (int col = 0; col < 4; col++) {
      for (int row = 2; row < 4; row++) {
        BarrierArray[row][col] = false;
        BarrierArray[row][NUM_COLS - col - 1] = false;
      }
    }
    for (int col = 0; col < 2; col++) {
      for (int row = 4; row < 6; row++) {
        BarrierArray[row][col] = false;
        BarrierArray[row][NUM_COLS - col - 1] = false;
      }
    }
  }

  /**
   * Draws the barrier on the screen.
   *
   * @param g2 The graphics context to use for drawing
   */
  public void drawBarrier(Graphics g2) {
    for (int row = 0; row < NUM_ROWS; row++) {
      for (int column = 0; column < NUM_COLS; column++) {
        if (BarrierArray[row][column] == true) {
          g2.setColor(Color.GREEN);
        } else {
          g2.setColor(Color.BLACK);
        }
        g2.fillRect(this.xPos + Constant.DIMENSION_BARRIER*column, this.yPos + Constant.DIMENSION_BARRIER*row, Constant.DIMENSION_BARRIER, Constant.DIMENSION_BARRIER);
      }
    }
  }

  /**
   * Returns the column in the table associated with the barrier hit by the shot.
   *
   * @param xMissile the x-coordinate of the shot
   * @return the column number
   */
  public int findBarrierColumn(int xMissile) {
    int column = -1;
    column = (xMissile - this.xPos) / Constant.DIMENSION_BARRIER;
    return column;
  }

  /**
   * Finds the first brick starting from the bottom of the column in the table associated with the barrier or returns -1.
   *
   * @param column the column number of the barrier
   * @return the row number of the brick or -1 if no brick found
   */
  public int findBrick(int column) {
    int row = NUM_ROWS - 1;
    while (row >= 0 && BarrierArray[row][column] == false) {
      row--;
    }
    return row;
  }

  /**
   * Removes the first 10 bricks in the column starting from the bottom if they exist.
   *
   * @param row the row number of the brick to start removing
   * @param column the column number of the brick to start removing
   */
  public void removeBricks(int row, int column) {
    for (int counter = 0; counter < 10; counter++) {
      if (row - counter >= 0) {
        BarrierArray[row - counter][column] = false;
        if (column < NUM_COLS - 1) {
          BarrierArray[row - counter][column + 1] = false;
        }
      }
    }
  }

  /**
   * Calls the previous 3 methods to break the bricks in the barrier.
   *
   * @param xShot the x-coordinate of the shot that hit the barrier
   */
  public void breakBricks(int xShot) {
//    Audio.playSound("/sounds/soundBrickBreak.wav");
    int column = this.findBarrierColumn(xShot);
    this.removeBricks(findBrick(column), column);
  }

  /**
   * Finds the first brick from the top of the column of the array associated with the castle,
   * or returns -1 if there is no brick.
   *
   * @param column the column number of the castle
   * @return the row number of the brick or -1 if no brick found
   */
  public int findTopBrick(int column) {
    int row = 0;
    if (column != -1) {
      while (row < NUM_ROWS && !BarrierArray[row][column]) {
        row++;
      }
    }
    return row;
  }

  /**
   * Removes the top 10 bricks of the column if they exist.
   *
   * @param row the row number of the brick to start removing
   * @param column the column number of the brick to start removing
   */
  private void removeTopBricks(int row, int column) {
    for (int i = 0; i < 10; i++) {
      if (row + i < NUM_ROWS && column != -1) {
        BarrierArray[row + i][column] = false;
        if (column < NUM_COLS - 3) {
          BarrierArray[row + i][column + 1] = false;
        }
      }
    }
  }

  /**
   * Calls the previous 3 methods to break the top bricks in the castle.
   *
   * @param xShot the x-coordinate of the shot that hit the castle
   */
  public void breakTopBricks(int xShot) {
//    Audio.playSound("/sounds/soundBrickBreak.wav");
    int column = this.findBarrierColumn(xShot);
    this.removeTopBricks(findTopBrick(column), column);
  }
  public void loadBarriersState(Document barrierDoc) {
    for (int row = 0; row < NUM_ROWS; row++) {
      for (int col = 0; col < NUM_COLS; col++) {
        BarrierArray[row][col] = barrierDoc.getBoolean("barrier_" + row + "_" + col);
      }
    }
  }
  public Document getState() {
    Document barrierState = new Document();
    for (int row = 0; row < NUM_ROWS; row++) {
      for (int col = 0; col < NUM_COLS; col++) {
        barrierState.put("barrier_" + row + "_" + col, BarrierArray[row][col]);
      }
    }
    return barrierState;
  }

}