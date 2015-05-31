package dangine.audio;

import java.util.HashMap;
import java.util.Map;

import dangine.debugger.Debugger;
import dangine.image.Resources;

public class DangineMusics {
    public static final String DEFAULT_PLACEHOLDER_MUSIC = "musicnotfound";
    private static Map<String, DangineMusic> musicMap = new HashMap<String, DangineMusic>();

    public static void initialize() {
        Debugger.info("Loading musics...");
        if (Resources.shouldUseManifest()) {
            musicMap = MusicLoader.loadMusics(Resources.getResouceManifest());
        } else {
            musicMap = MusicLoader.loadMusics();
        }
    }

    public static DangineMusic getMusicByName(String musicName) {
        if (!musicMap.containsKey(musicName)) {
            Debugger.warn("Music " + musicName + " not found!");
            return null;
        }
        return musicMap.get(musicName);
    }
}
