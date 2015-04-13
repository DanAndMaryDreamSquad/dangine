package dangine.scene;

import dangine.bots.BotRespawner;
import dangine.entity.Creature;
import dangine.entity.Obstruction;
import dangine.entity.combat.subpower.SubPower;
import dangine.entity.gameplay.Boundaries;
import dangine.entity.gameplay.MatchParameters;
import dangine.entity.gameplay.Respawner;
import dangine.entity.world.Background;
import dangine.entity.world.World;
import dangine.utility.Utility;

public class BotMatchSceneSchema implements SceneSchema {

    MatchParameters matchParameters = null;

    public BotMatchSceneSchema() {

    }

    Creature creature = new Creature();
    Obstruction obstruction = new Obstruction();
    Background background = new Background(World.randomWorld());
    Boundaries boundaries = new Boundaries();

    @Override
    public void apply(Scene scene) {
        for (int i = 0; i < Utility.getPlayers().getPlayers().size(); i++) {
            scene.addUpdateable(new Respawner(i));
        }
        scene.addUpdateable(background);
        scene.getParentNode().addChild(background.getDrawable());
        scene.addUpdateable(boundaries);
        scene.addUpdateable(scene.getMatchOrchestrator().getScoreKeeper());
        scene.getParentNode().addChild(scene.getMatchOrchestrator().getScoreKeeper().getDrawable());

        scene.addUpdateable(new BotRespawner(-1));
        scene.getMatchOrchestrator().getScoreKeeper().addBotToGame();

        Utility.getMatchParameters().addPlayerPower(0, SubPower.NONE);
    }

}
