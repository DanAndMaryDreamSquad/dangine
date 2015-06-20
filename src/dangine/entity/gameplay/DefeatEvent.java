package dangine.entity.gameplay;

import dangine.entity.Hero;
import dangine.utility.Utility;

public class DefeatEvent implements MatchEvent {

    int defeatedPlayer;
    int playerWhoDefeatedThis;

    public DefeatEvent(int defeatedPlayer, int playerWhoDefeatedThis) {
        this.defeatedPlayer = defeatedPlayer;
        this.playerWhoDefeatedThis = playerWhoDefeatedThis;
    }

    @Override
    public void process() {
        Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper()
                .onPlayerDefeatsAnother(defeatedPlayer, playerWhoDefeatedThis);

        Hero hero = Utility.getActiveScene().getHero(defeatedPlayer);
        if (hero != null) {
            hero.destroyForReal();
        }

        if (Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().shouldPlayerRespawn(defeatedPlayer)) {
            Respawner respawner = new Respawner(defeatedPlayer);
            Utility.getActiveScene().addUpdateable(respawner);
        }
    }
}
