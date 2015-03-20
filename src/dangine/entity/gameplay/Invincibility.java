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
    SceneGraphNode node = new SceneGraphNode();

    public Invincibility(Hero hero) {
        this.hero = hero;
        hero.setImmunity(true);
        node.setZValue(1.0f);
        node.setPosition(-50, -50);
        hero.getBlox().getBase().addChild(node);
        node.addChild(new DangineShape(100, 100, Color.yellow));
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
