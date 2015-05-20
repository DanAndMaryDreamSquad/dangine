package dangine.entity.gameplay;

import dangine.debugger.Debugger;
import dangine.entity.IsUpdateable;
import dangine.utility.Utility;

public class MatchRestarter implements IsUpdateable {

    final float MAX_TIME = 5000f;
    float timer = 0;

    public MatchRestarter() {
        Debugger.info("Restarting game in 5...");
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();

        if (timer > MAX_TIME) {
            Debugger.info("Restarting game");
            Utility.getGameLoop().startCharacterSelect();
        }

    }

}
