package dangine.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import com.badlogic.gdx.math.Matrix4;

public class DangineOpenGL {

    // Setup variables
    static private final String WINDOW_TITLE = "Sample";
    static private final int WIDTH = 300;
    static private final int HEIGHT = 200;

    static private Matrix4 projection;
    static private Matrix4 model;
    static private Matrix4 view;

    public static void setupOpenGL() {
        projection = new Matrix4().setToOrtho2D(0, 0, WIDTH, HEIGHT, -1, 1);
        model = new Matrix4();
        view = new Matrix4();
        
        // Setup an OpenGL context with API version 3.2
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(
                    true);

            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle(WINDOW_TITLE);
            Display.create(pixelFormat, contextAtrributes);

            GL11.glViewport(0, 0, WIDTH, HEIGHT);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Setup an XNA like background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        
        
    }

    public static void destroyOpenGL() {
        Display.destroy();
    }

}
