package dangine.demogame;

import org.newdawn.slick.Color;

import dangine.entity.Background;
import dangine.entity.Creature;
import dangine.entity.Hero;
import dangine.entity.Obstruction;
import dangine.entity.combat.GreatSword;
import dangine.game.DangineGame;
import dangine.scene.Scene;
import dangine.utility.Utility;

public class DemoGame implements DangineGame {

    Creature creature = new Creature();
    Obstruction obstruction = new Obstruction();
    Scene scene = new Scene();
    Background background = new Background();

    @Override
    public void init() {
        Utility.setActiveScene(scene);
        Utility.getGraphics().setBackground(new Color(40, 40, 32));

        // for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
        for (int i = 0; i < Utility.getPlayers().getPlayers().size(); i++) {
            Hero hero = new Hero(i);
            GreatSword greatsword = new GreatSword(i);
            hero.setPosition((i + 1) * 100, 200);
            hero.equipWeapon(greatsword);
            scene.getCameraNode().addChild(hero.getDrawable());
            scene.addUpdateable(hero);
            scene.addUpdateable(greatsword);
        }
        scene.addUpdateable(creature);
        scene.getParentNode().addChild(creature.getDrawable());
        // scene.getParentNode().addChild(new
        // DangineImage(Resources.getImageByName("mary")));
        scene.getCameraNode().addChild(obstruction.getDrawable());
        scene.addUpdateable(background);
        scene.getParentNode().addChild(background.getDrawable());
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
