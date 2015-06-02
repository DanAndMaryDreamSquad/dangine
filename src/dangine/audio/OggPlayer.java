package dangine.audio;

import javax.sound.sampled.FloatControl;

public class OggPlayer {

    private volatile boolean isStopRequested = false;
    private volatile boolean isStartRequested = false;
    private volatile boolean isResetRequested = false;
    private volatile boolean isPauseRequested = false;
    private volatile boolean isResumeRequested = false;
    private volatile boolean isNoMusicPlaying = true;
    private volatile boolean isRepeating = true;
    private volatile boolean needToReadHeader = true;
    private volatile boolean needToChangeVolume = true;
    MusicEffect currentTrack = null;
    OggPlayerDTO oggPlayerDTO = new OggPlayerDTO();
    float volume = 1.0f;
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
        if (isResumeRequested) {
            resumeTrack();
        }
        if (isPauseRequested) {
            return;
        }

        if (needToReadHeader) {
            if (OggPlayerCodec.readHeader(oggPlayerDTO) && OggPlayerCodec.initializeSound(oggPlayerDTO)) {
                needToReadHeader = false;
            }
        }
        if (needToChangeVolume) {
            updateVolume();
            needToChangeVolume = false;
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
        needToChangeVolume = true;
    }

    public void requestResetTrack() {
        isResetRequested = true;
    }

    public void requestPauseTrack() {
        isPauseRequested = true;
    }

    public void requestResumeTrack() {
        isResumeRequested = true;
    }

    public void requestSetVolume(float volume) {
        if (this.volume == volume) {
            return;
        }
        this.volume = volume;
        needToChangeVolume = true;
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

    private void resumeTrack() {
        isResumeRequested = false;
        isPauseRequested = false;
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

    public void updateVolume() {
        float gain = volume;
        try {
            FloatControl control = (FloatControl) oggPlayerDTO.getOutputLine()
                    .getControl(FloatControl.Type.MASTER_GAIN);
            if (gain == -1) {
                control.setValue(0);
            } else {
                float max = control.getMaximum();
                float min = control.getMinimum(); // negative values all seem to
                                                  // be zero?
                float range = max - min;

                control.setValue(min + (range * gain));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

}
