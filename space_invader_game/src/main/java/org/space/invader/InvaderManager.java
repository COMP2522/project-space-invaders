package org.space.invader;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bson.Document;

/**
 * The InvaderManager class is responsible for managing the invaders in the game.
 * It is responsible for creating the invaders, moving them, and checking for collisions.
 */
public class InvaderManager {
  public static final int ALT_INIT_INVADER = 120;
  public static final int XPOS_INIT_INVADER = 29 + Window.WINDOW_MARGIN;
  public static final int SPACE_ROW_INVADER = 40;
  public static final int SPACE_COLUMN_INVADER = 10;

  //Invader movement unit
  public static final int DX_INVADER = 2;
  public static final int DY_INVADER = 20;
  public static final int INVADER_SPEED = 4;

  // Total number of aliens
  public static final int NUMBER_INVADER = 50;
  public static final int HIGH_VALUE_INVADER = 50;
  public static final int MIDDLE_VALUE_INVADER = 40;
  public static final int LOW_VALUE_INVADER = 20;
  final int NUM_ROWS = 5;
  final int NUM_COLS = 10;
  protected List<Invader> invaders;

  /**
   * A 2D array representing the current position of each invader in the group.
   */
  protected Invader[][] tabInvader = new Invader[NUM_ROWS][NUM_COLS];

  /**
   * A boolean representing whether the invaders are currently moving to the right.
   */
  private boolean goToRight;

  /**
   * An integer representing the current speed of the invaders.
   */
  private int speed;

  /**
   * An array representing the position of a dead invader in the group.
   */
  private int[] tabInvaderDead = {-1, -1};

  /**
   * A boolean representing the current position of the invaders.
   */
  private boolean pos1;

  /**
   * A random number generator used for various events in the game.
   */
  Random accident = new Random();

  /**
   * The total number of invaders in the group.
   */
  private int invaderNum = NUMBER_INVADER;

  /**
   * The count of how many times the sound of the invaders has been played.
   */
  private int countSoundInvader = 0;

  /**
   * Constructor for InvaderManager class.
   * Initializes the invader table and sets the initial position,
   * speed and direction of the invaders.
   */
  public InvaderManager() {
    this.initTableInvaders();
    this.goToRight = true;
    this.pos1 = true;
    this.speed = INVADER_SPEED;
  }

  /**
   * Initializes the invader table.
   */
  public void initTableInvaders() {
    //create the invader table
    for (int column = 0; column < 10; column++) {
      this.tabInvader[0][column] = new Invader(XPOS_INIT_INVADER
              + (Invader.INVADER_SIZE + SPACE_COLUMN_INVADER) * column,
              ALT_INIT_INVADER, "/alien111.png", "/alien111.png");
      for (int row = 1; row < 3; row++) {
        this.tabInvader[row][column] = new Invader(XPOS_INIT_INVADER
                + (Invader.INVADER_SIZE + SPACE_COLUMN_INVADER) * column,
                ALT_INIT_INVADER + SPACE_ROW_INVADER * row, "/alien222.png", "/alien222.png");
      }
      for (int row = 3; row < 5; row++) {
        this.tabInvader[row][column] = new Invader(XPOS_INIT_INVADER
                + (Invader.INVADER_SIZE + SPACE_COLUMN_INVADER) * column,
                ALT_INIT_INVADER + SPACE_ROW_INVADER * row, "/alien333.png", "/alien333.png");
      }
    }
  }

  /**
   * Moves and draws the invaders in the game.
   *
   * @param g Graphics object to draw the invaders.
   */
  public void drawInvader(Graphics g, boolean isPaused) {
    if (Stopwatch.count % (100 - 10 * this.speed) == 0) {
      this.moveInvader(isPaused);
    }
    //Draw the invaders in the invader table
    for (int column = 0; column < 10; column++) {
      for (int row = 0; row < 5; row++) {
        if (this.tabInvader[row][column] != null) {
          this.tabInvader[row][column].chooseImage(pos1);
          g.drawImage(this.tabInvader[row][column].getImg(), this.tabInvader[row][column].getxPos(),
                  this.tabInvader[row][column].getyPos(), null);
        }
      }
    }
  }

  /**
   * Detects if the invaders have touched the left border of the game window.
   *
   * @return true if the invaders touch the left border, false otherwise.
   */
  public boolean touchLeftBorder() {
    //Method of detecting the left border of the window
    boolean response = false;
    for (int column = 0; column < 10; column++) {
      for (int row = 0; row < 5; row++) {
        if (this.tabInvader[row][column] != null) {
          if (this.tabInvader[row][column].getxPos() < Window.WINDOW_MARGIN) {
            response = true;
            break;
          }
        }
      }
    }
    return response;
  }

  /**
   * Determines whether any invader in the group has touched the right border of the window.
   *
   * @return True if any invader has touched the right border, false otherwise.
   */
  public boolean touchRightBorder() {
    //Method of detecting the right border of the window
    boolean response = false;
    for (int column = 0; column < 10; column++) {
      for (int row = 0; row < 5; row++) {
        if (this.tabInvader[row][column] != null) {
          if (this.tabInvader[row][column].getxPos()
                  > Window.WINDOW_SIZE - Invader.INVADER_SIZE - DX_INVADER - Window.WINDOW_MARGIN) {
            response = true;
            break;
          }
        }
      }
    }
    return response;
  }

  /**
   * Changes the direction of movement of the invaders and lowers them by one notch if they have
   * touched the right or left border of the window.
   */
  public void invaderTurnAndLower() {
    //Method which changes the direction of movement of the alien and lowers it by one notch
    if (this.touchRightBorder() == true) {
      for (int column = 0; column < 10; column++) {
        for (int row = 0; row < 5; row++) {
          if (this.tabInvader[row][column] != null) {
            this.tabInvader[row][column].setyPos(this.tabInvader[row][column].getyPos()
                    + DY_INVADER);
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
                      this.tabInvader[row][column].getyPos() + DY_INVADER);
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

  /**
   * Moves the invaders in the group by a certain
   * amount based on their current direction of movement.
   */
  public void moveInvader(boolean isPaused) {
    if (isPaused) {
      return;
    }

    //Method of moving the invaders
    if (this.tabInvaderDead[0] != -1) { // Eliminate the invader if necessary
      eliminateInvaderDead(tabInvaderDead);
      tabInvaderDead[0] = -1;
    }
    if (this.goToRight == true) { // Move to right
      for (int column = 0; column < 10; column++) {
        for (int row = 0; row < 5; row++) {
          if (this.tabInvader[row][column] != null) {
            this.tabInvader[row][column].setxPos(this.tabInvader[row][column].getxPos()
                    + DX_INVADER);
          }
        }
      }
    } else { // Move to left
      for (int column = 0; column < 10; column++) {
        for (int row = 0; row < 5; row++) {
          if (this.tabInvader[row][column] != null) {
            this.tabInvader[row][column].setxPos(this.tabInvader[row][column].getxPos()
                    - DX_INVADER);
          }
        }
      }
    }
    //increase of the count of the sound
    this.countSoundInvader++;
    if (this.pos1 == true) {
      this.pos1 = false;
    } else {
      this.pos1 = true;
    }
    //Changer the direction if the invader hit the window border
    this.invaderTurnAndLower();
  }

  /**
   * Checks whether the given player missile has hit any invader in the group, and updates the score
   * and positions of any affected objects accordingly.
   *
   * @param missilePlayer The missile fired by the player to check for hits.
   */
  public void missilePlayerTouchInvader(MissilePlayer missilePlayer) {
    //Missile contact with invader
    for (int column = 0; column < 10; column++) {
      for (int row = 0; row < 5; row++) {
        if (this.tabInvader[row][column] != null) {
          if (missilePlayer.killInvader(this.tabInvader[row][column]) == true) {
            this.tabInvader[row][column].alive = false; // We kill the invader
            missilePlayer.yPos = -1; // We kill the shot
            // We save the position of the dead alien in the array
            this.tabInvaderDead[0] = row;
            this.tabInvaderDead[1] = column;
            if (row == 0) {
              Window.score = Window.score + HIGH_VALUE_INVADER;
            } else if (row > 0 && row < 3) {
              Window.score = Window.score + MIDDLE_VALUE_INVADER;
            } else {
              Window.score = Window.score + LOW_VALUE_INVADER;
            }
            break;
          }
        }
      }
    }
  }

  /**
   * Removes a dead invader from the group.
   *
   * @param tabInvaderDead an int array representing the coordinates of the dead invader
   */
  private void eliminateInvaderDead(int[] tabInvaderDead) {
    //Method of removing the dead invader from the array
    this.tabInvader[tabInvaderDead[0]][tabInvaderDead[1]] = null;
    this.invaderNum--;
  }

  /**
   * Chooses a random invader to draw.
   *
   * @return an int array representing the coordinates of the chosen invader
   */
  public int[] chooseInvaderToDraw() {
    // Returns the position of an invader drawn at random in arrInvader at the bottom of its
    // column (row, column)
    int[] positionInvader = {-1, -1};
    if (this.invaderNum != 0) { // We check that there are still living invaders
      do {
        int column = accident.nextInt(10); // We randomly draw a column from the
        // array of invaders
        for (int row = 4; row >= 0; row--) {
          if (tabInvader[row][column] != null) {
            positionInvader[0] = this.tabInvader[row][column].getxPos();
            positionInvader[1] = this.tabInvader[row][column].getyPos();
            break;
          }
        }
      } while (positionInvader[0] == -1);
    }
    return positionInvader;
  }

  /**
   * Gets the number of invaders currently in the group.
   *
   * @return an int representing the number of invaders
   */
  public int getInvaderNum() {
    return invaderNum;
  }

  /**
   * Gets the position of the lowest invader in the group.
   *
   * @return an int representing the altitude of the lowest invader's feet
   */
  public int positionInvaderLowest() {
    // Return the altitude of the feet of the lowest invader
    int posBas = 0;
    int posBasFinal = 0;
    for (int column = 1; column < 10; column++) {
      for (int row = 4; row >= 0; row--) {
        if (this.tabInvader[row][column] != null) {
          posBas = this.tabInvader[row][column].yPos + this.tabInvader[row][column].height;
          break;
        }
      }
      if (posBas > posBasFinal) {
        posBasFinal = posBas;
      }
    }
    return posBasFinal;
  }

  /**
   * gets the state of the invader manager.
   *
   * @return state as Document
   */
  public Document getState() {
    if (this.invaders == null) {
      this.invaders = new ArrayList<>();
    }
    List<Document> invaderStates = new ArrayList<>();
    for (Invader invader : invaders) {
      invaderStates.add(invader.getState());
    }
    Document state = new Document();
    state.put("invaders", invaderStates);
    return state;
  }

  /**
   * loads the state of the invader manager.
   *
   * @param invaderManagerState as Document
   */
  public void loadState(Document invaderManagerState) {
    if (invaderManagerState != null) {
      List<Document> invaderStates = (List<Document>) invaderManagerState.get("invaders");
      for (int i = 0; i < invaders.size(); i++) {
        invaders.get(i).loadState(invaderStates.get(i));
      }
    }
  }
}





