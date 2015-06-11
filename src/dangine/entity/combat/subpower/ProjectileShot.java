package dangine.entity.combat.subpower;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.Vortex;
import dangine.entity.combat.CombatEvent;
import dangine.entity.combat.CombatEventHitbox;
import dangine.entity.combat.CombatResolver;
import dangine.entity.combat.CombatResolver.EventType;
import dangine.entity.visual.ExplosionVisual;
import dangine.graphics.DanginePictureParticle;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Method;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class ProjectileShot implements IsUpdateable, HasDrawable {

    public static final int HITBOX_SIZE = 20;
    final float SPEED = 0.36f;
    final float DURATION = 3000f;
    final ShotSceneGraph draw = new ShotSceneGraph();
    final Vector2f velocity = new Vector2f();
    final CombatEvent onHit;
    final CombatEventHitbox hitbox;
    final int ownerId;
    float timer = 0;

    public ProjectileShot(int ownerId, Vector2f position, Vector2f velocity) {
        this.ownerId = ownerId;
        this.velocity.x = velocity.x;
        this.velocity.y = velocity.y;
        onHit = new CombatEvent(ownerId, position, HITBOX_SIZE, getOnHitBy(), this, EventType.PROJECTILE,
                CombatResolver.getTypeToTargets().get(EventType.PROJECTILE));
        hitbox = new CombatEventHitbox(onHit);
        Utility.getActiveScene().getCameraNode().addChild(hitbox.getDrawable());
    }

    @Override
    public void update() {
        float delta = Utility.getGameTime().getDeltaTimeF();
        timer += delta;
        draw.setRotation(timer * 0.36f);
        float shiftX = velocity.x * delta * SPEED;
        float shiftY = velocity.y * delta * SPEED;
        Vector2f position = draw.getBase().getPosition();
        position.x += shiftX;
        position.y += shiftY;

        onHit.setPosition(position);
        hitbox.setPosition(position.x - HITBOX_SIZE, position.y - HITBOX_SIZE);
        Utility.getActiveScene().getCombatResolver().addEvent(onHit);

        if (timer > DURATION) {
            this.destroy();
        }
    }

    public void destroy() {
        Utility.getActiveScene().removeUpdateable(this);
        Utility.getActiveScene().getCameraNode().removeChild(this.getDrawable());
        Utility.getActiveScene().getCameraNode().removeChild(hitbox.getDrawable());

        Vector2f position = draw.getBase().getPosition();
        DanginePictureParticle particle = ParticleEffectFactory.create(8, 16, ParticleEffectFactory.energyColors);
        ExplosionVisual visual = new ExplosionVisual(position.x, position.y, particle, 0, 360, 0.01f, 0.1f, 500f);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
    }

    public Method<CombatEvent> getOnHitBy() {
        return new Method<CombatEvent>() {

            @Override
            public void call(CombatEvent arg) {
                if (arg.getCreator() instanceof Vortex) {
                    return;
                }
                SoundPlayer.play(SoundEffect.PROJECTILE_CLASH);
                ProjectileShot.this.destroy();
            }
        };
    }

    @Override
    public IsDrawable getDrawable() {
        return draw.getDrawable();
    }

    public SceneGraphNode getNode() {
        return draw.getBase();
    }

}
