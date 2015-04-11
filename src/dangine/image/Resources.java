package dangine.image;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;

import dangine.debugger.Debugger;

public class Resources {
    public static final String DEFAULT_PLACEHOLDER_IMAGE = "imagenotfound";
    private static Map<String, Image> imageMap = new HashMap<String, Image>();

    public static void initialize() {

        if (shouldUseManifest()) {
            ResourceManifest manifest = ResourceManifest.load();
            imageMap = ImageLoader.loadImages(manifest);
        } else {
            imageMap = ImageLoader.loadImages();

        }
    }

    public static Image getImageByName(String imageName) {
        if (!imageMap.containsKey(imageName)) {
            Debugger.warn("Image " + imageName + " not found!");
            return null;
        }
        return imageMap.get(imageName);
    }

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
