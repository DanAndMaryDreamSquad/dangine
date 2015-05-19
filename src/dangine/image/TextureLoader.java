package dangine.image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import dangine.debugger.Debugger;
import dangine.graphics.DangineTexture;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class TextureLoader {

    public static final String DEFAULT_IMAGE_DIRECTORY = "src" + System.getProperty("file.separator") + "assets"
            + System.getProperty("file.separator") + "images";
    public static List<String> filePlusDirectories;

    public static Map<String, DangineTexture> loadImages(ResourceManifest manifest) {
        return loadImages(manifest.getImages());
    }

    public static Map<String, DangineTexture> loadImages() {
        filePlusDirectories = DirectoryRecursor.listFileNames(DEFAULT_IMAGE_DIRECTORY);
        ResourceManifest manifest = new ResourceManifest(filePlusDirectories);
        manifest.save();
        return loadImages(filePlusDirectories);
    }

    private static Map<String, DangineTexture> loadImages(List<String> filenames) {
        Map<String, DangineTexture> images = new HashMap<String, DangineTexture>();
        for (String filename : filenames) {
            TextureLoader.addImageWithFilename(filename, images);
        }
        return images;
    }

    private static void addImageWithFilename(String filename, Map<String, DangineTexture> images) {

        if (!filename.contains(".png")) {
            return;
        }

        DangineTexture texture = loadPNGDangineTexture(filename, GL13.GL_TEXTURE0);
        images.put(texture.getName(), texture);

    }

    public static int loadPNGTexture(String filename, int textureUnit) {
        // ByteBuffer buf = null;
        // int tWidth = 0;
        // int tHeight = 0;
        //
        // try {
        // // Open the PNG file as an InputStream
        // InputStream in;
        // if (Resources.shouldUseManifest()) {
        // in =
        // ResourceManifest.class.getClassLoader().getResourceAsStream(filename);
        // } else {
        // in = new FileInputStream(filename);
        // }
        // // Link the PNG decoder to this stream
        // PNGDecoder decoder = new PNGDecoder(in);
        //
        // // Get the width and height of the texture
        // tWidth = decoder.getWidth();
        // tHeight = decoder.getHeight();
        //
        // // Decode the PNG file in a ByteBuffer
        // buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() *
        // decoder.getHeight());
        // decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
        // buf.flip();
        //
        // in.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // System.exit(-1);
        // }
        //
        // // Create a new texture object in memory and bind it
        // int texId = GL11.glGenTextures();
        // GL13.glActiveTexture(textureUnit);
        // GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
        //
        // // All RGB bytes are aligned to each other and each component is 1
        // byte
        // GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        //
        // // Upload the texture data and generate mip maps (for scaling)
        // GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, tWidth,
        // tHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
        // buf);
        // GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        //
        // // Setup the ST coordinate system
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
        // GL11.GL_REPEAT);
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
        // GL11.GL_REPEAT);
        //
        // // Setup what to do when the texture has to be scaled
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
        // GL11.GL_LINEAR);
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
        // GL11.GL_LINEAR_MIPMAP_LINEAR);
        //
        // return texId;
        return 0;
    }

    public static DangineTexture loadPNGDangineTexture(String filename, int textureUnit) {
        ByteBuffer buf = null;
        int tWidth = 0;
        int tHeight = 0;

        try {
            // Open the PNG file as an InputStream

            InputStream in;
            if (Resources.shouldUseManifest()) {
                Debugger.info("manifesting via " + filename);
                filename = filename.replace('\\', '/');
                in = ResourceManifest.class.getClassLoader().getResourceAsStream(filename);
            } else {
                Debugger.info("eclipse load via " + filename);
                in = new FileInputStream(filename);
            }

            Debugger.info("Input Stream? " + in);
            Debugger.info("bytes in " + filename + " " + in.available() + " ");
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            // Get the width and height of the texture
            tWidth = decoder.getWidth();
            tHeight = decoder.getHeight();

            // Decode the PNG file in a ByteBuffer
            buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
            buf.flip();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Create a new texture object in memory and bind it
        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(textureUnit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        // All RGB bytes are aligned to each other and each component is 1 byte
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        // Upload the texture data and generate mip maps (for scaling)
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, tWidth, tHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
                buf);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        // Setup the ST coordinate system
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        // Setup what to do when the texture has to be scaled
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);

        String name = translateFilePathToImageName(filename);
        DangineTexture texture = new DangineTexture(texId, name, tWidth, tHeight);
        return texture;
    }

    public static String translateFilePathToImageName(String filename) {
        // wipe off the directory
        int last = filename.lastIndexOf(System.getProperty("file.separator"));
        String textureName = filename.substring(last + 1, filename.length());

        last = textureName.lastIndexOf("/");
        textureName = textureName.substring(last + 1, textureName.length());

        // Get rid of file extension
        textureName = textureName.replace(".png", "");

        // just in case, ha ha ha
        textureName = textureName.toLowerCase();
        return textureName;
    }
}
