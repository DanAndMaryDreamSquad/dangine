package dangine.entity.combat;

import org.newdawn.slick.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.scenegraph.drawable.DangineShape;

public class GreatSwordSceneGraph implements HasDrawable {

    float scale = 2.0f;
    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode sword = new SceneGraphNode();
    SceneGraphNode leftArm = new SceneGraphNode();
    DangineShape leftArmShape = new DangineShape(5, 5, Color.green);
    SceneGraphNode rightArm = new SceneGraphNode();
    DangineShape rightArmShape = new DangineShape(5, 5, Color.cyan);

    public GreatSwordSceneGraph() {
        sword.addChild(new DangineImage("greatsword"));
        sword.setZValue(-0.5f);
        sword.setScale(scale, scale);

        leftArm.addChild(leftArmShape);
        rightArm.addChild(rightArmShape);

        sword.addChild(leftArm);
        sword.addChild(rightArm);
        base.addChild(sword);
    }

    public void addHitbox(IsDrawable hitbox) {
        base.addChild(hitbox);
    }

    public void removeHitbox(IsDrawable hitbox) {
        base.removeChild(hitbox);
    }

    public SceneGraphNode getSword() {
        return sword;
    }

    public SceneGraphNode getLeftArm() {
        return leftArm;
    }

    public SceneGraphNode getRightArm() {
        return rightArm;
    }

    public SceneGraphNode getBase() {
        return base;
    }

    @Override
    public IsDrawable getDrawable() {
        return getBase();
    }

    public DangineShape getLeftArmShape() {
        return leftArmShape;
    }

    public DangineShape getRightArmShape() {
        return rightArmShape;
    }

}
