package dangine.scenegraph.drawable;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class ParticleEffectFactory {

    public static final Color[] fireColors = { Color.red, Color.orange, Color.yellow };
    public static final Color[] energyColors = { Color.blue, Color.cyan, Color.white };
    public static final Color[] greenColors = { Color.green, new Color(100, 230, 0), new Color(0, 230, 100),
            Color.white };

    public static DangineParticle create(int numberOfParticles, int size, Color[] colors) {
        List<DangineParticleData> data = new ArrayList<DangineParticleData>();
        for (int i = 0; i < numberOfParticles; i++) {
            Color color = new Color(colors[i % colors.length]);
            Vector2f offset = new Vector2f(0, 0);
            DangineParticleData particle = new DangineParticleData(size, size, color, offset);
            data.add(particle);
        }
        return new DangineParticle(data);
    }

    public static DangineParticle createCircle(int numberOfParticles, int size, Color[] colors, float radius) {
        List<DangineParticleData> data = new ArrayList<DangineParticleData>();
        for (int i = 0; i < numberOfParticles; i++) {
            Color color = new Color(colors[i % colors.length]);
            float arcSize = 360.0f / numberOfParticles;
            float angle = arcSize * i;
            Vector2f offset = new Vector2f(angle).scale(radius);
            DangineParticleData particle = new DangineParticleData(size, size, color, offset);
            data.add(particle);
        }
        return new DangineParticle(data);
    }
}
