package dangine.menu;

import java.util.List;

import dangine.scenegraph.SceneGraphNode;

public class DangineFormatter {

    public static void format(List<SceneGraphNode> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            SceneGraphNode node = nodes.get(i);
            node.setPosition(0, i * 20);
        }
    }

}
