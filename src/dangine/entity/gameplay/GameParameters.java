package dangine.entity.gameplay;

import dangine.audio.DangineMusicPlayer;
import dangine.audio.SoundEffect;
import dangine.debugger.Debugger;
import dangine.utility.DangineSavedSettings;

public class GameParameters {

    float musicVolume = 1.0f;
    float soundVolume = 1.0f;

    public float getMusicVolume() {
        return musicVolume;
    }

    public String getMusicVolumeString() {
        return (int) (musicVolume * 100) + "%";
    }

    public void setMusicVolume(float musicVolume) {
        musicVolume = (Math.round((musicVolume * 10)) * 10) / 100.0f;
        Debugger.info("Setting music volume to " + musicVolume);
        DangineMusicPlayer.setVolume(musicVolume);
        DangineSavedSettings.INSTANCE.setMusicVolumePercent(Math.round(musicVolume * 100));
        this.musicVolume = musicVolume;
    }

    public float getSoundEffectVolume() {
        return soundVolume;
    }

    public String getSoundEffectVolumeString() {
        return (int) (soundVolume * 100) + "%";
    }

    public void setSoundEffectVolume(float soundVolume) {
        soundVolume = (Math.round((soundVolume * 10)) * 10) / 100.0f;
        SoundEffect.updateVolumeOfAllSoundEffects(soundVolume);
        DangineSavedSettings.INSTANCE.setSoundVolumePercent(Math.round(soundVolume * 100));
        this.soundVolume = soundVolume;
    }

}
