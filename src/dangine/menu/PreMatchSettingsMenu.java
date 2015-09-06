package dangine.menu;

import org.lwjgl.util.Color;

import dangine.bots.BotType;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.subpower.SubPower;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.graphics.DangineStringPicture;
import dangine.menu.DangineMenuItem.Action;
import dangine.scene.CharacterSelect;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.BloxColorer;
import dangine.utility.Utility;

public class PreMatchSettingsMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();
    final DangineMenuItem stockItem = new DangineMenuItem("Stock: " + Utility.getMatchParameters().getStartingStock(),
            getStockIncrementAction(), getStockDecrementAction());
    final DangineMenuItem roundsItem = new DangineMenuItem("Rounds: "
            + Utility.getMatchParameters().getRoundKeeper().getRoundsRequiredToWin(), getIncreaseRoundsAction(),
            getDecreaseRoundsAction());

    final DangineMenuItem botItem = new DangineMenuItem("Bots: " + Utility.getMatchParameters().getNumberOfBots(),
            getBotsIncrementAction(), getBotsDecrementAction());
    final SceneGraphNode powerTextNode = new SceneGraphNode();
    final DangineStringPicture powerText = new DangineStringPicture();
    final SceneGraphNode typeTextNode = new SceneGraphNode();
    final DangineStringPicture typeText = new DangineStringPicture();

    final DangineMenuItem vampireItem = new DangineMenuItem("life steal: "
            + Utility.getMatchParameters().isVampireMode(), getNextLifeStealAction());

    DangineMenuItem friendlyFireItem = new DangineMenuItem("Friendly Fire: "
            + Utility.getMatchParameters().isFriendlyFire(), getToggleFriendlyFireAction());

    public PreMatchSettingsMenu() {
        selector.setOnEscape(getOnEscapeAction());
        menu.addItem(new DangineMenuItem("Confirm", getConfirmAction()));
        menu.addItem(stockItem);
        menu.addItem(roundsItem);
        menu.addItem(vampireItem);
        if (Utility.getMatchParameters().getMatchType() == MatchType.TEAM_VERSUS) {
            menu.addItem(friendlyFireItem);
        }
        menu.addItem(botItem);
        menu.addItem(new DangineMenuItem("Bot Sub Power", getNextPowerAction()));
        menu.getBase().addChild(powerTextNode);
        menu.addItem(new DangineMenuItem("Bot Type:", getNextBotTypeAction()));
        menu.getBase().addChild(typeTextNode);
        menu.addItem(new DangineMenuItem("Back", getOnEscapeAction()));
        DangineFormatter.formatDoubleWide(menu.getBase().getChildNodes());
        menu.getItem(0).getBase().addChild(selector.getDrawable());
        menu.getBase().setPosition(Utility.getResolution().x * 0.12f, Utility.getResolution().y * (0.12f));

        powerTextNode.addChild(powerText);
        typeTextNode.addChild(typeText);
        enforceBotCount();
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
        typeText.setText("" + Utility.getMatchParameters().getBotType());
    }

    private void enforceBotCount() {
        int botCount = Utility.getMatchParameters().getNumberOfBots();
        if (Utility.getPlayers().getPlayers().size() <= 1) {
            botCount = Math.max(1, botCount);
        } else {
            botCount = Math.max(0, botCount);
        }
        botCount = Math.min(10, botCount);
        Utility.getMatchParameters().setNumberOfBots(botCount);
        botItem.getItemText().setText("Bots: " + Utility.getMatchParameters().getNumberOfBots());

    }

    private void enforceStockCount() {
        int stock = Utility.getMatchParameters().getStartingStock();
        int minStock = 0;
        int maxStock = 10;
        if (Utility.getMatchParameters().isVampireMode()) {
            minStock = 1;
            maxStock = 3;
        }

        stock = Math.max(minStock, stock);
        stock = Math.min(maxStock, stock);
        Utility.getMatchParameters().setStartingStock(stock);
        stockItem.getItemText().setText("Stock: " + Utility.getMatchParameters().getStartingStock());
    }

    private Action getStockIncrementAction() {
        return new Action() {

            @Override
            public void execute() {
                int startingStock = Utility.getMatchParameters().getStartingStock();
                startingStock++;
                Utility.getMatchParameters().setStartingStock(startingStock);
                enforceStockCount();
            }

        };
    }

    private Action getStockDecrementAction() {
        return new Action() {

            @Override
            public void execute() {
                int startingStock = Utility.getMatchParameters().getStartingStock();
                startingStock--;
                Utility.getMatchParameters().setStartingStock(startingStock);
                enforceStockCount();
            }

        };
    }

    private Action getIncreaseRoundsAction() {
        return new Action() {

            @Override
            public void execute() {
                int rounds = Utility.getMatchParameters().getRoundKeeper().getRoundsRequiredToWin();
                if (rounds < 10) {
                    rounds++;
                }
                Utility.getMatchParameters().getRoundKeeper().setRoundsRequiredToWin(rounds);
                roundsItem.getItemText().setText(
                        "Rounds: " + Utility.getMatchParameters().getRoundKeeper().getRoundsRequiredToWin());
            }

        };
    }

    private Action getDecreaseRoundsAction() {
        return new Action() {

            @Override
            public void execute() {
                int rounds = Utility.getMatchParameters().getRoundKeeper().getRoundsRequiredToWin();
                if (rounds > 1) {
                    rounds--;
                }
                Utility.getMatchParameters().getRoundKeeper().setRoundsRequiredToWin(rounds);
                roundsItem.getItemText().setText(
                        "Rounds: " + Utility.getMatchParameters().getRoundKeeper().getRoundsRequiredToWin());
            }

        };
    }

    private Action getToggleFriendlyFireAction() {
        return new Action() {

            @Override
            public void execute() {
                boolean isFriendlyFire = Utility.getMatchParameters().isFriendlyFire();
                isFriendlyFire = !isFriendlyFire;
                Utility.getMatchParameters().setFriendlyFire(isFriendlyFire);
                friendlyFireItem.getItemText().setText(
                        "Friendly Fire: " + Utility.getMatchParameters().isFriendlyFire());
            }

        };
    }

    private Action getBotsIncrementAction() {
        return new Action() {

            @Override
            public void execute() {
                int numberOfBots = Utility.getMatchParameters().getNumberOfBots();
                numberOfBots++;
                Utility.getMatchParameters().setNumberOfBots(numberOfBots);
                enforceBotCount();
                updateText();
            }
        };
    }

    private Action getBotsDecrementAction() {
        return new Action() {

            @Override
            public void execute() {
                int numberOfBots = Utility.getMatchParameters().getNumberOfBots();
                numberOfBots--;
                Utility.getMatchParameters().setNumberOfBots(numberOfBots);
                enforceBotCount();
                updateText();
            }
        };
    }

    private Action getConfirmAction() {
        return new Action() {

            @Override
            public void execute() {
                Color color = BloxColorer.randomColor();
                color = BloxColorer.tintColor(color);
                SubPower power = Utility.getMatchParameters().getPlayerPower(-1);
                for (int i = 1; i < Utility.getMatchParameters().getNumberOfBots() + 1; i++) {
                    Utility.getMatchParameters().addPlayerColor(-i, color);
                    Utility.getMatchParameters().addPlayerPower(-i, power);
                }
                WorldSelectionMenu worldMenu = new WorldSelectionMenu();
                Utility.getActiveScene().addUpdateable(worldMenu);
                Utility.getActiveScene().getParentNode().addChild(worldMenu.getDrawable());
                Utility.getActiveScene().removeUpdateable(PreMatchSettingsMenu.this);
                Utility.getActiveScene().getParentNode().removeChild(PreMatchSettingsMenu.this.getDrawable());
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

    private Action getNextBotTypeAction() {
        return new Action() {

            @Override
            public void execute() {
                BotType type = Utility.getMatchParameters().getBotType();
                type = type.nextType();
                Utility.getMatchParameters().setBotType(type);
                updateText();
            }
        };
    }

    private Action getNextLifeStealAction() {
        return new Action() {

            @Override
            public void execute() {
                boolean isLifeSteal = Utility.getMatchParameters().isVampireMode();
                isLifeSteal = !isLifeSteal;
                Utility.getMatchParameters().setVampireMode(isLifeSteal);
                vampireItem.getItemText().setText("life steal: " + isLifeSteal);
                enforceStockCount();
            }
        };
    }

    private Action getOnEscapeAction() {
        return new Action() {

            @Override
            public void execute() {
                CharacterSelect characterSelect = new CharacterSelect();
                Utility.getActiveScene().addUpdateable(characterSelect);
                Utility.getActiveScene().removeUpdateable(PreMatchSettingsMenu.this);
                Utility.getActiveScene().getParentNode().removeChild(PreMatchSettingsMenu.this.getDrawable());
            }
        };
    }
}