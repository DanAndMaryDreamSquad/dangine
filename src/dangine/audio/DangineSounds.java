package dangine.audio;

import java.util.HashMap;
import java.util.Map;

import dangine.debugger.Debugger;
import dangine.image.Resources;

public class DangineSounds {

    public static final String DEFAULT_PLACEHOLDER_SOUND = "soundnotfound";
    private static Map<String, DangineSound> soundMap = new HashMap<String, DangineSound>();

    public static void initialize() {
        Debugger.info("Loading sounds...");
        if (Resources.shouldUseManifest()) {
            soundMap = SoundLoader.loadSounds(Resources.getResouceManifest());
        } else {
            soundMap = SoundLoader.loadSounds();
        }
    }

    public static DangineSound getSoundByName(String soundName) {
        if (!soundMap.containsKey(soundName)) {
            Debugger.warn("Sound " + soundName + " not found!");
            return null;
        }
        return soundMap.get(soundName);
    }
}
