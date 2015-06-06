package dangine.entity.gameplay;

import dangine.entity.IsUpdateable;
import dangine.player.DanginePlayer;
import dangine.utility.Utility;

public class ReturnToMenuChecker implements IsUpdateable {

    @Override
    public void update() {
        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            if (player.getCurrentInput().isButtonThree()) {
                Utility.getGameLoop().startTitleMenu();
            }
        }

    }

}
