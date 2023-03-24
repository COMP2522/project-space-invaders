package org.space.invader;

import javax.swing.*;
import java.awt.*;

/**
 *  Player class extends the Sprite class.
 *  It represents a player object in a 2D space invader game.
 */
public class Player extends Sprite {
    /** An integer that counts the number of times a player is destroyed. */
    private int counter = 0;

    //  static Player player = new Player();
    /** A constructor that sets the initial values for the player object.
     * It sets the position, size, height, direction, and image of the player.
     */
    public Player() {
        super.xPos = Constant.X_POS_INIT_PLAYER;
        super.yPos = Constant.Y_POS_PLAYER;
        super.size = Constant.PLAYER_SIZE;
        super.height = Constant.PLAYER_HEIGHT;
        super.dx = 0;
        super.dy = 0;

        super.strImg1 = "/spaceship2.png";
        super.strImg2 = "/playerDes1.png";
        super.strImg3 = "/playerDes2.png";

//    super.ico = new ImageIcon(getClass().getResource(strImg1));
        super.ico = new ImageIcon(getClass().getResource(super.strImg1));
//    super.ico = new ImageIcon(getClass().getResource(strImg1).getImage());
        super.img = this.ico.getImage();
        super.alive = true;
    }

    /**
     * Limits the movement of the player to within the left and right boundaries
     * of the game window.
     *
     * @return xPos x position of the player.
     */
    public int playerLimit() {
        if (this.dx < 0) {
            if (this.xPos > Constant.LIMIT_LEFT_PLAYER) {
                this.xPos = this.xPos + this.dx;
            }
        } else if (this.dx > 0) {
            if (this.xPos + this.dx < Constant.LIMIT_RIGHT_PLAYER) {
                this.xPos = this.xPos + this.dx;
            }
        }
        return this.xPos;
    }

    /**
     * Draws the player object on the screen. If the player is not alive,
     * it calls the destructionPlayer() method to animate the player's destruction.
     *
     * @param g
     */
    public void drawPlayer(Graphics g) {
        if (this.alive == false) {
            this.destructionPlayer();
        }
        g.drawImage(this.img, this.playerLimit(), this.yPos, null);
    }

    /**
     *  Animates the player's destruction. It switches between two images of an exploding
     *  player to create a blinking effect. Once the animation is complete, it sets the
     *  game variable to 'false' to end the game.
     */
    public void destructionPlayer() {
        if (counter < 300) {
            if (Stopwatch.count % 2 == 0) {
                super.ico = new ImageIcon(getClass().getResource(super.strImg2));
            } else {
                super.ico = new ImageIcon(getClass().getResource(super.strImg3));
            }
            counter++;
        } else {
            Window.game = false;
        }
        super.img = this.ico.getImage();
    }
}
