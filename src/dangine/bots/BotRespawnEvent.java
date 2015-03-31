package dangine.bots;

import dangine.entity.gameplay.MatchEvent;
import dangine.entity.visual.ExplosionVisual;
import dangine.entity.visual.RespawnVisual;
import dangine.utility.Utility;

public class BotRespawnEvent implements MatchEvent {

    int botId = 0;
    float x;
    float y;

    public BotRespawnEvent(float x, float y, int botId) {
        this.x = x;
        this.y = y;
        this.botId = botId;
    }

    @Override
    public void process() {
        DangineBot bot = new DangineBot();
        bot.setPosition(x, y);
        Utility.getActiveScene().addUpdateable(bot);
        Utility.getActiveScene().getCameraNode().addChild(bot.getDrawable());
        BotGreatsword greatsword = new BotGreatsword();
        bot.equipWeapon(greatsword);
        Utility.getActiveScene().addUpdateable(greatsword);

        BotInvincibility invincibility = new BotInvincibility(bot);
        Utility.getActiveScene().addUpdateable(invincibility);

        bot.update();

        // RespawnVisual visual = new RespawnVisual((playerId + 1) * 200, 400);
        ExplosionVisual visual = RespawnVisual.createRespawnVisual(x, y);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());

    }

}