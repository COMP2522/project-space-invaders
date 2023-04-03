package org.space.invader;
import org.bson.Document;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import java.time.LocalDateTime;

import java.util.List;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;


/**
 * The Window class extends JPanel and is responsible for painting the game
 * window with its graphical components, including the player, the invaders,
 * missiles, barriers, and game messages. It implements the KeyListener interface
 * to receive keyboard input events from the user.
 */
public class Window extends JPanel {
  final int DisplayScore_size = 20;
  final int Displaytext_size = 65;
  final int NUMBER_COLUMN = 4;

  final int button_text_size = 30;

  /**
   * The game window.
   */
  static Window window;
  /**
   * A boolean that indicates whether the game is still running or not.
   */
  public static boolean game = true;
  /**
   * The player object.
   */
  protected Player player; // Declare the player object
  /**
   * The manager for the invaders.
   */
  public InvaderManager groupInvaders = new InvaderManager();
  /**
   * The player missile object.
   */
  public static MissilePlayer missilePlayer = new MissilePlayer();
  /**
   * Three missile invader objects.
   */
  public MissileInvader missileInvader1, missileInvader2, missileInvader3;
  /**
   * An array of barrier objects.
   */
  public static Barrier[] BarrierArray = new Barrier[4];
  /** The font used to display the score. */
  private Font DisplayScore = new Font("Arial", Font.PLAIN, DisplayScore_size);
  /** The font used to display text. */
  private Font Displaytext = new Font("Arial", Font.PLAIN, Displaytext_size);
  private Font ButtonText = new Font("Arial", Font.PLAIN, button_text_size);
  /** The score of the game. */
  public static int score;

  private Timer gameLoop;
  private boolean gameStarted = false;

  private String playerName = "";
  private boolean isGameOverHandled = false;

  protected boolean isPaused = false;

  private DatabaseHandler gameStateDbHandler;
  private DatabaseHandler dbHandler;
  private Timer rankingBoardDelay;
  boolean isRankingBoardDisplayed = false;

  Rectangle buttonBounds = new Rectangle(300, 300, 200, 60);


  /**
   * The constructor of the Window class. Sets up the graphical components,
   * including the player, the invaders, missiles, barriers, and game messages.
   */
  public Window() {
    super();
    Audio.playLoop("/background_music_cut.wav");
    gameStateDbHandler = new DatabaseHandler("test", "game_state");
    dbHandler = new DatabaseHandler("test", "players");


    // Add the name input panel
    JPanel namePanel = new JPanel();
    namePanel.setBounds(0, 0, 200, 50);
    namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
    namePanel.setBounds((Constant.WINDOW_SIZE - 200) / 2, (Constant.WINDOW_HEIGHT - 50) / 2, 200, 50);
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
    gameLoop = new Timer(1000 / 60, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        repaint();
      }
    });
    gameLoop.start();

    // Instantiation of Barrier Array
    for (int column = 0; column < NUMBER_COLUMN; column++) {
      this.BarrierArray[column] = new Barrier(Constant.WINDOW_MARGIN +
              Constant.X_POS_INIT_BARRIER + column * (Constant.SIZE_BARRIER + Constant.GAP_BARRIER));
    }

    // Instantiation of the stopwatch (at the end of the constructor)
    Thread stopwatch = new Thread(new Stopwatch());
    stopwatch.start();
  }

  private void savePlayerData() {

    Document playerDoc = DatabaseHandler.createPlayerDocument(player.getName(), window.score);
    dbHandler.insertDocument(playerDoc);

    System.out.println("Player score saved at " + LocalDateTime.now());
  }

  private void saveGameState() {
//    DatabaseHandler dbHandler = new DatabaseHandler("test", "game_state");

    // Save the current state of the game
    Document gameStateDoc = new Document();
    gameStateDoc.put("playerName", playerName);
    gameStateDoc.put("player", player.getState());
    gameStateDoc.put("groupInvaders", groupInvaders.getState());
    gameStateDoc.put("missilePlayer", missilePlayer.getState());
    List<Document> barrierDocs = new ArrayList<>();
    for (Barrier barrier : BarrierArray) {
      barrierDocs.add(barrier.getState());
    }
    gameStateDoc.put("barriers", barrierDocs);

    gameStateDoc.put("score", window.score);

    // Clear the previous state
    gameStateDbHandler.deleteAllDocuments();

    // Insert the new state
    gameStateDbHandler.insertDocument(gameStateDoc);

  }

  private void loadGameState() {
//    DatabaseHandler dbHandler = new DatabaseHandler("test", "game_state");

    Document gameStateDoc = gameStateDbHandler.getLatest();

    if (gameStateDoc != null) {
      playerName = gameStateDoc.getString("playerName");
      player.loadState((Document) gameStateDoc.get("player"));
      groupInvaders.loadState((Document) gameStateDoc.get("groupInvaders"));
      missilePlayer.loadState((Document) gameStateDoc.get("missilePlayer"));
      List<Document> barrierDocs = (List<Document>) gameStateDoc.get("barriers");
      for (int i = 0; i < BarrierArray.length; i++) {
        BarrierArray[i].loadBarriersState(barrierDocs.get(i));
      }

      score = gameStateDoc.getInteger("score");
    } else {
      System.out.println("No saved game state found.");
    }
  }

  public void togglePause() {
    isPaused = !isPaused;
    if (isPaused) {
      System.out.println("Saving game state..."); // Add this line
      saveGameState();
      gameLoop.stop();
    } else {
      System.out.println("Loading game state..."); // Add this line
      loadGameState();
      gameLoop.start();
    }
  }
  public void drawPausedScreen(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT);

    g.setColor(Color.WHITE);
    g.setFont(Displaytext);
    g.drawString("PAUSED", 135, 100);

    // Repaint the game objects while paused
    // Draw the player
    if (this.player != null) {
      this.player.drawPlayer(g);
    }

    // Draw the invaders
    this.groupInvaders.drawInvader(g, isPaused);

    // Draw the player's missile
    this.missilePlayer.drawPlayerMissile(g, isPaused);

    // Draw the barriers
    for (int column = 0; column < NUMBER_COLUMN; column++) {
      this.BarrierArray[column].drawBarrier(g);
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

  private void drawPlayAgain(Graphics g) {
    int yPos = 300;
    // Add play again button
    g.setFont(ButtonText);
    g.setColor(Color.BLUE);
    g.fillRect(300, yPos, 200, 60); // create button box
    g.setColor(Color.WHITE);
    g.drawString("PLAY AGAIN", 310, yPos + 40); // add text to button
  }


  public void restartGame(Graphics g) {
    // Stop the game loop
    gameLoop.stop();
    game = false;
    Stopwatch.count = 0;

    // Reset the score to 0
    score = 0;

    // Reset the player and invader objects
    player = new Player(playerName);
    groupInvaders = new InvaderManager();

    // Reset the player and invader missile objects
    missilePlayer = new MissilePlayer();
    missileInvader1 = null;
    missileInvader2 = null;
    missileInvader3 = null;

    // Reset the barrier objects
    for (int column = 0; column < NUMBER_COLUMN; column++) {
      BarrierArray[column] = new Barrier(Constant.WINDOW_MARGIN +
          Constant.X_POS_INIT_BARRIER + column * (Constant.SIZE_BARRIER + Constant.GAP_BARRIER));
    }

    // Reset the game over flag and handle flag
    game = true;
    isGameOverHandled = false;

    // Start the game loop
    gameLoop = new Timer(1000 / 60, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        repaint();
      }
    });
    gameLoop.start();
    Thread stopwatch = new Thread(new Stopwatch());
    stopwatch.start();

    paintComponent(g);
  }

  private void displayRankingBoard(Graphics g) {
    DatabaseHandler dbHandler = new DatabaseHandler("test", "players");
    List<Document> topPlayers = dbHandler.getTopPlayers(10); // Retrieve the top 10 players

    g.setColor(Color.WHITE);
    g.setFont(Displaytext);
    g.drawString("Ranking Board", 50, 100);

    g.setFont(ButtonText);
    g.drawString("Your (" + playerName + ") score is : " + score, 50, 180);

    int rank = 1;
    int yPos = 250;

    for (Document playerDoc : topPlayers) {
      String playerName = playerDoc.getString("playerName");
      int playerScore = playerDoc.getInteger("score");

      g.setFont(DisplayScore);
      g.drawString(rank + ". " + playerName + ": " + playerScore, 50, yPos);
      yPos += 30;
      rank++;
    }
    drawPlayAgain(g);

    this.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        if (buttonBounds.contains(mouseX, mouseY)) {
          repaint();
          restartGame(g);
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
    Graphics g2 = (Graphics2D) g;
    if (gameStarted) {
// Pause message
      if (isPaused) {
        drawPausedScreen(g);
      }
      // Only update the game state if the game is not paused
      if (!isPaused) {

        //Draw the window frame
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT);

        //Draw the green line on the bottom of the window
        g2.setColor(Color.GREEN);
        g2.fillRect(30, 530, 535, 5);

        // Display the score
        g.setFont(DisplayScore);
        g.drawString("SCORE : " + score, 400, 25);

        if (this.player != null) {
          this.player.drawPlayer(g2);
        }


        //Draw the invaders
        this.groupInvaders.drawInvader(g2, isPaused);

        // Drawing of the spaceship shot
        this.missilePlayer.drawPlayerMissile(g2, isPaused);

        // Draw the barriers
        for (int column = 0; column < NUMBER_COLUMN; column++) {
          this.BarrierArray[column].drawBarrier(g2);
        }

        // Start message
        if (Stopwatch.count < 500) {
          g.setFont(Displaytext);
          g.drawString("Good luck!", 100, 100);
        }

        // Game over message
        if (!this.player.isAlive()) {
          g.setFont(Displaytext);
          g.drawString("GAME OVER", 100, 100);
        }

        this.groupInvaders.missilePlayerTouchInvader(this.missilePlayer);
        // Direction of spaceship's contact with the castle
        this.missilePlayer.misPlayerDestroyBarrier(BarrierArray);

        // Drawing of the aliens' Missile
        if (Stopwatch.count % 500 == 0) {
          missileInvader1 = new MissileInvader(this.groupInvaders.chooseInvaderToDraw());
        }
        if (this.missileInvader1 != null) {
          this.missileInvader1.drawInvaderMissile(g2);
          this.missileInvader1.misInvaderDestroyBarrier(BarrierArray);
          if (this.missileInvader1.touchPlayer(player) == true) {
            this.player.setAlive(false);
          }
        }
        if (Stopwatch.count % 750 == 0) {
          missileInvader2 = new MissileInvader(this.groupInvaders.chooseInvaderToDraw());
        }
        if (this.missileInvader2 != null) {
          this.missileInvader2.drawInvaderMissile(g2);
          this.missileInvader2.misInvaderDestroyBarrier(BarrierArray);
          if (this.missileInvader2.touchPlayer(player) == true) {
            this.player.setAlive(false);
          }
        }
        if (Stopwatch.count % 900 == 0) {
          missileInvader3 = new MissileInvader(this.groupInvaders.chooseInvaderToDraw());
        }
        if (this.missileInvader3 != null) {
          this.missileInvader3.drawInvaderMissile(g2);
          this.missileInvader3.misInvaderDestroyBarrier(BarrierArray);
          if (this.missileInvader3.touchPlayer(player) == true) {
            this.player.setAlive(false);
          }
        }

        if (this.groupInvaders.getInvaderNum() == 0) {
          groupInvaders = new InvaderManager();
        }

        if (this.groupInvaders.positionInvaderLowest() > Constant.Y_POS_PLAYER) {
          this.player.destructPlayer();
          this.player.setAlive(false);
        }
// Display the player's name
        g.setFont(DisplayScore);
        g.drawString("PLAYER: " + playerName, 30, 25);

        // Game over message
        if (!player.isAlive() && !isGameOverHandled) {
          g.setFont(Displaytext);
          g.drawString("GAME OVER", 100, 100);
          savePlayerData();
          isGameOverHandled = true;
          // Delay the display of the ranking board
          int delay = 3000; // delay in milliseconds (3000 ms = 3 seconds)
          rankingBoardDelay = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              rankingBoardDelay.stop(); // Stop the timer after it has been triggered once
              isRankingBoardDisplayed = true;
              window.repaint(); // Repaint the window to display the ranking board
            }
          });
          rankingBoardDelay.start();

        }
// Add this block at the end of paintComponent method
        if (!player.isAlive() && isGameOverHandled && isRankingBoardDisplayed) {
          g.setColor(Color.BLACK);
          g.fillRect(0, 0, Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT);

//          isGameOverHandled = true;
          displayRankingBoard(g);
        }


      }
    }
  }



  /**
   *  Creates a JFrame and adds a custom JPanel that handles the game logic and rendering.
   *
   *  @param args The command line arguments.
   */
  public static void main(String[] args)  {

    JFrame frame = new JFrame("Space Invaders");
    frame.setSize(Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setAlwaysOnTop(true);

    window = new Window();
    window.setPreferredSize(new Dimension(Constant.WINDOW_SIZE, Constant.WINDOW_HEIGHT));
    frame.add(window);
    frame.pack();
    frame.setVisible(true);

  }
}