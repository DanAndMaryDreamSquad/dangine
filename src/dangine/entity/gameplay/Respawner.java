package dangine.entity.gameplay;

import dangine.entity.IsUpdateable;
import dangine.entity.visual.ExplosionVisual;
import dangine.entity.visual.RespawnVisual;
import dangine.utility.MathUtility;
import dangine.utility.Utility;

public class Respawner implements IsUpdateable {

    final float MAX_TIME = 2000f;
    final float PULSE_MAX_TIME = 500f;
    final float SPAWN_POSITION_BUFFER = 50f;
    float pulseTimer = 0;
    float timer = 0;
    final int playerId;
    float x;
    float y;

    public Respawner(int playerId) {
        this.playerId = playerId;
        this.x = MathUtility.randomFloat(0 + SPAWN_POSITION_BUFFER, Utility.getResolution().x - SPAWN_POSITION_BUFFER);
        this.y = MathUtility.randomFloat(0 + SPAWN_POSITION_BUFFER, Utility.getResolution().y - SPAWN_POSITION_BUFFER);
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        pulseTimer += Utility.getGameTime().getDeltaTimeF();
        if (pulseTimer > PULSE_MAX_TIME) {
            pulseTimer = 0;
            ExplosionVisual visual = RespawnVisual.createRespawnVisual(x, y);
            Utility.getActiveScene().addUpdateable(visual);
            Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
        }
        if (timer > MAX_TIME) {
            Utility.getActiveScene().getMatchOrchestrator().addEvent(new RespawnEvent(x, y, getPlayerId()));
            Utility.getActiveScene().removeUpdateable(this);
        }

    }

    public int getPlayerId() {
        return playerId;
    }

}
