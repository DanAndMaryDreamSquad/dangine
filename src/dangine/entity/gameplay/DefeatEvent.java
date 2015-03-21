package dangine.entity.gameplay;

import dangine.utility.Utility;

public class DefeatEvent implements MatchEvent {

    int playerId;

    public DefeatEvent(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void process() {
        Respawner respawner = new Respawner(playerId);
        Utility.getActiveScene().addUpdateable(respawner);
    }

}
