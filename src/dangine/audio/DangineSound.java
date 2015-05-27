package dangine.audio;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class DangineSound {
    /** Buffers hold sound data. */
    IntBuffer buffer = BufferUtils.createIntBuffer(1);
    /** Sources are points emitting sound. */
    IntBuffer source = BufferUtils.createIntBuffer(1);
    /** Position of the source sound. */
    FloatBuffer sourcePos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f })
            .rewind();
    /** Velocity of the source sound. */
    FloatBuffer sourceVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f })
            .rewind();

    WaveData waveData;
    String name;

    public DangineSound(String name, WaveData data) {
        this.waveData = data;
        this.name = name;
        loadALData();
    }

    private int loadALData() {
        // Load wav data into a buffer.
        AL10.alGenBuffers(buffer);

        if (AL10.alGetError() != AL10.AL_NO_ERROR) {
            return AL10.AL_FALSE;
        }
        AL10.alBufferData(buffer.get(0), waveData.format, waveData.data, waveData.samplerate);

        // Bind the buffer with the source.
        AL10.alGenSources(source);

        if (AL10.alGetError() != AL10.AL_NO_ERROR)
            return AL10.AL_FALSE;

        AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buffer.get(0));
        AL10.alSourcef(source.get(0), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(source.get(0), AL10.AL_GAIN, 1.0f);
        AL10.alSource(source.get(0), AL10.AL_POSITION, sourcePos);
        AL10.alSource(source.get(0), AL10.AL_VELOCITY, sourceVel);

        // Do another error check and return.
        if (AL10.alGetError() == AL10.AL_NO_ERROR)
            return AL10.AL_TRUE;

        return AL10.AL_FALSE;
    }

    public void play() {
        AL10.alSourcePlay(source.get(0));
    }
    
    public void stop() {
        AL10.alSourceStop(source.get(0));
    }
    
    public void pause() {
        AL10.alSourcePause(source.get(0));
    }

    public void destroy() {
        waveData.dispose();
        AL10.alDeleteSources(source);
        AL10.alDeleteBuffers(buffer);
    }

    public String getName() {
        return name;
    }

}
