package dangine.animation;

import dangine.scenegraph.SceneGraphNode;
import dangine.utility.MathUtility;
import dangine.utility.Vector2f;

public class Orientation {

    Vector2f position;
    Vector2f centerOfRotation;
    float angle;

    public Orientation(Vector2f position, Vector2f centerOfRotation, float angle) {
        this.position = position;
        this.centerOfRotation = centerOfRotation;
        this.angle = angle;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getCenterOfRotation() {
        return centerOfRotation;
    }

    public float getAngle() {
        return angle;
    }

    public void apply(SceneGraphNode node, float scale) {
        node.setPosition(position.x * scale, position.y * scale);
        node.setCenterOfRotation(centerOfRotation.x * scale, centerOfRotation.y * scale);
        node.setAngle(angle);
    }

    public static void apply(SceneGraphNode node, Orientation source, Orientation target, float scale, float percent) {
        float x = MathUtility.rangify(source.getPosition().x, target.getPosition().x, percent);
        float y = MathUtility.rangify(source.getPosition().y, target.getPosition().y, percent);
        float centerX = MathUtility.rangify(source.getCenterOfRotation().x, target.getCenterOfRotation().x, percent);
        float centerY = MathUtility.rangify(source.getCenterOfRotation().y, target.getCenterOfRotation().y, percent);
        float angle = MathUtility.rangify(source.getAngle(), target.getAngle(), percent);

        node.setPosition(x * scale, y * scale);
        node.setCenterOfRotation(centerX * scale, centerY * scale);
        node.setAngle(angle);
    }

    public static void applyShortestPath(SceneGraphNode node, Orientation source, Orientation target, float scale,
            float percent) {
        float x = MathUtility.rangify(source.getPosition().x, target.getPosition().x, percent);
        float y = MathUtility.rangify(source.getPosition().y, target.getPosition().y, percent);
        float centerX = MathUtility.rangify(source.getCenterOfRotation().x, target.getCenterOfRotation().x, percent);
        float centerY = MathUtility.rangify(source.getCenterOfRotation().y, target.getCenterOfRotation().y, percent);
        float angle;
        if (Math.abs(source.getAngle() - target.getAngle()) > 180f) {
            if (source.getAngle() > target.getAngle()) {
                angle = MathUtility.rangify(source.getAngle() - 360f, target.getAngle(), percent);
            } else {
                angle = MathUtility.rangify(source.getAngle(), target.getAngle() - 360f, percent);
            }
        } else {
            angle = MathUtility.rangify(source.getAngle(), target.getAngle(), percent);
        }

        node.setPosition(x * scale, y * scale);
        node.setCenterOfRotation(centerX * scale, centerY * scale);
        node.setAngle(angle);
    }

}
