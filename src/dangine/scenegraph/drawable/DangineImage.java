package dangine.scenegraph.drawable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import dangine.entity.IsDrawable;
import dangine.image.Resources;
import dangine.scenegraph.RenderData;
import dangine.utility.Utility;

public class DangineImage implements IsDrawable {

    int width;
    int height;
    Color color;
    Image image;
    RenderData data = new RenderData(this);

    public DangineImage(Image image) {
        this(image, 20, 20, Color.red);
    }

    public DangineImage(String image) {
        this(Resources.getImageByName(image));
    }

    public DangineImage(Image image, int width, int height, Color color) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public void draw() {
        Utility.getGraphics().setColor(color);
//        Utility.getGraphics().drawImage(image, 0, 0);
    }

    @Override
    public RenderData getRenderData() {
        return data;
    }

    @Override
    public IsDrawable copy() {
        return new DangineImage(image, width, height, color);
    }

}
