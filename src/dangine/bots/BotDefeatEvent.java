package dangine.bots;

import dangine.entity.gameplay.MatchEvent;
import dangine.utility.Utility;

public class BotDefeatEvent implements MatchEvent {

    int playerId;

    public BotDefeatEvent(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void process() {
        Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().deductStock(playerId);
        DangineBot bot = Utility.getActiveScene().getBot(playerId);
        if (bot != null) {
            bot.destroyForReal();
        }
        if (Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().hasLivesLeft(playerId)) {
            BotRespawner respawner = new BotRespawner(playerId);
            Utility.getActiveScene().addUpdateable(respawner);
        }
    }
}
