package dangine.scene;

import dangine.bots.BotGreatsword;
import dangine.bots.DangineBot;
import dangine.entity.Background;
import dangine.entity.Creature;
import dangine.entity.Obstruction;
import dangine.entity.gameplay.Boundaries;
import dangine.entity.gameplay.MatchParameters;
import dangine.entity.gameplay.Respawner;
import dangine.utility.Utility;

public class BotMatchSceneSchema implements SceneSchema {

    MatchParameters matchParameters = null;

    public BotMatchSceneSchema() {

    }

    Creature creature = new Creature();
    Obstruction obstruction = new Obstruction();
    Background background = new Background();
    Boundaries boundaries = new Boundaries();

    public void apply(Scene scene) {
        for (int i = 0; i < Utility.getPlayers().getPlayers().size(); i++) {
            scene.addUpdateable(new Respawner(i));
        }
        scene.addUpdateable(background);
        scene.getParentNode().addChild(background.getDrawable());
        scene.addUpdateable(boundaries);
        scene.getParentNode().addChild(scene.getMatchOrchestrator().getScoreKeeper().getDrawable());

        DangineBot bot = new DangineBot();
        scene.addUpdateable(bot);
        scene.getCameraNode().addChild(bot.getDrawable());
        BotGreatsword greatsword = new BotGreatsword();
        bot.equipWeapon(greatsword);
        scene.addUpdateable(greatsword);
    }

}
