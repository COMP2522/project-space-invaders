package org.space.invader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class InvaderTest {

  private InvaderManager invaderManager = new InvaderManager();
  private Graphics graphics;

  @BeforeEach
  public void setUp() {
    invaderManager = new InvaderManager();
  }

  @Test
  public void testInvaderManager() {
    assertNotNull(invaderManager);
  }


    @Test
    public void testInvaderImage() {
      Invader invader = new Invader(0, 0, "/alien111.png", "/alien111.png");
      ImageIcon expectedImage = new ImageIcon(getClass().getResource("/alien111.png"));
      ImageIcon actualImage = invader.ico;
      assertEquals(expectedImage.getImage(), actualImage.getImage());

  }

  @Test
  public void testTouchLeftBorder() {
    // test if the invaders have touched the left border of the game window
    assertTrue(invaderManager.touchLeftBorder());
  }

  @Test
  public void testTouchRightBorder() {
    // test if any invader in the group has touched the right border of the window
    assertTrue(invaderManager.touchRightBorder());
  }

  @Test
  public void testInvaderTurnAndLower() {
    // test if the direction of movement of the invaders changes and they are lowered by one notch
    invaderManager.invaderTurnAndLower();
    boolean goToRight = invaderManager.getGoToRight();
    assertFalse(goToRight);
  }
}
