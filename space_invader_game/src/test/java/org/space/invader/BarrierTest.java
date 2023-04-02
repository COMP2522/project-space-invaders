package org.space.invader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class BarrierTest {

  final int INVALID = -1;

  private static final int NUM_ROWS = Constant.HEIGHT_BARRIER / Constant.DIMENSION_BARRIER;
  /** The number of columns in the barrier */
  private static final int NUM_COLS = Constant.SIZE_BARRIER / Constant.DIMENSION_BARRIER;

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

    assertArrayEquals(expectedArray, Barrier.BarrierArray);
  }


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

  @Test
  public void testFindBrick() {
    Barrier barrier = new Barrier(0);
    int actualRow = barrier.findBrick(7);
    assertEquals(24, actualRow);
    barrier.removeBricks(24, 7);
    actualRow = barrier.findBrick(7);
    assertEquals(14, actualRow);

  }

  @Test
  public void testRemoveBricks() {
    Barrier barrier = new Barrier(0);
    barrier.removeBricks(4, 1);
    assertFalse(Barrier.BarrierArray[4][1]);
  }

  @Test
  public void misPlayerDestroyBarrierTest() {
    Window.window = new Window();
    Window.window.player = new Player("test");
//    while(Window.window.missilePlayer.barrierHit==0){
      Keyboard keyboard = new Keyboard(Window.window);
      KeyEvent event = new KeyEvent(Window.window, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
      keyboard.keyPressed(event);
      Window.window.missilePlayer.misPlayerDestroyBarrier(Window.BarrierArray);



    assertEquals(0, Window.window.missilePlayer.barrierHit);
  }

}