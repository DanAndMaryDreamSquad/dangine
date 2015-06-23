package dangine.scene;

import dangine.audio.DangineMusicPlayer;
import dangine.audio.MusicEffect;
import dangine.entity.gameplay.Boundaries;
import dangine.entity.gameplay.MatchParameters;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.entity.gameplay.Respawner;
import dangine.entity.gameplay.ReturnToMenuChecker;
import dangine.entity.gameplay.soccer.SoccerBall;
import dangine.entity.gameplay.soccer.SoccerGoal;
import dangine.entity.world.ObstaclePack;
import dangine.utility.Utility;

public class MatchSceneSchema implements SceneSchema {

    MatchParameters matchParameters = null;

    public MatchSceneSchema() {

    }

    public MatchSceneSchema(MatchParameters matchParameters) {
        this.matchParameters = matchParameters;
    }

    Boundaries boundaries = new Boundaries();

    @Override
    public void apply(Scene scene) {
        if (Utility.getMatchParameters().getMatchType() == MatchType.SOCCER) {
            SoccerBall soccerBall = new SoccerBall();
            scene.addUpdateable(soccerBall);
            scene.getCameraNode().addChild(soccerBall.getDrawable());
            SoccerGoal soccerGoal = new SoccerGoal(1);
            scene.addUpdateable(soccerGoal);
            scene.getCameraNode().addChild(soccerGoal.getDrawable());
        }

        scene.addUpdateable(new ReturnToMenuChecker());
        for (int i = 0; i < Utility.getPlayers().getPlayers().size(); i++) {
            scene.addUpdateable(new Respawner(i));
        }
        scene.addUpdateable(boundaries);
        scene.addUpdateable(scene.getMatchOrchestrator().getScoreKeeper());
        scene.getParentNode().addChild(scene.getMatchOrchestrator().getScoreKeeper().getDrawable());

        Utility.getMatchParameters().getCurrentWorld().createWorld(scene);
        if (Utility.getMatchParameters().isRandomWorld()) {
            ObstaclePack.randomObstacles().applyObstacles(scene);
        } else {
            Utility.getMatchParameters().getCurrentWorld().getDefaultObstaclePack().applyObstacles(scene);
        }

        DangineMusicPlayer.startTrack(MusicEffect.BATTLE_SCENE);
    }

}
