package dangine.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import dangine.debugger.Debugger;
import dangine.utility.DangineSavedSettings;
import dangine.utility.Vector2f;

public class DangineOpenGL {

    // Setup variables
    static private final String WINDOW_TITLE = "Sample";
    static public final Vector2f WORLD_RESOLUTION = new Vector2f(1920, 1080);
    static public final Vector2f WINDOW_RESOLUTION = new Vector2f(1920, 1080);
    static public PixelFormat pixelFormat = new PixelFormat();
    static public ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withForwardCompatible(true)
            .withProfileCore(true);

    public static void setupOpenGL() {
        WINDOW_RESOLUTION.x = DangineSavedSettings.INSTANCE.getResolutionX();
        WINDOW_RESOLUTION.y = DangineSavedSettings.INSTANCE.getResolutionY();

        List<int[]> supportedResolutions = getSortedDisplayResolutionsAscending();
        int[] largestResolution = supportedResolutions.get(supportedResolutions.size() - 1);
        if (!isResolutionSupported((int) WINDOW_RESOLUTION.x, (int) WINDOW_RESOLUTION.y, supportedResolutions)) {
            Debugger.warn("Requested resolution " + (int) WINDOW_RESOLUTION.x + " x " + (int) WINDOW_RESOLUTION.y
                    + " is too large for this display. Defaulting to highest found resolution, " + largestResolution[0]
                    + " x " + largestResolution[1]);
            WINDOW_RESOLUTION.x = largestResolution[0];
            WINDOW_RESOLUTION.y = largestResolution[1];
        }
        // Setup an OpenGL context with API version 3.2
        try {
            // Anti-aliasing level
            // 0 will put a red block at 1.5f rounded into pixel 1
            // 4 will smooth it out over pixel 1 and 2.
            PixelFormat pixelFormat = new PixelFormat(0, 0, 0, DangineSavedSettings.INSTANCE.getAntiAliasingLevel());
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(
                    true);
            DisplayMode mode;
            if (DangineSavedSettings.INSTANCE.isFullscreen()) {
                mode = findBestDisplayModeForFullscreenResolution((int) WINDOW_RESOLUTION.x, (int) WINDOW_RESOLUTION.y);
                Display.setFullscreen(true);
            } else {
                mode = new DisplayMode((int) WINDOW_RESOLUTION.x, (int) WINDOW_RESOLUTION.y);
            }
            Display.setVSyncEnabled(true);
            Display.setDisplayMode(mode);
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
        refreshTextScaleForResolution();
    }

    public static void viewportOpenGL() {
        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, (int) WINDOW_RESOLUTION.x, (int) WINDOW_RESOLUTION.y);
    }

    public static void destroyOpenGL() {
        Display.destroy();
    }

    public static void refreshTextScaleForResolution() {
        if (WINDOW_RESOLUTION.y < 600) {
            DangineStringPicture.STRING_SCALE = 1.0f;
        } else if (WINDOW_RESOLUTION.y >= 600 && WINDOW_RESOLUTION.y < 750) {
            DangineStringPicture.STRING_SCALE = 1.4f;
        } else if (WINDOW_RESOLUTION.y >= 750 && WINDOW_RESOLUTION.y < 1000) {
            DangineStringPicture.STRING_SCALE = 2.0f;
        } else {
            DangineStringPicture.STRING_SCALE = 3.0f;
        }
    }

    public static DisplayMode findBestDisplayModeForFullscreenResolution(int width, int height) {

        try {
            DisplayMode bestModeWeveFoundSoFar = null;

            DisplayMode[] modes = Display.getAvailableDisplayModes();
            int freq = 0;

            for (int i = 0; i < modes.length; i++) {
                DisplayMode candidateMode = modes[i];

                if ((candidateMode.getWidth() == width) && (candidateMode.getHeight() == height)) {
                    if ((bestModeWeveFoundSoFar == null) || (candidateMode.getFrequency() >= freq)) {
                        if ((bestModeWeveFoundSoFar == null)
                                || (candidateMode.getBitsPerPixel() > bestModeWeveFoundSoFar.getBitsPerPixel())) {
                            bestModeWeveFoundSoFar = candidateMode;
                            freq = bestModeWeveFoundSoFar.getFrequency();
                        }
                    }
                    if ((candidateMode.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
                            && (candidateMode.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                        bestModeWeveFoundSoFar = candidateMode;
                        break;
                    }
                }
            }

            if (bestModeWeveFoundSoFar == null) {
                System.out.println("Failed to find value mode: " + width + "x" + height + " fs=true");
                return null;
            } else {
                return bestModeWeveFoundSoFar;
            }
        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=true" + e);
        }
        return null;
    }

    public static List<int[]> getSortedDisplayResolutionsAscending() {
        List<int[]> resolutionPairs = new ArrayList<int[]>();
        DisplayMode[] modes = null;
        try {
            modes = Display.getAvailableDisplayModes();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        for (DisplayMode mode : modes) {
            int[] pair = new int[2];
            pair[0] = mode.getWidth();
            pair[1] = mode.getHeight();
            if (!listContainsPair(resolutionPairs, pair)) {
                resolutionPairs.add(pair);
            }
        }
        Collections.sort(resolutionPairs, new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });
        return resolutionPairs;
    }

    private static boolean listContainsPair(List<int[]> pairs, int[] pair) {
        for (int[] p : pairs) {
            if (p[0] == pair[0] || p[1] == pair[1]) {
                return true;
            }
        }
        return false;
    }

    private static boolean isResolutionSupported(int width, int height, List<int[]> resolutions) {
        int[] largestResolution = resolutions.get(resolutions.size() - 1);
        if (largestResolution[0] < width || largestResolution[1] < height) {
            return false;
        }
        return true;
    }

    public static float getWindowWorldAspectX() {
        return DangineOpenGL.WORLD_RESOLUTION.x / DangineOpenGL.WINDOW_RESOLUTION.x;
    }

    public static float getWindowWorldAspectY() {
        return DangineOpenGL.WORLD_RESOLUTION.y / DangineOpenGL.WINDOW_RESOLUTION.y;
    }
}
