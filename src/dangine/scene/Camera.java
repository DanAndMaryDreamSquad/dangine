package dangine.scene;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.HasDrawable;
import dangine.entity.Hero;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.Utility;

public class Camera implements IsUpdateable, HasDrawable {

    SceneGraphNode cameraNode = new SceneGraphNode();
    List<Hero> trackings = new ArrayList<Hero>();
    float offset = 0;

    public SceneGraphNode getCameraNode() {
        return cameraNode;
    }

    @Override
    public IsDrawable getDrawable() {
        return cameraNode;
    }

    @Override
    public void update() {
        if (Utility.getPlayers().getPlayers().size() != trackings.size()) {
            trackings.clear();
            for (int i = 0; i < Utility.getPlayers().getPlayers().size(); i++) {
                Hero hero = Utility.getActiveScene().getHero(i);
                if (hero != null) {
                    trackings.add(hero);
                }
            }
        }
        float x = 0;
        float y = 0;
        for (Hero hero : trackings) {
            x += hero.getPosition().x;
            y += hero.getPosition().y;
        }
        x = x / trackings.size();
        y = y / trackings.size();
        x -= Utility.getResolution().x;
        y -= Utility.getResolution().y;
        x = -x;
        y = -y;
        // cameraNode.setPosition(x, y);

    }
}
