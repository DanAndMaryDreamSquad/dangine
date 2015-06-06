package dangine.utility;

import org.lwjgl.input.Keyboard;

import dangine.debugger.Debugger;
import dangine.input.DangineOpenGLInput;

public class FunctionKeyEvents {
    private static boolean hitBoxesVisible = false;
    private static boolean f1down = false;

    public static void processFunctionKeys() {
        if (DangineOpenGLInput.isKeyDown(Keyboard.KEY_F1) && f1down == false) {
            Debugger.info("flipped from " + hitBoxesVisible + " to " + !hitBoxesVisible);
            hitBoxesVisible = !hitBoxesVisible;
            f1down = true;
        } else if (!DangineOpenGLInput.isKeyDown(Keyboard.KEY_F1)) {
            f1down = false;
        }
    }

    public static boolean isHitBoxesVisible() {
        return hitBoxesVisible;
    }
}
