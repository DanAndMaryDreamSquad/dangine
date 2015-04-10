package dangine.scenegraph;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import dangine.debugger.Debugger;
import dangine.entity.IsDrawable;
import dangine.utility.ScreenUtility;

public class FlattenSceneGraphNode {

    public static SceneGraphNode flatten(SceneGraphNode node) {
        // If the thing being flattened is in the middle of an animation, we
        // need to update it
        node.updateTransformsAndPropagate();
        SceneGraphNode result = new SceneGraphNode();
        List<SceneGraphNode> nodes = fetchCreateChildNodes(node, new ArrayList<SceneGraphNode>());
        for (SceneGraphNode childNode : nodes) {
            Vector2f vector = childNode.getPosition();
            vector = ScreenUtility.getWorldPositionFromScreenPosition(vector);
            childNode.setPosition(vector);
            result.addChild(childNode);
        }
        if (result.childNodes.size() > 10) {
            Debugger.warn("large flatten operation size " + result.childNodes.size());
        }
        Vector2f cameraTranslation = new Vector2f(0, 0);
        ScreenUtility.getWorldPositionFromScreenPosition(cameraTranslation);
        result.setPosition(cameraTranslation.x, cameraTranslation.y);
        return result;
    }

    public static List<SceneGraphNode> fetchCreateChildNodes(SceneGraphNode node, List<SceneGraphNode> nodes) {
        for (IsDrawable childShape : node.getChildren()) {
            SceneGraphNode newNode = new SceneGraphNode();
            newNode.pullTransformsFromMatrix(node.getMatrix());
            newNode.addChild(childShape.copy());
            nodes.add(newNode);
        }
        for (SceneGraphNode childNode : node.getChildNodes()) {
            nodes.addAll(fetchCreateChildNodes(childNode, new ArrayList<SceneGraphNode>()));
        }

        return nodes;
    }

}
