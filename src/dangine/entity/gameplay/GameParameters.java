package dangine.entity.gameplay;

import dangine.audio.DangineMusicPlayer;

public class GameParameters {

    float musicVolume = 1.0f;

    public float getMusicVolume() {
        return musicVolume;
    }

    public String getMusicVolumeString() {
        return (int) (musicVolume * 100) + "%";
    }

    public void setMusicVolume(float musicVolume) {
        musicVolume = (Math.round((musicVolume * 10)) * 10) / 100.0f;
        DangineMusicPlayer.setVolume(musicVolume);
        this.musicVolume = musicVolume;
    }

}
