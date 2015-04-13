package dangine.scene;

import dangine.entity.Creature;
import dangine.entity.Obstruction;
import dangine.entity.gameplay.Boundaries;
import dangine.entity.gameplay.MatchParameters;
import dangine.entity.gameplay.Respawner;
import dangine.entity.world.Background;
import dangine.utility.Utility;

public class MatchSceneSchema implements SceneSchema {

    MatchParameters matchParameters = null;

    public MatchSceneSchema() {

    }

    public MatchSceneSchema(MatchParameters matchParameters) {
        this.matchParameters = matchParameters;
    }

    Creature creature = new Creature();
    Obstruction obstruction = new Obstruction();
    Background background = new Background(Utility.getMatchParameters().getCurrentWorld());
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

        scene.addUpdateable(obstruction);
        scene.getCameraNode().addChild(obstruction.getDrawable());
    }

}
