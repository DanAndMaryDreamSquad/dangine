package dangine.entity.world;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineImage;
import dangine.utility.Utility;

public class Background implements HasDrawable, IsUpdateable {

    final Tessellation tessellation;
    DangineImage background1;
    DangineImage background2;
    DangineImage background3;
    DangineImage background4;
    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode node1 = new SceneGraphNode();
    SceneGraphNode node2 = new SceneGraphNode();
    SceneGraphNode node3 = new SceneGraphNode();
    SceneGraphNode node4 = new SceneGraphNode();
    float scale = 1.0f;
    float panSpeedX = 0.0f;
    float panSpeedY = 0.0f;

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    public Background(World world) {
        this.tessellation = world.getTessellation();
        background1 = new DangineImage(world.getBgImage());
        background2 = new DangineImage(world.getBgImage());
        background3 = new DangineImage(world.getBgImage());
        background4 = new DangineImage(world.getBgImage());
        panSpeedX = world.getPanSpeedX();
        panSpeedY = world.getPanSpeedY();

        // scale = (int) (Utility.getResolution().x / background.getWidth());
        scale = (scale * world.getScale());
        base.addChild(node2);
        base.addChild(node1);
        base.addChild(node3);
        base.addChild(node4);
        base.setZValue(1.0f);

        node1.addChild(background1);
        node1.setZValue(1.0f);
        node1.setScale(scale, scale);

        node2.addChild(background2);
        node2.setZValue(1.0f);
        node2.setScale(scale, scale);

        node3.addChild(background3);
        node3.setZValue(1.0f);
        node3.setScale(scale, scale);

        node4.addChild(background4);
        node4.setZValue(1.0f);
        node4.setScale(scale, scale);

        switch (tessellation) {
        case NONE:
            break;
        case FLIP_TO_SMOOTH:
            node2.setHorzitontalFlip(-1);
            node3.setVerticalFlip(-1);
            node4.setHorzitontalFlip(-1);
            node4.setVerticalFlip(-1);
            break;
        }

        x2 = -background1.getWidth() * scale;
        y2 = -background1.getHeight() * scale;

    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    @Override
    public void update() {
        x1 += Utility.getGameTime().getDeltaTimeF() * panSpeedX;
        x2 += Utility.getGameTime().getDeltaTimeF() * panSpeedX;
        if (panSpeedX > 0) {
            if (x1 > background1.getWidth() * scale) {
                x1 += -background1.getWidth() * scale * 2;
            }
            if (x2 > background1.getWidth() * scale) {
                x2 += -background1.getWidth() * scale * 2;
            }
        }
        if (panSpeedX < 0) {
            if (x1 < -background1.getWidth() * scale) {
                x1 -= -background1.getWidth() * scale * 2;
            }
            if (x2 < -background1.getWidth() * scale) {
                x2 -= -background1.getWidth() * scale * 2;
            }
        }
        y1 += Utility.getGameTime().getDeltaTimeF() * panSpeedY;
        y2 += Utility.getGameTime().getDeltaTimeF() * panSpeedY;
        if (panSpeedY > 0) {
            if (y1 > background1.getHeight() * scale) {
                y1 += -background1.getHeight() * scale * 2;
            }
            if (y2 > background1.getHeight() * scale) {
                y2 += -background1.getHeight() * scale * 2;
            }
        }
        if (panSpeedY < 0) {
            if (y1 < -background1.getHeight() * scale) {
                y1 -= -background1.getHeight() * scale * 2;
            }
            if (y2 < -background1.getHeight() * scale) {
                y2 -= -background1.getHeight() * scale * 2;
            }
        }
        x1 = Math.round(x1);
        x2 = Math.round(x2);
        y1 = Math.round(y1);
        y2 = Math.round(y2);

        switch (tessellation) {
        case NONE:
            node1.setPosition(x1, y1);
            node3.setPosition(x1, y2);
            node2.setPosition(x2, y1);
            node4.setPosition(x2, y2);
            break;
        case FLIP_TO_SMOOTH:
            node1.setPosition(x1, y1);
            node3.setPosition(x1, y2 + background1.getHeight() * scale);
            node2.setPosition(x2 + background1.getWidth() * scale, y1);
            node4.setPosition(x2 + background1.getWidth() * scale, y2 + background1.getHeight() * scale);
            break;
        }
    }

}
