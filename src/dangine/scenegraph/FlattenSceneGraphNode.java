package dangine.scenegraph;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Matrix4;

import dangine.debugger.Debugger;
import dangine.entity.IsDrawable;
import dangine.graphics.IsDrawable32;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class FlattenSceneGraphNode {

    public static float[] temp = new float[3];

    public static SceneGraphNode flatten(SceneGraphNode node) {
        // If the thing being flattened is in the middle of an animation, we
        // need to update it
        node.updateTransformsAndPropagate();
        SceneGraphNode result = new SceneGraphNode();
        List<SceneGraphNode> nodes = fetchCreateChildNodes(node, new ArrayList<SceneGraphNode>());
        for (SceneGraphNode childNode : nodes) {
            Vector2f vector = childNode.getPosition();
            temp[0] = vector.x;
            temp[1] = vector.y;
            temp[2] = 0;
            Matrix4 camera = Utility.getActiveScene().getSceneToWorldTransform();
            Matrix4.mulVec(camera.getValues(), temp);
            vector.x = temp[0];
            vector.y = temp[1];

            childNode.setPosition(vector);
            result.addChild(childNode);
        }
        if (result.childNodes.size() > 10) {
            Debugger.warn("large flatten operation size " + result.childNodes.size());
        }
        Vector2f cameraTranslation = new Vector2f(0, 0);
        // ScreenUtility.getWorldPositionFromScreenPosition(cameraTranslation);
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
        for (IsDrawable32 childShape : node.getChildren32()) {
            SceneGraphNode newNode = new SceneGraphNode();
            newNode.pullTransformsFromMatrix(node.getMatrix()); // TODO get the
                                                                // scale right
            newNode.setScale(1.0f, -1.0f);
            newNode.addChild(childShape.copy());
            nodes.add(newNode);
        }
        for (SceneGraphNode childNode : node.getChildNodes()) {
            nodes.addAll(fetchCreateChildNodes(childNode, new ArrayList<SceneGraphNode>()));
        }

        return nodes;
    }

}
