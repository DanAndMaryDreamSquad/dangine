package dangine.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import dangine.scenegraph.DangineShape;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class Creature implements IsUpdateable, IsDrawable {

    final float speed = 0.08f;
    final Vector2f position = new Vector2f(0, 0);
    float angle = 0;

    SceneGraphNode node = new SceneGraphNode();

    public Creature() {
        position.y = 100;
        node = new SceneGraphNode();
        node.setPosition(position);
        node.addChild(new DangineShape());
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
        arm.addChild(new DangineShape(10, 10, Color.blue));
        return arm;
    }

    @Override
    public void draw() {
        node.draw();
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

}
