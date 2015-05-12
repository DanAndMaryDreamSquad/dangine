package dangine.graphics;

import org.lwjgl.util.Color;

import dangine.scenegraph.SceneGraphNode;

public class DangineBox implements IsDrawable32 {

    RenderData32 data = new RenderData32(this);
    DangineColoredQuad quad;
    SceneGraphNode node = new SceneGraphNode();
    Color color;
    int width;
    int height;

    public DangineBox() {
        this(20, 20, new Color(Color.RED));
    }

    public DangineBox(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
        quad = new DangineColoredQuad(color);
        node.setPosition(getWidth() / 2, getHeight() / 2);
        node.setScale(getWidth(), -getHeight());
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
        return new DangineBox(getWidth(), getHeight(), color);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
