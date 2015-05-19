package dangine.scenegraph;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Matrix4;

import dangine.entity.IsDrawable;
import dangine.graphics.DangineOpenGL;
import dangine.graphics.IsDrawable32;
import dangine.graphics.RenderData32;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

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
    List<IsDrawable32> children32 = new ArrayList<IsDrawable32>();
    Matrix4 matrix = new Matrix4();

    public void propagate() {
        for (SceneGraphNode childNode : childNodes) {
            childNode.updateTransformsAndPropagate();
        }
        for (IsDrawable child : children) {
            RenderData data = child.getRenderData();
            data.updateBuffer(getMatrix());
            Utility.getRenderQueue().add(data);
        }
        for (IsDrawable32 child : children32) {
            child.getNode().transform();
            RenderData32 data = child.getRenderData32();
            data.updateBuffer(child.getNode().getMatrix());
            Utility.getRenderQueue32().add(data);
        }
    }

    public void updateTransformsAndPropagate() {
        transform();
        for (SceneGraphNode childNode : childNodes) {
            childNode.updateTransformsAndPropagate();
        }
        for (IsDrawable child : children) {
            RenderData data = child.getRenderData();
            data.updateBuffer(getMatrix());
            Utility.getRenderQueue().add(data);
        }
        for (IsDrawable32 child : children32) {
            child.getNode().transform();
            RenderData32 data = child.getRenderData32();
            data.updateBuffer(child.getNode().getMatrix());
            Utility.getRenderQueue32().add(data);
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

    public void pullTransformsFromSelf() {
        this.pullTransformsFromMatrix(getMatrix());
    }

    // TODO get the scale and angles right.
    public void pullTransformsFromMatrix(Matrix4 pullMatrix) {
        position.x = pullMatrix.val[Matrix4.M03];
        position.x = ((position.x * DangineOpenGL.WIDTH) / 2.0f) + (DangineOpenGL.WIDTH / 2.0f);
        position.y = pullMatrix.val[Matrix4.M13];
        position.y = DangineOpenGL.HEIGHT
                - (((position.y * DangineOpenGL.HEIGHT) / 2.0f) + (DangineOpenGL.HEIGHT / 2.0f));
        zValue = pullMatrix.val[Matrix4.M23] * 1000;

        scale.x = pullMatrix.getScaleX();
        if (pullMatrix.val[Matrix4.M00] < 0) {
            horzitontalFlip = -1;
        }
        scale.y = pullMatrix.getScaleY();
        if (pullMatrix.val[Matrix4.M11] < 0) {
            verticalFlip = -1;
        }

        angle = (float) -Math.asin(pullMatrix.getValues()[Matrix4.M01] / scale.y);
        angle = (float) Math.toDegrees(angle);
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

    public void addChild(IsDrawable32 drawable) {
        drawable.getNode().setParent(this);
        children32.add(drawable);
    }

    public void removeChild(IsDrawable target) {
        if (target instanceof SceneGraphNode) {
            SceneGraphNode n = (SceneGraphNode) target;
            childNodes.remove(n);
            return;
        }
        children.remove(target);
    }

    public void removeChild(IsDrawable32 target) {
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

    public void setVerticalFlip(int verticalFlip) {
        this.verticalFlip = verticalFlip;
    }

    public Matrix4 getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix4 matrix) {
        this.matrix = matrix;
    }

    @Override
    public void draw() {
        // never called
    }

    @Override
    public RenderData getRenderData() {
        return null;
    }

    public List<SceneGraphNode> getChildNodes() {
        return childNodes;
    }

    public List<IsDrawable> getChildren() {
        return children;
    }

    public List<IsDrawable32> getChildren32() {
        return children32;
    }

    @Override
    public IsDrawable copy() {
        // TODO Auto-generated method stub
        return null;
    }

    public Vector2f getCenterOfRotation() {
        return centerOfRotation;
    }
}
