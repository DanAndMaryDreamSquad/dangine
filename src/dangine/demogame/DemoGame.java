package dangine.demogame;

import org.newdawn.slick.Color;

import dangine.entity.Creature;
import dangine.entity.Hero;
import dangine.entity.combat.GreatSword;
import dangine.game.DangineGame;
import dangine.image.Resources;
import dangine.input.DangineKeyInputMapper;
import dangine.scene.Scene;
import dangine.scenegraph.drawable.DangineImage;
import dangine.utility.Utility;

public class DemoGame implements DangineGame {

    Creature creature = new Creature();
    Hero hero = new Hero();
    GreatSword greatsword = new GreatSword();
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
        hero.equipWeapon(greatsword);
        scene.addUpdateable(greatsword);
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
