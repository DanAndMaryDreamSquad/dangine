package dangine.graphics;

import org.lwjgl.util.Color;

import dangine.scenegraph.SceneGraphNode;
import dangine.utility.FunctionKeyEvents;

public class DangineCirclePicture implements IsDrawable32 {
    RenderData32 data = new RenderData32(this);
    DangineTexturedQuad quad;
    SceneGraphNode node = new SceneGraphNode();
    float radius;
    boolean isHitbox = false;

    public DangineCirclePicture(float radius) {
        this(radius, new Color(Color.BLACK));
    }

    public DangineCirclePicture(float radius, Color color) {
        this.radius = radius;
        quad = new DangineTexturedQuad("circle128x128");
        float scale = ((radius * 2) / 128f);
        node.setPosition(scale * (getWidth() / 2), scale * (getHeight() / 2));
        node.setScale(getWidth() * scale, -getHeight() * scale);
    }

    public void update() {

    }

    public void draw() {
        if (!isHitbox || FunctionKeyEvents.isHitBoxesVisible()) {
            quad.updateTransformationMatrixOfShader(node.getMatrix());
            quad.drawQuad();
        }
    }

    public SceneGraphNode getNode() {
        return node;
    }

    public int getWidth() {
        return quad.getTexture().getWidth();
    }

    public int getHeight() {
        return quad.getTexture().getHeight();
    }

    public void setHitbox(boolean isHitbox) {
        this.isHitbox = isHitbox;
    }

    @Override
    public RenderData32 getRenderData32() {
        return data;
    }

    @Override
    public IsDrawable32 copy() {
        return new DanginePicture(quad.getTexture());
    }

}
