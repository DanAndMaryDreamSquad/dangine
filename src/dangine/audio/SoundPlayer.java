package dangine.audio;

public class SoundPlayer {

    // TODO later this will track which sounds are playing and make duplicates
    // if we want the
    // same sound to play multiple times at once.
    public static void play(SoundEffect effect) {
        effect.play();
    }

    public static void pause(SoundEffect effect) {
        effect.pauseAll();
    }

    public static void stop(SoundEffect effect) {
        effect.stopAll();
    }

}
