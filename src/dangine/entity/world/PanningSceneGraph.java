package dangine.entity.world;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DanginePicture;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class PanningSceneGraph implements HasDrawable, IsUpdateable {

    final Tessellation tessellation;
    // DangineImage background1;
    // DangineImage background2;
    // DangineImage background3;
    // DangineImage background4;
    DanginePicture background1;
    DanginePicture background2;
    DanginePicture background3;
    DanginePicture background4;
    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode node1 = new SceneGraphNode();
    SceneGraphNode node2 = new SceneGraphNode();
    SceneGraphNode node3 = new SceneGraphNode();
    SceneGraphNode node4 = new SceneGraphNode();
    float scale = 1.0f;
    float panSpeedX = 0.0f;
    float panSpeedY = 0.0f;
    float angle = 0.0f;

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    final float ROUNDING_MULTIPLE = 10.0f;

    public PanningSceneGraph(Background world) {
        this(world.getBgImage(), world.getPanSpeedX(), world.getPanSpeedY(), world.getScale(), world.getTessellation());
    }

    public PanningSceneGraph(Middleground world) {
        this(world.getBgImage(), world.getPanSpeedX(), world.getPanSpeedY(), world.getScale(), world.getTessellation());
    }

    public PanningSceneGraph(String image, float panSpeedX, float panSpeedY, float scale, Tessellation tessellation) {
        this.tessellation = tessellation;
        background1 = new DanginePicture(image).applyFilter();
        background2 = new DanginePicture(image).applyFilter();
        background3 = new DanginePicture(image).applyFilter();
        background4 = new DanginePicture(image).applyFilter();
        if (this.tessellation == Tessellation.NONE) {
            background1.getQuad().withWrapModeRepeat();
            background2.getQuad().withWrapModeRepeat();
            background3.getQuad().withWrapModeRepeat();
            background4.getQuad().withWrapModeRepeat();
        }
        this.panSpeedX = panSpeedX;
        this.panSpeedY = panSpeedY;

        this.scale = (this.scale * scale);
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
            base.addChild(node2);
            base.addChild(node1);
            base.addChild(node3);
            base.addChild(node4);
            break;
        case FLIP_TO_SMOOTH:
            base.addChild(node2);
            base.addChild(node1);
            base.addChild(node3);
            base.addChild(node4);

            node2.setHorzitontalFlip(-1);
            node3.setVerticalFlip(-1);
            node4.setHorzitontalFlip(-1);
            node4.setVerticalFlip(-1);
            break;
        case CENTER:
            base.addChild(node1);
            float centerX = (Utility.getResolution().x - background1.getWidth()) / 2.0f;
            float centerY = (Utility.getResolution().y - background1.getHeight()) / 2.0f;
            node1.setCenterOfRotation(background1.getWidth() / 2, background1.getHeight() / 2);
            node1.setPosition(centerX, centerY);
            break;
        }

        x2 = -background1.getWidth() * scale * ROUNDING_MULTIPLE;
        y2 = -background1.getHeight() * scale * ROUNDING_MULTIPLE;

    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    @Override
    public void update() {
        x1 += Utility.getGameTime().getDeltaTimeF() * panSpeedX * ROUNDING_MULTIPLE;
        x2 += Utility.getGameTime().getDeltaTimeF() * panSpeedX * ROUNDING_MULTIPLE;
        if (panSpeedX > 0) {
            if (x1 / ROUNDING_MULTIPLE > background1.getWidth() * scale) {
                x1 += -background1.getWidth() * scale * 2 * ROUNDING_MULTIPLE;
            }
            if (x2 / ROUNDING_MULTIPLE > background1.getWidth() * scale) {
                x2 += -background1.getWidth() * scale * 2 * ROUNDING_MULTIPLE;
            }
        }
        if (panSpeedX < 0) {
            if (x1 / ROUNDING_MULTIPLE < -background1.getWidth() * scale) {
                x1 -= -background1.getWidth() * scale * 2 * ROUNDING_MULTIPLE;
            }
            if (x2 / ROUNDING_MULTIPLE < -background1.getWidth() * scale) {
                x2 -= -background1.getWidth() * scale * 2 * ROUNDING_MULTIPLE;
            }
        }
        y1 += Utility.getGameTime().getDeltaTimeF() * panSpeedY * ROUNDING_MULTIPLE;
        y2 += Utility.getGameTime().getDeltaTimeF() * panSpeedY * ROUNDING_MULTIPLE;
        if (panSpeedY > 0) {
            if (y1 / ROUNDING_MULTIPLE > background1.getHeight() * scale) {
                y1 += -background1.getHeight() * scale * 2 * ROUNDING_MULTIPLE;
            }
            if (y2 / ROUNDING_MULTIPLE > background1.getHeight() * scale) {
                y2 += -background1.getHeight() * scale * 2 * ROUNDING_MULTIPLE;
            }
        }
        if (panSpeedY < 0) {
            if (y1 / ROUNDING_MULTIPLE < -background1.getHeight() * scale) {
                y1 -= -background1.getHeight() * scale * 2 * ROUNDING_MULTIPLE;
            }
            if (y2 / ROUNDING_MULTIPLE < -background1.getHeight() * scale) {
                y2 -= -background1.getHeight() * scale * 2 * ROUNDING_MULTIPLE;
            }
        }
        x1 = Math.round(x1);
        x2 = Math.round(x2);
        y1 = Math.round(y1);
        y2 = Math.round(y2);
        float tx1, tx2, ty1, ty2;
        tx1 = Math.round(x1 / ROUNDING_MULTIPLE);
        tx2 = Math.round(x2 / ROUNDING_MULTIPLE);
        ty1 = Math.round(y1 / ROUNDING_MULTIPLE);
        ty2 = Math.round(y2 / ROUNDING_MULTIPLE);
        // tx1 = x1 / ROUNDING_MULTIPLE;
        // tx2 = x2 / ROUNDING_MULTIPLE;
        // ty1 = y1 / ROUNDING_MULTIPLE;
        // ty2 = y2 / ROUNDING_MULTIPLE;
        switch (tessellation) {
        case NONE:
            node1.setPosition(tx1, ty1);
            node3.setPosition(tx1, ty2);
            node2.setPosition(tx2, ty1);
            node4.setPosition(tx2, ty2);
            break;
        case FLIP_TO_SMOOTH:
            node1.setPosition(tx1, ty1);
            node3.setPosition(tx1, ty2 + background1.getHeight() * scale);
            node2.setPosition(tx2 + background1.getWidth() * scale, ty1);
            node4.setPosition(tx2 + background1.getWidth() * scale, ty2 + background1.getHeight() * scale);
            break;
        case CENTER:
            break;
        default:
            break;
        }

        if (tessellation == Tessellation.CENTER) {
            angle += Utility.getGameTime().getDeltaTimeF() * 0.0015f;
            node1.setAngle(angle);
        }
    }

}
