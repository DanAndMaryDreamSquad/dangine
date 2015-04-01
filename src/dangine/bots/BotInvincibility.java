package dangine.bots;

import org.newdawn.slick.Color;

import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineShape;
import dangine.utility.Utility;

public class BotInvincibility implements IsUpdateable {

    final DangineBot bot;
    float timer = 0;
    final float MAX_TIME = 3000;
    final int SIZE = 60;
    SceneGraphNode node = new SceneGraphNode();
    final Color color = new Color(1.0f, 1.0f, 0.0f, 0.5f);

    public BotInvincibility(DangineBot bot) {
        this.bot = bot;
        bot.setImmunity(true);
        node.setZValue(1.0f);
        node.setPosition(-SIZE / 2, -SIZE / 2);
        bot.getBlox().getBase().addChild(node);
        node.addChild(new DangineShape(SIZE, SIZE, color));
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        if (timer >= MAX_TIME) {
            bot.setImmunity(false);
            bot.getBlox().getBase().removeChild(node);
            Utility.getActiveScene().removeUpdateable(this);
        }
    }

}