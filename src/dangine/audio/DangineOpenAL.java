package dangine.audio;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

public class DangineOpenAL {

    /** Position of the listener. */
    static FloatBuffer listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3)
            .put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

    /** Velocity of the listener. */
    static FloatBuffer listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3)
            .put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();

    /**
     * Orientation of the listener. (first 3 elements are "at", second 3 are
     * "up")
     */
    static FloatBuffer listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6)
            .put(new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f }).rewind();

    public static void setupOpenAL() {
        // Initialize OpenAL and clear the error bit.
        try {
            AL.create();
        } catch (LWJGLException le) {
            le.printStackTrace();
            return;
        }
        AL10.alGetError();

        setListenerValues();

    }

    /**
     * void setListenerValues()
     * 
     * We already defined certain values for the Listener, but we need to tell
     * OpenAL to use that data. This function does just that.
     */
    static void setListenerValues() {
        AL10.alListener(AL10.AL_POSITION, listenerPos);
        AL10.alListener(AL10.AL_VELOCITY, listenerVel);
        AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
    }

    public static void destroyOpenAL() {
        AL.destroy();
    }
}
