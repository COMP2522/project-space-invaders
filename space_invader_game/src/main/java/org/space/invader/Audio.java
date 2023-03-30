package org.space.invader;


import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;


/**
 * Represents an audio clip that can be played.
 */
public class Audio {

  private Clip clip;

  /**
   * Constructs a new Audio object from the specified sound file.
   *
   * @param song the path to the sound file
   */
  private Audio(String song) {
    try {
      AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(song));
      AudioFormat baseFormat = audio.getFormat();
      AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
          baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
      AudioInputStream decodedAudio = AudioSystem.getAudioInputStream(targetFormat, audio);
      clip = AudioSystem.getClip();
      clip.open(decodedAudio);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Gets the clip associated with this Audio object.
   *
   * @return the Clip object
   */
  public Clip getClip() {
    return clip;
  }

  /**
   * Starts playing the audio clip.
   *
   * @param loop whether the clip should be looped or not
   */
  public void play(boolean loop) {
    if (loop) {
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } else {
      clip.start();
    }
  }

  /**
   * Stops playing the audio clip.
   */
  public void stop() {
    clip.stop();
  }

  /**
   * Plays the specified sound file once.
   *
   * @param song the path to the sound file
   */
  public static void playSound(String song) {
    Audio s = new Audio(song);
    s.play(false);
  }

  /**
   * Plays the specified sound file in a loop.
   *
   * @param song the path to the sound file
   */
  public static void playLoop(String song) {
    Audio s = new Audio(song);
    s.play(true);

  public static void playSound(String song) {
    try {
      Audio s = new Audio(song);
      s.play();
    } catch (Exception e) {
      System.err.println("Error playing sound: " + song);
      e.printStackTrace();
    }
  }

}
