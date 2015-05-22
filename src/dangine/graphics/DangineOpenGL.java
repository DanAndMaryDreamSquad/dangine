package dangine.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import dangine.utility.Vector2f;

public class DangineOpenGL {

    // Setup variables
    static private final String WINDOW_TITLE = "Sample";
    static public final Vector2f WORLD_RESOLUTION = new Vector2f(800, 600);
    static public final Vector2f WINDOW_RESOLUTION = new Vector2f(800, 600);
    static public PixelFormat pixelFormat = new PixelFormat();
    static public ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withForwardCompatible(true)
            .withProfileCore(true);

    public static void setupOpenGL() {
        // Setup an OpenGL context with API version 3.2
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(
                    true);

            Display.setDisplayMode(new DisplayMode((int) WINDOW_RESOLUTION.x, (int) WINDOW_RESOLUTION.y));
            Display.setTitle(WINDOW_TITLE);
            Display.create(pixelFormat, contextAtrributes);

            GL11.glViewport(0, 0, (int) WINDOW_RESOLUTION.x, (int) WINDOW_RESOLUTION.y);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Setup an XNA like background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, (int) WINDOW_RESOLUTION.x, (int) WINDOW_RESOLUTION.y);

        GL11.glEnable(GL11.GL_BLEND); // you enable blending function (for
                                      // images with transparency)
        // Some sort of blending function that
        // supports images with transparency
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void viewportOpenGL() {
        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, (int) WINDOW_RESOLUTION.x, (int) WINDOW_RESOLUTION.y);
    }

    public static void destroyOpenGL() {
        Display.destroy();
    }

}
