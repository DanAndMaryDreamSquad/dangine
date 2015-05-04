package dangine.graphics;

import dangine.scenegraph.SceneGraphNode;

public class DanginePicture {
    DangineTexturedQuad quad;
    SceneGraphNode node = new SceneGraphNode();

    public DanginePicture(String imageName) {
        quad = new DangineTexturedQuad(imageName);
        node.setScale(200, 200);
        node.setPosition(100, 100);
    }
    
    public DanginePicture(DangineTexture texture) {
        quad = new DangineTexturedQuad(texture);
        node.setScale(100, 100);
        node.setPosition(100, 100);
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
