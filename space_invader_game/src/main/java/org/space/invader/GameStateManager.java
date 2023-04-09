package org.space.invader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * GameStateManager class that handles the game state.
 */
public class GameStateManager {
  private DatabaseHandler gameStateHandler;
  private DatabaseHandler playerDataHandler;

  /**
   * Constructor for GameStateManager.
   *
   * @param gameStateHandler as DatabaseHandler object
   * @param playerDataHandler as DatabaseHandler object
   */
  public GameStateManager(DatabaseHandler gameStateHandler, DatabaseHandler playerDataHandler) {
    this.gameStateHandler = gameStateHandler;
    this.playerDataHandler = playerDataHandler;
  }

  /**
   * Get the game state from the database.
   *
   * @param playerName as String
   * @param score as integer
   */
  public void savePlayerData(String playerName, int score) {
    Document playerDoc = DatabaseHandler.createPlayerDocument(playerName, score);
    playerDataHandler.insertDocument(playerDoc);
    System.out.println("Player score saved at " + LocalDateTime.now());
  }

  /**
   * Save the game state from the database.
   *
   * @param playerName as String
   * @param player as Player object
   * @param groupInvaders as InvaderManager object
   * @param missilePlayer as MissilePlayer object
   * @param BarrierArray as Barrier array
   * @param score as integer
   */
  public void saveGameState(String playerName, Player player, InvaderManager groupInvaders,
                            MissilePlayer missilePlayer, Barrier[] BarrierArray, int score) {
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
    gameStateDoc.put("score", score);

    gameStateHandler.deleteAllDocuments();
    gameStateHandler.insertDocument(gameStateDoc);
  }

  /**
   * Load the game state from the database.
   *
   * @param playerName as String
   * @param player as Player object
   * @param groupInvaders as InvaderManager object
   * @param missilePlayer as MissilePlayer object
   * @param BarrierArray as Barrier array
   * @return boolean
   */

  public boolean loadGameState(String playerName, Player player, InvaderManager groupInvaders,
                               MissilePlayer missilePlayer, Barrier[] BarrierArray) {
    Document gameStateDoc = gameStateHandler.getLatest();

    if (gameStateDoc != null) {
      playerName = gameStateDoc.getString("playerName");
      player.loadState((Document) gameStateDoc.get("player"));
      groupInvaders.loadState((Document) gameStateDoc.get("groupInvaders"));
      missilePlayer.loadState((Document) gameStateDoc.get("missilePlayer"));
      List<Document> barrierDocs = (List<Document>) gameStateDoc.get("barriers");
      for (int i = 0; i < BarrierArray.length; i++) {
        BarrierArray[i].loadBarriersState(barrierDocs.get(i));
      }
      return true;
    } else {
      return false;
    }
  }
}