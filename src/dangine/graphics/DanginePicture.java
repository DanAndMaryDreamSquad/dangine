package dangine.graphics;

import dangine.entity.world.BackgroundFilterMode;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.DangineSavedSettings;

public class DanginePicture implements IsDrawable32 {
    RenderData32 data = new RenderData32(this);
    DangineTexturedQuad quad;
    SceneGraphNode node = new SceneGraphNode();

    public DanginePicture(String imageName) {
        quad = new DangineTexturedQuad(imageName);
        node.setPosition(getWidth() / 2, getHeight() / 2);
        node.setScale(getWidth(), -getHeight());
    }

    public DanginePicture(DangineTexture texture) {
        quad = new DangineTexturedQuad(texture);
        node.setPosition(getWidth() / 2, getHeight() / 2);
        node.setScale(getWidth(), -getHeight());
    }

    public void update() {

    }

    public void draw() {
        quad.updateTransformationMatrixOfShader(node.getMatrix());
        quad.drawQuad();
    }

    public DanginePicture applyFilter() {
        BackgroundFilterMode mode = BackgroundFilterMode.fromInt(DangineSavedSettings.INSTANCE
                .getBackgroundFilterMode());
        mode.setFilter(quad);
        return this;
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

    @Override
    public RenderData32 getRenderData32() {
        return data;
    }

    @Override
    public IsDrawable32 copy() {
        return new DanginePicture(quad.getTexture());
    }

}
