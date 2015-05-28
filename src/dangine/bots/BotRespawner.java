package dangine.bots;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.entity.IsUpdateable;
import dangine.entity.visual.ExplosionVisual;
import dangine.entity.visual.RespawnVisual;
import dangine.utility.MathUtility;
import dangine.utility.Utility;

public class BotRespawner implements IsUpdateable {

    final float MAX_TIME = 2000f;
    final float PULSE_MAX_TIME = 500f;
    final float SPAWN_POSITION_BUFFER = 50f;
    float pulseTimer = 0;
    float timer = 0;
    final int botId;
    float x;
    float y;

    public BotRespawner(int botId) {
        this.botId = botId;
        this.x = MathUtility.randomFloat(0 + SPAWN_POSITION_BUFFER, Utility.getResolution().x - SPAWN_POSITION_BUFFER);
        this.y = MathUtility.randomFloat(0 + SPAWN_POSITION_BUFFER, Utility.getResolution().y - SPAWN_POSITION_BUFFER);
    }

    @Override
    public void update() {
        if (timer == 0) {
            SoundPlayer.play(SoundEffect.RESPAWN_START);
        }
        timer += Utility.getGameTime().getDeltaTimeF();
        pulseTimer += Utility.getGameTime().getDeltaTimeF();
        if (pulseTimer > PULSE_MAX_TIME) {
            pulseTimer = 0;
            SoundPlayer.play(SoundEffect.RESPAWN_PULSE);
            ExplosionVisual visual = RespawnVisual.createRespawnVisual(x, y);
            Utility.getActiveScene().addUpdateable(visual);
            Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
        }
        if (timer > MAX_TIME) {
            Utility.getActiveScene().getMatchOrchestrator().addEvent(new BotRespawnEvent(x, y, botId));
            Utility.getActiveScene().removeUpdateable(this);
        }

    }

}
