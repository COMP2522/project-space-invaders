package org.space.invader;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BarrierTest {

  /** The number of rows in the barrier */
  private static final int NUM_ROWS = Barrier.HEIGHT_BARRIER / Barrier.DIMENSION_BARRIER;
  /** The number of columns in the barrier */
  private static final int NUM_COLS = Barrier.SIZE_BARRIER / Barrier.DIMENSION_BARRIER;

  /**
   * This method tests the initBarrierArray method in the Barrier class
   */
  @Test
  public void testInitBarrierArray() {
    Barrier barrier = new Barrier(0);
    barrier.initBarrierArray();

    boolean[][] expectedArray = new boolean[NUM_ROWS][NUM_COLS];

    for (int row = 0; row < expectedArray.length; row++) {
      for (int col = 0; col < expectedArray[0].length; col++) {
        expectedArray[row][col] = true;
      }
    }

    // Make a shape for the top of the castle
    for (int col = 0; col < 6; col++) {
      for (int row = 0; row < 2; row++) {
        expectedArray[row][col] = false;
        expectedArray[row][NUM_COLS - col - 1] = false;
      }
    }
    for (int col = 0; col < 4; col++) {
      for (int row = 2; row < 4; row++) {
        expectedArray[row][col] = false;
        expectedArray[row][NUM_COLS - col - 1] = false;
      }
    }
    for (int col = 0; col < 2; col++) {
      for (int row = 4; row < 6; row++) {
        expectedArray[row][col] = false;
        expectedArray[row][NUM_COLS - col - 1] = false;
      }
    }

    assertArrayEquals(expectedArray, barrier.barrierArray);
  }


  /**
   * This method tests the findBarrierColumn method in the Barrier class
   */
  @Test
  public void testFindBarrierColumn() {
    Barrier barrier = new Barrier(0);
    int xMissile = 82;
    int actualColumn = barrier.findBarrierColumn(xMissile);
    assertEquals(xMissile/2, actualColumn);

    xMissile = 41;

    actualColumn = barrier.findBarrierColumn(xMissile);
    assertEquals(xMissile/2, actualColumn);

    xMissile = 122;
    actualColumn = barrier.findBarrierColumn(xMissile);
    assertEquals(xMissile/2, actualColumn);
  }

  /**
   * This method tests the findBrick method in the Barrier class
   */
  @Test
  public void testFindBrick() {
    Barrier barrier = new Barrier(0);
    int actualRow = barrier.findBrick(7);
    assertEquals(24, actualRow);
    barrier.removeBricks(24, 7);
    actualRow = barrier.findBrick(7);
    assertEquals(14, actualRow);

  }

  /**
   * This method tests the removeBricks method in the Barrier class
   */
  @Test
  public void testRemoveBricks() {
    Barrier barrier = new Barrier(0);
    barrier.removeBricks(4, 1);
    assertFalse(barrier.barrierArray[4][1]);
  }
}