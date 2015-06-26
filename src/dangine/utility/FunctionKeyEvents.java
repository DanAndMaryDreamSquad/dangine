package dangine.utility;

import org.lwjgl.input.Keyboard;

import dangine.debugger.Debugger;
import dangine.input.DangineOpenGLInput;

public class FunctionKeyEvents {
    private static boolean hitBoxesVisible = true;
    private static boolean f1down = false;
    private static boolean f2down = false;
    private static boolean f3down = false;
    private static boolean f4down = false;
    private static boolean f5down = false;

    public static void printFunctionKeys() {
        Debugger.info("Function Key Operations:");
        Debugger.info("F1 - toggle hitboxes");
        Debugger.info("F2 - quick start match");
        Debugger.info("F3 / F4 - zoom camera");
        Debugger.info("F5 - toggle dynamic camera on / off");
    }

    public static void processFunctionKeys() {
        if (DangineOpenGLInput.isKeyDown(Keyboard.KEY_F1) && f1down == false) {
            Debugger.info("flipped from " + hitBoxesVisible + " to " + !hitBoxesVisible);
            hitBoxesVisible = !hitBoxesVisible;
            f1down = true;
        } else if (!DangineOpenGLInput.isKeyDown(Keyboard.KEY_F1)) {
            f1down = false;
        }
        if (DangineOpenGLInput.isKeyDown(Keyboard.KEY_F2) && f2down == false) {
            f2down = true;
            Utility.getGameLoop().startInstanceTestMatch();
        } else if (!DangineOpenGLInput.isKeyDown(Keyboard.KEY_F2)) {
            f2down = false;
        }
        if (DangineOpenGLInput.isKeyDown(Keyboard.KEY_F3) && f3down == false) {
            f3down = true;
            Utility.getActiveScene().getCamera().increaseZoom();
        } else if (!DangineOpenGLInput.isKeyDown(Keyboard.KEY_F3)) {
            f3down = false;
        }
        if (DangineOpenGLInput.isKeyDown(Keyboard.KEY_F4) && f4down == false) {
            f4down = true;
            Utility.getActiveScene().getCamera().decreaseZoom();
        } else if (!DangineOpenGLInput.isKeyDown(Keyboard.KEY_F4)) {
            f4down = false;
        }
        if (DangineOpenGLInput.isKeyDown(Keyboard.KEY_F5) && f5down == false) {
            f5down = true;
            Utility.getActiveScene().getCamera().setEnabled(!Utility.getActiveScene().getCamera().isEnabled());
        } else if (!DangineOpenGLInput.isKeyDown(Keyboard.KEY_F5)) {
            f5down = false;
        }
    }

    public static boolean isHitBoxesVisible() {
        return hitBoxesVisible;
    }
}
