package dangine.graphics;


public class DangineTexture {

    final private int textureId;
    final private String name;
    
    public DangineTexture(int textureId, String filename) {
        this.textureId = textureId;
        this.name = filename;
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

}
