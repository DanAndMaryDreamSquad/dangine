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

import dangine.debugger.Debugger;
import dangine.graphics.DangineOpenGL;
import dangine.scenegraph.SceneGraphNode;

public class ScreenUtility {

    private static float[] temp = new float[3];

    @Deprecated
    public static void matrixIntoGLLoad(FloatBuffer buffer, Matrix4 matrix) {
        buffer.put(matrix.getValues(), 0, 16);
        buffer.flip();
        GL11.glLoadMatrix(buffer);
    }

    public static Vector2f getScreenPosition(SceneGraphNode node, Vector2f inOutPosition) {
        node.transform();
        // ScreenUtility.matrixIntoGLLoad(BufferUtils.createFloatBuffer(16),
        // node.getMatrix());
        inOutPosition = gluProject(node, inOutPosition);
        inOutPosition.y = DangineOpenGL.DISPLAY_HEIGHT - inOutPosition.y;
        return inOutPosition;
    }

    public static Vector2f getWorldPositionFromScreenPosition(Vector2f inOutPosition) {
        temp[0] = inOutPosition.x;
        temp[1] = inOutPosition.y;
        temp[2] = 0;
        Matrix4 camera = Utility.getActiveScene().getCameraNode().getMatrix().cpy();
        Matrix4.mulVec(camera.inv().getValues(), temp);
        inOutPosition.x = temp[0];
        inOutPosition.y = temp[1];
        return inOutPosition;
    }

    public static Vector2f getWorldPosition(SceneGraphNode node, Vector2f inOutPosition) {
        Vector2f screenPosition = getScreenPosition(node, inOutPosition);
        return getWorldPositionFromScreenPosition(screenPosition);
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

    public static Vector2f gluProject(SceneGraphNode node, Vector2f inOutPosition) {
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        FloatBuffer position = BufferUtils.createFloatBuffer(3);

        Matrix4 mvp = node.getMatrix().cpy();
        Matrix4 projectionInverse = Utility.getActiveScene().getParentNode().getMatrix().cpy().inv();
        Matrix4 mv = projectionInverse.mul(mvp);

        modelview.put(mvp.getValues());
        // modelview.put(mv.getValues());
        modelview.flip();
        projection.put(Utility.getActiveScene().getParentNode().getMatrix().getValues());
        projection.flip();
        // GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        // GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);

        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

        // GLU.gluProject(0, 0, 0, modelview, projection, viewport, position);
        GLU.gluProject(0, 0, 0, modelview, projection, viewport, position);
        Debugger.info("unprojected, MV:n" + modelview.toString() + "\nP:\n" + projection);
        Vector2f r =  floatBufferToVector2f(position, inOutPosition);
        Debugger.info("result: " + r.toString());
        return r;
    }

    public static Vector2f floatBufferToVector2f(FloatBuffer floatBuffer, Vector2f inOutPosition) {
        return inOutPosition.set(floatBuffer.get(0), floatBuffer.get(1));
    }

}
