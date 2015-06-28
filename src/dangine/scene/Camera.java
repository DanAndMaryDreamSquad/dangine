package dangine.scene;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Matrix4;

import dangine.bots.BotRespawner;
import dangine.bots.DangineBot;
import dangine.entity.HasDrawable;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.Respawner;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.MathUtility;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class Camera implements IsUpdateable, HasDrawable {

    SceneGraphNode cameraNode = new SceneGraphNode();
    Matrix4 screenToWorldTransform = new Matrix4();
    List<Vector2f> trackings = new ArrayList<Vector2f>();
    boolean retrack = false;
    boolean enabled = true;
    float offset = 0;
    float x = 0;
    float y = 0;
    float scale = 1.5f;
    float minX = 9999;
    float maxX = -9999;
    float minY = 9999;
    float maxY = -9999;
    float MIN_SCALE = 1.0f;
    float MAX_SCALE = 2.0f;
    float AREA_BETWEEN_CHARACTER_AND_EDGE_FACTOR = 2.5f;
    float DISTANCE_FOR_MAX_SCALE = 100;
    float DISTANCE_FOR_MIN_SCALE = 600;

    public SceneGraphNode getCameraNode() {
        return cameraNode;
    }

    @Override
    public IsDrawable getDrawable() {
        return cameraNode;
    }

    @Override
    public void update() {
        if (Utility.getPlayers().getPlayers().size() != trackings.size() || retrack) {
            trackings.clear();
            for (int i = 0; i < Utility.getPlayers().getPlayers().size(); i++) {
                Hero hero = Utility.getActiveScene().getHero(i);
                if (hero != null) {
                    trackings.add(hero.getPosition());
                }
            }
            for (int i = 1; i < Utility.getMatchParameters().getNumberOfBots() + 1; i++) {
                DangineBot bot = Utility.getActiveScene().getBot(-i);
                if (bot != null) {
                    trackings.add(bot.getPosition());
                }
            }
            List<Respawner> respawners = Utility.getActiveScene().getUpdateables(Respawner.class);
            List<BotRespawner> botRespawners = Utility.getActiveScene().getUpdateables(BotRespawner.class);

            for (Respawner respawner : respawners) {
                trackings.add(respawner.getPosition());
            }
            for (BotRespawner botRespawner : botRespawners) {
                trackings.add(botRespawner.getPosition());
            }
        }
        x = 0;
        y = 0;
        minX = 9999;
        maxX = -9999;
        minY = 9999;
        maxY = -9999;
        for (Vector2f position : trackings) {
            x += position.x;
            y += position.y;
            minX = Math.min(minX, position.x);
            maxX = Math.max(maxX, position.x);
            minY = Math.min(minY, position.y);
            maxY = Math.max(maxY, position.y);
        }
        calculateZoomFromDistances();
        x = x / trackings.size();
        y = y / trackings.size();
        x -= Utility.getResolution().x / (2.0f * scale);
        y -= Utility.getResolution().y / (2.0f * scale);
        x = -x;
        y = -y;
        x *= scale;
        y *= scale;
        enforceBoundaries();
        if (trackings.size() == 0 || !isEnabled()) {
            cameraNode.setScale(MIN_SCALE, MIN_SCALE);
            cameraNode.setPosition(0, 0);
            screenToWorldTransform.idt();
            screenToWorldTransform.inv();
        } else {
            cameraNode.setScale(scale, scale);
            cameraNode.setPosition(x, y);
            updateSceneToWorldTransformation();
        }

    }

    private void calculateZoomFromDistances() {
        if (trackings.size() <= 1) {
            scale = MIN_SCALE;
            return;
        }
        float distanceX = maxX - minX;
        float distanceY = maxY - minY;
        float xpercent = (distanceX - DISTANCE_FOR_MAX_SCALE) / (DISTANCE_FOR_MIN_SCALE - DISTANCE_FOR_MAX_SCALE);
        xpercent = Math.max(0, xpercent);
        float ypercent = (distanceY - DISTANCE_FOR_MAX_SCALE) / (DISTANCE_FOR_MIN_SCALE - DISTANCE_FOR_MAX_SCALE);
        ypercent = Math.max(0, ypercent);
        xpercent = MathUtility.logFunction(xpercent);
        ypercent = MathUtility.logFunction(ypercent);
        xpercent = 1.0f - xpercent;
        ypercent = 1.0f - ypercent;
        float fx = MathUtility.rangify(MIN_SCALE, MAX_SCALE, xpercent);
        float fy = MathUtility.rangify(MIN_SCALE, MAX_SCALE, ypercent);

        scale = Math.min(fx, fy);
        scale = Math.min(MAX_SCALE, scale);
        scale = Math.max(MIN_SCALE, scale);

    }

    private void enforceBoundaries() {
        float xBuffer = (scale - 1.0f) * Utility.getResolution().x;
        float yBuffer = (scale - 1.0f) * Utility.getResolution().y;
        x = Math.max(x, 0 - xBuffer);
        x = Math.min(x, 0);
        y = Math.max(y, 0 - yBuffer);
        y = Math.min(y, 0);
    }

    private void updateSceneToWorldTransformation() {
        Vector2f position = cameraNode.getPosition();
        Vector2f centerOfRotation = cameraNode.getCenterOfRotation();
        float angle = cameraNode.getAngle();
        Vector2f scale = cameraNode.getScale();
        screenToWorldTransform.idt();
        screenToWorldTransform.translate(position.x, position.y, 0);
        screenToWorldTransform.translate(centerOfRotation.x, centerOfRotation.y, 0);
        screenToWorldTransform.rotate(0, 0, 1, angle);
        screenToWorldTransform.translate(-centerOfRotation.x, -centerOfRotation.y, 0);
        screenToWorldTransform.scale(scale.x, scale.y, 1.0f);
        screenToWorldTransform.inv();
    }

    public Matrix4 getScreenToWorldTransform() {
        return screenToWorldTransform;
    }

    public void increaseZoom() {
        scale += 0.5f;
    }

    public void decreaseZoom() {
        scale -= 0.5f;
    }

    public void retrack() {
        this.retrack = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
