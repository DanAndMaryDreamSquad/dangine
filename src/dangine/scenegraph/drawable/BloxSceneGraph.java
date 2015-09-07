package dangine.scenegraph.drawable;

import org.lwjgl.util.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.graphics.DangineBox;
import dangine.scenegraph.SceneGraphNode;

public class BloxSceneGraph implements HasDrawable {

    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode body = new SceneGraphNode();
    BloxHeadSceneGraph head = new BloxHeadSceneGraph();
    SceneGraphNode leftLeg = new SceneGraphNode();
    SceneGraphNode rightLeg = new SceneGraphNode();
    SceneGraphNode leftArm = new SceneGraphNode();
    SceneGraphNode rightArm = new SceneGraphNode();
    final DangineBox bodyShape = new DangineBox();
    final DangineBox leftLegShape = new DangineBox(10, 20, new Color(Color.PURPLE));
    final DangineBox rightLegShape = new DangineBox(10, 20, new Color(Color.RED));
    final DangineBox leftArmShape = new DangineBox(10, 10, new Color(Color.GREEN));
    final DangineBox rightArmShape = new DangineBox(10, 10, new Color(Color.CYAN));
    SceneGraphNode weaponNode = null;

    public BloxSceneGraph() {
        bodyShape.withGlow();
        leftLegShape.withGlow();
        rightLegShape.withGlow();
        leftArmShape.withGlow();
        rightArmShape.withGlow();

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

    public void addWeapon(SceneGraphNode weaponNode) {
        this.weaponNode = weaponNode;
        body.addChild(this.weaponNode);
    }

    public void reset() {
        body.removeChild(this.weaponNode);
        removeHands();
        addHands();
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

    public DangineBox getBodyShape() {
        return bodyShape;
    }

    public DangineBox getLeftLegShape() {
        return leftLegShape;
    }

    public DangineBox getRightLegShape() {
        return rightLegShape;
    }

    public DangineBox getLeftArmShape() {
        return leftArmShape;
    }

    public DangineBox getRightArmShape() {
        return rightArmShape;
    }
}
