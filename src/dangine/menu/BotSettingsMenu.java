package dangine.menu;

import org.newdawn.slick.Color;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.subpower.SubPower;
import dangine.menu.DangineMenuItem.Action;
import dangine.scene.CharacterSelect;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.BloxColorer;
import dangine.scenegraph.drawable.DangineText;
import dangine.utility.Utility;

public class BotSettingsMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();
    final SceneGraphNode powerTextNode = new SceneGraphNode();
    final DangineText powerText = new DangineText();
    SceneGraphNode botNumberTextNode = new SceneGraphNode();
    DangineText botNumberText = new DangineText();

    public BotSettingsMenu() {
        selector.setOnEscape(getOnEscapeAction());
        menu.addItem(new DangineMenuItem("Confirm Bot Settings", getConfirmAction()));
        menu.addItem(new DangineMenuItem("Bot Sub Power", getNextPowerAction()));
        menu.getBase().addChild(powerTextNode);
        menu.addItem(new DangineMenuItem("Number of Bots:", getBotsIncrementAction(), getBotsDecrementAction()));
        menu.addItem(new DangineMenuItem("Back", getOnEscapeAction()));
        menu.getBase().addChild(botNumberTextNode);
        DangineFormatter.format(menu.getBase().getChildNodes());
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.75f));

        powerTextNode.addChild(powerText);
        botNumberTextNode.addChild(botNumberText);
        botNumberTextNode.setPosition(140, 60);
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
        botNumberText.setText("" + Utility.getMatchParameters().getNumberOfBots());
    }

    private Action getBotsIncrementAction() {
        return new Action() {

            @Override
            public void execute() {
                int numberOfBots = Utility.getMatchParameters().getNumberOfBots();
                if (numberOfBots < 10) {
                    numberOfBots++;
                }
                Utility.getMatchParameters().setNumberOfBots(numberOfBots);
                updateText();
            }
        };
    }

    private Action getBotsDecrementAction() {
        return new Action() {

            @Override
            public void execute() {
                int numberOfBots = Utility.getMatchParameters().getNumberOfBots();
                if (numberOfBots > 1) {
                    numberOfBots--;
                }
                Utility.getMatchParameters().setNumberOfBots(numberOfBots);
                updateText();
            }
        };
    }

    private Action getConfirmAction() {
        return new Action() {

            @Override
            public void execute() {
                Color color = BloxColorer.randomColor();
                SubPower power = Utility.getMatchParameters().getPlayerPower(-1);
                for (int i = 1; i < Utility.getMatchParameters().getNumberOfBots() + 1; i++) {
                    Utility.getMatchParameters().addPlayerColor(-i, color);
                    Utility.getMatchParameters().addPlayerPower(-i, power);
                }
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