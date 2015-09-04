package dangine.entity.gameplay;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Color;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DanginePicture;
import dangine.harness.Provider;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class LifeIndicator implements IsUpdateable, HasDrawable {

    final float OFFSET = 20;
    final float DISTANCE = 50;
    int size = 0;
    final float SPEED = 3200;
    float angle = 0;
    final int ownerId;
    float timer = 0;
    int lives;

    SceneGraphNode base = new SceneGraphNode();
    List<SceneGraphNode> lifeNodes = new ArrayList<SceneGraphNode>();

    Provider<Vector2f> positionProvider;

    public LifeIndicator(Provider<Vector2f> positionProvider, int ownerId, int lives) {
        this.ownerId = ownerId;
        this.lives = lives;
        Color color = Utility.getMatchParameters().getPlayerColor(ownerId);
        for (int i = 0; i < lives; i++) {
            DanginePicture image = new DanginePicture("hearticon");
            image.getQuad().setTextureColor(color);
            size = image.getWidth() / 2;
            SceneGraphNode node = new SceneGraphNode();
            node.addChild(image);
            lifeNodes.add(node);
            base.addChild(node);
        }
        positionNodes();
        this.positionProvider = positionProvider;
    }

    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        Vector2f position = positionProvider.get();
        base.setPosition(position.x, position.y);
        base.setAngle(timer * 0.2f);
    }

    public void updateLives(int newLives) {
        Debugger.warn("life update! " + newLives + " " + ownerId);
        lives = newLives;
        Color color = Utility.getMatchParameters().getPlayerColor(ownerId);
        for (int i = lifeNodes.size(); i < lives; i++) {
            DanginePicture image = new DanginePicture("hearticon");
            image.getQuad().setTextureColor(color);
            size = image.getWidth() / 2;
            SceneGraphNode node = new SceneGraphNode();
            node.addChild(image);
            lifeNodes.add(node);
            base.addChild(node);
        }
        positionNodes();
    }

    private void positionNodes() {
        for (int i = 0; i < lives; i++) {
            SceneGraphNode node = lifeNodes.get(i);
            float angle = (360f / (float) lives) * i;
            angle = (float) Math.toRadians(angle);
            Debugger.warn("a " + angle);
            float x = (float) Math.cos(angle) * DISTANCE;
            float y = (float) -Math.sin(angle) * DISTANCE;
            Debugger.warn("x " + x + " y " + y);
            node.setPosition(x - size, y - size);
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    public void destroy() {
        Utility.getActiveScene().removeUpdateable(this);
        Utility.getActiveScene().getCameraNode().removeChild(base);
    }

    public int getOwnerId() {
        return ownerId;
    }

    public Provider<Vector2f> getPositionProvider() {
        return positionProvider;
    }

}
