package dangine.bots;

import dangine.entity.gameplay.MatchEvent;
import dangine.utility.Utility;

public class BotDefeatEvent implements MatchEvent {

    int defeatedPlayer;
    int playerWhoDefeatedThis;

    public BotDefeatEvent(int defeatedPlayer, int playerWhoDefeatedThis) {
        this.defeatedPlayer = defeatedPlayer;
        this.playerWhoDefeatedThis = playerWhoDefeatedThis;
    }

    @Override
    public void process() {
        Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper()
                .onPlayerDefeatsAnother(defeatedPlayer, playerWhoDefeatedThis);
        DangineBot bot = Utility.getActiveScene().getBot(defeatedPlayer);
        if (bot != null) {
            bot.destroyForReal();
        }
        if (Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().shouldPlayerRespawn(defeatedPlayer)) {
            BotRespawner respawner = new BotRespawner(defeatedPlayer);
            Utility.getActiveScene().addUpdateable(respawner);
        }
    }
}
