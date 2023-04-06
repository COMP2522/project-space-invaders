package org.space.invader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import org.bson.Document;

/**
 * The Window class extends JPanel and is responsible for painting the game
 * window with its graphical components, including the player, the invaders,
 * missiles, barriers, and game messages. It implements the KeyListener interface
 * to receive keyboard input events from the user.
 */
public class Window extends JPanel {
  public static final int WINDOW_SIZE = 600;
  public static final int WINDOW_HEIGHT = 600;
  public static final int WINDOW_MARGIN = 50;
  final int displayScoreSize = 20;

  /**
   * Font size of the text displayed.
   */
  final int displayTextSize = 65;

  /** Number of the barriers.*/
  final int numberBarrier = 4;

  /** The game window. */
  static Window window;

  /** A boolean that indicates whether the game is still running or not. */
  public static boolean game = true;

  /** The player object. */
  protected Player player; // Declare the player object

  /** The manager for the invaders. */
  public InvaderManager groupInvaders = new InvaderManager();

  /** The player missile object. */
  public static MissilePlayer missilePlayer = new MissilePlayer();

  /** Three missile invader objects.*/
  public MissileInvader missileInvader1;
  public MissileInvader missileInvader2;
  public MissileInvader missileInvader3;

  /** An array of barrier objects. */
  public static Barrier[] barrierArray = new Barrier[4];

  /** The font used to display the score. */
  private final Font displayScore = new Font("Arial", Font.PLAIN, displayScoreSize);

  /** The font used to display text. */
  private final Font displayText = new Font("Arial", Font.PLAIN, displayTextSize);

  /** The score of the game. */
  public static int score;

  public Timer gameLoop;
  boolean gameStarted = false;
  String playerName = "";
  public boolean isGameOverHandled = false;
  protected boolean isPaused = false;
  DatabaseHandler gameStateHandler;
  DatabaseHandler playerDataHandler;
  private GameStateManager gameStateManager;
  private final GameRestart gameRestart = new GameRestart();
  private Timer rankingBoardDelay;
  boolean isRankingBoardDisplayed = false;
  Rectangle buttonBounds = new Rectangle(300, 300, 200, 60);

  /**
   * The constructor of the Window class. Sets up the graphical components,
   * including the player, the invaders, missiles, barriers, and game messages.
   */
  public Window() {
    super();
    setup();
  }

  /**
   * Sets up the initial screen of the game window.
   */
  public void setup() {
    try {
      Audio audio = Audio.getInstance();
      audio.playBgm();
    } catch (Exception e) {
      e.printStackTrace();
    }
    gameStateHandler = new DatabaseHandler("test", "game_state");
    playerDataHandler = new DatabaseHandler("test", "players");
    gameStateManager = new GameStateManager(gameStateHandler, playerDataHandler);
    
    // Add the name input panel
    JPanel namePanel = new JPanel();
    namePanel.setBounds(0, 0, 200, 50);
    namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
    namePanel.setBounds((WINDOW_SIZE - 200) / 2, (WINDOW_HEIGHT - 50) / 2, 200, 50);
    namePanel.setOpaque(false);

    JLabel nameLabel = new JLabel("Enter your name: ");
    JTextField nameField = new JTextField(10);
    namePanel.add(nameLabel);
    namePanel.add(nameField);

    setLayout(null);
    add(namePanel);

    this.setFocusable(true);
    this.requestFocusInWindow();
    this.addKeyListener(new Keyboard(this)); // Pass the Window instance to the Keyboard constructor
    nameField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String playerName = nameField.getText();
        remove(namePanel);
        window.repaint();
        // Initialize the game with the player's name
        initializePlayer(playerName);
        // Set the game to start
        gameStarted = true;
      }

      private void initializePlayer(String playerName) {
        Window.this.playerName = playerName; // Add this line to set the playerName
        player = new Player(playerName);
      }
    });


    // Initialize the game loop and start it
    gameLoop = new Timer(1000 / 60, e -> repaint());
    gameLoop.start();

    // Instantiation of Barrier Array
    for (int column = 0; column < numberBarrier; column++) {
      barrierArray[column] = new Barrier(WINDOW_MARGIN
          + Barrier.X_POS_INIT_BARRIER + column * (Barrier.SIZE_BARRIER + Barrier.GAP_BARRIER));
    }
    // Instantiation of the stopwatch (at the end of the constructor)
    Thread stopwatch = new Thread(new Stopwatch());
    stopwatch.start();
  }

  /**
   * Toggles the pause state of the game.
   */
  public void togglePause() {
    isPaused = !isPaused;
    if (isPaused) {
      System.out.println("Saving game state..."); // Add this line
      gameStateManager.saveGameState(
          playerName, player, groupInvaders, missilePlayer, barrierArray, score);
      gameLoop.stop();
    } else {
      System.out.println("Loading game state...");
      if (gameStateManager.loadGameState(
          playerName, player, groupInvaders, missilePlayer, barrierArray)) {
        gameLoop.start();
      } else {
        System.out.println("No saved game state found.");
      }
    }
  }

  /**
   * Draws the paused screen.
   *
   * @param g Graphics
   */
  public void drawPausedScreen(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WINDOW_SIZE, WINDOW_HEIGHT);

    g.setColor(Color.WHITE);
    g.setFont(displayText);
    g.drawString("PAUSED", 135, 100);

    // Repaint the game objects while paused
    // Draw the player
    if (this.player != null) {
      this.player.drawPlayer(g);
    }

    // Draw the invaders
    this.groupInvaders.drawInvader(g, isPaused);

    // Draw the player's missile
    missilePlayer.drawPlayerMissile(g, isPaused);

    // Draw the barriers
    for (int column = 0; column < numberBarrier; column++) {
      barrierArray[column].drawBarrier(g);
    }

    // Draw the invaders' missiles
    if (this.missileInvader1 != null) {
      this.missileInvader1.drawInvaderMissile(g);
    }
    if (this.missileInvader2 != null) {
      this.missileInvader2.drawInvaderMissile(g);
    }
    if (this.missileInvader3 != null) {
      this.missileInvader3.drawInvaderMissile(g);
    }
  }

  /**
   * Draws the missiles that comes from the invaders in different time.
   *
   * @param g Graphics
   */

  public void shootInvaderMissile1(Graphics g) {
    if (Stopwatch.count % 500 == 0) {
      missileInvader1 = new MissileInvader(groupInvaders.chooseInvaderToDraw());
    }
    if (missileInvader1 != null) {
      missileInvader1.drawInvaderMissile(g);
      missileInvader1.misInvaderDestroyBarrier(barrierArray);
      if (missileInvader1.touchPlayer(player)) {
        player.setAlive(false);
      }
    }
  }

  /**
   * Draws the missiles that comes from the invaders in different time.
   *
   * @param g Graphics
   */
  public void shootInvaderMissile2(Graphics g) {
    if (Stopwatch.count % 750 == 0) {
      missileInvader2 = new MissileInvader(groupInvaders.chooseInvaderToDraw());
    }
    if (missileInvader2 != null) {
      missileInvader2.drawInvaderMissile(g);
      missileInvader2.misInvaderDestroyBarrier(barrierArray);
      if (missileInvader2.touchPlayer(player)) {
        player.setAlive(false);
      }
    }
  }

  /**
   * Draws the missiles that comes from the invaders in different time.
   *
   * @param g Graphics
   */
  public void shootInvaderMissile3(Graphics g) {
    if (Stopwatch.count % 900 == 0) {
      missileInvader3 = new MissileInvader(groupInvaders.chooseInvaderToDraw());
    }
    if (missileInvader3 != null) {
      missileInvader3.drawInvaderMissile(g);
      missileInvader3.misInvaderDestroyBarrier(barrierArray);
      if (missileInvader3.touchPlayer(player)) {
        player.setAlive(false);
      }
    }
  }

  /**
   * Displays the ranking board after the player dies.
   *
   * @param g Graphics
   */
  private void displayRankingBoard(Graphics g) {
    DatabaseHandler dbHandler = new DatabaseHandler("test", "players");
    List<Document> topPlayers = dbHandler.getTopPlayers(10); // Retrieve the top 10 players

    g.setColor(Color.WHITE);
    g.setFont(displayText);
    g.drawString("Ranking Board", 50, 100);

    g.setFont(gameRestart.buttonText);
    g.drawString("Your (" + playerName + ") score is : " + score, 50, 180);

    int rank = 1;
    int ypos = 250;

    for (Document playerDoc : topPlayers) {
      String playerName = playerDoc.getString("playerName");
      int playerScore = playerDoc.getInteger("score");

      g.setFont(displayScore);
      g.drawString(rank + ". " + playerName + ": " + playerScore, 50, ypos);
      ypos += 30;
      rank++;
    }
    gameRestart.drawRestart(g);

    this.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        if (buttonBounds.contains(mouseX, mouseY)) {
          repaint();
          gameRestart.restartGame(g, window);
        }
      }
    });
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
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (gameStarted) {
      // Pause message
      if (isPaused) {
        drawPausedScreen(g);
      }
      // Only update the game state if the game is not paused
      if (!isPaused) {

        //Draw the window frame
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_HEIGHT);

        //Draw the green line on the bottom of the window
        g.setColor(Color.GREEN);
        g.fillRect(30, 530, 535, 5);

        // Display the score
        g.setFont(displayScore);
        g.drawString("SCORE : " + score, 400, 25);

        if (this.player != null) {
          this.player.drawPlayer(g);
        }

        //Draw the invaders
        this.groupInvaders.drawInvader(g, isPaused);

        // Drawing of the spaceship shot
        missilePlayer.drawPlayerMissile(g, isPaused);

        // Draw the barriers
        for (int column = 0; column < numberBarrier; column++) {
          barrierArray[column].drawBarrier(g);
        }

        // Start message
        if (Stopwatch.count < 500) {
          g.setFont(displayText);
          g.drawString("Good luck!", 100, 100);
        }

        // Game over message
        if (!this.player.isAlive()) {
          g.setFont(displayText);
          g.drawString("GAME OVER", 100, 100);
        }

        this.groupInvaders.missilePlayerTouchInvader(missilePlayer);
        // Direction of spaceship's contact with the barrier
        missilePlayer.misPlayerDestroyBarrier(barrierArray);

        shootInvaderMissile1(g);
        shootInvaderMissile2(g);
        shootInvaderMissile3(g);

        if (this.groupInvaders.getInvaderNum() == 0) {
          groupInvaders = new InvaderManager();
        }

        if (this.groupInvaders.positionInvaderLowest() > Player.Y_POS_PLAYER) {
          this.player.destructPlayer();
          this.player.setAlive(false);
        }
        // Display the player's name
        g.setFont(displayScore);
        g.drawString("PLAYER: " + playerName, 30, 25);

        // Game over message
        if (!player.isAlive() && !isGameOverHandled) {
          g.setFont(displayText);
          g.drawString("GAME OVER", 100, 100);
          gameStateManager.savePlayerData(player.getName(), score);
          isGameOverHandled = true;
          // Delay the display of the ranking board
          int delay = 3000; // delay in milliseconds (3000 ms = 3 seconds)
          rankingBoardDelay = new Timer(delay, e -> {
            rankingBoardDelay.stop(); // Stop the timer after it has been triggered once
            isRankingBoardDisplayed = true;
            window.repaint(); // Repaint the window to display the ranking board
          });
          rankingBoardDelay.start();

        }
        // Add this block at the end of paintComponent method
        if (!player.isAlive() && isGameOverHandled && isRankingBoardDisplayed) {
          g.setColor(Color.BLACK);
          g.fillRect(0, 0, WINDOW_SIZE, WINDOW_HEIGHT);
          displayRankingBoard(g);
        }
      }
    }
  }

  /**
   * Creates a JFrame and adds a custom JPanel that handles the game logic and rendering.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    JFrame frame = new JFrame("Space Invaders");
    frame.setSize(WINDOW_SIZE, WINDOW_HEIGHT);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setAlwaysOnTop(true);

    window = new Window();
    window.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_HEIGHT));
    frame.add(window);
    frame.pack();
    frame.setVisible(true);
  }
}





