package dangine.scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.scenegraph.SceneGraphNode;

public class Scene implements IsUpdateable, IsDrawable {

    final SceneGraphNode parentNode = new SceneGraphNode();
    List<IsUpdateable> updateables = new ArrayList<IsUpdateable>();
    List<IsUpdateable> toAdd = new LinkedList<IsUpdateable>();
    List<IsUpdateable> toRemove = new LinkedList<IsUpdateable>();

    @Override
    public void update() {
        for (IsUpdateable update : updateables) {
            update.update();
        }
        for (IsUpdateable update : toAdd) {
            updateables.add(update);
        }
        toAdd.clear();
        for (IsUpdateable update : toRemove) {
            updateables.remove(update);
        }
        toRemove.clear();

    }

    @Override
    public void draw() {
        parentNode.draw();
    }

    public void addUpdateable(IsUpdateable updateable) {
        toAdd.add(updateable);
    }

    public void removeUpdateable(IsUpdateable updateable) {
        toRemove.add(updateable);
    }

    public SceneGraphNode getParentNode() {
        return parentNode;
    }

}
