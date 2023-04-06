package org.space.invader;

import java.awt.*;
import org.bson.Document;

/**
 * The Barrier class represents a defensive barrier in the Space Invaders game.
 * It extends the Sprite class and contains methods for initializing the barrier,
 * drawing it on the screen, finding the column of a shot on the barrier,
 * finding the first brick in a column, and removing bricks from a column.
 */

public class Barrier extends Sprite {

  /** The dimension of the barrier. */
  public static final int DIMENSION_BARRIER = 2;

  /** The width of the barrier. */
  public static final int SIZE_BARRIER = 72;

  /** The height of the barrier. */
  public static final int HEIGHT_BARRIER = 50;

  /** The y position of the barrier. */
  public final static int Y_POS_BARRIER = 400;

  /** The initial x position of the left most barrier. */
  public final static int X_POS_INIT_BARRIER = 39;


  /** The gap between the barriers. */
  public final static int GAP_BARRIER = 42;

  /** The number of rows in the barrier. */
  private static final int NUM_ROWS = HEIGHT_BARRIER / DIMENSION_BARRIER;
  /** The number of columns in the barrier. */
  private static final int NUM_COLS = SIZE_BARRIER / DIMENSION_BARRIER;
  /** An array representing the bricks in the barrier. */
  public boolean[][] barrierArray = new boolean[NUM_ROWS][NUM_COLS];

  /**
   * Constructs a Barrier object with the given x-position.
   *
   *  @param xpos The x-coordinate of the leftmost point of the barrier
   */

  public Barrier(int xpos) {
    super.xPos = xpos; // The leftmost point of the barrier
    super.yPos = Y_POS_BARRIER; // Ordinate of the top of the barrier

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
        barrierArray[row][col] = true;
      }
    }

    // Fill all cells without bricks with false
    for (int col = 0; col < 6; col++) {
      for (int row = 0; row < 2; row++) {
        barrierArray[row][col] = false;
        barrierArray[row][NUM_COLS - col - 1] = false;
      }
    }
    for (int col = 0; col < 4; col++) {
      for (int row = 2; row < 4; row++) {
        barrierArray[row][col] = false;
        barrierArray[row][NUM_COLS - col - 1] = false;
      }
    }
    for (int col = 0; col < 2; col++) {
      for (int row = 4; row < 6; row++) {
        barrierArray[row][col] = false;
        barrierArray[row][NUM_COLS - col - 1] = false;
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
        if (barrierArray[row][column] == true) {
          g2.setColor(Color.GREEN);
        } else {
          g2.setColor(Color.BLACK);
        }
        g2.fillRect(this.xPos + DIMENSION_BARRIER * column, this.yPos + DIMENSION_BARRIER * row,
            DIMENSION_BARRIER, DIMENSION_BARRIER);
      }
    }
  }

  /**
   * Returns the column in the table associated with the barrier hit by the shot.
   *
   * @param xmissile the x-coordinate of the shot
   * @return the column number
   */
  public int findBarrierColumn(int xmissile) {
    int column = -1;
    column = (xmissile - this.xPos) / DIMENSION_BARRIER;
    return column;
  }

  /**
   * Finds the first brick starting from the bottom of the column in the table associated
   * with the barrier or returns -1.
   *
   * @param column the column number of the barrier
   * @return the row number of the brick or -1 if no brick found
   */
  public int findBrick(int column) {
    int row = NUM_ROWS - 1;
    while (row >= 0 && barrierArray[row][column] == false) {
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
        barrierArray[row - counter][column] = false;
        if (column < NUM_COLS - 1) {
          barrierArray[row - counter][column + 1] = false;
        }
      }
    }
  }

  /**
   * Calls the previous 3 methods to break the bricks in the barrier.
   *
   * @param xshot the x-coordinate of the shot that hit the barrier
   */
  public void breakBricks(int xshot) {
    int column = this.findBarrierColumn(xshot);
    this.removeBricks(findBrick(column), column);
  }

  /**
   * Finds the first brick from the top of the column of the array associated with the barrier,
   * or returns -1 if there is no brick.
   *
   * @param column the column number of the castle
   * @return the row number of the brick or -1 if no brick found
   */
  public int findTopBrick(int column) {
    int row = 0;
    if (column != -1) {
      while (row < NUM_ROWS && !barrierArray[row][column]) {
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
        barrierArray[row + i][column] = false;
        if (column < NUM_COLS - 1) {
          barrierArray[row + i][column + 1] = false;
        }
      }
    }
  }

  /**
   * Calls the previous 3 methods to break the top bricks in the castle.
   *
   * @param xshot the x-coordinate of the shot that hit the castle
   */
  public void breakTopBricks(int xshot) {
    int column = this.findBarrierColumn(xshot);
    this.removeTopBricks(findTopBrick(column), column);
  }

  /**
   * Loads the state of the barrier for MongoDB.
   *
   * @param barrierDoc the document of the barrier state.
   */
  public void loadBarriersState(Document barrierDoc) {
    for (int row = 0; row < NUM_ROWS; row++) {
      for (int col = 0; col < NUM_COLS; col++) {
        barrierArray[row][col] = barrierDoc.getBoolean("barrier_" + row + "_" + col);
      }
    }
  }

  /**
   * Gets the state of the barrier from MongoDB.
   *
   * @return barrierState
   */
  public Document getState() {
    Document barrierState = new Document();
    for (int row = 1; row < NUM_ROWS; row++) {
      for (int col = 1; col < NUM_COLS; col++) {
        barrierState.put("barrier_" + row + "_" + col, barrierArray[row][col]);
      }
    }
    return barrierState;
  }
}




