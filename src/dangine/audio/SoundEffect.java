package dangine.audio;

public enum SoundEffect {

    // menu
    MENU_SELECT("chime1b"), //
    MENU_TICK("chime1b"), //
    MENU_BACK("chime1b"), //

    // sword
    CHARGE_SWING_LIGHT("chime1b"), //
    CHARGE_SWING_HEAVY("chime1b"), //
    START_SWING_LIGHT("chime1b"), //
    START_SWING_HEAVY("chime1b"), //
    CLASH_LIGHT("chime1b"), //
    CLASH_MEDIUM("chime1b"), //
    CLASH_HEAVY("chime1b"), //

    // powers
    DASH_READY("chime1b"), //
    DASH_START("chime1b"), //
    PROJECTILE_READY("chime1b"), //
    PROJECTILE_START("chime1b"), //
    PROJECTILE_CLASH("chime1b"), //
    PROJECTILE_HIT("chime1b"), //
    COUNTER_READY("chime1b"), //
    COUNTER_START("chime1b"), //
    COUNTER_CLASH("chime1b"), //

    // defeat
    VORTEX_DEFEAT("chime1b"), //
    SWORD_DEFEAT("chime1b"), //
    RINGOUT_DEFEAT("chime1b"), //

    // respawn
    RESPAWN_START("chime1b"), //
    RESPAWN_PULSE("chime1b"), //
    RESPAWN_END("chime1b"), //

    // events
    BUMPER_HIT("chime1b"), //
    ROUND_OVER("chime1b");

    String name;

    // TODO support multiple different sounds per effect.
    SoundEffect(String name) {
        this.name = name;
    }

    void play() {
        DangineSounds.getSoundByName(name).play();
    }

    void pause() {
        DangineSounds.getSoundByName(name).pause();
    }

    void stop() {
        DangineSounds.getSoundByName(name).stop();
    }

}
