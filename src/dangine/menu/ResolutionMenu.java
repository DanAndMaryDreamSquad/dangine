package dangine.menu;

import java.util.ArrayList;
import java.util.List;

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
import dangine.utility.DangineSavedSettings;
import dangine.utility.Utility;

public class ResolutionMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();

    DangineMenuItem displayModeItem = new DangineMenuItem("Set to Fullscreen", getDisplayModeAction());

    DangineMenuItem borderlessWindowItem = new DangineMenuItem("Borderless Window ?", getBorderlessWindowModeAction());
    List<DangineMenuItem> resolutionItems = buildResolutionItems();
    DangineMenuItem doneItem = new DangineMenuItem("Done", getExitMenuAction());

    public ResolutionMenu() {
        selector.setOnEscape(getExitMenuAction());
        menu.addItem(displayModeItem);
        menu.addItem(borderlessWindowItem);
        for (DangineMenuItem item : resolutionItems) {
            menu.addItem(item);
        }
        menu.addItem(doneItem);
        DangineFormatter.format(menu.getBase().getChildNodes());

        menu.getBase().setPosition(Utility.getResolution().x * 0.12f, Utility.getResolution().y * 0.12f);
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
            displayModeItem.getItemText().setText("Set to Windowed");
        } else {
            displayModeItem.getItemText().setText("Set to Fullscreen");
        }
        if (DangineSavedSettings.INSTANCE.isBorderlessWindow()) {
            borderlessWindowItem.getItemText().setText("Set Bordered Window");
        } else {
            borderlessWindowItem.getItemText().setText("Set Borderless Window");
        }
        List<int[]> resolutionPairs = DangineOpenGL.getSortedDisplayResolutionsAscending();
        for (int i = 0; i < resolutionItems.size(); i++) {
            DangineMenuItem item = resolutionItems.get(i);
            int[] pair = resolutionPairs.get(i);
            item.getItemText().setText(pair[0] + " x " + pair[1]);
        }
        doneItem.getItemText().setText("Done");
        DangineFormatter.format(menu.getBase().getChildNodes());

    }

    private List<DangineMenuItem> buildResolutionItems() {
        List<DangineMenuItem> items = new ArrayList<DangineMenuItem>();
        List<int[]> resolutionPairs = DangineOpenGL.getSortedDisplayResolutionsAscending();
        for (int[] pair : resolutionPairs) {
            items.add(new DangineMenuItem(pair[0] + " x " + pair[1], getChangeResolutionAction(pair[0], pair[1])));
        }
        return items;
    }

    private Action getChangeResolutionAction(final int x, final int y) {
        return new Action() {

            @Override
            public void execute() {
                DangineSavedSettings.INSTANCE.setResolutionX(x);
                DangineSavedSettings.INSTANCE.setResolutionY(y);
                setResolution(x, y, DangineSavedSettings.INSTANCE.isFullscreen());
                DangineOpenGL.WINDOW_RESOLUTION.set(DangineSavedSettings.INSTANCE.getResolutionX(),
                        DangineSavedSettings.INSTANCE.getResolutionY());
                DangineSavedSettings.INSTANCE.save();
                DangineOpenGL.refreshTextScaleForResolution();
                DangineOpenGLInput.clearKeyStates();
                updateText();
            }
        };
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

    private Action getBorderlessWindowModeAction() {
        return new Action() {

            @Override
            public void execute() {
                if (Display.isFullscreen()) {
                    Debugger.warn("Borderless window trying to be changed in full screen");
                } else {
                    toggleBorderlessWindow();
                }
            }
        };
    }

    private void toggleBorderlessWindow() {
        boolean isBorderless = Boolean.parseBoolean(System.getProperty("org.lwjgl.opengl.Window.undecorated"));
        Debugger.info("borderless right now? " + isBorderless);
        isBorderless = !isBorderless;
        DangineSavedSettings.INSTANCE.setBorderlessWindow(isBorderless);
        System.setProperty("org.lwjgl.opengl.Window.undecorated",
                String.valueOf(DangineSavedSettings.INSTANCE.isBorderlessWindow()));
        DangineSavedSettings.INSTANCE.save();
        Utility.setReRunRequested(true);
        Utility.setCloseRequested(true);
        DangineOpenGLInput.clearKeyStates();
    }

    public void setFullScreenMode() {

        try {
            Display.setDisplayMode(DangineOpenGL.findBestDisplayModeForFullscreenResolution(
                    DangineSavedSettings.INSTANCE.getResolutionX(), DangineSavedSettings.INSTANCE.getResolutionY()));
            Display.setFullscreen(true);
            DangineSavedSettings.INSTANCE.setFullscreen(true);
            DangineOpenGL.WINDOW_RESOLUTION.set(Display.getWidth(), Display.getHeight());
            DangineOpenGL.WINDOW_RESOLUTION.set(DangineSavedSettings.INSTANCE.getResolutionX(),
                    DangineSavedSettings.INSTANCE.getResolutionY());
            DangineSavedSettings.INSTANCE.save();

        } catch (LWJGLException e) {
            Debugger.info("Could not set display to fullscreen mode.");
        }

    }

    public void setWindowedMode() {
        try {
            System.setProperty("org.lwjgl.opengl.Window.undecorated",
                    String.valueOf(DangineSavedSettings.INSTANCE.isBorderlessWindow()));
            DisplayMode mode = new DisplayMode(DangineSavedSettings.INSTANCE.getResolutionX(),
                    DangineSavedSettings.INSTANCE.getResolutionY());
            Display.setDisplayMode(mode);
            Display.setFullscreen(false);
            DangineSavedSettings.INSTANCE.setFullscreen(false);
            DangineOpenGL.WINDOW_RESOLUTION.set(DangineSavedSettings.INSTANCE.getResolutionX(),
                    DangineSavedSettings.INSTANCE.getResolutionY());
            DangineSavedSettings.INSTANCE.save();

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
                DangineSavedSettings.INSTANCE.save();
                TitleMenu titleMenu = new TitleMenu();
                Utility.getActiveScene().addUpdateable(titleMenu);
                Utility.getActiveScene().removeUpdateable(ResolutionMenu.this);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(ResolutionMenu.this.getDrawable());
            }
        };
    }

}
