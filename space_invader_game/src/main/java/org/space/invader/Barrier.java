package org.space.invader;

import java.awt.*;

public class Barrier extends Sprite {

  /**** VARIABLES ****/

  private final int NUM_ROWS = Constant.HEIGHT_BARRIER / Constant.DIMENSION_BARRIER;
  private final int NUM_COLS = Constant.SIZE_BARRIER / Constant.DIMENSION_BARRIER;

  // array containing the bricks of the barrier (the cell contains true for a brick and false for empty)
  boolean BarrierArray[][] = new boolean[NUM_ROWS][NUM_COLS];

  /**** CONSTRUCTOR ****/

  public Barrier(int xPos) {
    super.xPos = xPos; // The leftmost point of the barrier
    super.yPos = Constant.Y_POS_BARRIER; // Ordinate of the top of the barrier

    this.initBarrierArray();
  }


  /**** METHODS ****/

// Creation of the initial array associated with the undamaged barrier
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
    // Barrier entrance
    for (int row = 18; row < NUM_ROWS; row++) {
      for (int col = 10; col < NUM_COLS - 10; col++) {
        BarrierArray[row][col] = false;
      }
    }

    // Barrier entrance
    for (int column = 12; column < NUM_COLS - 12; column++) {
      for (int row = 16; row < 18; row++) {
        BarrierArray[row][column] = false;
        BarrierArray[row][NUM_COLS - column - 1] = false;
      }
    }
    for (int column = 14; column < NUM_COLS - 14; column++) {
      for (int row = 14; row < 16; row++) {
        BarrierArray[row][column] = false;
        BarrierArray[row][NUM_COLS - column - 1] = false;
      }
    }
    for (int column = 0; column < 2; column++) {
      for (int row = 4; row < 6; row++) {
        BarrierArray[row][column] = false;
        BarrierArray[row][NUM_COLS - column - 1] = false;
      }
    }
  }

  // Draw the barrier
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

  public int findBarrierColumn(int xMissile) {
    // Find the column in the table associated with the barrier hit by the shot
    int column = -1;
    column = (xMissile - this.xPos) / Constant.DIMENSION_BARRIER;
    return column;
  }

  public int findBrick(int column) {
    // Find the first brick starting from the bottom of the column in the table associated with the barrier or return -1
    int row = NUM_ROWS - 1;
    while (row >= 0 && BarrierArray[row][column] == false) {
      row--;
    }
    return row;
  }

  private void removeBricks(int row, int column) {
    // Remove the first 6 bricks in the column starting from the bottom if they exist
    for (int counter = 0; counter < 6; counter++) {
      if (row - counter >= 0) {
        BarrierArray[row - counter][column] = false;
        if (column < NUM_COLS - 1) {
          BarrierArray[row - counter][column + 1] = false;
        }
      }
    }
  }

  public void breakBricks(int xShot) {
    // Calls the previous 3 methods
    Audio.playSound("/sounds/soundBrickBreak.wav");
    int column = this.findBarrierColumn(xShot);
    this.removeBricks(findBrick(column), column);
  }

  public int findTopBrick(int column) {
    // Finds the first brick from the top of the column of the array associated with the castle,
    // or returns -1 if there is no brick
    int row = 0;
    if (column != -1) {
      while (row < NUM_ROWS && !BarrierArray[row][column]) {
        row++;
      }
    }
    return row;
  }

  private void removeTopBricks(int row, int column) {
    // Removes the top 6 bricks of the column if they exist
    for (int i = 0; i < 6; i++) {
      if (row + i < NUM_ROWS && column != -1) {
        BarrierArray[row + i][column] = false;
        if (column < NUM_COLS - 1) {
          BarrierArray[row + i][column + 1] = false;
        }
      }
    }
  }

  public void breakTopBricks(int xShot) {
    // Calls the previous 3 methods
//    Audio.playSound("/sounds/soundBrickBreak.wav");
    int column = this.findBarrierColumn(xShot);
    this.removeTopBricks(findTopBrick(column), column);
  }

}
