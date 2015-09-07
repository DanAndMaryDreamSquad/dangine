package dangine.entity.visual;

import org.lwjgl.util.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DangineBox;
import dangine.menu.DangineMenuItem.Action;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class SceneChangeVisual implements IsUpdateable, HasDrawable {

    final float SPEED = 8.0f;
    SceneGraphNode base = new SceneGraphNode();
    float timer = 0;
    boolean active = false;
    Action action = null;

    enum Direction {
        ON, OFF, NONE;
    }

    Direction direction = Direction.NONE;

    public SceneChangeVisual() {
        DangineBox mid = new DangineBox((int) Utility.getResolution().x, (int) Utility.getResolution().y, new Color(
                Color.BLACK));
        SceneGraphNode midNode = new SceneGraphNode();
        midNode.setPosition(0, 0);
        midNode.addChild(mid);

        DangineBox fade = new DangineBox((int) Utility.getResolution().x, (int) Utility.getResolution().y, new Color(
                Color.BLACK));
        SceneGraphNode fadeNode = new SceneGraphNode();
        fade.setColor(new Color(Color.BLACK), new Color(0, 0, 0, 0), new Color(0, 0, 0, 0), new Color(Color.BLACK));
        fadeNode.addChild(fade);
        fadeNode.setPosition(0, Utility.getResolution().y);

        DangineBox fade2 = new DangineBox((int) Utility.getResolution().x, (int) Utility.getResolution().y, new Color(
                Color.BLACK));
        SceneGraphNode fadeNode2 = new SceneGraphNode();
        fade2.setColor(new Color(0, 0, 0, 0), new Color(Color.BLACK), new Color(Color.BLACK), new Color(0, 0, 0, 0));
        fadeNode2.addChild(fade2);
        fadeNode2.setPosition(0, -Utility.getResolution().y);

        base.addChild(fadeNode);
        base.addChild(fadeNode2);
        base.addChild(midNode);
        base.setZValue(-100f);
    }

    public SceneChangeVisual startOnScreen() {
        return this;
    }

    public SceneChangeVisual startOffScreen() {
        return this;
    }

    public void moveOffScreen() {
        Utility.getActiveScene().addUpdateable(this);
        Utility.getActiveScene().getParentNode().addChild(this.getDrawable());
        active = true;
        direction = Direction.OFF;
    }

    public void moveOnScreen(Action action) {
        Utility.getActiveScene().addUpdateable(this);
        Utility.getActiveScene().getParentNode().addChild(this.getDrawable());
        active = true;
        direction = Direction.ON;
        timer = 0;
        this.action = action;
    }

    public void clear() {
        Utility.getActiveScene().removeUpdateable(this);
        Utility.getActiveScene().getParentNode().removeChild(this.getDrawable());
        active = false;
        direction = Direction.NONE;
    }

    public boolean isDone() {
        float y = 0;
        switch (direction) {
        case NONE:
            return false;
        case ON:
            y = timer * SPEED;
            if (y > Utility.getResolution().y * 2) {
                return true;
            }
            break;
        case OFF:
            y = timer * SPEED;
            if (y > Utility.getResolution().y * 2) {
                return true;
            }
            break;
        }
        return false;
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    @Override
    public void update() {
        if (direction == Direction.NONE) {
            return;
        }
        timer += Utility.getGameTime().getDeltaTimeF();
        float y = timer * SPEED;
        y = Math.min(y, Utility.getResolution().y * 2);
        if (y >= Utility.getResolution().y * 2) {
            if (Direction.OFF == direction) {
                clear();
            }
            if (Direction.ON == direction) {
                if (action != null) {
                    action.execute();
                }
            }
        }
        if (direction == Direction.OFF) {
            base.setPosition(0, y);
        }
        if (direction == Direction.ON) {
            base.setPosition(0, -((Utility.getResolution().y * 2) - y));
        }
    }

}
