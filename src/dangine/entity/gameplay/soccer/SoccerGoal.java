package dangine.entity.gameplay.soccer;

import org.lwjgl.util.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.Obstruction;
import dangine.entity.gameplay.GoalScoreEvent;
import dangine.graphics.DangineBox;
import dangine.graphics.DanginePicture;
import dangine.graphics.DangineTexture;
import dangine.graphics.DangineTextures;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class SoccerGoal implements IsUpdateable, HasDrawable {

    final float SCALE = 4;
    String goalImageName = "soccergoal";
    SceneGraphNode base = new SceneGraphNode();
    SceneGraphNode topBoxNode = new SceneGraphNode();
    SceneGraphNode bottomBoxNode = new SceneGraphNode();
    DangineTexture goalImage = DangineTextures.getImageByName(goalImageName);
    Obstruction topBlock;
    Obstruction bottomBlock;
    DangineBox topBox;
    DangineBox bottomBox;
    Vector2f position = new Vector2f(0, 0);
    int ownerId;

    public SoccerGoal(int ownerId) {
        base.addChild(new DanginePicture(goalImage));
        base.setScale(SCALE, SCALE);
        this.ownerId = ownerId;
        createBlocks();
        this.anchor();
    }

    private void createBlocks() {
        topBlock = new Obstruction();
        Utility.getActiveScene().addUpdateable(topBlock);
        Utility.getActiveScene().getCameraNode().addChild(topBlock.getDrawable());
        bottomBlock = new Obstruction();
        Utility.getActiveScene().addUpdateable(bottomBlock);
        Utility.getActiveScene().getCameraNode().addChild(bottomBlock.getDrawable());

        Color color;
        if (ownerId == 0) {
            color = new Color(Color.RED);
        } else {
            color = new Color(Color.BLUE);
        }
        int boxSize = (int) (topBlock.getWidth() * 0.5f);
        topBox = new DangineBox(boxSize, boxSize, color);
        bottomBox = new DangineBox(boxSize, boxSize, color);
        topBoxNode.addChild(topBox);
        bottomBoxNode.addChild(bottomBox);
        Utility.getActiveScene().getCameraNode().addChild(topBoxNode);
        Utility.getActiveScene().getCameraNode().addChild(bottomBoxNode);
    }

    public void anchor() {
        float blockX = 0;
        int side = ownerId % 2;
        if (side == 0) {
            position.x = 0;
            blockX = 0;
        } else {
            position.x = Utility.getResolution().x - (goalImage.getWidth() * SCALE);
            blockX = position.x;
        }
        position.y = (Utility.getResolution().y - (goalImage.getHeight() * SCALE)) / 2;
        base.setPosition(position);
        topBlock.setPosition(blockX, position.y - topBlock.getHeight());
        bottomBlock.setPosition(blockX, position.y + (goalImage.getHeight() * SCALE));
        int boxOffset = (int) (topBlock.getWidth() * 0.25f);
        topBoxNode.setPosition(blockX + boxOffset, position.y - topBlock.getHeight() + boxOffset);
        bottomBoxNode.setPosition(blockX + boxOffset, position.y + (goalImage.getHeight() * SCALE) + boxOffset);
    }

    public boolean isColliding(float x, float y) {
        return x > position.x //
                && x < position.x + (goalImage.getWidth() * SCALE) //
                && y > position.y //
                && y < position.y + (goalImage.getHeight() * SCALE);
    }

    @Override
    public void update() {
        SoccerBall soccerBall = Utility.getActiveScene().getUpdateable(SoccerBall.class);
        if (soccerBall == null) {
            return;
        }
        if (isColliding(soccerBall.getPosition().x + SoccerBall.HITBOX_SIZE, soccerBall.getPosition().y
                + SoccerBall.HITBOX_SIZE)) {
            Utility.getActiveScene().getMatchOrchestrator().addEvent(new GoalScoreEvent(ownerId, ownerId));
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

}
