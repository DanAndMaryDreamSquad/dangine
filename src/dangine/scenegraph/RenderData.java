package dangine.scenegraph;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

import com.badlogic.gdx.math.Matrix4;

import dangine.debugger.Debugger;
import dangine.entity.IsDrawable;
import dangine.utility.ScreenUtility;

public class RenderData implements Comparable<RenderData> {

    IsDrawable draw;
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    Matrix4 matrix = null;
    Matrix4f mat = null;
    float z;

    public RenderData(IsDrawable draw) {
        if (draw == null) {
            Debugger.info();
        }
        this.draw = draw;
    }

    public void updateBuffer(Matrix4 matrix) {
        this.matrix = matrix;
        this.z = matrix.val[Matrix4.M23];
    }

    public void draw() {
        ScreenUtility.matrixIntoGLLoad(buffer, matrix);
        draw.draw();
    }

    @Override
    public int compareTo(RenderData o) {
        return Float.compare(o.getZ(), z);
    }

    public float getZ() {
        return z;
    }
}
