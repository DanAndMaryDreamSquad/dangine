package dangine.entity.visual;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DangineBox;
import dangine.graphics.DanginePictureParticle;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.scenegraph.drawable.ParticleEffectFactory;
import dangine.utility.MathUtility;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class FinalDefeatVisual implements IsUpdateable, HasDrawable {
    final int NUMBER_OF_LINES = 5;
    final int LINE_WIDTH = 20;
    final int TIME_BETWEEN_LINES = 200;
    final float DELAY = 100f;
    final float MAX_TIME = 2000f;
    final float SPEED = 0.75f;
    final float SPIN_SPEED = 0.025f;
    float timer = 0;
    float scaleY = 1.0f;
    float x, y;
    boolean active = false;
    SceneGraphNode node = new SceneGraphNode();
    List<SceneGraphNode> childNodes = new ArrayList<SceneGraphNode>();
    List<SceneGraphNode> lineNodes = new ArrayList<SceneGraphNode>();
    List<Vector2f> velocities = new ArrayList<Vector2f>();
    BloxSceneGraph blox;

    public FinalDefeatVisual(float x, float y, BloxSceneGraph blox) {
        this.x = x;
        this.y = y;
        this.blox = blox;
        for (int i = 0; i < NUMBER_OF_LINES; i++) {
            SceneGraphNode childNode = new SceneGraphNode();
            node.addChild(childNode);
            childNodes.add(childNode);
            float angle = MathUtility.randomFloat(0, 90.0f) + ((i % 4) * 90.0f);
            childNode.setAngle(angle);
            SceneGraphNode lineNode = new SceneGraphNode();
            lineNodes.add(lineNode);
            childNode.addChild(lineNode);
        }
        node.setPosition(x, y);
        node.setZValue(0f);
        Utility.getActiveScene().getCameraNode().addChild(blox.getDrawable());
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    @Override
    public void update() {
        timer += Utility.getGameTime().getDeltaTimeF();
        if (timer > DELAY && !active) {
            for (SceneGraphNode lineNode : lineNodes) {
                DangineBox box = new DangineBox(LINE_WIDTH, LINE_WIDTH, new Color(Color.WHITE));
                box.setColor(new Color(Color.WHITE), new Color(255, 255, 255, 0), new Color(255, 255, 255, 0),
                        new Color(Color.WHITE));
                lineNode.addChild(box);
                lineNode.setPosition(-LINE_WIDTH / 2, LINE_WIDTH / 4);
            }
            active = true;
        }
        if (active) {
            float totalSpin = timer * SPIN_SPEED;
            node.setAngle(totalSpin);
            for (int i = 0; i < lineNodes.size(); i++) {
                float y = (timer - (i * TIME_BETWEEN_LINES)) * SPEED;
                y = Math.max(0, y);
                y = Math.min(10, y);
                lineNodes.get(i).setScale(1.0f, y);

            }
        }
        if (timer >= MAX_TIME) {
            Utility.getActiveScene().removeUpdateable(this);
            Utility.getActiveScene().getCameraNode().removeChild(node);
            Utility.getActiveScene().getCameraNode().removeChild(blox.getDrawable());

            DanginePictureParticle particle = ParticleEffectFactory.create(18, 25, ParticleEffectFactory.fireColors);
            ExplosionVisual visual = new ExplosionVisual(x, y, particle, 0, 360, 0.36f, 1500f);
            Utility.getActiveScene().addUpdateable(visual);
            Utility.getActiveScene().getCameraNode().addChild(visual.getDrawable());
        }

    }
}