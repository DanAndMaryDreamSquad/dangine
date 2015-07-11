package dangine.scene;

import dangine.audio.DangineMusicPlayer;
import dangine.audio.MusicEffect;
import dangine.bots.BotRespawner;
import dangine.entity.Creature;
import dangine.entity.Obstruction;
import dangine.entity.gameplay.Boundaries;
import dangine.entity.gameplay.MatchParameters;
import dangine.entity.gameplay.Respawner;
import dangine.entity.gameplay.ReturnToMenuChecker;
import dangine.entity.world.ObstaclePack;
import dangine.utility.Utility;

public class BotMatchSceneSchema implements SceneSchema {

    MatchParameters matchParameters = null;

    public BotMatchSceneSchema() {

    }

    Creature creature = new Creature();
    Obstruction obstruction = new Obstruction();
    Boundaries boundaries = new Boundaries();

    @Override
    public void apply(Scene scene) {
        scene.getMatchOrchestrator().getScoreKeeper().setupMatch();
        scene.getCameraNode().addChild(boundaries.getDrawable());
        scene.addUpdateable(new ReturnToMenuChecker());

        for (int i = 0; i < Utility.getPlayers().getPlayers().size(); i++) {
            scene.addUpdateable(new Respawner(i));
        }
        scene.addUpdateable(boundaries);
        scene.addUpdateable(scene.getMatchOrchestrator().getScoreKeeper());
        scene.getParentNode().addChild(scene.getMatchOrchestrator().getScoreKeeper().getDrawable());

        for (int i = 1; i < Utility.getMatchParameters().getNumberOfBots() + 1; i++) {
            scene.addUpdateable(new BotRespawner(-i));
            // scene.getMatchOrchestrator().getScoreKeeper().addBotToGame(-i);
            // if (Utility.getMatchParameters().getMatchType() ==
            // MatchType.COOP_VS_BOTS) {
            // Utility.getMatchParameters().addPlayerTeam(-i, -1);
            // }
        }

        Utility.getMatchParameters().getCurrentWorld().createWorld(scene);
        if (Utility.getMatchParameters().isRandomWorld()) {
            ObstaclePack.randomObstacles().applyObstacles(scene);
        } else {
            Utility.getMatchParameters().getCurrentWorld().getDefaultObstaclePack().applyObstacles(scene);
        }

        DangineMusicPlayer.startTrack(MusicEffect.BATTLE_SCENE);
    }

}
