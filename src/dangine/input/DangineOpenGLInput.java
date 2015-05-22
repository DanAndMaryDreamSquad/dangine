package dangine.input;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;

public class DangineOpenGLInput {

    static Set<Integer> keysDown = new HashSet<Integer>();

    public static void poll() {
        // keysDown.clear();

        while (Keyboard.next()) {
            // LWJGL Jargon for wether or not
            // the key was pressed (true) or
            // lifted (false)
            if (Keyboard.getEventKeyState()) {
                keysDown.add(Keyboard.getEventKey());
            } else {
                keysDown.remove(Keyboard.getEventKey());
            }

        }

    }

    public static boolean isKeyDown(int key) {
        return keysDown.contains(key);
    }

    public static void clearKeyStates() {
        keysDown.clear();
    }

}
