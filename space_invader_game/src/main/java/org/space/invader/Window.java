package org.space.invader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
/**
 * The Window class extends JPanel and is responsible for painting the game
 * window with its graphical components, including the player, the invaders,
 * missiles, barriers, and game messages. It implements the KeyListener interface
 * to receive keyboard input events from the user.
 */
public class Window extends JPanel {

  /** The game window. */
  static Window window;
  /** A boolean that indicates whether the game is still running or not. */
  public static boolean game = true;
  /** The player object. */
  static Player player = new Player();
  /** The manager for the invaders. */
  public InvaderManager groupInvaders = new InvaderManager();
  /** The player missile object. */
  public static MissilePlayer missilePlayer = new MissilePlayer();
  /** Three missile invader objects. */
  public MissileInvader missileInvader1, missileInvader2, missileInvader3;
  /** An array of barrier objects. */
  public static Barrier[] BarrierArray = new Barrier[4];
  /** The font used to display the score. */
  private Font DisplayScore = new Font("Arial", Font.PLAIN, 20);
  /** The font used to display text. */
  private Font Displaytext = new Font("Arial", Font.PLAIN, 80);
  /** The score of the game. */
  public static int score = 0;

  /**
   *  The constructor of the Window class. Sets up the graphical components,
   *  including the player, the invaders, missiles, barriers, and game messages.
   */
  public Window(){
    super();

    this.setFocusable(true);
    this.requestFocusInWindow();
    this.addKeyListener(new Keyboard());

//    Thread thread = new Thread(new Stopwatch());
//    thread.start();

    // Instantiation of Barrier Array
    for (int column = 0; column < 4; column++) {
      this.BarrierArray[column] = new Barrier(Constant.WINDOW_MARGIN +
          Constant.X_POS_INIT_BARRIER + column * (Constant.SIZE_BARRIER + Constant.GAP_BARRIER));
    }

    // Instantiation of the stopwatch (at the end of the constructor)
    Thread stopwatch = new Thread(new Stopwatch());
    stopwatch.start();
  }

  /**
   *  Paints all the components of the game. It draws the window frame, green line at the bottom,
   *  score display, player, invaders, player missiles, barriers and game messages. It also checks
   *  for collisions between the missiles and the barriers and the player and the missiles. Finally,
   *  it checks for game over and end of game conditions, and restarts the game if all invaders are
   *  destroyed.
   *
   *  @param g the Graphics object used to draw the components of the game.
   */
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics g2 = (Graphics2D)g;

    //Draw the window frame
    g2.setColor(Color.BLACK);
    g2.fillRect(0,0, Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT);

    //Draw the green line on the bottom of the window
    g2.setColor(Color.GREEN);
    g2.fillRect(30, 530, 535, 5);

    // Display the screen
    g.setFont(DisplayScore);
    g.drawString("SCORE : " + score, 400, 25);

    // Draw the player
    this.player.drawPlayer(g2);

    //Draw the invaders
    this.groupInvaders.drawInvader(g2);

    // Drawing of the spaceship shot
    this.missilePlayer.drawPlayerMissile(g2);

//    // draw player
//    g2.drawImage(this.player.getImg(),this.player.getxPos(),this.player.getyPos(),null);

    // Draw the barriers
    for (int column = 0; column < 4; column++) {
      this.BarrierArray[column].drawBarrier(g2);
    }

    // Start message
    if(Stopwatch.count < 500) {
      g.setFont(Displaytext);
      g.drawString("Good luck!", 95, 100);
    }

    // Game over message
    if(this.player.isAlive() == false) {
      g.setFont(Displaytext);
      g.drawString("GAME OVER", 50, 100);
    }

    this.groupInvaders.misslePlayerTouchInvader(this.missilePlayer);
    // Direction of spaceship's contact with the castle
    this.missilePlayer.misPlayerDestroyBarrier(BarrierArray);

    // Drawing of the aliens' Missile
    if(Stopwatch.count % 500 == 0) {
      missileInvader1 = new MissileInvader(this.groupInvaders.chooseInvaderToDraw());}
    if(this.missileInvader1 != null) {
      this.missileInvader1.drawInvaderMissile(g2);
      this.missileInvader1.misInvaderDestroyBarrier(BarrierArray);
      if(this.missileInvader1.touchPlayer(player) == true) {this.player.setAlive(false);}
    }
    if(Stopwatch.count % 750 == 0) {
      missileInvader2 = new MissileInvader(this.groupInvaders.chooseInvaderToDraw());}
    if(this.missileInvader2 != null) {
      this.missileInvader2.drawInvaderMissile(g2);
      this.missileInvader2.misInvaderDestroyBarrier(BarrierArray);
      if(this.missileInvader2.touchPlayer(player) == true) {this.player.setAlive(false);}
    }
    if(Stopwatch.count % 900 == 0) {
      missileInvader3 = new MissileInvader(this.groupInvaders.chooseInvaderToDraw());}
    if(this.missileInvader3 != null) {
      this.missileInvader3.drawInvaderMissile(g2);
      this.missileInvader3.misInvaderDestroyBarrier(BarrierArray);
      if(this.missileInvader3.touchPlayer(player) == true) {this.player.setAlive(false);}
    }

    if(this.groupInvaders.getInvaderNum() == 0) {groupInvaders = new InvaderManager();}

    if(this.groupInvaders.positionInvaderLowest() > Constant.Y_POS_PLAYER) {this.player.destructionPlayer();}
  }

  /**
   *  Creates a JFrame and adds a custom JPanel that handles the game logic and rendering.
   *
   *  @param args The command line arguments.
   */
//  public static void main(String[] args) {
//    JFrame frame = new JFrame("Space Invaders");
//    frame.setSize(Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT);
//    frame.setResizable(false);
//    frame.setLocationRelativeTo(null);
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.setAlwaysOnTop(true);
//
//
//    window = new Window();
//    window.setPreferredSize(new Dimension(Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT));
//    frame.add(window);
//    frame.pack();
//    frame.setVisible(true);
//  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Space Invaders");
    frame.setSize(Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setAlwaysOnTop(true);

    JPanel mainMenuPanel = new JPanel();
    mainMenuPanel.setPreferredSize(new Dimension(Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT));
    JButton playButton = new JButton("Play");
    playButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        window.setPreferredSize(new Dimension(Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT));
        frame.add(window);
        frame.pack();
        frame.setVisible(true);
        playButton.setFocusable(false);
      }
    });

    mainMenuPanel.add(playButton);
    window = new Window();
    window.setPreferredSize(new Dimension(Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT));
    frame.add(mainMenuPanel);
    frame.pack();
    frame.setVisible(true);
  }

}
