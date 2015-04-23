package dangine.menu;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.subpower.SubPower;
import dangine.menu.DangineMenuItem.Action;
import dangine.scene.CharacterSelect;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;
import dangine.utility.Utility;

public class BotSettingsMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();
    final SceneGraphNode powerTextNode = new SceneGraphNode();
    final DangineText powerText = new DangineText();

    public BotSettingsMenu() {
        selector.setOnEscape(getOnEscapeAction());
        menu.addItem(new DangineMenuItem("Confirm Bot Settings", getConfirmAction()));
        menu.addItem(new DangineMenuItem("Bot Sub Power", getNextPowerAction()));
        menu.getBase().addChild(powerTextNode);
        menu.addItem(new DangineMenuItem("Back", getOnEscapeAction()));
        DangineFormatter.format(menu.getBase().getChildNodes());
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.75f));

        powerTextNode.addChild(powerText);
        updateText();
    }

    @Override
    public void update() {
        selector.update();
        selector.scan(menu.getItems());
    }

    @Override
    public IsDrawable getDrawable() {
        return menu.getDrawable();
    }

    private void updateText() {
        powerText.setText("" + Utility.getMatchParameters().getPlayerPower(-1));
    }

    private Action getConfirmAction() {
        return new Action() {

            @Override
            public void execute() {
                WorldSelectionMenu worldMenu = new WorldSelectionMenu();
                Utility.getActiveScene().addUpdateable(worldMenu);
                Utility.getActiveScene().getParentNode().addChild(worldMenu.getDrawable());
                Utility.getActiveScene().removeUpdateable(BotSettingsMenu.this);
                Utility.getActiveScene().getParentNode().removeChild(BotSettingsMenu.this.getDrawable());
            }
        };
    }

    private Action getNextPowerAction() {
        return new Action() {

            @Override
            public void execute() {
                SubPower power = Utility.getMatchParameters().getPlayerPower(-1);
                power = power.nextPower();
                Utility.getMatchParameters().addPlayerPower(-1, power);
                updateText();
            }
        };
    }

    private Action getOnEscapeAction() {
        return new Action() {

            @Override
            public void execute() {
                CharacterSelect characterSelect = new CharacterSelect();
                Utility.getActiveScene().addUpdateable(characterSelect);
                Utility.getActiveScene().removeUpdateable(BotSettingsMenu.this);
                Utility.getActiveScene().getParentNode().removeChild(BotSettingsMenu.this.getDrawable());
            }
        };
    }
}