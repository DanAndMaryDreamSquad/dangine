package dangine.menu;

import java.util.List;

import dangine.graphics.DangineFont;
import dangine.graphics.DangineOpenGL;
import dangine.graphics.DangineStringPicture;
import dangine.scenegraph.SceneGraphNode;

public class DangineFormatter {

    public static void format(List<SceneGraphNode> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            SceneGraphNode node = nodes.get(i);
            node.setPosition(0, i * DangineFont.CHARACTER_HEIGHT_IN_PIXELS * DangineStringPicture.STRING_SCALE
                    * DangineOpenGL.getWindowWorldAspectY());
        }
    }

}
