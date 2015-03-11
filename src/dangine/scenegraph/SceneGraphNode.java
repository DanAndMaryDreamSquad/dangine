package dangine.scenegraph;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.geom.Vector2f;

import dangine.entity.IsDrawable;

public class SceneGraphNode implements IsDrawable {

    float angle = 0.0f;
    final Vector2f position = new Vector2f(0, 0);
    final Vector2f scale = new Vector2f(1, 1);
    final Vector2f centerOfRotation = new Vector2f(0, 0);
    float zValue = 0.0f;
    int horzitontalFlip = 1;
    int verticalFlip = 1;
    SceneGraphNode parent = null;
    List<IsDrawable> children = new ArrayList<IsDrawable>();
    IsDrawable thing = null;

    @Override
    public void draw() {
        GL11.glPushMatrix();
        transform();
        if (thing != null) {
            thing.draw();
        }
        for (IsDrawable drawable : children) {
            drawable.draw();
        }
        GL11.glPopMatrix();
    }

    public void transform() {
        GL11.glTranslatef(position.x, position.y, zValue / 1000);
        GL11.glTranslatef(centerOfRotation.x, centerOfRotation.y, 0);
        GL11.glRotatef(angle, 0, 0, 1.0f);
        GL11.glTranslatef(-centerOfRotation.x, -centerOfRotation.y, 0);
        GL11.glScalef(scale.x * horzitontalFlip, scale.y * verticalFlip, 1.0f);
    }

    public void addChild(SceneGraphNode child) {
        children.add(child);
        child.setParent(this);
    }

    public void addChild(IsDrawable drawable) {
        children.add(drawable);
    }

    public void removeChild(IsDrawable target) {
        children.remove(target);
    }

    public void setPosition(Vector2f position) {
        this.setPosition(position.x, position.y);
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void setThing(IsDrawable thing) {
        this.thing = thing;
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

}
