package dangine.game;

import org.newdawn.slick.Color;

import dangine.entity.Creature;
import dangine.utility.Utility;

public class DemoGame implements DangineGame {

    Creature creature = new Creature();

    @Override
    public void init() {
        Utility.getGraphics().setBackground(new Color(40, 40, 32));
    }

    @Override
    public void update() {
        creature.update();
    }

    @Override
    public void draw() {
        creature.draw();
    }

}
