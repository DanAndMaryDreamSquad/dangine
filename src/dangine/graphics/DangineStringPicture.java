package dangine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import dangine.scenegraph.SceneGraphNode;

public class DangineStringPicture implements IsDrawable32 {

    RenderData32 data = new RenderData32(this);
    DangineTexturedQuad quad;
    SceneGraphNode node = new SceneGraphNode();
    DangineTexture texture;
    String text;
    Color color;

    public DangineStringPicture() {
        this("sample text", new Color(Color.BLACK));
    }

    public DangineStringPicture(String text, Color color) {
        this.color = color;
        this.text = text;
        texture = DangineTextureGenerator.generateStringTexture(text);
        quad = new DangineTexturedQuad(texture);
        float aspectX = DangineOpenGL.getWindowWorldAspectX();
        float aspectY = DangineOpenGL.getWindowWorldAspectY();
        node.setScale(texture.getWidth() * aspectX, texture.getHeight() * aspectY);
        node.setPosition((getWidth() / 2) * aspectX, (getHeight() / 2) * 1.0f * aspectY);
    }

    public void setText(String text) {
        this.text = text;
        GL11.glDeleteTextures(texture.getTextureId());
        texture = DangineTextureGenerator.generateStringTexture(text);
        quad.setTexture(texture);
        float aspectX = DangineOpenGL.getWindowWorldAspectX();
        float aspectY = DangineOpenGL.getWindowWorldAspectY();
        node.setScale(texture.getWidth() * aspectX, texture.getHeight() * aspectY);
        node.setPosition((getWidth() / 2) * aspectX, (getHeight() / 2) * 1.0f * aspectY);
    }

    public void update() {

    }

    public void draw() {
        quad.updateTransformationMatrixOfShader(node.getMatrix());
        quad.drawQuad();
    }

    public int getWidth() {
        return quad.getTexture().getWidth();
    }

    public int getHeight() {
        return quad.getTexture().getHeight();
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
        return new DangineStringPicture(text, color);
    }

    public void setAlpha(float alpha) {
        color.setAlpha((int) (alpha * 255f));
        quad.setTextureColor(color);
    }
}
