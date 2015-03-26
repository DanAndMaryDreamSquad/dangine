package dangine.scenegraph.drawable;

import org.newdawn.slick.Color;

import dangine.entity.combat.GreatSwordSceneGraph;

public class BloxColorer {

    public static int SHADE_OFFSET = 50;

    public static void color(BloxSceneGraph blox, Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        Color darker = new Color(r - SHADE_OFFSET, g - SHADE_OFFSET, b - SHADE_OFFSET);
        Color lighter = new Color(r + SHADE_OFFSET, g + SHADE_OFFSET, b + SHADE_OFFSET);
        blox.getBodyShape().setColor(color);
        blox.getLeftArmShape().setColor(darker);
        blox.getRightArmShape().setColor(lighter);
        blox.getLeftLegShape().setColor(darker);
        blox.getRightLegShape().setColor(lighter);
        blox.getHead().getHeadShape().setColor(darker);
    }

    public static void color(GreatSwordSceneGraph greatsword, Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        Color darker = new Color(r - SHADE_OFFSET, g - SHADE_OFFSET, b - SHADE_OFFSET);
        Color lighter = new Color(r + SHADE_OFFSET, g + SHADE_OFFSET, b + SHADE_OFFSET);
        greatsword.getLeftArmShape().setColor(darker);
        greatsword.getRightArmShape().setColor(lighter);

    }

}
