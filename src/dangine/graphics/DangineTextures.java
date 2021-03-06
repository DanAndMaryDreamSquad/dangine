package dangine.graphics;

import java.util.HashMap;
import java.util.Map;

import dangine.debugger.Debugger;
import dangine.image.Resources;
import dangine.image.TextureLoader;

public class DangineTextures {
    public static final String DEFAULT_PLACEHOLDER_IMAGE = "imagenotfound";
    private static Map<String, DangineTexture> textureMap = new HashMap<String, DangineTexture>();

    public static void initialize() {
        Debugger.info("Loading images...");
        if (Resources.shouldUseManifest()) {
            textureMap = TextureLoader.loadImages(Resources.getResouceManifest());
        } else {
            textureMap = TextureLoader.loadImages();
        }
    }

    public static DangineTexture getImageByName(String textureName) {
        if (!textureMap.containsKey(textureName)) {
            Debugger.warn("Image " + textureName + " not found!");
            return null;
        }
        return textureMap.get(textureName);
    }
}
