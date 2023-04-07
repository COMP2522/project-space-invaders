package org.space.invader;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.sound.sampled.*;

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
  private final Clip deadInvader;

  /**
   * The sound clip for when the player dies.
   */
  private final Clip deadPlayer;

  /**
   * The sound clip for when the barrier got attacked.
   */
  private final Clip barrier;

  /**
   * The sound clip for when the missile is shot.
   */
  private final Clip missilePlayer;

  /**
   * The background music.
   */
  private final Clip bgm;


  /**
   * Constructs a new Audio object from the specified sound file.
   *
   * @throws FileNotFoundException if the specified sound file cannot be found.
   */
  private Audio() throws FileNotFoundException {
    Path invaderP = Paths.get("audio", "InvaderDead.wav");
    deadInvader = loadAudio(invaderP);

    Path playerP = Paths.get("audio", "player_dead.wav");
    deadPlayer  = loadAudio(playerP);

    Path barrierP = Paths.get("audio", "attacked_barrier.wav");
    barrier = loadAudio(barrierP);

    Path missileP = Paths.get("audio", "missile.wav");
    missilePlayer = loadAudio(missileP);

    Path bgmP = Paths.get("audio", "bgm.wav");
    bgm = loadAudio(bgmP);
  }

  /**
   * Returns the singleton instance of the Audio class.
   * If the instance has not been created yet, it will create a new one.
   *
   * @return the singleton instance of the Audio class.
   * @throws FileNotFoundException if the specified sound file cannot be found.
   */
  public static Audio getInstance() throws FileNotFoundException {
    if (onlyAudio == null) {
      onlyAudio = new Audio();
    }
    return onlyAudio;
  }

  /**
   * Loads an audio clip from the specified file path.
   *
   * @param path the path to the audio file.
   * @return the loaded audio clip.
   * @throws FileNotFoundException if the specified sound file cannot be found.
   */
  Clip loadAudio(Path path) throws FileNotFoundException {
    try {
      AudioInputStream audioInputStream
          = AudioSystem.getAudioInputStream(new File(path.toFile().toURI()));
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
    if (deadInvader != null) {
      deadInvader.setFramePosition(0);
      deadInvader.start();
    }
  }

  /**
   * Plays the sound clip for when the player dies.
   */
  public void playDeadPlayer() {
    if (deadPlayer != null) {
      deadPlayer.setFramePosition(0);
      deadPlayer.start();
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
    if (missilePlayer != null) {
      missilePlayer.setFramePosition(0);
      missilePlayer.start();
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

