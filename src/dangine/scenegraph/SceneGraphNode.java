package dangine.scenegraph;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import com.badlogic.gdx.math.Matrix4;

import dangine.entity.IsDrawable;
import dangine.utility.Utility;

public class SceneGraphNode implements IsDrawable {

    float angle = 0.0f;

    final Vector2f position = new Vector2f(0, 0);
    final Vector2f scale = new Vector2f(1, 1);
    final Vector2f centerOfRotation = new Vector2f(0, 0);
    float zValue = 0.0f;
    int horzitontalFlip = 1;
    int verticalFlip = 1;
    SceneGraphNode parent = null;
    List<SceneGraphNode> childNodes = new ArrayList<SceneGraphNode>();
    List<IsDrawable> children = new ArrayList<IsDrawable>();
    Matrix4 matrix = new Matrix4();

    @Override
    public void draw() {
        // never called
    }

    public void updateTransformsAndPropagate() {
        transform();
        for (SceneGraphNode childNode : childNodes) {
            childNode.updateTransformsAndPropagate();
        }
        for (IsDrawable child : children) {
            RenderData data = new RenderData(child);
            data.updateBuffer(getMatrix());
            Utility.getRenderQueue().add(data);
        }
    }

    public void transform() {
        matrix.idt();
        matrix.translate(position.x, position.y, zValue / 1000);
        matrix.translate(centerOfRotation.x, centerOfRotation.y, 0);
        matrix.rotate(0, 0, 1, angle);
        matrix.translate(-centerOfRotation.x, -centerOfRotation.y, 0);
        matrix.scale(scale.x * horzitontalFlip, scale.y * verticalFlip, 1.0f);

        if (parent != null) {
            matrix = matrix.mulLeft(parent.getMatrix());
        }
    }

    public void addChild(IsDrawable drawable) {
        if (drawable instanceof SceneGraphNode) {
            SceneGraphNode n = (SceneGraphNode) drawable;
            n.setParent(this);
            childNodes.add(n);
            return;
        }
        children.add(drawable);
    }

    public void removeChild(IsDrawable target) {
        if (target instanceof SceneGraphNode) {
            SceneGraphNode n = (SceneGraphNode) target;
            childNodes.remove(n);
            return;
        }
        children.remove(target);
    }

    public void setPosition(Vector2f position) {
        this.setPosition(position.x, position.y);
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void setCenterOfRotation(Vector2f position) {
        this.setCenterOfRotation(position.x, position.y);
    }

    public void setCenterOfRotation(float x, float y) {
        this.centerOfRotation.x = x;
        this.centerOfRotation.y = y;
    }

    public void setScale(Vector2f scale) {
        this.setScale(scale.x, scale.y);
    }

    public void setScale(float x, float y) {
        this.scale.x = x;
        this.scale.y = y;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public SceneGraphNode getParent() {
        return parent;
    }

    public void setParent(SceneGraphNode parent) {
        this.parent = parent;
    }

    public float getZValue() {
        return zValue;
    }

    public void setZValue(float zValue) {
        this.zValue = zValue;
    }

    public float getAngle() {
        return angle;
    }

    public Vector2f getScale() {
        return scale;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setXPosition(float x) {
        this.position.x = x;
    }

    public void setYPosition(float y) {
        this.position.y = y;
    }

    public void setHorzitontalFlip(int horzitontalFlip) {
        this.horzitontalFlip = horzitontalFlip;
    }

    public Matrix4 getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix4 matrix) {
        this.matrix = matrix;
    }
}
