package dangine.scenegraph.drawable;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import dangine.graphics.DanginePictureParticle;
import dangine.utility.Vector2f;

public class ParticleEffectFactory {

    public static final ReadableColor[] fireColors = { Color.RED, Color.ORANGE, Color.YELLOW };
    public static final ReadableColor[] energyColors = { Color.BLUE, Color.CYAN, Color.WHITE };
    public static final ReadableColor[] yellowColors = { Color.YELLOW, Color.WHITE, new Color(200, 200, 0) };
    public static final ReadableColor[] greenColors = { Color.GREEN, new Color(100, 230, 0), new Color(0, 230, 100),
            Color.WHITE };

    public static DanginePictureParticle create(int numberOfParticles, int size, ReadableColor[] colors) {
        List<DangineParticleData> data = new ArrayList<DangineParticleData>();
        for (int i = 0; i < numberOfParticles; i++) {
            Color color = new Color(colors[i % colors.length]);
            Vector2f offset = new Vector2f(0, 0);
            DangineParticleData particle = new DangineParticleData(size, size, color, offset);
            data.add(particle);
        }
        return new DanginePictureParticle(data);
    }

    public static DanginePictureParticle createCircle(int numberOfParticles, int size, ReadableColor[] colors,
            float radius) {
        List<DangineParticleData> data = new ArrayList<DangineParticleData>();
        for (int i = 0; i < numberOfParticles; i++) {
            Color color = new Color(colors[i % colors.length]);
            float arcSize = 360.0f / numberOfParticles;
            float angle = arcSize * i;
            Vector2f offset = new Vector2f(angle);
            offset.scale(radius);
            DangineParticleData particle = new DangineParticleData(size, size, color, offset);
            data.add(particle);
        }
        return new DanginePictureParticle(data);
    }
}
