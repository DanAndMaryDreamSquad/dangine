package dangine.entity.gameplay;

import dangine.debugger.Debugger;
import dangine.entity.Hero;
import dangine.entity.combat.GreatSword;
import dangine.entity.visual.RespawnVisual;
import dangine.utility.Utility;

public class RespawnEvent implements MatchEvent {

    int playerId = 0;

    public RespawnEvent(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void process() {
        Debugger.info("Player: " + playerId + " spawning in");

        Hero hero = new Hero(playerId);
        GreatSword greatsword = new GreatSword(playerId);
        hero.setPosition((playerId + 1) * 200, 400);
        hero.equipWeapon(greatsword);
        Utility.getActiveScene().getCameraNode().addChild(hero.getDrawable());
        Utility.getActiveScene().addUpdateable(hero);
        Utility.getActiveScene().addUpdateable(greatsword);
        Invincibility invincibility = new Invincibility(hero);
        Utility.getActiveScene().addUpdateable(invincibility);

        hero.update();

        RespawnVisual visual = new RespawnVisual((playerId + 1) * 200, 400);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());

    }

}
