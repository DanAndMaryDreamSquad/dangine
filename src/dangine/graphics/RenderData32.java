package dangine.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.badlogic.gdx.math.Matrix4;

import dangine.debugger.Debugger;
import dangine.utility.Utility;

public class RenderData32  implements Comparable<RenderData32>  {

    IsDrawable32 draw;
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    Matrix4 matrix = null;
    float z;

    public RenderData32(IsDrawable32 draw) {
        if (draw == null) {
            Debugger.info();
        }
        this.draw = draw;
    }

    public void updateBuffer(Matrix4 matrix) {
        this.matrix = matrix;
        if (this.z != matrix.val[Matrix4.M23]) {
            Utility.getRenderQueue().invalidSortedOrder();
            this.z = matrix.val[Matrix4.M23];
        }
    }

    public void draw() {
        draw.draw();
    }

    @Override
    public int compareTo(RenderData32 o) {
        return Float.compare(o.getZ(), z);
    }

    public float getZ() {
        return z;
    }
}
