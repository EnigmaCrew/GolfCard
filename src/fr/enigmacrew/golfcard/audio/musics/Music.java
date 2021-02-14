package fr.enigmacrew.golfcard.audio.musics;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import fr.enigmacrew.golfcard.Config;

public class Music {

	public static Clip music;
	
	public static void playMusic(URL music_) {
		
		/*
		 * Play in loop a music with the volume set by the user (default : 50%)
		 * Only one music can be played. It is defined by the variable "music".
		 */
		
		try {
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(music_);
			music = AudioSystem.getClip();
			music.open(audioInput);
			refreshVolume();
			music.start();
			music.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
	}
	
	public static float getVolume() {
		// Return the current volume of the clip
	    FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);        
	    return (float) Math.pow(10f, gainControl.getValue() / 20f);
	}

	public static void setVolume(float volume) {
		// Set the current volume of the clip
	    if (volume < 0f || volume > 1f)
	        throw new IllegalArgumentException("Volume not valid: " + volume);
	    FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);        
	    gainControl.setValue(20f * (float) Math.log10(volume));
	}
	
	public static void refreshVolume() {
		// Refresh the current volume of the clip
		setVolume(Config.VOLUME);
	}
	
}
