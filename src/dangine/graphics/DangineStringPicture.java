package dangine.graphics;

import org.lwjgl.opengl.GL11;

import dangine.scenegraph.SceneGraphNode;

public class DangineStringPicture implements IsDrawable32 {
    
    RenderData32 data = new RenderData32(this);
    DangineTexturedQuad quad;
    SceneGraphNode node = new SceneGraphNode();
    DangineTexture texture;
    String text;

    public DangineStringPicture(String text) {
        this.text = text;
        texture = DangineTextureGenerator.generateStringTexture(text);
        quad = new DangineTexturedQuad(texture);
        node.setScale(texture.getWidth(), texture.getHeight());
        node.setPosition(100, 100);
    }
    
    public void changeText(String text) {
        this.text = text;
        GL11.glDeleteTextures(texture.getTextureId());
        texture = DangineTextureGenerator.generateStringTexture(text);
        node.setScale(texture.getWidth(), texture.getHeight());
    }

    public void update() {

    }

    public void draw() {
        quad.updateTransformationMatrixOfShader(node.getMatrix());
        quad.drawQuad();
    }

    public SceneGraphNode getNode() {
        return node;
    }

    @Override
    public RenderData32 getRenderData32() {
        return data;
    }

    @Override
    public IsDrawable32 copy() {
        return new DangineStringPicture(text);
    }
}
