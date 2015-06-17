package dangine.entity.combat;

import dangine.bots.DangineBotLogic;
import dangine.input.DangineSampleInput;

public class BotGreatswordInputProvider implements GreatswordInputProvider {

    DangineBotLogic logic = new DangineBotLogic();

    @Override
    public DangineSampleInput getInput(GreatSword greatSword) {
        return logic.getWhatDoWithWeapon(greatSword);
    }

}
