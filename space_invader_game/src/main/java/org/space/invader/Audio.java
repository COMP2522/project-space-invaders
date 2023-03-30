package org.space.invader;


import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;





/**
 * Represents an audio clip that can be played.
 */
public class Audio {

  // VARIABLES
  private Clip clip;

  /**
   * Constructs a new Audio object from the specified sound file.
   *
   * @param song the path to the sound file
   */
  private Audio(String song){
    try {
      AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(song));
      clip = AudioSystem.getClip();
      clip.open(audio);
    } catch (Exception e) {}
  }


  /**
   * Gets the clip associated with this Audio object.
   *
   * @return the Clip object
   */
  public Clip getClip(){
    return clip;
  }

  /**
   * Starts playing the audio clip.
   */
  public void play(){
    clip.start();
  }

  /**
   * Stops playing the audio clip.
   */
  public void stop(){
    clip.stop();
  }

  /**
   * Plays the specified sound file.
   *
   * @param song the path to the sound file
   */

//  public static void playSound(String song){
//    Audio s = new Audio(song);
//    s.play();
//  }

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
