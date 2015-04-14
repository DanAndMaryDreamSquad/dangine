package dangine.scene;

import dangine.entity.Creature;
import dangine.entity.Hero;
import dangine.entity.Obstruction;
import dangine.entity.combat.GreatSword;
import dangine.entity.gameplay.Boundaries;
import dangine.entity.gameplay.MatchParameters;
import dangine.entity.world.Background;
import dangine.entity.world.PanningSceneGraph;
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
        for (int i = 0; i < Utility.getPlayers().getPlayers().size(); i++) {
            Hero hero = new Hero(i);
            GreatSword greatsword = new GreatSword(i);
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
