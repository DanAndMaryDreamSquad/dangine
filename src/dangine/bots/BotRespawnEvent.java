package dangine.bots;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.entity.combat.BotGreatswordInputProvider;
import dangine.entity.combat.GreatSword;
import dangine.entity.gameplay.LifeIndicator;
import dangine.entity.gameplay.MatchEvent;
import dangine.entity.visual.ExplosionVisual;
import dangine.entity.visual.RespawnVisual;
import dangine.harness.Provider;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

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
        Utility.getActiveScene().getCamera().retrack();
        SoundPlayer.play(SoundEffect.RESPAWN_END);
        final DangineBot bot = new DangineBot(botId);
        bot.setPosition(x, y);
        Utility.getActiveScene().addUpdateable(bot);
        Utility.getActiveScene().getCameraNode().addChild(bot.getDrawable());
        GreatSword greatsword = new GreatSword(botId, new BotGreatswordInputProvider());
        bot.equipWeapon(greatsword);
        Utility.getActiveScene().addUpdateable(greatsword);
        Utility.getMatchParameters().givePlayerPower(bot);

        BotInvincibility invincibility = new BotInvincibility(bot);
        Utility.getActiveScene().addUpdateable(invincibility);

        bot.update();

        int lives = Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().getPlayerScore(botId).getStock();
        Provider<Vector2f> positionProvider = new Provider<Vector2f>() {
            @Override
            public Vector2f get() {
                return bot.getPosition();
            }
        };
        LifeIndicator lifeIndicator = new LifeIndicator(positionProvider, botId, lives);
        Utility.getActiveScene().addUpdateable(lifeIndicator);
        Utility.getActiveScene().getCameraNode().addChild(lifeIndicator.getDrawable());

        // RespawnVisual visual = new RespawnVisual((playerId + 1) * 200, 400);
        ExplosionVisual visual = RespawnVisual.createRespawnVisual(x, y);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());

    }

}