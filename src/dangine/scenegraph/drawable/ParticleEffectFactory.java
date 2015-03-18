package dangine.scenegraph.drawable;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class ParticleEffectFactory {

    private static final Color[] fireColors = { Color.red, Color.orange, Color.yellow };

    public static DangineParticle createFire(int numberOfParticles, int size) {
        List<DangineParticleData> data = new ArrayList<DangineParticleData>();
        for (int i = 0; i < numberOfParticles; i++) {
            Color color = new Color(fireColors[i % fireColors.length]);
            Vector2f offset = new Vector2f(0, 0);
            DangineParticleData particle = new DangineParticleData(size, size, color, offset);
            data.add(particle);
        }
        return new DangineParticle(data);
    }

}
