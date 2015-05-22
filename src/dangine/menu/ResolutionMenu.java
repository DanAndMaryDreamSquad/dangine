package dangine.menu;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.graphics.DangineOpenGL;
import dangine.input.DangineOpenGLInput;
import dangine.menu.DangineMenuItem.Action;
import dangine.utility.Utility;

public class ResolutionMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();

    DangineMenuItem displayModeItem = new DangineMenuItem("Fullscreen", getDisplayModeAction());

    public ResolutionMenu() {
        selector.setOnEscape(getExitMenuAction());
        menu.addItem(displayModeItem);
        menu.addItem(new DangineMenuItem("Done", getExitMenuAction()));
        DangineFormatter.format(menu.getBase().getChildNodes());

        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.65f));
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        updateText();
    }

    @Override
    public IsDrawable getDrawable() {
        return menu.getDrawable();
    }

    @Override
    public void update() {
        selector.update();
        selector.scan(menu.getItems());
    }

    private void updateText() {
        if (Display.isFullscreen()) {
            displayModeItem.getItemText().setText("Windowed");
        } else {
            displayModeItem.getItemText().setText("Fullscreen");
        }
    }

    private Action getDisplayModeAction() {
        return new Action() {

            @Override
            public void execute() {
                if (Display.isFullscreen()) {
                    setWindowedMode();
                } else {
                    setFullScreenMode();
                }

                updateText();

                // Clear all key states since keyboard events are missed when
                // display mode changes.
                DangineOpenGLInput.clearKeyStates();
            }
        };
    }

    // TODO: Create resolution change actions.

    public void setFullScreenMode() {

        try {
            Display.setDisplayMode(Display.getDesktopDisplayMode());
            Display.setFullscreen(true);
            DangineOpenGL.WINDOW_RESOLUTION.set(Display.getWidth(), Display.getHeight());

        } catch (LWJGLException e) {
            Debugger.info("Could not set display to fullscreen mode.");
        }

    }

    public void setWindowedMode() {
        try {
            Display.setDisplayMode(Display.getDesktopDisplayMode());
            Display.setFullscreen(false);
            DangineOpenGL.WINDOW_RESOLUTION.set(Display.getWidth(), Display.getHeight());

        } catch (LWJGLException e) {
            Debugger.info("Could not set display to windowed mode.");
        }
    }

    public void setResolution(int width, int height, boolean fullscreen) {

        // return if requested DisplayMode is already set
        if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height)
                && (Display.isFullscreen() == fullscreen)) {
            return;
        }

        try {
            DisplayMode bestModeWeveFoundSoFar = null;

            if (fullscreen) {
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

                        // if we've found a match for bpp and frequence against
                        // the
                        // original display mode then it's probably best to go
                        // for this one
                        // since it's most likely compatible with the monitor
                        if ((candidateMode.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
                                && (candidateMode.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                            bestModeWeveFoundSoFar = candidateMode;
                            break;
                        }
                    }
                }
            } else {
                bestModeWeveFoundSoFar = new DisplayMode(width, height);
            }

            if (bestModeWeveFoundSoFar == null) {
                System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
                return;
            }

            Display.setDisplayMode(bestModeWeveFoundSoFar);
            Display.setFullscreen(fullscreen);

        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
        }
    }

    private Action getExitMenuAction() {
        return new Action() {

            @Override
            public void execute() {
                TitleMenu titleMenu = new TitleMenu();
                Utility.getActiveScene().addUpdateable(titleMenu);
                Utility.getActiveScene().removeUpdateable(ResolutionMenu.this);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(ResolutionMenu.this.getDrawable());
            }

        };
    }

}
