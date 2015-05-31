package dangine.audio;

public enum MusicEffect {
    TITLE_MENU("intro"), //
    CHARACTER_SELECT("battleslow"), //
    BATTLE_SCENE("loop");

    String name;

    MusicEffect(String name) {
        this.name = name;
    }

    public DangineMusic getDangineMusic() {
        return DangineMusics.getMusicByName(name);
    }
}
