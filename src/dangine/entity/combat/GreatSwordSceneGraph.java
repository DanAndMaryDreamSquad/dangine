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
    SceneGraphNode swingBlur = new SceneGraphNode();
    SceneGraphNode stabBlur = new SceneGraphNode();
    SceneGraphNode staticBlur = new SceneGraphNode();
    SceneGraphNode leftArm = new SceneGraphNode();
    DangineBox leftArmShape = new DangineBox(5, 5, new Color(Color.GREEN));
    SceneGraphNode rightArm = new SceneGraphNode();
    DangineBox rightArmShape = new DangineBox(5, 5, new Color(Color.CYAN));
    boolean isSwingBlurEnabled = false;
    boolean isStabBlurEnabled = false;

    public GreatSwordSceneGraph() {
        sword.addChild(new DanginePicture("greatsword"));
        sword.setZValue(-0.5f);
        sword.setScale(scale, scale);
        swingBlur.addChild(new DanginePicture("wooshpostblur"));
        swingBlur.setZValue(-0.5f);
        swingBlur.setPosition(35, -2);
        swingBlur.setHorzitontalFlip(-1);
        stabBlur.addChild(new DanginePicture("stabpostblur2"));
        stabBlur.setZValue(-0.5f);
        stabBlur.setPosition(17, -4);
        stabBlur.setHorzitontalFlip(-1);
        staticBlur.addChild(new DanginePicture("greatswordglow"));
        staticBlur.setZValue(-0.2f);
        staticBlur.setPosition(13, -4);
        staticBlur.setHorzitontalFlip(-1);

        leftArm.addChild(leftArmShape);
        rightArm.addChild(rightArmShape);

        sword.addChild(leftArm);
        sword.addChild(rightArm);
        sword.addChild(staticBlur);
        base.addChild(sword);
    }

    public void enableSwingBlur() {
        if (!isSwingBlurEnabled) {
            sword.addChild(swingBlur);
            isSwingBlurEnabled = true;
        }
    }

    public void disableSwingBlur() {
        if (isSwingBlurEnabled) {
            sword.removeChild(swingBlur);
            isSwingBlurEnabled = false;
        }
    }

    public void enableStabBlur() {
        if (!isStabBlurEnabled) {
            sword.addChild(stabBlur);
            isStabBlurEnabled = true;
        }
    }

    public void disableStabBlur() {
        if (isStabBlurEnabled) {
            sword.removeChild(stabBlur);
            isStabBlurEnabled = false;
        }
    }

    public void addHitbox(IsDrawable hitbox) {
        sword.addChild(hitbox);
        // base.addChild(hitbox);
    }

    public void removeHitbox(IsDrawable hitbox) {
        sword.removeChild(hitbox);
        // base.removeChild(hitbox);
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
