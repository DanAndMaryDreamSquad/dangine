package dangine.entity.gameplay;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.debugger.Debugger;
import dangine.entity.Hero;
import dangine.entity.combat.GreatSword;
import dangine.entity.combat.PlayerGreatswordInputProvider;
import dangine.entity.visual.ExplosionVisual;
import dangine.entity.visual.RespawnVisual;
import dangine.harness.Provider;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

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
        Utility.getActiveScene().getCamera().retrack();
        SoundPlayer.play(SoundEffect.RESPAWN_END);
        Debugger.info("Player: " + playerId + " spawning in");

        final Hero hero = new Hero(playerId);
        GreatSword greatsword = new GreatSword(playerId, new PlayerGreatswordInputProvider());
        hero.setPosition(x, y);
        hero.equipWeapon(greatsword);
        Utility.getMatchParameters().givePlayerPower(hero);
        Utility.getActiveScene().getCameraNode().addChild(hero.getDrawable());
        Utility.getActiveScene().addUpdateable(hero);
        Utility.getActiveScene().addUpdateable(greatsword);
        Invincibility invincibility = new Invincibility(hero);
        Utility.getActiveScene().addUpdateable(invincibility);

        hero.update();

        int lives = Utility.getActiveScene().getMatchOrchestrator().getScoreKeeper().getPlayerScore(playerId)
                .getStock();
        Provider<Vector2f> positionProvider = new Provider<Vector2f>() {
            @Override
            public Vector2f get() {
                return hero.getPosition();
            }
        };
        LifeIndicator lifeIndicator = new LifeIndicator(positionProvider, playerId, lives);
        Utility.getActiveScene().addUpdateable(lifeIndicator);
        Utility.getActiveScene().getCameraNode().addChild(lifeIndicator.getDrawable());

        ExplosionVisual visual = RespawnVisual.createRespawnVisual(x, y);
        Utility.getActiveScene().addUpdateable(visual);
        Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());

        Utility.getActiveScene().getUpdateable(Boundaries.class).rescanHeroes();
    }

}
