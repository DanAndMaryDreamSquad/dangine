package dangine.scenegraph.drawable;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import dangine.entity.combat.GreatSwordSceneGraph;
import dangine.utility.MathUtility;

public class BloxColorer {

    public static final ReadableColor[] COLORS = { Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGREY, Color.GREY,
            Color.GREEN, Color.LTGREY, Color.PURPLE, Color.ORANGE, Color.RED, Color.WHITE, Color.YELLOW };

    public static final ReadableColor[] TEAM_COLORS = { Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGREY };

    public static final int indexOf(Color color) {
        for (int i = 0; i < COLORS.length; i++) {
            Color c = new Color(COLORS[i]);
            if (c.equals(color)) {
                return i;
            }
        }
        return -1;
    }

    public static int SHADE_OFFSET = 50;

    public static void color(BloxSceneGraph blox, Color color) {
        if (color == null) {
            return;
        }
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        Color darker = new Color((int) Math.max(0, r - SHADE_OFFSET), (int) Math.max(0, g - SHADE_OFFSET),
                (int) Math.max(0, b - SHADE_OFFSET));
        Color lighter = new Color((int) Math.min(255, r + SHADE_OFFSET), (int) Math.min(255, g + SHADE_OFFSET),
                (int) Math.min(255, b + SHADE_OFFSET));
        blox.getBodyShape().setColor(color);
        blox.getLeftArmShape().setColor(darker);
        blox.getRightArmShape().setColor(lighter);
        blox.getLeftLegShape().setColor(darker);
        blox.getRightLegShape().setColor(lighter);
        blox.getHead().getHeadShape().setColor(darker);
    }

    public static void color(GreatSwordSceneGraph greatsword, Color color) {
        if (color == null) {
            return;
        }
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        Color darker = new Color((int) Math.max(0, r - SHADE_OFFSET), (int) Math.max(0, g - SHADE_OFFSET),
                (int) Math.max(0, b - SHADE_OFFSET));
        Color lighter = new Color((int) Math.min(255, r + SHADE_OFFSET), (int) Math.min(255, g + SHADE_OFFSET),
                (int) Math.min(255, b + SHADE_OFFSET));
        greatsword.getLeftArmShape().setColor(darker);
        greatsword.getRightArmShape().setColor(lighter);

    }

    public static Color randomColor() {
        return new Color(COLORS[MathUtility.randomInt(0, COLORS.length - 1)]);
    }

}
