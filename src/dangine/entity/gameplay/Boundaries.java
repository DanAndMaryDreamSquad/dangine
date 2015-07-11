package dangine.entity.gameplay;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Color;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.entity.HasDrawable;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.visual.ExplosionVisual;
import dangine.graphics.DangineBox;
import dangine.graphics.DanginePictureParticle;
import dangine.player.DanginePlayer;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class Boundaries implements IsUpdateable, HasDrawable {

    final int BORDER_THICKNESS = 50;
    SceneGraphNode base = new SceneGraphNode();
    List<Hero> heroes = new ArrayList<Hero>();
    float MAX_X = Utility.getResolution().x;
    float MAX_Y = Utility.getResolution().y;

    public Boundaries() {
        DangineBox top = new DangineBox((int) Utility.getResolution().x, BORDER_THICKNESS, new Color(Color.RED));
        top.setColor(new Color(Color.BLACK), new Color(0, 0, 0, 0), new Color(0, 0, 0, 0), new Color(Color.BLACK));
        DangineBox bottom = new DangineBox((int) Utility.getResolution().x, BORDER_THICKNESS, new Color(Color.GREEN));
        bottom.setColor(new Color(0, 0, 0, 0), new Color(Color.BLACK), new Color(Color.BLACK), new Color(0, 0, 0, 0));
        DangineBox left = new DangineBox(BORDER_THICKNESS, (int) Utility.getResolution().y, new Color(Color.BLUE));
        left.setColor(new Color(Color.BLACK), new Color(Color.BLACK), new Color(0, 0, 0, 0), new Color(0, 0, 0, 0));
        DangineBox right = new DangineBox(BORDER_THICKNESS, (int) Utility.getResolution().y, new Color(Color.YELLOW));
        right.setColor(new Color(0, 0, 0, 0), new Color(0, 0, 0, 0), new Color(Color.BLACK), new Color(Color.BLACK));
        SceneGraphNode topNode = new SceneGraphNode();
        topNode.addChild(top);
        topNode.setPosition(0, 0);
        SceneGraphNode bottomNode = new SceneGraphNode();
        bottomNode.addChild(bottom);
        bottomNode.setPosition(0, (int) Utility.getResolution().y - BORDER_THICKNESS);
        SceneGraphNode leftNode = new SceneGraphNode();
        leftNode.addChild(left);
        leftNode.setPosition(0, 0);
        SceneGraphNode rightNode = new SceneGraphNode();
        rightNode.addChild(right);
        rightNode.setPosition((int) Utility.getResolution().x - BORDER_THICKNESS, 0);
        base.addChild(topNode);
        base.addChild(bottomNode);
        base.addChild(leftNode);
        base.addChild(rightNode);
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
                SoundPlayer.play(SoundEffect.RINGOUT_DEFEAT);
                hero.destroy(hero.getPlayerId());
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

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

}
