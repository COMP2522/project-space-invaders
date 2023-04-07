package org.space.invader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  This class is used to test the Invader class
 *
 * @author Tae Hyung Lee
 * @version 17.0.4
 */
public class InvaderTest extends InvaderManager {

  private InvaderManager invaderManager = new InvaderManager();

  /**
   * This method is used to set up the invader manager before each test
   */
  @BeforeEach
  public void setUp() {
    invaderManager = new InvaderManager();
  }

  /**
   * This method is used to test if the invader manager is created properly
   */
  @Test
  public void testInvaderManager() {
    assertNotNull(invaderManager);
  }


  /**
   * This method is used to test if the invader image is created properly
   */
  @Test
  public void testInvaderImage() {
    Invader invader = new Invader(0, 0, "/alien111.png", "/alien111.png");
    ImageIcon expectedImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/alien111.png")));
    ImageIcon actualImage = invader.ico;
    assertEquals(expectedImage.getImage(), actualImage.getImage());

  }

  /**
   * this is the test if any invader in the group has touched the right border of the window
   */
  @Test
  public void testTouchLeftBorder() {
    InvaderManager invaderManager = new InvaderManager();
    Invader invader = new Invader(-10, 0, "/alien111.png", "/alien111.png");
    invaderManager.tabInvader[0][0] = invader;
    assertTrue(invaderManager.touchLeftBorder());
  }


  /**
   * this is the test if any invader in the group has touched the right border of the window
   */
  @Test
  public void testTouchRightBorder() {
    InvaderManager invaderManager = new InvaderManager();
    Invader invader = new Invader(1000, 0, "/alien111.png", "/resources/image/alien111.png");
    invaderManager.tabInvader[0][0] = invader;
    assertTrue(invaderManager.touchRightBorder());
  }

  /**
   * this is the test if the direction of movement of the invaders changes and they are lowered by one notch
   */
  @Test
  public void testInvaderTurnAndLower() {
    InvaderManager invaderManager = new InvaderManager();
    Invader invader = new Invader(1000, 0, "/alien111.png", "/resources/image/alien111.png");
    invaderManager.tabInvader[0][0] = invader;
    invaderManager.invaderTurnAndLower();
    assertEquals(20, invaderManager.tabInvader[0][0].getyPos());
  }


  /**
   * this is the test if the invaders are moving and shooting properly
   */
  @Test
  public void testMoveMissileInvader() {
    Window.window = new Window();
    Window.window.groupInvaders = new InvaderManager();

    MissileInvader missile = new MissileInvader(Window.window.groupInvaders.chooseInvaderToDraw());
    int originalYPos = missile.getYPos();

    // Call the moveMissileInvader() method once
    while(missile.getYPos()== 305) {
      missile.moveMissileInvader();
    }

    // Verify that the missile has moved down by DY_MISSILE_INVADER pixels
    int expectedYPos = originalYPos + MissileInvader.DY_MISSILE_INVADER;
    assertEquals(expectedYPos, missile.getYPos());
  }

}
