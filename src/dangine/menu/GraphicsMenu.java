package dangine.menu;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.world.BackgroundFilterMode;
import dangine.graphics.DangineAntiAliasing;
import dangine.graphics.DangineOpenGL;
import dangine.graphics.DangineStringPicture;
import dangine.menu.DangineMenuItem.Action;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.DangineSavedSettings;
import dangine.utility.Utility;

public class GraphicsMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();

    DangineMenuItem antiAliasingItem = new DangineMenuItem("Anti-Aliasing: "
            + DangineSavedSettings.INSTANCE.getAntiAliasingLevel(), getAntiAliasingUpAction(),
            getAntiAliasingDownAction());

    DangineMenuItem bgFilterItem = new DangineMenuItem("Background Filter: "
            + BackgroundFilterMode.fromInt(DangineSavedSettings.INSTANCE.getBackgroundFilterMode()),
            getNextBackgroundFilterMode());

    SceneGraphNode antiAliasTextNode = new SceneGraphNode();
    DangineStringPicture antiAliasText = new DangineStringPicture();

    public GraphicsMenu() {
        selector.setOnEscape(getExitMenuAction());
        menu.addItem(antiAliasingItem);
        menu.addItem(bgFilterItem);
        menu.addItem(new DangineMenuItem("Done", getExitMenuAction()));
        menu.getBase().addChild(antiAliasTextNode);
        DangineFormatter.format(menu.getBase().getChildNodes());
        antiAliasTextNode.addChild(antiAliasText);
        antiAliasText.setText("Anti-alias changes require game restart.");
        antiAliasTextNode.setPosition(-260 * DangineOpenGL.getWindowWorldAspectX() * DangineStringPicture.STRING_SCALE,
                110 * DangineOpenGL.getWindowWorldAspectY() * DangineStringPicture.STRING_SCALE);

        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.6f));
        menu.getItem(0).getBase().addChild(selector.getDrawable());

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

    private Action getAntiAliasingDownAction() {
        return new Action() {

            @Override
            public void execute() {
                int antiAliasingLevel = DangineSavedSettings.INSTANCE.getAntiAliasingLevel();
                antiAliasingLevel = DangineAntiAliasing.decrement(antiAliasingLevel);
                DangineSavedSettings.INSTANCE.setAntiAliasingLevel(antiAliasingLevel);
                DangineSavedSettings.INSTANCE.save();
                antiAliasingItem.getItemText().setText(
                        "Anti-Aliasing: " + DangineSavedSettings.INSTANCE.getAntiAliasingLevel());
            }

        };
    }

    private Action getAntiAliasingUpAction() {
        return new Action() {

            @Override
            public void execute() {
                int antiAliasingLevel = DangineSavedSettings.INSTANCE.getAntiAliasingLevel();
                antiAliasingLevel = DangineAntiAliasing.increment(antiAliasingLevel);
                DangineSavedSettings.INSTANCE.setAntiAliasingLevel(antiAliasingLevel);
                DangineSavedSettings.INSTANCE.save();
                antiAliasingItem.getItemText().setText(
                        "Anti-Aliasing: " + DangineSavedSettings.INSTANCE.getAntiAliasingLevel());
            }

        };
    }

    private Action getNextBackgroundFilterMode() {
        return new Action() {

            @Override
            public void execute() {
                int filterMode = DangineSavedSettings.INSTANCE.getBackgroundFilterMode();
                filterMode = BackgroundFilterMode.fromInt(filterMode).nextMode().getFilterMode();
                DangineSavedSettings.INSTANCE.setBackgroundFilterMode(filterMode);
                DangineSavedSettings.INSTANCE.save();
                String filterString = "Background Filter: "
                        + BackgroundFilterMode.fromInt(DangineSavedSettings.INSTANCE.getBackgroundFilterMode());
                bgFilterItem.getItemText().setText(filterString);
            }

        };
    }

    private Action getExitMenuAction() {
        return new Action() {

            @Override
            public void execute() {
                DangineSavedSettings.INSTANCE.save();
                TitleMenu titleMenu = new TitleMenu();
                Utility.getActiveScene().addUpdateable(titleMenu);
                Utility.getActiveScene().removeUpdateable(GraphicsMenu.this);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(GraphicsMenu.this.getDrawable());
            }

        };
    }

}
