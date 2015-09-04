package dangine.entity.gameplay;

import dangine.debugger.Debugger;
import dangine.entity.IsUpdateable;
import dangine.utility.Utility;

public class MatchRestarter implements IsUpdateable {

    final float MAX_TIME = 5000f;
    float timer = 0;
    Destination destination;

    public enum Destination {
        NEXT_ROUND, CHAR_SELECT;
    }

    public MatchRestarter() {
        if (Utility.getMatchParameters().getRoundKeeper().shouldPlayAnotherRound()) {
            this.destination = Destination.NEXT_ROUND;
        } else {
            this.destination = Destination.CHAR_SELECT;
        }
        Debugger.info("Restarting game in 5... destination: " + destination);
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();

        if (timer > MAX_TIME) {
            Debugger.info("Restarting game");
            switch (destination) {
            case NEXT_ROUND:
                Utility.getMatchParameters().getRoundKeeper().incrementRound();
                Utility.getGameLoop().startMatch();
                break;
            case CHAR_SELECT:
                Utility.getGameLoop().startCharacterSelect();
                break;
            }
        }

    }

}
