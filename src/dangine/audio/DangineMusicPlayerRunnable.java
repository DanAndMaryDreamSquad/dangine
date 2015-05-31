package dangine.audio;

import dangine.debugger.Debugger;

public class DangineMusicPlayerRunnable implements Runnable {

    // Because if it isn't volatile, the compile will cache it in a weird way
    // that can cause the thread to never join. How about that.
    volatile boolean isCloseRequested = false;
    OggPlayer player;

    public DangineMusicPlayerRunnable() {
        player = new OggPlayer();
    }

    @Override
    public void run() {
        Debugger.info("Music Player Thread Running");
        while (!isCloseRequested()) {
            player.run();
        }
        player.destroyOggPlayer();
        Debugger.info("Music Player Thread Joining");

    }

    public boolean isCloseRequested() {
        return isCloseRequested;
    }

    public void setCloseRequested(boolean isCloseRequested) {
        this.isCloseRequested = isCloseRequested;
    }

    public void startTrack(MusicEffect musicEffect) {
        player.requestStartTrack(musicEffect);
    }

    public void stopTrack() {
        player.requestStopTrack();
    }

    public void resetTrack() {
        player.requestResetTrack();
    }

}
