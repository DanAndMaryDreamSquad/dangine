package dangine.image;

import dangine.debugger.Debugger;

public class Resources {

    public static Boolean shouldUseManifest = null;

    public static boolean shouldUseManifest() {
        if (shouldUseManifest != null) {
            return shouldUseManifest;
        }
        String loadedFrom = Resources.class.getResource("Resources.class").toString();
        Debugger.info(loadedFrom);
        if (loadedFrom.startsWith("jar")) {
            Debugger.info("Loading from manifest");
            shouldUseManifest = true;
            return true;
        }
        Debugger.info("Loading from eclipse");
        shouldUseManifest = false;
        return false;
    }

}
