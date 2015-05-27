package dangine.entity.gameplay;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.utility.Utility;

public class DefeatEvent implements MatchEvent {

    int playerId;

    public DefeatEvent(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void process() {
        SoundPlayer.play(SoundEffect.DEFEAT);
        Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().deductStock(playerId);

        if (Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().hasLivesLeft(playerId)) {
            Respawner respawner = new Respawner(playerId);
            Utility.getActiveScene().addUpdateable(respawner);
        }
    }
}
