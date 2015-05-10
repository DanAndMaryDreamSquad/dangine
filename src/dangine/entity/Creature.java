package dangine.entity;

import org.lwjgl.util.Color;
import org.newdawn.slick.geom.Vector2f;

import dangine.graphics.DangineBox;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class Creature implements IsUpdateable, HasDrawable {

    final float speed = 0.08f;
    final Vector2f position = new Vector2f(0, 0);
    float angle = 0;

    SceneGraphNode node = new SceneGraphNode();

    public Creature() {
        position.y = 100;
        node = new SceneGraphNode();
        node.setPosition(position);
        node.addChild(new DangineBox());
        float[][] arms = { { 10, 10 }, { -10, -10 }, { 10, -10 }, { -10, 10 } };
        SceneGraphNode arm = null;
        for (float[] pos : arms) {
            arm = createArm(pos[0], pos[1]);
            arm.setZValue(1.0f);
            node.addChild(arm);
        }

    }

    private SceneGraphNode createArm(float x, float y) {
        SceneGraphNode arm = new SceneGraphNode();
        arm.setPosition(x, y);
        arm.addChild(new DangineBox(10, 10, new Color(Color.BLUE)));
        return arm;
    }

    @Override
    public void update() {
        angle += Utility.getGameTime().getDeltaTime() * speed * 0.5f;
        position.x += Utility.getGameTime().getDeltaTime() * speed;
        node.setPosition(position.x, position.y);
        // node.setAngle(angle);
        if (position.x > 200) {
            position.x = 0;
        }

    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

}
