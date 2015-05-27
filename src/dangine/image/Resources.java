package dangine.image;

import dangine.audio.DangineSounds;
import dangine.audio.SoundLoader;
import dangine.debugger.Debugger;
import dangine.graphics.DangineTextures;

public class Resources {

    public static Boolean shouldUseManifest = null;
    private static ResourceManifest resourceManifest = null;

    public static void initialize() {
        DangineTextures.initialize();
        DangineSounds.initialize();

        if (!shouldUseManifest()) {
            ResourceManifest manifest = new ResourceManifest(TextureLoader.getFilePlusDirectories(),
                    SoundLoader.getFilePlusDirectories());
            manifest.save();
        }

    }

    public static boolean shouldUseManifest() {
        if (shouldUseManifest != null) {
            return shouldUseManifest;
        }
        String loadedFrom = Resources.class.getResource("Resources.class").toString();
        Debugger.info(loadedFrom);
        if (loadedFrom.startsWith("jar")) {
            Debugger.info("Loading from manifest");
            shouldUseManifest = true;
            return shouldUseManifest;
        }
        Debugger.info("Loading from eclipse");
        shouldUseManifest = false;
        return shouldUseManifest;
    }

    public static ResourceManifest getResouceManifest() {
        if (resourceManifest == null) {
            resourceManifest = ResourceManifest.load();
        }
        return resourceManifest;
    }

}
