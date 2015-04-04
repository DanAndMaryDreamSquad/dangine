package dangine.entity.combat.subpower;

import dangine.entity.Hero;
import dangine.entity.visual.ExplosionVisual;
import dangine.scenegraph.drawable.DangineParticle;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Utility;

public enum SubPower {

    NONE {
        @Override
        public void givePower(Hero hero) {

        }

        @Override
        public void createEffect(float x, float y) {
        }

    },
    DASH {
        @Override
        public void givePower(Hero hero) {
            hero.setDashPower(new DashPower());
        }

        @Override
        public void createEffect(float x, float y) {
            DangineParticle particle = ParticleEffectFactory.create(16, 16, ParticleEffectFactory.greenColors);
            ExplosionVisual visual = new ExplosionVisual(x, y, particle, 0, 360, 0.01f, 0.1f, 500f);
            Utility.getActiveScene().addUpdateable(visual);
            Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
        }
    },
    PROJECTILE {
        @Override
        public void givePower(Hero hero) {
            hero.setProjectilePower(new ProjectilePower(hero.getPlayerId()));
        }

        @Override
        public void createEffect(float x, float y) {
            DangineParticle particle = ParticleEffectFactory.create(16, 16, ParticleEffectFactory.energyColors);
            ExplosionVisual visual = new ExplosionVisual(x, y, particle, 0, 360, 0.01f, 0.1f, 500f);
            Utility.getActiveScene().addUpdateable(visual);
            Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
        }
    };

    public abstract void givePower(Hero hero);

    public abstract void createEffect(float x, float y);

    public SubPower nextPower() {
        return SubPower.values()[(this.ordinal() + 1) % SubPower.values().length];
    }

}
