package dangine.entity.combat;

import org.newdawn.slick.Color;

import dangine.entity.IsDrawable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.scenegraph.drawable.DangineShape;

public class GreatSwordSceneGraph implements IsDrawable {

    float scale = 2.0f;
    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode leftArm = new SceneGraphNode();
    SceneGraphNode rightArm = new SceneGraphNode();

    public GreatSwordSceneGraph() {
        base.addChild(new DangineImage("greatsword"));
        base.setZValue(-1.0f);
        base.setScale(scale, scale);

        leftArm.addChild(new DangineShape(5, 5, Color.green));
        rightArm.addChild(new DangineShape(5, 5, Color.cyan));

        base.addChild(leftArm);
        base.addChild(rightArm);
    }

    @Override
    public void draw() {
        base.draw();
    }

    public SceneGraphNode getBase() {
        return base;
    }

    public SceneGraphNode getLeftArm() {
        return leftArm;
    }

    public SceneGraphNode getRightArm() {
        return rightArm;
    }

}
