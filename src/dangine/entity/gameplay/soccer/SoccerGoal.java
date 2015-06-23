package dangine.entity.gameplay.soccer;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.GoalScoreEvent;
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
    DangineTexture goalImage = DangineTextures.getImageByName(goalImageName);
    Vector2f position = new Vector2f(0, 0);
    int ownerId;

    public SoccerGoal(int ownerId) {
        base.addChild(new DanginePicture(goalImage));
        base.setScale(SCALE, SCALE);
        this.ownerId = ownerId;
        this.anchor();
    }

    public void anchor() {
        int side = ownerId % 2;
        if (side == 0) {
            position.x = 0;
        } else {
            position.x = Utility.getResolution().x - (goalImage.getWidth() * SCALE);
        }
        position.y = (Utility.getResolution().y - (goalImage.getHeight() * SCALE)) / 2;
        base.setPosition(position);
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
        if (isColliding(soccerBall.getPosition().x, soccerBall.getPosition().y)) {
            Utility.getActiveScene().getMatchOrchestrator().addEvent(new GoalScoreEvent(ownerId, ownerId));
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

}
