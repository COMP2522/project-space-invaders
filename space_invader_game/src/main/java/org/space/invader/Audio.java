package org.space.invader;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Singleton Audio class that manages the sound.
 */
public class Audio {

  /**
   * Singleton instance of the audio class.
   */
  private static Audio onlyAudio;

  /**
   * The sound clip for when the invader dies.
   */
  private final Clip dead_invader;

  /**
   * The sound clip for when the player dies.
   */
  private final Clip dead_player;

  /**
   * The sound clip for when the barrier got attacked.
   */
  private final Clip barrier;

  /**
   * The sound clip for when the missile is shot.
   */
  private final Clip missile_player;

  /**
   * The background music.
   */
  private final Clip bgm;


  /**
   * Constructs a new Audio object from the specified sound file.
   * @throws FileNotFoundException if the specified sound file cannot be found.
   * @throws LineUnavailableException if a Line cannot be opened due to resource restrictions.
   */
  private Audio() throws FileNotFoundException {
    Path invaderP = Paths.get("resources","audio", "InvaderDead.wav");
    dead_invader = loadAudio(invaderP);

    Path playerP = Paths.get("resources","audio", "player_dead.wav");
    dead_player  = loadAudio(playerP);

    Path barrierP = Paths.get("resources","audio", "attacked_barrier.wav");
    barrier = loadAudio(barrierP);

    Path missilePlayerP = Paths.get("resources","audio", "missile_player.wav");
    missile_player = loadAudio(missilePlayerP);

    Path bgmP = Paths.get("resources","audio","bgm.wav");
    bgm = loadAudio(bgmP);
  }

  /**
   * Returns the singleton instance of the Audio class.
   * If the instance has not been created yet, it will create a new one.
   * @return the singleton instance of the Audio class.
   * @throws FileNotFoundException if the specified sound file cannot be found.
   * @throws LineUnavailableException if a Line cannot be opened due to resource restrictions.
   */
  public static Audio getInstance() throws FileNotFoundException, LineUnavailableException {
    if(onlyAudio == null) {
      onlyAudio = new Audio();
    }
    return onlyAudio;
  }

  /**
   * Loads an audio clip from the specified file path.
   * @param path the path to the audio file.
   * @return the loaded audio clip.
   * @throws FileNotFoundException if the specified sound file cannot be found.
   */
  Clip loadAudio(Path path) throws FileNotFoundException {
    try {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path.toFile().toURI()));
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      return clip;
    } catch (Exception e) {
      throw new FileNotFoundException("Can't find audio file at " + path.toString());
    }
  }

  /**
   *  Plays the sound clip for when the invader dies.
   */
  public void playDeadInvader() {
    if (dead_invader != null) {
      dead_invader.setFramePosition(0);
      dead_invader.start();
    }
  }

  /**
   * Plays the sound clip for when the player dies.
   */
  public void playDeadPlayer() {
    if (dead_player != null) {
      dead_player.setFramePosition(0);
      dead_player.start();
    }
  }

  /**
   * Plays the sound clip for when the barrier got attacked.
   */
  public void playBarrier() {
    if (barrier != null) {
      barrier.setFramePosition(0);
      barrier.start();
    }
  }

  /**
   * Plays the sound clip for when the player shoot.
   */
  public void playMissilePlayer() {
    if (missile_player != null) {
      missile_player.setFramePosition(0);
      missile_player.start();
    }
  }

  /**
   * Plays the background music.
   */
  public void playBgm() {
    if (bgm != null) {
      bgm.loop(Clip.LOOP_CONTINUOUSLY);
    }
  }
}

