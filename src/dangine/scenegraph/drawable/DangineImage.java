package dangine.scenegraph.drawable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import dangine.entity.IsDrawable;
import dangine.utility.Utility;

public class DangineImage implements IsDrawable {

    int width;
    int height;
    Color color;
    Image image;

    public DangineImage(Image image) {
        this(image, 20, 20, Color.red);
    }

    public DangineImage(Image image, int width, int height, Color color) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw() {
        Utility.getGraphics().setColor(color);
        Utility.getGraphics().drawImage(image, 0, 0);
    }

}
