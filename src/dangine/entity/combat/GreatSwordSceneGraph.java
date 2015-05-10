package dangine.entity.combat;

import org.lwjgl.util.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.graphics.DangineBox;
import dangine.graphics.DanginePicture;
import dangine.scenegraph.SceneGraphNode;

public class GreatSwordSceneGraph implements HasDrawable {

    float scale = 2.0f;
    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode sword = new SceneGraphNode();
    SceneGraphNode leftArm = new SceneGraphNode();
    DangineBox leftArmShape = new DangineBox(5, 5, new Color(Color.GREEN));
    SceneGraphNode rightArm = new SceneGraphNode();
    DangineBox rightArmShape = new DangineBox(5, 5, new Color(Color.CYAN));

    public GreatSwordSceneGraph() {
        sword.addChild(new DanginePicture("greatsword"));
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

    public DangineBox getLeftArmShape() {
        return leftArmShape;
    }

    public DangineBox getRightArmShape() {
        return rightArmShape;
    }

}
