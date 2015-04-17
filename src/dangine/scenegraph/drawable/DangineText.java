package dangine.scenegraph.drawable;

import org.newdawn.slick.Color;

import dangine.entity.IsDrawable;
import dangine.scenegraph.RenderData;
import dangine.utility.Utility;

public class DangineText implements IsDrawable {

    String text;
    Color color;
    RenderData data = new RenderData(this);
    float alpha = 1.0f;

    public DangineText() {
        this("Sample Text", new Color(Color.black));
    }

    public DangineText(String text, Color color) {
        this.color = color;
        this.text = text;
    }

    @Override
    public void draw() {
        Color c = Utility.getMatchParameters().getTextColor();
        c.a = alpha;
        Utility.getGraphics().setColor(c);
        Utility.getGraphics().drawString(text, 0, 0);
        c.a = 1.0f;
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
        return new DangineText(text, color);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

}
