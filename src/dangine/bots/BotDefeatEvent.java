package dangine.bots;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.entity.gameplay.MatchEvent;
import dangine.utility.Utility;

public class BotDefeatEvent implements MatchEvent {

    int playerId;

    public BotDefeatEvent(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void process() {
        SoundPlayer.play(SoundEffect.DEFEAT);
        Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().deductStock(playerId);

        if (Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().hasLivesLeft(playerId)) {
            BotRespawner respawner = new BotRespawner(playerId);
            Utility.getActiveScene().addUpdateable(respawner);
        }
    }
}
