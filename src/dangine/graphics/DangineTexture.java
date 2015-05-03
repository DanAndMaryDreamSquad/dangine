package dangine.graphics;


public class DangineTexture {

    final private int textureId;
    final private String name;
    final private int width;
    final private int height;
    
    public DangineTexture(int textureId, String filename, int width, int height) {
        this.textureId = textureId;
        this.name = filename;
        this.width = width;
        this.height = height;
    }
    
    /**
     * The ID of the texture as far as the graphics card is concerned.
     * This is what you pass into "GL11.glBindTexture(GL11.GL_TEXTURE_2D, int textureId)"
     * i.e. GL11.glBindTexture(GL11.GL_TEXTURE_2D, DangineTextureGenerator.createdTexture);
     */
    public int getTextureId() {
        return textureId;
    }
    
    /**
     * The filename minus the path and the extensions.
     */
    public String getName() {
        return name;        
    }

    /**
     * The width in pixels of the actual image file, as pulled from the .png
     */
    public int getWidth() {
        return width;
    }

    /**
     * The height in pixels of the actual image file, as pulled from the .png
     */
    public int getHeight() {
        return height;
    }

}
