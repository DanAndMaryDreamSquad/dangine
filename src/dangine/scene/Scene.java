package dangine.scene;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;

public class Scene implements IsUpdateable, IsDrawable {

    final SceneGraphNode parentNode = new SceneGraphNode();
    List<IsUpdateable> updateables = new ArrayList<IsUpdateable>();

    @Override
    public void update() {
        for (IsUpdateable update : updateables) {
            update.update();
        }
    }

    @Override
    public void draw() {
        parentNode.draw();
    }

    public void addUpdateable(IsUpdateable updateable) {
        updateables.add(updateable);
    }

    public void removeUpdateable(IsUpdateable updateable) {
        updateables.remove(updateable);
    }

    public SceneGraphNode getParentNode() {
        return parentNode;
    }

}
