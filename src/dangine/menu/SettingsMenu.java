package dangine.menu;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.menu.DangineMenuItem.Action;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;
import dangine.utility.Utility;

public class SettingsMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();
    SceneGraphNode node = new SceneGraphNode();
    DangineText stockText = new DangineText();

    public SettingsMenu() {
        menu.addItem(new DangineMenuItem("Stock: ", getStockIncrementAction(), getStockDecrementAction()));
        menu.addItem(new DangineMenuItem("Done", getExitMenuAction()));
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.75f));
        menu.getBase().addChild(node);
        node.addChild(stockText);
        updateText();
        DangineFormatter.format(menu.getBase().getChildNodes());
        node.setPosition(60, 0);
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
        stockText.setText("" + Utility.getMatchParameters().getStartingStock());
    }

    private Action getStockIncrementAction() {
        return new Action() {

            @Override
            public void execute() {
                int startingStock = Utility.getMatchParameters().getStartingStock();
                if (startingStock < 100) {
                    startingStock++;
                }
                Utility.getMatchParameters().setStartingStock(startingStock);
                updateText();
            }

        };
    }

    private Action getStockDecrementAction() {
        return new Action() {

            @Override
            public void execute() {
                int startingStock = Utility.getMatchParameters().getStartingStock();
                if (startingStock > 0) {
                    startingStock--;
                }
                Utility.getMatchParameters().setStartingStock(startingStock);
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
                Utility.getActiveScene().removeUpdateable(SettingsMenu.this);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(SettingsMenu.this.getDrawable());
            }

        };
    }

}
