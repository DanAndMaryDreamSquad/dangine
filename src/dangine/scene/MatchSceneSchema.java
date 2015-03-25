package dangine.scene;

import dangine.entity.Background;
import dangine.entity.Creature;
import dangine.entity.Obstruction;
import dangine.entity.gameplay.Boundaries;
import dangine.entity.gameplay.Respawner;
import dangine.utility.Utility;

public class MatchSceneSchema {

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
    }

}
