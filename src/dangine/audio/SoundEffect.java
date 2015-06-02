package dangine.audio;

import java.util.List;

public enum SoundEffect {

    // menu
    MENU_SELECT("chime1b"), //
    MENU_TICK("chime1b"), //
    MENU_BACK("chime1b"), //

    // sword
    CHARGE_SWING_LIGHT("charge1"), //
    CHARGE_SWING_HEAVY("charge1"), //
    START_SWING_LIGHT("swing1"), //
    START_SWING_HEAVY("swing1", "charge1"), //
    CLASH_LIGHT("clash2"), //
    CLASH_MEDIUM("clash2"), //
    CLASH_HEAVY("clash2"), //

    // powers
    DASH_READY("chime1b"), //
    DASH_START("dash"), //
    PROJECTILE_READY("chime1b"), //
    PROJECTILE_START("projectilefire"), //
    PROJECTILE_CLASH("chime1b"), //
    PROJECTILE_HIT("chime1b"), //
    COUNTER_READY("chime1b"), //
    COUNTER_START("chime1b"), //
    COUNTER_CLASH("chime1b"), //

    // defeat
    VORTEX_DEFEAT("vortexdefeat"), //
    SWORD_DEFEAT("chime1b"), //
    RINGOUT_DEFEAT("chime1b"), //

    // respawn
    RESPAWN_START("chime1b"), //
    RESPAWN_PULSE("chime1b"), //
    RESPAWN_END("chime1b"), //

    // events
    BUMPER_HIT("chime1b"), //
    ROUND_OVER("chime1b");

    String[] names;
    int index = 0;

    // TODO support multiple different sounds per effect.
    SoundEffect(String... names) {
        this.names = names;
    }

    DangineSound play() {
        DangineSound instance = DangineSounds.getSoundByName(names[index]);
        instance.play();
        index = (index + 1) % names.length;
        return instance;
    }

    // TODO support pausing of duplicate sounds
    void pauseAll() {
        for (String name : names) {
            List<DangineSound> sounds = DangineSounds.getAllSoundsByName(name);
            for (DangineSound sound : sounds) {
                sound.pause();
            }
        }
    }

    void stopAll() {
        for (String name : names) {
            List<DangineSound> sounds = DangineSounds.getAllSoundsByName(name);
            for (DangineSound sound : sounds) {
                sound.stop();
            }
        }
    }

    public static void updateVolumeOfAllSoundEffects(float newVolume) {
        for (SoundEffect soundEffect : SoundEffect.values()) {
            for (String name : soundEffect.names) {
                DangineSounds.getSoundByName(name).updateVolume(newVolume);
            }
        }
    }

}
