package dangine.graphics;

import java.util.ArrayList;
import java.util.List;

import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineParticleData;

public class DanginePictureParticle implements IsDrawable32 {
    RenderData32 data = new RenderData32(this);
    SceneGraphNode base = new SceneGraphNode();
    List<DangineParticleData> particles = new ArrayList<DangineParticleData>();
    List<SceneGraphNode> nodes = new ArrayList<SceneGraphNode>();
    List<DangineColoredQuad> quads = new ArrayList<DangineColoredQuad>();

    public DanginePictureParticle(List<DangineParticleData> particles) {
        this.particles = particles;
        for (DangineParticleData data : particles) {
            DangineColoredQuad quad = new DangineColoredQuad(data.getColor());
            SceneGraphNode node = new SceneGraphNode();
            node.setPosition(data.getOffset().x + (data.getWidth() / 2), data.getOffset().y + (data.getHeight() / 2));
            node.setScale(data.getWidth(), -data.getHeight());
            quads.add(quad);
            nodes.add(node);
            base.addChild(node);
        }
    }

    public void draw() {
        for (int i = 0; i < particles.size(); i++) {
            DangineParticleData data = particles.get(i);
            SceneGraphNode node = nodes.get(i);
            DangineColoredQuad quad = quads.get(i);
            node.setPosition(data.getOffset().x + (data.getWidth() / 2), data.getOffset().y + (data.getHeight() / 2));
            node.setScale(data.getWidth(), -data.getHeight());
            quad.setQuadColor(data.getColor());
        }
        base.propagate();
        for (int i = 0; i < particles.size(); i++) {
            DangineColoredQuad quad = quads.get(i);
            SceneGraphNode node = nodes.get(i);
            quad.updateTransformationMatrixOfShader(node.getMatrix());
            quad.drawQuad();
        }
    }

    public SceneGraphNode getNode() {
        return base;
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
