package dangine.input;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;

public class DangineOpenGLInput {

    static Set<Integer> keysDown = new HashSet<Integer>();

    public static void poll() {
        keysDown.clear();

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                keysDown.add(Keyboard.getEventKey());
            }
        }

    }

    public static boolean isKeyDown(int key) {
        return keysDown.contains(key);
    }

}
