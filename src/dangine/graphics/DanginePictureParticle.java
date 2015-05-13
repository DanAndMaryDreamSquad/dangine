package dangine.graphics;

import java.util.ArrayList;
import java.util.List;

import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineParticleData;

public class DanginePictureParticle implements IsDrawable32 {
    RenderData32 data = new RenderData32(this);
    SceneGraphNode node = new SceneGraphNode();
    List<DangineParticleData> particles = new ArrayList<DangineParticleData>();
    List<DangineColoredQuad> quads = new ArrayList<DangineColoredQuad>();

    public DanginePictureParticle(List<DangineParticleData> particles) {
        this.particles = particles;
        for (DangineParticleData data : particles) {
            DangineColoredQuad quad = new DangineColoredQuad(data.getColor());
            quads.add(quad);
        }
        node.setPosition(getWidth() / 2, getHeight() / 2);
        node.setScale(getWidth(), -getHeight());
    }
   
    public void draw() {
        for (int i = 0; i < particles.size(); i++) {
            DangineColoredQuad quad = quads.get(i);
            quad.updateTransformationMatrixOfShader(node.getMatrix());
            quad.drawQuad();
        }
    }

    public SceneGraphNode getNode() {
        return node;
    }

    public int getWidth() {
        return particles.get(0).getWidth();
    }

    public int getHeight() {
        return particles.get(0).getHeight();
    }

    @Override
    public RenderData32 getRenderData32() {
        return data;
    }

    @Override
    public IsDrawable32 copy() {
        return new DanginePictureParticle(particles);
    }

    public List<DangineParticleData> getParticles() {
        return particles;
    }

}
