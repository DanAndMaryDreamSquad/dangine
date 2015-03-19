package dangine.scenegraph.drawable;

import org.newdawn.slick.Color;

import dangine.entity.IsDrawable;
import dangine.scenegraph.RenderData;
import dangine.utility.Utility;

public class DangineCircle implements IsDrawable {

    float radius;
    Color color;
    RenderData data = new RenderData(this);

    public DangineCircle(float radius) {
        this(radius, Color.pink);
    }

    public DangineCircle(float radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void draw() {
        Utility.getGraphics().setColor(color);
        Utility.getGraphics().drawOval(0, 0, radius, radius);
    }

    @Override
    public RenderData getRenderData() {
        return data;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public IsDrawable copy() {
        return new DangineCircle(radius, color);
    }
}
