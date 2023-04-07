package no.uib.inf101.sem2.bomberman.midi;

import java.io.InputStream;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

/**
 * Play the Bomberman music. Sample usage:
 * <code>
 * BombermanSong music = new BombermanSong();
 * music.run();
 * </code>
 *
 * @author made from TetrisSong by Torstein Strømme
 */
public class BombermanSong implements Runnable {

  private String BOMBERMANMUSIC;
  private Sequencer sequencer;

  public BombermanSong(String BOMBERMANMUSIC) {
    this.BOMBERMANMUSIC = BOMBERMANMUSIC;
  }

  @Override
  public void run() {
    InputStream song =
      BombermanSong.class.getClassLoader().getResourceAsStream(BOMBERMANMUSIC);
    this.doPlayMidi(song, true);
  }

  private void doPlayMidi(final InputStream is, final boolean loop) {
    try {
      this.doStopMidiSounds();
      (this.sequencer = MidiSystem.getSequencer()).setSequence(
          MidiSystem.getSequence(is)
        );
      if (loop) {
        this.sequencer.setLoopCount(-1);
      }
      this.sequencer.open();
      this.sequencer.start();
    } catch (Exception e) {
      this.midiError("" + e);
    }
  }

  public void doStopMidiSounds() {
    try {
      if (this.sequencer == null || !this.sequencer.isRunning()) {
        return;
      }
      this.sequencer.stop();
      this.sequencer.close();
    } catch (Exception e) {
      this.midiError("" + e);
    }
    this.sequencer = null;
  }

  public void doPauseMidiSounds() {
    try {
      if (this.sequencer == null || !this.sequencer.isRunning()) {}
      this.sequencer.stop();
    } catch (Exception e) {
      this.midiError("" + e);
    }
  }

  public void doUnpauseMidiSounds() {
    try {
      if (this.sequencer == null) {}
      this.sequencer.start();
    } catch (Exception e) {
      this.midiError("" + e);
    }
  }

  private void midiError(final String msg) {
    System.err.println("Midi error: " + msg);
    this.sequencer = null;
  }

  /**
   * Check if the music is playing.
   *
   * @return true if the music is playing, false otherwise
   */
  public boolean isRunning() {
    if (this.sequencer != null && this.sequencer.isRunning()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Pause the music.
   */
  public void pause() {
    this.doPauseMidiSounds();
  }

  /**
   * Change the song playing.
   * @param BOMBERMANMUSIC
   */
  public void changeSong(String BOMBERMANMUSIC) {
    this.BOMBERMANMUSIC = BOMBERMANMUSIC;
  }
}
