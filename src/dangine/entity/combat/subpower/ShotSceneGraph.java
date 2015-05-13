package dangine.entity.combat.subpower;

import org.lwjgl.util.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.graphics.DangineBox;
import dangine.graphics.DanginePictureParticle;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.ParticleEffectFactory;

public class ShotSceneGraph implements HasDrawable {

    final SceneGraphNode base = new SceneGraphNode();
    final SceneGraphNode ring = new SceneGraphNode();
    final DanginePictureParticle ringParticle = ParticleEffectFactory.createCircle(8, 8,
            ParticleEffectFactory.energyColors, ProjectileShot.HITBOX_SIZE);
    final SceneGraphNode core = new SceneGraphNode();
    final DangineBox coreShape = new DangineBox(20, 20, new Color(Color.BLUE));

    public ShotSceneGraph() {
        base.addChild(ring);
        base.addChild(core);

        core.addChild(coreShape);
        core.setPosition(-ProjectileShot.HITBOX_SIZE / 2, -ProjectileShot.HITBOX_SIZE / 2);
        core.setCenterOfRotation(ProjectileShot.HITBOX_SIZE / 2, ProjectileShot.HITBOX_SIZE / 2);

        ring.addChild(ringParticle);
        ring.setPosition(-4, -4);
        ring.setCenterOfRotation(4, 4);
    }

    public void setRotation(float angle) {
        ring.setAngle(-angle);
        core.setAngle(angle);
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    public SceneGraphNode getBase() {
        return base;
    }

}
