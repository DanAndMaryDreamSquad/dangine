package dangine.audio;

import org.lwjgl.util.WaveData;

public class DangineSound {

    WaveData waveData;
    String name;

    public DangineSound(String name, WaveData data) {
        this.waveData = data;
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
