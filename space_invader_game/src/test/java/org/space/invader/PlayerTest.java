package org.space.invader;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

  @Test
  public void testRightKeyPressed() {
    Window window = new Window();
    window.player = new Player("test");
    Keyboard keyboard = new Keyboard(window);
    KeyEvent event = new KeyEvent(window, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
    keyboard.keyPressed(event);
    assertEquals(Constant.DX_PLAYER, window.player.getXspeed());
  }

    @Test
    public void testLeftKeyPressed() {
        Window window = new Window();
        window.player = new Player("test");
        Keyboard keyboard = new Keyboard(window);
        KeyEvent event = new KeyEvent(window, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        keyboard.keyPressed(event);
        assertEquals(-Constant.DX_PLAYER, window.player.getXspeed());
    }

    @Test
    public void testSpaceKeyPressed() {
        Window window = new Window();
        window.player = new Player("test");
        Keyboard keyboard = new Keyboard(window);
        KeyEvent event = new KeyEvent(window, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
        keyboard.keyPressed(event);
      assertTrue(Window.missilePlayer.isPlayerShoot());
    }

  @Test
  public void testDrawPlayer() {
    Window window = new Window();
    window.player = new Player("test");
    BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = bufferedImage.createGraphics();
    window.player.drawPlayer(g2d);
    // Add your assertion here
    assertNotNull(window.player.getImg());
  }

  @Test
  public void testPlayerLimit() {
    Player player = new Player("test");
    // Test the playerLimit() method when dx < 0
    player.setDx(-5);
    player.setXPos(200);
    assertEquals(195, player.playerLimit());
    // Test the playerLimit() method when dx > 0
    player.setDx(5);
    player.setXPos(600);
    assertEquals(600, player.playerLimit());
  }
}
