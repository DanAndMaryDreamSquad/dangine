package dangine.scenegraph.drawable;

import org.newdawn.slick.Color;

import dangine.entity.IsDrawable;
import dangine.utility.Utility;

public class DangineShape implements IsDrawable {

    int width;
    int height;
    Color color;

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

}
