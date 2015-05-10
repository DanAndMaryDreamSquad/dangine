package dangine.scenegraph.drawable;

import org.lwjgl.util.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.graphics.DangineBox;
import dangine.scenegraph.SceneGraphNode;

public class BloxHeadSceneGraph implements HasDrawable {

    SceneGraphNode head = new SceneGraphNode();
    DangineBox headShape = new DangineBox(20, 20, new Color(Color.BLUE));
    SceneGraphNode leftEye = new SceneGraphNode();
    SceneGraphNode rightEye = new SceneGraphNode();

    public BloxHeadSceneGraph() {

        head.addChild(headShape);
        head.setPosition(-1, -20);
        head.setCenterOfRotation(10, 10);
        head.setZValue(-1.0f);
        leftEye.addChild(new DangineBox(2, 4, new Color(Color.BLACK)));
        leftEye.setPosition(5, 5);
        leftEye.setZValue(-1.0f);
        leftEye.setCenterOfRotation(1, 2);
        rightEye.addChild(new DangineBox(2, 4, new Color(Color.BLACK)));
        rightEye.setZValue(-1.0f);
        rightEye.setPosition(10, 5);
        rightEye.setCenterOfRotation(1, 2);

        head.addChild(leftEye);
        head.addChild(rightEye);
    }

    public SceneGraphNode getHead() {
        return head;
    }

    public SceneGraphNode getLeftEye() {
        return leftEye;
    }

    public SceneGraphNode getRightEye() {
        return rightEye;
    }

    @Override
    public IsDrawable getDrawable() {
        return getHead();
    }

    public DangineBox getHeadShape() {
        return headShape;
    }

}
