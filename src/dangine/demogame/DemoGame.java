package dangine.demogame;

import org.newdawn.slick.Color;

import dangine.entity.Creature;
import dangine.entity.Hero;
import dangine.game.DangineGame;
import dangine.image.Resources;
import dangine.input.DangineKeyInputMapper;
import dangine.scene.Scene;
import dangine.scenegraph.drawable.DangineImage;
import dangine.utility.Utility;

public class DemoGame implements DangineGame {

    Creature creature = new Creature();
    Hero hero = new Hero();
    Scene scene = new Scene();
    DangineKeyInputMapper keyMapper = new DangineKeyInputMapper();

    @Override
    public void init() {
        Utility.getGraphics().setBackground(new Color(40, 40, 32));
        scene.addUpdateable(creature);
        scene.getParentNode().addChild(creature);
        scene.addUpdateable(hero);
        scene.getParentNode().addChild(hero.getDrawable());
        scene.getParentNode().addChild(new DangineImage(Resources.getImageByName("mary")));
    }

    @Override
    public void update() {
        scene.update();
    }

    @Override
    public void draw() {
        scene.draw();
    }

}
