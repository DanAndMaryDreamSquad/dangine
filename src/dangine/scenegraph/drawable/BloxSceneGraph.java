package dangine.scenegraph.drawable;

import org.newdawn.slick.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.scenegraph.SceneGraphNode;

public class BloxSceneGraph implements HasDrawable {

    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode body = new SceneGraphNode();
    BloxHeadSceneGraph head = new BloxHeadSceneGraph();
    SceneGraphNode leftLeg = new SceneGraphNode();
    SceneGraphNode rightLeg = new SceneGraphNode();
    SceneGraphNode leftArm = new SceneGraphNode();
    SceneGraphNode rightArm = new SceneGraphNode();
    final DangineShape bodyShape = new DangineShape();
    final DangineShape leftLegShape = new DangineShape(10, 20, Color.magenta);
    final DangineShape rightLegShape = new DangineShape(10, 20, Color.pink);
    final DangineShape leftArmShape = new DangineShape(10, 10, Color.green);
    final DangineShape rightArmShape = new DangineShape(10, 10, Color.cyan);

    public BloxSceneGraph() {
        body.addChild(bodyShape);

        // head.addChild(new DangineShape(20, 20, Color.blue));
        // head.setPosition(-1, -25);
        leftArm.addChild(leftArmShape);
        leftArm.setPosition(-15, 0);
        leftArm.setZValue(-1.0f);
        rightArm.addChild(rightArmShape);
        rightArm.setPosition(15, 0);
        rightArm.setZValue(-1.0f);
        leftLeg.addChild(leftLegShape);
        leftLeg.setPosition(-10, 20);
        rightLeg.addChild(rightLegShape);
        rightLeg.setPosition(20, 20);

        base.addChild(body);
        body.addChild(head.getDrawable());
        body.addChild(leftArm);
        body.addChild(rightArm);
        body.addChild(leftLeg);
        body.addChild(rightLeg);

        base.setScale(1, 1);
    }

    public void removeHands() {
        body.removeChild(leftArm);
        body.removeChild(rightArm);
    }

    public void addHands() {
        body.addChild(leftArm);
        body.addChild(rightArm);
    }

    public SceneGraphNode getBase() {
        return base;
    }

    public BloxHeadSceneGraph getHead() {
        return head;
    }

    public SceneGraphNode getBody() {
        return body;
    }

    @Override
    public IsDrawable getDrawable() {
        return getBase();
    }

    public DangineShape getBodyShape() {
        return bodyShape;
    }

    public DangineShape getLeftLegShape() {
        return leftLegShape;
    }

    public DangineShape getRightLegShape() {
        return rightLegShape;
    }

    public DangineShape getLeftArmShape() {
        return leftArmShape;
    }

    public DangineShape getRightArmShape() {
        return rightArmShape;
    }
}
