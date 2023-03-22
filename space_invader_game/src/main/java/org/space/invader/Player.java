package org.space.invader;

import javax.swing.*;
import java.awt.*;

public class Player extends Sprite {
  static Player player = new Player();
  public Player() {
    super.xPos = Constant.X_POS_INIT_PLAYER;
    super.yPos = Constant.Y_POS_PLAYER;
    super.size = Constant.PLAYER_SIZE;
    super.height = Constant.PLAYER_HEIGHT;
    super.dx = 0;
    super.dy = 0;

    super.strImg1 = "/spaceship2.png";
//    super.strImg2 = "/src/image/spaceship.png";
//    super.strImg3 = "/src/image/spaceship.png";


    super.ico = new ImageIcon(getClass().getResource(strImg1));
//    super.ico = new ImageIcon(getClass().getResource(strImg1).getImage());
    super.img = this.ico.getImage();
    super.alive = true;

  }

  public int playerLimit(){
    if(this.dx <0){
      if(this.xPos>Constant.LIMIT_LEFT_PLAYER){
        this.xPos += this.dx;
      } else if(this.dx>0){
        if(this.xPos + this.dx<Constant.LIMIT_RIGHT_VAISSEAU){
          this.xPos+= this.dx;
        }
      }

    }
    return this.xPos;
  }
  public void drawPlayer(Graphics g) {
    if(this.alive == false) {this.destructionPlayer();}
    g.drawImage(this.img, this.deplacementVaisseau(), this.yPos, null);
  }

  public void destructionPlayer() {
    if(compteur < 300) {
      if(Stopwatch.count % 2 == 0) {super.ico = new ImageIcon(getClass().getResource(super.strImg2));}
      else {super.ico = new ImageIcon(getClass().getResource(super.strImg3));}
      compteur++;
    }else {Main.jeu = false;}
    super.img = this.ico.getImage();
  }
}
