package dangine.bots;

import org.lwjgl.util.Color;

import dangine.entity.IsUpdateable;
import dangine.graphics.DangineBox;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class BotInvincibility implements IsUpdateable {

    final DangineBot bot;
    float timer = 0;
    final float MAX_TIME = 3000;
    final int SIZE = 60;
    SceneGraphNode node = new SceneGraphNode();
    final Color color = new Color(255, 255, 0, 127);

    public BotInvincibility(DangineBot bot) {
        this.bot = bot;
        bot.setImmunity(true);
        node.setZValue(1.0f);
        node.setPosition(-SIZE / 2, -SIZE / 2);
        bot.getBlox().getBase().addChild(node);
        node.addChild(new DangineBox(SIZE, SIZE, color));
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