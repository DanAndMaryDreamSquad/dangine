package dangine.entity.gameplay;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import dangine.entity.Hero;
import dangine.entity.IsUpdateable;
import dangine.entity.visual.ExplosionVisual;
import dangine.graphics.DanginePictureParticle;
import dangine.player.DanginePlayer;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Utility;

public class Boundaries implements IsUpdateable {

    List<Hero> heroes = new ArrayList<Hero>();
    float MAX_X = Utility.getResolution().x;
    float MAX_Y = Utility.getResolution().y;

    public Boundaries() {

    }

    @Override
    public void update() {
        if (heroes.size() != Utility.getPlayers().getPlayers().size()) {
            for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
                Hero hero = Utility.getActiveScene().getHero(player.getPlayerId());
                if (hero != null) {
                    heroes.add(hero);
                }
            }
        }

        boolean needsClear = false;
        for (Hero hero : heroes) {
            if (isOutOfBounds(hero.getPosition()) && !hero.isImmunity()) {
                hero.destroy();
                needsClear = true;
                Vector2f position = hero.getPosition();
                float x = position.x;
                float y = position.y;
                float angle = getAngleOfEffect(position);
                DanginePictureParticle particle = ParticleEffectFactory
                        .create(18, 25, ParticleEffectFactory.fireColors);
                ExplosionVisual visual = new ExplosionVisual(x + getOffsetX(position), y + getOffsetY(position),
                        particle, angle - 45, angle + 45, 0.36f, 1500f);
                Utility.getActiveScene().addUpdateable(visual);
                Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
            }
        }

        if (needsClear) {
            heroes.clear();
        }

    }

    public void rescanHeroes() {
        heroes.clear();
    }

    private boolean isOutOfBounds(Vector2f position) {
        return position.x < 0 || position.x > MAX_X || position.y < 0 || position.y > MAX_Y;
    }

    private float getAngleOfEffect(Vector2f position) {
        if (position.x < 0) {
            return 0;
        }
        if (position.y < 0) {
            return 90;
        }
        if (position.x > MAX_X) {
            return 180;
        }
        if (position.y > MAX_Y) {
            return 270;
        }
        return 0;
    }

    private float getOffsetX(Vector2f position) {
        if (position.x < 0) {
            return -75;
        }
        if (position.x > MAX_X) {
            return 75;
        }
        return 0;
    }

    private float getOffsetY(Vector2f position) {

        if (position.y < 0) {
            return -75;
        }
        if (position.y > MAX_Y) {
            return 75;
        }
        return 0;
    }

}
