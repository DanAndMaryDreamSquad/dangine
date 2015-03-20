package dangine.entity.gameplay;

public class DefeatEvent implements MatchEvent {

    int playerId;

    public DefeatEvent(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void process() {
        // TODO Auto-generated method stub

    }

}
