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
    SceneGraphNode node = new SceneGraphNode();

    public BotInvincibility(DangineBot bot) {
        this.bot = bot;
        bot.setImmunity(true);
        node.setZValue(1.0f);
        node.setPosition(-50, -50);
        bot.getBlox().getBase().addChild(node);
        node.addChild(new DangineShape(100, 100, Color.yellow));
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