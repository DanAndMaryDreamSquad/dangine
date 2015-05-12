package dangine.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DangineOpenGL {

    // Setup variables
    static private final String WINDOW_TITLE = "Sample";
    static public final int WIDTH = 800;
    static public final int HEIGHT = 600;

    public static void setupOpenGL() {
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

        GL11.glEnable(GL11.GL_BLEND); // you enable blending function (for
                                      // images with transparency)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); // Some
                                                                          // sort
                                                                          // of
                                                                          // blending
                                                                          // function
                                                                          // that
                                                                          // supports
                                                                          // images
                                                                          // with
                                                                          // transparency
    }

    public static void viewportPortOpenGL() {
        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }

    public static void destroyOpenGL() {
        Display.destroy();
    }

}
