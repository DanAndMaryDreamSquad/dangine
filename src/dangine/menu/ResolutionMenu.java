package dangine.menu;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.menu.DangineMenuItem.Action;
import dangine.utility.Utility;

public class ResolutionMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();

    DangineMenuItem fullScreenItem = new DangineMenuItem("Fullscreen", getFullscreenResolutionAction());

    public ResolutionMenu() {
        selector.setOnEscape(getExitMenuAction());
        menu.addItem(fullScreenItem);
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

    // TODO
    private void updateText() {
    }

    // TODO
    private Action getFullscreenResolutionAction() {
        return new Action() {

            @Override
            public void execute() {
                Debugger.info("woo");
                updateText();
            }
        };
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
