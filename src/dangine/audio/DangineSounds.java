package dangine.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dangine.debugger.Debugger;
import dangine.image.Resources;

public class DangineSounds {

    public static final String DEFAULT_PLACEHOLDER_SOUND = "soundnotfound";
    private static Map<String, DangineSound> soundMap = new HashMap<String, DangineSound>();
    private static SoundDuplicates soundDuplicates = new SoundDuplicates();

    public static void initialize() {
        Debugger.info("Loading sounds...");
        if (Resources.shouldUseManifest()) {
            soundMap = SoundLoader.loadSounds(Resources.getResouceManifest());
        } else {
            soundMap = SoundLoader.loadSounds();
        }
        soundDuplicates.duplicateSounds();
    }

    public static DangineSound getSoundByName(String soundName) {
        if (!soundMap.containsKey(soundName)) {
            Debugger.warn("Sound " + soundName + " not found!");
            return null;
        }
        if (soundDuplicates.hasDuplicates(soundName)) {
            return soundDuplicates.getNextCopy(soundName);
        }
        return soundMap.get(soundName);
    }

    public static List<DangineSound> getAllSoundsByName(String soundName) {
        if (!soundMap.containsKey(soundName)) {
            Debugger.warn("Sound " + soundName + " not found!");
            return null;
        }
        if (soundDuplicates.hasDuplicates(soundName)) {
            return soundDuplicates.getAllCopies(soundName);
        }
        List<DangineSound> sounds = new ArrayList<DangineSound>();
        sounds.add(soundMap.get(soundName));
        return sounds;
    }

    public static void destroySounds() {
        soundDuplicates.destroyDuplicates();
        for (DangineSound sound : soundMap.values()) {
            sound.destroy();
        }
    }
}
