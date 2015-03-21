package dangine.entity.gameplay;

import dangine.utility.Utility;

public class DefeatEvent implements MatchEvent {

    int playerId;

    public DefeatEvent(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void process() {
        Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().deductStock(playerId);

        if (!Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().checkSceneOver()) {
            Respawner respawner = new Respawner(playerId);
            Utility.getActiveScene().addUpdateable(respawner);
        }
    }
}
