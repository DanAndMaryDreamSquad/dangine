package dangine.scenegraph.drawable;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.IsDrawable;
import dangine.scenegraph.RenderData;
import dangine.utility.Utility;

public class DangineParticle implements IsDrawable {

    RenderData data = new RenderData(this);
    List<DangineParticleData> particles = new ArrayList<DangineParticleData>();

    @Deprecated
    public DangineParticle(List<DangineParticleData> particles) {
        this.particles = particles;
    }

    @Override
    public void draw() {
        for (DangineParticleData p : particles) {
            // Utility.getGraphics().setColor(p.color);
            Utility.getGraphics().fillRect(p.offset.x, p.offset.y, p.width, p.height);
        }
    }

    @Override
    public RenderData getRenderData() {
        return data;
    }

    public List<DangineParticleData> getParticles() {
        return particles;
    }

    @Override
    public IsDrawable copy() {
        return new DangineParticle(particles);
    }
}
