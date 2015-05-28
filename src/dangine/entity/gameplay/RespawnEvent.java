package dangine.entity.gameplay;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.debugger.Debugger;
import dangine.entity.Hero;
import dangine.entity.combat.GreatSword;
import dangine.entity.visual.ExplosionVisual;
import dangine.entity.visual.RespawnVisual;
import dangine.utility.Utility;

public class RespawnEvent implements MatchEvent {

    int playerId = 0;
    float x;
    float y;

    public RespawnEvent(float x, float y, int playerId) {
        this.x = x;
        this.y = y;
        this.playerId = playerId;
    }

    @Override
    public void process() {
        SoundPlayer.play(SoundEffect.RESPAWN_END);
        Debugger.info("Player: " + playerId + " spawning in");

        Hero hero = new Hero(playerId);
        GreatSword greatsword = new GreatSword(playerId);
        hero.setPosition(x, y);
        hero.equipWeapon(greatsword);
        Utility.getMatchParameters().givePlayerPower(hero);
        Utility.getActiveScene().getCameraNode().addChild(hero.getDrawable());
        Utility.getActiveScene().addUpdateable(hero);
        Utility.getActiveScene().addUpdateable(greatsword);
        Invincibility invincibility = new Invincibility(hero);
        Utility.getActiveScene().addUpdateable(invincibility);

        hero.update();

        // RespawnVisual visual = new RespawnVisual((playerId + 1) * 200, 400);
        ExplosionVisual visual = RespawnVisual.createRespawnVisual(x, y);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());

        Utility.getActiveScene().getUpdateable(Boundaries.class).rescanHeroes();
    }

}
