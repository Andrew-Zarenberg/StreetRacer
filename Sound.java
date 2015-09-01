// http://stackoverflow.com/questions/11919009/using-javax-sound-sampled-clip-to-play-loop-and-stop-mutiple-sounds-in-a-game

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Handles playing, stoping, and looping of sounds for the game.
 * @author Tyler Thomas
 *
 */
public class Sound {
  private Clip clip;
  
    private boolean go = false;//true;
      /* For some reason this doesn't work on the school computers, so setting this to false allows the game to 
       * play without sounds and without any runtime errors
       */
  
  public Sound(String fileName) {
    if(go){
      
    // specify the sound to play
    // (assuming the sound can be played by the audio system)
    // from a wave File
    try {
      File file = new File(fileName);
      if (file.exists()) {
        AudioInputStream sound = AudioSystem.getAudioInputStream(file);
        // load the sound into memory (a Clip)
        clip = AudioSystem.getClip();
        clip.open(sound);
      }
      else {
        throw new RuntimeException("Sound: file not found: " + fileName);
      }
    }
    catch (MalformedURLException e) {
      e.printStackTrace();
      throw new RuntimeException("Sound: Malformed URL: " + e);
    }
    catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
      throw new RuntimeException("Sound: Unsupported Audio File: " + e);
    }
    catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Sound: Input/Output Error: " + e);
    }
    catch (LineUnavailableException e) {
      e.printStackTrace();
      throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
    }
    
    // play, stop, loop the sound clip
    }
  }
  public void play(){
    if(go){
      clip.setFramePosition(0);  // Must always rewind!
      clip.start();
    }
  }
  public void loop(){
    if(go){
      clip.setFramePosition(0);  // Must always rewind!
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
  }
  public void stop(){
    if(go) clip.stop();
  }
}