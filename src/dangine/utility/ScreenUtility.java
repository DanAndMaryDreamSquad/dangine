package dangine.utility;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.geom.Vector2f;

import com.badlogic.gdx.math.Matrix4;

import dangine.scenegraph.SceneGraphNode;

public class ScreenUtility {

    public static void matrixIntoGLLoad(FloatBuffer buffer, Matrix4 matrix) {
        buffer.put(matrix.getValues(), 0, 16);
        buffer.flip();
        GL11.glLoadMatrix(buffer);
    }

    public static Vector2f getProjectedPosition(SceneGraphNode node, Vector2f inOutPosition) {
        node.transform();
        ScreenUtility.matrixIntoGLLoad(BufferUtils.createFloatBuffer(16), node.getMatrix());
        inOutPosition = gluProject(inOutPosition);
        inOutPosition.y = 480 - inOutPosition.y;
        return inOutPosition;
    }

    public static List<SceneGraphNode> fetchParents(SceneGraphNode node) {
        LinkedList<SceneGraphNode> parents = new LinkedList<SceneGraphNode>();
        parents.addFirst(node);
        while (node.getParent() != null) {
            node = node.getParent();
            parents.addFirst(node);
        }
        return parents;
    }

    public static Vector2f gluProject(Vector2f inOutPosition) {
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        FloatBuffer position = BufferUtils.createFloatBuffer(3);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

        GLU.gluProject(0, 0, 0, modelview, projection, viewport, position);
        return floatBufferToVector2f(position, inOutPosition);
    }

    public static Vector2f floatBufferToVector2f(FloatBuffer floatBuffer, Vector2f inOutPosition) {
        return inOutPosition.set(floatBuffer.get(0), floatBuffer.get(1));
    }

}
