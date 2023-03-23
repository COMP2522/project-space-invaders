package org.space.invader;

import java.awt.Graphics;
import java.util.Random;


public class InvaderManager {

  private Invader tabInvader[][] = new Invader[5][10];
  private boolean goToRight, pos1;
  private int speed;

  private int[] tabInvaderDead = {-1,-1}; //Replacement of the dead alien in the alien table

  Random accident = new Random();

  private int invaderNum = Constant.NUMBER_INVADER;

  private int countSoundInvader = 0;


  public InvaderManager(){
    this.initTableInvaders();
    this.goToRight = true;
    this.pos1 = true;
    this.speed = Constant.INVADER_SPEED;
  }

private void initTableInvaders(){
    //create the invader table
  for(int column = 0; column < 10; column++){
    this.tabInvader[0][column] = new Invader(Constant.XPOS_INIT_INVADER + (Constant.INVADER_SIZE + Constant.SPACE_COLOMN_INVADER)* column,
            Constant.ALT_INIT_INVADER, "/alien111.png", "/alien222.png");
    for(int row = 1; row <3; row++){
      this.tabInvader[row][column] = new Invader(Constant.XPOS_INIT_INVADER + (Constant.INVADER_SIZE + Constant.SPACE_COLOMN_INVADER)* column,
              Constant.ALT_INIT_INVADER + Constant.SPACE_ROW_INVADER * row, "/alien222.png", "/alien333.png"  );
    }
    for(int row = 3; row <5; row++){
      this.tabInvader[row][column] = new Invader(Constant.XPOS_INIT_INVADER + (Constant.INVADER_SIZE + Constant.SPACE_COLOMN_INVADER)* column,
              Constant.ALT_INIT_INVADER + Constant.SPACE_ROW_INVADER * row, "/alien333.png", "/alien111.png"  );
    }

  }
}
public void drawInvader(Graphics g){
  if(Stopwatch.count % (100 - 10 * this.speed) == 0) {this.moveInvader();}
    //Draw the invaders in the invader table
  for(int column = 0; column < 10; column++){
    for(int row = 0; row < 5; row++){
      if(this.tabInvader[row][column] != null) {
        this.tabInvader[row][column].chooseImage(pos1);
        g.drawImage(this.tabInvader[row][column].getImg(), this.tabInvader[row][column].getxPos(),
                this.tabInvader[row][column].getyPos(), null);
      }
    }
  }
}

  private boolean touchLeftBorder() {
    //Method of detecting the left border of the window
    boolean response = false;
    for (int column = 0; column < 10; column++) {
      for (int row = 0; row < 5; row++) {
        if (this.tabInvader[row][column] != null) {
          if (this.tabInvader[row][column].getxPos() < Constant.WINDOW_MARGIN) {
            response = true;
            break;
          }
          }
        }
      }
      return response;
    }

  private boolean touchRightBorder() {
    //Method of detecting the right border of the window

    boolean response = false;
    for(int column=0; column<10; column++) {
      for(int  row=0;  row<5;  row++) {
        if(this.tabInvader[row][column] != null) {
          if(this.tabInvader[row][column].getxPos() >
                  Constant.WINDOW_SIZE - Constant.INVADER_SIZE - Constant.DX_INVADER - Constant.WINDOW_MARGIN) {
            response = true;
            break;
          }
          }
        }
      }
    return response;
    }


  public void invaderTurnAndLower() {
    //Method which changes the direction of movement of the alien and lowers it by one notch

    if (this.touchRightBorder() == true) {
      for (int column = 0; column < 10; column++) {
        for (int row = 0; row < 5; row++) {
          if (this.tabInvader[row][column] != null) {
            this.tabInvader[row][column].setyPos(this.tabInvader[row][column].getyPos() + Constant.DY_INVADER);
          }
        }
      }
      this.goToRight = false;
      if (this.speed < 9) {
        this.speed++;
      }
    } else {
      if (this.touchLeftBorder() == true) {
        for (int column = 0; column < 10; column++) {
          for (int row = 0; row < 5; row++) {
            if (this.tabInvader[row][column] != null) {
              this.tabInvader[row][column].setyPos(
                      this.tabInvader[row][column].getyPos() + Constant.DY_INVADER);
            }
            }
          }
          this.goToRight = true;
          if (this.speed < 9) {
            this.speed++;
          }
        }
      }
    }

  public void moveInvader() {
    //Method of moving the invaders

    if(this.tabInvaderDead[0] != -1) { // Eliminate the invader if necessary
      eliminateInvaderDead(tabInvaderDead);
      tabInvaderDead[0] = -1;
    }
    if(this.goToRight == true) { // Move to right
      for(int column=0; column<10; column++) {
        for(int row=0; row<5; row++) {
//          if(this.tabInvader[row][column] != null) {
            this.tabInvader[row][column].setxPos(this.tabInvader[row][column].getxPos() + Constant.DX_INVADER);

        }
      }
    }else{ // Move to left
      for(int column=0; column<10; column++) {
        for(int  row=0;  row<5;  row++) {
          if(this.tabInvader[row][column] != null) {
            this.tabInvader[row][column].setxPos(this.tabInvader[row][column].getxPos() - Constant.DX_INVADER);
          }
        }
      }
    }
    //Play the sound of invader
    this.playSoundInvader();
    //increase of the count of the sound
    this.countSoundInvader++;
    // Changement de l'image de l'alien
    if(this.pos1 == true) {this.pos1 = false;}
    else {this.pos1 = true;}
    //Changer the direction if the invader hit the window border

    this.invaderTurnAndLower();
  }

  public void misslePlayerTouchInvader(MissilePlayer missilePlayer) {
    //Missile contact with invader
    //Taehyuk part here
    for(int column=0; column<10; column++) {
      for(int row=0; row<5; row++) {
        if(this.tabInvader[row][column] != null) {
          if(missilePlayer.killInvader(this.tabInvader[row][column]) == true) {
            this.tabInvader[row][column].alive = false; // We kill the invader
            missilePlayer.yPos = -1; // We kill the shot
            // We save the position of the dead alien in the array
            this.tabInvaderDead[0] = row;
            this.tabInvaderDead[1] = column;
            if(row == 0) {
              //Don't know what to modify
              Window.score = Window.score + Constant.HIGH_VALUE_INVADER;}
            else if(row>0 && row<3) {
              Window.score = Window.score + Constant.MIDDLE_VALUE_INVADER;}
            else {
              Window.score = Window.score + Constant.LOW_VALUE_INVADER;}
            break;
          }
        }
      }
    }
  }

  private void eliminateInvaderDead(int[] tabInvaderDead) {
    //Method of removing the dead invader from the array
    this.tabInvader[tabInvaderDead[0]][tabInvaderDead[1]] = null;
    this.invaderNum--;
  }

  public int[] chooseInvaderToDraw() {
    // Returns the position of an invader drawn at random in arrInvader at the bottom of its
    // column (row, column)
    int positionInvader[] = {-1,-1};
    if(this.invaderNum != 0) { // We check that there are still living invaders
      do {int column = accident.nextInt(10); // We randomly draw a column from the
        // array of invaders
        for(int row=4;row>=0;row--) { // On cherche le 1er alien vivant
          // en partant du bas
          if(tabInvader[row][column]!= null) {
            positionInvader[0] = this.tabInvader[row][column].getxPos();
            positionInvader[1] = this.tabInvader[row][column].getyPos();
            break;
          }
        }
      } while(positionInvader[0] == -1);
    }
    return positionInvader;
  }

  private void playSoundInvader() { // Method that plays the sound of the alien (4 possible sounds)
    int count = this.countSoundInvader % 4;
    if(count==0) {Audio.playSound("/sons/sonAlien1.wav");}
    else if(count==1) {Audio.playSound("/sons/sonAlien2.wav");}
    else if(count==2) {Audio.playSound("/sons/sonAlien3.wav");}
    else {Audio.playSound("/sons/sonAlien4.wav");}
  }

  public int getInvaderNum() {return invaderNum;}

  public int positionInvaderLowest() {
    // Return the altitude of the feet of the lowest invader
    int posBas = 0, posBasFinal = 0;
    for(int column=1; column<10;column++) {
      for(int row=4; row>=0;row--) {
        if(this.tabInvader[row][column] != null) {
          posBas = this.tabInvader[row][column].yPos + this.tabInvader[row][column].height;
          break;
        }
      }
      if(posBas > posBasFinal) {posBasFinal = posBas;}
    }
    return posBasFinal;
  }
}





