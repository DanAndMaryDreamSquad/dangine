package dangine.image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageLoader {
    public static final String DEFAULT_IMAGE_DIRECTORY = "src" + System.getProperty("file.separator") + "assets"
            + System.getProperty("file.separator") + "images";
    public static List<String> filePlusDirectories;

    public static Map<String, Image> loadImages(ResourceManifest manifest) {
        return loadImages(manifest.getImages());
    }

    public static Map<String, Image> loadImages() {
        filePlusDirectories = DirectoryRecursor.listFileNames(DEFAULT_IMAGE_DIRECTORY);
        ResourceManifest manifest = new ResourceManifest(filePlusDirectories);
        manifest.save();
        return loadImages(filePlusDirectories);
    }

    private static Map<String, Image> loadImages(List<String> filenames) {
        Map<String, Image> images = new HashMap<String, Image>();
        for (String filename : filenames) {
            ImageLoader.addImageWithFilename(filename, images);
        }

        return images;
    }

    private static void addImageWithFilename(String filename, Map<String, Image> images) {
        Image image;
        try {
            if (!(filename.contains(".png") || filename.contains(".jpg")))
                return;
            image = new Image(filename, false, GL11.GL_DOUBLE);

            // wipe off the directory
            int last = filename.lastIndexOf(System.getProperty("file.separator"));
            filename = filename.substring(last + 1, filename.length());

            last = filename.lastIndexOf("/");
            filename = filename.substring(last + 1, filename.length());

            // Get rid of file extension
            filename = filename.replace(".png", "");
            filename = filename.replace(".jpg", "");

            // just in case, ha ha ha
            filename = filename.toLowerCase();

            image.setName(filename);
            images.put(filename, image);
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

}
