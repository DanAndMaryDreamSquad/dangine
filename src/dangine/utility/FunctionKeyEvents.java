package dangine.utility;

import org.lwjgl.input.Keyboard;

import dangine.debugger.Debugger;
import dangine.input.DangineOpenGLInput;

public class FunctionKeyEvents {
    private static boolean hitBoxesVisible = true;
    private static boolean f12down = false;

    public static void processFunctionKeys() {
        if (DangineOpenGLInput.isKeyDown(Keyboard.KEY_F12) && f12down == false) {
            Debugger.info("flipped from " + hitBoxesVisible + " to " + !hitBoxesVisible);
            hitBoxesVisible = !hitBoxesVisible;
            f12down = true;
        } else if (!DangineOpenGLInput.isKeyDown(Keyboard.KEY_F12)) {
            f12down = false;
        }
    }

    public static boolean isHitBoxesVisible() {
        return hitBoxesVisible;
    }
}
