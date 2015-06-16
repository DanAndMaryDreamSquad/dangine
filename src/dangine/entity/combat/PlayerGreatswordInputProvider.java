package dangine.entity.combat;

import dangine.input.DangineSampleInput;
import dangine.utility.Utility;

public class PlayerGreatswordInputProvider implements GreatswordInputProvider {

    @Override
    public DangineSampleInput getInput(GreatSword greatSword) {
        return Utility.getPlayers().getPlayer(greatSword.getPlayerId()).getCurrentInput();
    }

}
