package dangine.audio;

public enum SoundEffect {

    SELECT("chime1b"), DEFEAT("combobreak");

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
