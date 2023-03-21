package org.space.invader;

import java.awt.Graphics;


public class InvaderManager {

  private Invader tabInvader[][] = new Invader[5][10];

  public InvaderManager(){
    this.initTableInvaders();
  }

private void initTableInvaders(){
    //create the invader table
  for(int column = 0; column < 10; column++){
    this.tabInvader[0][column] = new Invader(Constant.XPOS_INIT_INVADER + (Constant.INVADER_SIZE + Constant.SPACE_COLOMN_INVADER)* column,
            Constant.ALT_INIT_INVADER, "/image/alien11.png", "/image/alien11.png");
    for(int row = 1; row <3; row++){
      this.tabInvader[row][column] = new Invader(Constant.XPOS_INIT_INVADER + (Constant.INVADER_SIZE + Constant.SPACE_COLOMN_INVADER)* column,
              Constant.ALT_INIT_INVADER + Constant.SPACE_ROW_INVADER * row, "/image/alien22.png", "/image/alien22.png"  );
    }
    for(int row = 3; row <5; row++){
      this.tabInvader[row][column] = new Invader(Constant.XPOS_INIT_INVADER + (Constant.INVADER_SIZE + Constant.SPACE_COLOMN_INVADER)* column,
              Constant.ALT_INIT_INVADER + Constant.SPACE_ROW_INVADER * row, "/image/alien33.png", "/image/alien33.png"  );
    }

  }
}
public void drawInvader(Graphics g){
    //Draw the invaders in the invader table
  for(int column = 0; column < 10; column++){
    for(int row = 0; row < 5; row++){
      g.drawImage(this.tabInvader[row][column].getImg(), this.tabInvader[row][column].getxPos(),
              this.tabInvader[row][column].getyPos(), null );
    }
  }
}




}
