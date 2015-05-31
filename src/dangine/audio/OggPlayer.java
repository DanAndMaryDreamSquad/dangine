package dangine.audio;

public class OggPlayer {

    private volatile boolean isStopRequested = false;
    private volatile boolean isStartRequested = false;
    private volatile boolean isResetRequested = false;
    private volatile boolean isNoMusicPlaying = true;
    private volatile boolean isRepeating = true;
    private volatile boolean needToReadHeader = true;
    MusicEffect currentTrack = null;
    OggPlayerDTO oggPlayerDTO = new OggPlayerDTO();
    public boolean ready = false;

    public OggPlayer() {
    }

    public void run() {
        if (isNoMusicPlaying) {
            onNoMusicPlaying();
            return;
        }
        if (isStartRequested) {
            startTrack();
        }
        if (isResetRequested) {
            resetTrack();
        }
        if (isStopRequested) {
            isNoMusicPlaying = true;
            return;
        }

        if (needToReadHeader) {
            if (OggPlayerCodec.readHeader(oggPlayerDTO) && OggPlayerCodec.initializeSound(oggPlayerDTO)) {
                needToReadHeader = false;
            }
        }
        OggPlayerCodec.readBody(oggPlayerDTO);

        if (isTrackFinished()) {
            if (isRepeating) {
                resetTrack();
            } else {
                isNoMusicPlaying = true;
            }
        }
        return;
    }

    public void destroyOggPlayer() {
        OggPlayerCodec.cleanUp(oggPlayerDTO);
    }

    public void requestStopTrack() {
        isStopRequested = true;
    }

    public void requestStartTrack(MusicEffect musicEffect) {
        this.currentTrack = musicEffect;
        isStopRequested = false;
        isStartRequested = true;
        isNoMusicPlaying = false;
    }

    public void requestResetTrack() {
        isResetRequested = true;
    }

    private void startTrack() {
        OggPlayerCodec.cleanUp(oggPlayerDTO);
        oggPlayerDTO.initializeJOrbis();
        oggPlayerDTO.setInputStream(currentTrack.getDangineMusic().getInputStream());
        oggPlayerDTO.getInputStream().mark(99999999);
        needToReadHeader = true;
        isStartRequested = false;
        isNoMusicPlaying = false;
    }

    private void resetTrack() {
        oggPlayerDTO.resetTrack();
        OggPlayerCodec.readHeader(oggPlayerDTO);
        isResetRequested = false;
    }

    private void onNoMusicPlaying() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("We don't have an input stream and therefor " + "cannot continue.");
    }

    private boolean isTrackFinished() {
        return oggPlayerDTO.getJoggPage().eos() != 0;
    }

}
