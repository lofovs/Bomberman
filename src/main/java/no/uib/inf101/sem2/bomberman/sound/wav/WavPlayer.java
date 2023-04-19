package no.uib.inf101.sem2.bomberman.sound.wav;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class WavPlayer {

    public void play(String filename) {
        try {
            InputStream inputStream = WavPlayer.class.getClassLoader().getResourceAsStream(filename);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000); // wait until clip finishes playing
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
