package dangine.scenegraph.drawable;

import org.newdawn.slick.Color;

import dangine.entity.IsDrawable;
import dangine.scenegraph.RenderData;
import dangine.utility.Utility;

public class DangineShape implements IsDrawable {

    int width;
    int height;
    Color color;
    RenderData data = new RenderData(this);

    public DangineShape() {
        this(20, 20, Color.red);
    }

    public DangineShape(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw() {
        Utility.getGraphics().setColor(color);
        Utility.getGraphics().fillRect(0, 0, width, height);
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
        return new DangineShape(width, height, color);
    }

}
