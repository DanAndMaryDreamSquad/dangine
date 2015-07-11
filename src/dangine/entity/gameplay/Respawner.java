package dangine.entity.gameplay;

import dangine.audio.SoundEffect;
import dangine.audio.SoundPlayer;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.entity.visual.ExplosionVisual;
import dangine.entity.visual.RespawnVisual;
import dangine.utility.MathUtility;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class Respawner implements IsUpdateable {

    final float MAX_TIME = 2000f;
    final float PULSE_MAX_TIME = 500f;
    final float SPAWN_POSITION_BUFFER = 50f;
    float pulseTimer = 0;
    float timer = 0;
    final int playerId;
    Vector2f position;

    public Respawner(int playerId) {
        this.playerId = playerId;
        float x;
        float y;
        if (Utility.getMatchParameters().getMatchType().isTeamMode()
                && Utility.getMatchParameters().getMatchType() == MatchType.SOCCER) {
            float left;
            float right;
            if (Utility.getMatchParameters().getPlayerIdToTeam().get(playerId) == 0) {
                left = SPAWN_POSITION_BUFFER;
                right = (Utility.getResolution().x / 2) - SPAWN_POSITION_BUFFER;
            } else {
                left = (Utility.getResolution().x / 2) + SPAWN_POSITION_BUFFER;
                right = Utility.getResolution().x - SPAWN_POSITION_BUFFER;
            }
            x = MathUtility.randomFloat(left, right);
            y = MathUtility.randomFloat(Utility.getResolution().y * 0.25f, Utility.getResolution().y * 0.75f);
        } else {
            x = MathUtility.randomFloat(0 + SPAWN_POSITION_BUFFER, Utility.getResolution().x - SPAWN_POSITION_BUFFER);
            y = MathUtility.randomFloat(0 + SPAWN_POSITION_BUFFER, Utility.getResolution().y - SPAWN_POSITION_BUFFER);
        }
        this.position = new Vector2f(x, y);
        Utility.getActiveScene().getCamera().retrack();
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
            ExplosionVisual visual = RespawnVisual.createRespawnVisual(position.x, position.y);
            Utility.getActiveScene().addUpdateable(visual);
            Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
        }
        if (timer > MAX_TIME) {
            Utility.getActiveScene().getMatchOrchestrator()
                    .addEvent(new RespawnEvent(position.x, position.y, getPlayerId()));
            Utility.getActiveScene().removeUpdateable(this);

        }

    }

    public Vector2f getPosition() {
        return position;
    }

    public int getPlayerId() {
        return playerId;
    }

}
