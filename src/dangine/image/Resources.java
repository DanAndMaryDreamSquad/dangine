package dangine.image;

import dangine.debugger.Debugger;

public class Resources {

    public static boolean shouldUseManifest() {
        String loadedFrom = Resources.class.getResource("Resources.class").toString();
        Debugger.info(loadedFrom);
        if (loadedFrom.startsWith("jar")) {
            Debugger.info("Loading from manifest");
            return true;
        }
        Debugger.info("Loading from eclipse");
        return false;
    }

}
