package dangine.audio;

public class DangineMusicPlayer {

    static Thread musicPlayingThread;
    static DangineMusicPlayerRunnable musicPlayingRunnable;

    public static void initialize() {
        musicPlayingRunnable = new DangineMusicPlayerRunnable();
        musicPlayingThread = new Thread(musicPlayingRunnable);
    }

    public static void startMusicPlayerThread() {
        musicPlayingThread.start();
    }

    public static void destroyMusicPlayerThread() {
        musicPlayingRunnable.setCloseRequested(true);
        try {
            musicPlayingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startTrack(MusicEffect musicEffect) {
        musicPlayingRunnable.startTrack(musicEffect);
    }

    public static void stopCurrentTrack() {
        musicPlayingRunnable.stopTrack();
    }

    public static void resetCurrentTrack() {
        musicPlayingRunnable.resetTrack();
    }

}
