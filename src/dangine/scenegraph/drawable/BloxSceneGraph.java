package dangine.scenegraph.drawable;

import org.newdawn.slick.Color;

import dangine.entity.IsDrawable;
import dangine.scenegraph.SceneGraphNode;

public class BloxSceneGraph implements IsDrawable {

    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode body = new SceneGraphNode();
    BloxHeadSceneGraph head = new BloxHeadSceneGraph();
    SceneGraphNode leftLeg = new SceneGraphNode();
    SceneGraphNode rightLeg = new SceneGraphNode();
    SceneGraphNode leftArm = new SceneGraphNode();
    SceneGraphNode rightArm = new SceneGraphNode();

    public BloxSceneGraph() {
        body.addChild(new DangineShape());

        // head.addChild(new DangineShape(20, 20, Color.blue));
        // head.setPosition(-1, -25);
        leftArm.addChild(new DangineShape(10, 10, Color.green));
        leftArm.setPosition(-15, 0);
        leftArm.setZValue(-1.0f);
        rightArm.addChild(new DangineShape(10, 10, Color.cyan));
        rightArm.setPosition(15, 0);
        rightArm.setZValue(-1.0f);
        leftLeg.addChild(new DangineShape(10, 20, Color.magenta));
        leftLeg.setPosition(-10, 20);
        rightLeg.addChild(new DangineShape(10, 20, Color.pink));
        rightLeg.setPosition(20, 20);

        base.addChild(body);
        body.addChild(head);
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

    @Override
    public void draw() {
        base.draw();
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
}
