package dangine.scene;

import dangine.entity.Creature;
import dangine.entity.Hero;
import dangine.entity.Obstruction;
import dangine.entity.combat.GreatSword;
import dangine.entity.combat.PlayerGreatswordInputProvider;
import dangine.entity.gameplay.Boundaries;
import dangine.entity.gameplay.MatchParameters;
import dangine.entity.gameplay.ReturnToMenuChecker;
import dangine.entity.gameplay.soccer.SoccerBall;
import dangine.entity.gameplay.soccer.SoccerGoal;
import dangine.entity.world.Background;
import dangine.entity.world.PanningSceneGraph;
import dangine.input.DangineControllerAssignments;
import dangine.input.DangineControllerAssignments.Device;
import dangine.utility.Utility;

public class InstantMatchSceneSchema implements SceneSchema {

    MatchParameters matchParameters = null;

    public InstantMatchSceneSchema() {

    }

    Creature creature = new Creature();
    Obstruction obstruction = new Obstruction();
    PanningSceneGraph background = new PanningSceneGraph(Background.EXTRADIMENSIONAL);
    Boundaries boundaries = new Boundaries();

    @Override
    public void apply(Scene scene) {
        SoccerBall soccerBall = new SoccerBall();
        scene.addUpdateable(soccerBall);
        scene.getCameraNode().addChild(soccerBall.getDrawable());
        SoccerGoal soccerGoal = new SoccerGoal(1);
        scene.addUpdateable(soccerGoal);
        scene.getCameraNode().addChild(soccerGoal.getDrawable());
        if (Utility.getPlayers().getPlayers().isEmpty()) {
            DangineControllerAssignments.forceSetDeviceForPlayer(0, Device.KEYBOARD_LEFT);
            Utility.getPlayers().newPlayer();
            scene.getMatchOrchestrator().getScoreKeeper().addPlayerToGame(Utility.getPlayers().getPlayers().size() - 1);
        }
        scene.addUpdateable(new ReturnToMenuChecker());
        for (int i = 0; i < Utility.getPlayers().getPlayers().size(); i++) {
            Hero hero = new Hero(i);
            GreatSword greatsword = new GreatSword(i, new PlayerGreatswordInputProvider());
            hero.setPosition(100 + (i * 100), 200);
            hero.equipWeapon(greatsword);
            Utility.getActiveScene().getCameraNode().addChild(hero.getDrawable());
            Utility.getActiveScene().addUpdateable(hero);
            Utility.getActiveScene().addUpdateable(greatsword);
        }
        scene.addUpdateable(background);
        scene.getParentNode().addChild(background.getDrawable());
        scene.addUpdateable(boundaries);
        scene.getParentNode().addChild(scene.getMatchOrchestrator().getScoreKeeper().getDrawable());
    }

}
