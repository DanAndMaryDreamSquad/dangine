package dangine.entity.gameplay;

import dangine.entity.Hero;
import dangine.utility.Utility;

public class DefeatEvent implements MatchEvent {

    int playerId;

    public DefeatEvent(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void process() {
        Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().deductStock(playerId);

        Hero hero = Utility.getActiveScene().getHero(playerId);
        if (hero != null) {
            hero.destroyForReal();
        }

        if (Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().hasLivesLeft(playerId)) {
            Respawner respawner = new Respawner(playerId);
            Utility.getActiveScene().addUpdateable(respawner);
        }
    }
}
