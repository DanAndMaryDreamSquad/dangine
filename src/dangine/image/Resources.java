package dangine.image;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;

public class Resources {
    public static final String DEFAULT_PLACEHOLDER_IMAGE = "imagenotfound";
    private static Map<String, Image> imageMap = new HashMap<String, Image>();

    public static void initialize() {
        imageMap = ImageLoader.loadImages();
    }

    public static Image getImageByName(String imageName) {
        if (!imageMap.containsKey(imageName)) {
            return null;
        }
        return imageMap.get(imageName);
    }

}
