package dangine.graphics;

import org.lwjgl.util.Color;

import dangine.scenegraph.SceneGraphNode;

public class DangineBox {

    DangineColoredQuad quad;
    SceneGraphNode node = new SceneGraphNode();

    public DangineBox() {
        quad = new DangineColoredQuad(new Color(Color.RED));
        node.setScale(100, 100);
        node.setPosition(200, 100);
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

}
