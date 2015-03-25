package dangine.menu;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.scenegraph.SceneGraphNode;

public class DangineMenu implements HasDrawable {

    SceneGraphNode base = new SceneGraphNode();
    List<DangineMenuItem> items = new ArrayList<DangineMenuItem>();

    public boolean addItem(DangineMenuItem item) {
        base.addChild(item.getDrawable());
        return items.add(item);
    }

    public boolean removeItem(DangineMenuItem item) {
        base.removeChild(item.getDrawable());
        return items.remove(item);
    }

    public DangineMenuItem getItem(int index) {
        return items.get(index);
    }

    @Override
    public IsDrawable getDrawable() {
        return base;
    }

    public SceneGraphNode getBase() {
        return base;
    }

    public List<DangineMenuItem> getItems() {
        return items;
    }

}
