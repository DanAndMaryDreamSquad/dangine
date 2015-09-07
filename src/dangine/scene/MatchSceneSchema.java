package dangine.scene;

import dangine.audio.DangineMusicPlayer;
import dangine.audio.MusicEffect;
import dangine.bots.BotRespawner;
import dangine.entity.gameplay.Boundaries;
import dangine.entity.gameplay.MatchParameters;
import dangine.entity.gameplay.Respawner;
import dangine.entity.gameplay.ReturnToMenuChecker;
import dangine.entity.visual.StartOfRoundBanner;
import dangine.entity.world.ObstaclePack;
import dangine.utility.Utility;

public class MatchSceneSchema implements SceneSchema {

    MatchParameters matchParameters = null;
    Boundaries boundaries = new Boundaries();

    public MatchSceneSchema() {

    }

    public MatchSceneSchema(MatchParameters matchParameters) {
        this.matchParameters = matchParameters;
    }

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
        }

        Utility.getMatchParameters().getCurrentWorld().createWorld(scene);
        if (Utility.getMatchParameters().isRandomWorld()) {
            ObstaclePack.randomObstacles().applyObstacles(scene);
        } else {
            Utility.getMatchParameters().getCurrentWorld().getDefaultObstaclePack().applyObstacles(scene);
        }

        StartOfRoundBanner banner = new StartOfRoundBanner();
        scene.addUpdateable(banner);
        scene.getParentNode().addChild(banner.getDrawable());
        scene.getSceneChangeVisual().moveOffScreen();

        DangineMusicPlayer.startTrack(MusicEffect.BATTLE_SCENE);
        scene.update();
    }

}
