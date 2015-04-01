package dangine.entity.gameplay;

import org.newdawn.slick.Color;

import dangine.entity.Hero;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineShape;
import dangine.utility.Utility;

public class Invincibility implements IsUpdateable {

    final Hero hero;
    float timer = 0;
    final float MAX_TIME = 3000;
    final int SIZE = 60;
    SceneGraphNode node = new SceneGraphNode();
    final Color color = new Color(1.0f, 1.0f, 0.0f, 0.5f);

    public Invincibility(Hero hero) {
        this.hero = hero;
        hero.setImmunity(true);
        node.setZValue(1.0f);
        node.setPosition(-SIZE / 2, -SIZE / 2);
        hero.getBlox().getBase().addChild(node);
        node.addChild(new DangineShape(SIZE, SIZE, color));
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        if (timer >= MAX_TIME) {
            hero.setImmunity(false);
            hero.getBlox().getBase().removeChild(node);
            Utility.getActiveScene().removeUpdateable(this);
        }
    }

}
