package dangine.entity.combat;

import org.newdawn.slick.Color;

import dangine.collision.Collider;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.scenegraph.drawable.DangineShape;
import dangine.utility.Utility;

public class GreatSwordSceneGraph implements HasDrawable {

    float scale = 2.0f;
    Collider hitbox = new Collider();
    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode sword = new SceneGraphNode();
    SceneGraphNode leftArm = new SceneGraphNode();
    SceneGraphNode rightArm = new SceneGraphNode();

    public GreatSwordSceneGraph() {
        sword.addChild(new DangineImage("greatsword"));
        sword.setZValue(-1.0f);
        sword.setScale(scale, scale);

        leftArm.addChild(new DangineShape(5, 5, Color.green));
        rightArm.addChild(new DangineShape(5, 5, Color.cyan));

        sword.addChild(leftArm);
        sword.addChild(rightArm);
        base.addChild(sword);
    }

    public void addHitbox() {
        Utility.getActiveScene().addUpdateable(hitbox);
        base.addChild(hitbox.getDrawable());
    }

    public void removeHitbox() {
        Utility.getActiveScene().removeUpdateable(hitbox);
        base.removeChild(hitbox.getDrawable());
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

}
