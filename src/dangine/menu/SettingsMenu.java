package dangine.menu;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.movement.AttackMode;
import dangine.entity.movement.MovementMode;
import dangine.graphics.DangineOpenGL;
import dangine.graphics.DangineStringPicture;
import dangine.menu.DangineMenuItem.Action;
import dangine.scenegraph.SceneGraphNode;
import dangine.utility.DangineSavedSettings;
import dangine.utility.Utility;

public class SettingsMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();
    SceneGraphNode stockTextNode = new SceneGraphNode();
    DangineStringPicture stockText = new DangineStringPicture();
    SceneGraphNode movementModeTextNode = new SceneGraphNode();
    DangineStringPicture movementModeText = new DangineStringPicture();
    SceneGraphNode movementModeEnumNode = new SceneGraphNode();
    DangineStringPicture movementModeEnumText = new DangineStringPicture();

    SceneGraphNode attackModeTextNode = new SceneGraphNode();
    DangineStringPicture attackModeText = new DangineStringPicture();
    SceneGraphNode attackModeEnumNode = new SceneGraphNode();
    DangineStringPicture attackModeEnumText = new DangineStringPicture();

    DangineMenuItem movementModeItem = new DangineMenuItem("Movement mode: "
            + Utility.getMatchParameters().getMovementMode().toString(), getNextMovementModeAction());

    DangineMenuItem musicVolumeItem = new DangineMenuItem("Music Volume "
            + Utility.getGameParameters().getMusicVolumeString(), getMusicVolumeUpAction(), getMusicVolumeDownAction());

    DangineMenuItem soundVolumeItem = new DangineMenuItem("Sound Effect Volume "
            + Utility.getGameParameters().getSoundEffectVolumeString(), getSoundEffectVolumeUpAction(),
            getSoundEffectVolumeDownAction());

    DangineMenuItem friendlyFireItem = new DangineMenuItem("Friendly Fire: "
            + Utility.getMatchParameters().isFriendlyFire(), getToggleFriendlyFireAction());

    public SettingsMenu() {
        selector.setOnEscape(getExitMenuAction());
        menu.addItem(new DangineMenuItem("Stock: ", getStockIncrementAction(), getStockDecrementAction()));
        menu.addItem(friendlyFireItem);
        menu.addItem(musicVolumeItem);
        menu.addItem(soundVolumeItem);
        menu.addItem(movementModeItem);
        menu.getBase().addChild(movementModeTextNode);
        menu.getBase().addChild(new SceneGraphNode());
        menu.addItem(new DangineMenuItem("Done", getExitMenuAction()));
        menu.getBase().addChild(stockTextNode);
        DangineFormatter.formatDoubleWide(menu.getBase().getChildNodes());

        stockTextNode.addChild(stockText);
        movementModeTextNode.addChild(movementModeText);
        movementModeEnumNode.addChild(movementModeEnumText);
        // attackModeTextNode.addChild(attackModeText);
        // attackModeTextNode.addChild(attackModeEnumNode);
        // attackModeEnumNode.addChild(attackModeEnumText);

        stockTextNode.setPosition(60 * DangineOpenGL.getWindowWorldAspectX() * DangineStringPicture.STRING_SCALE, 0);
        movementModeTextNode.setPosition(0, movementModeTextNode.getPosition().y);
        attackModeTextNode.setPosition(0, attackModeTextNode.getPosition().y);
        movementModeEnumNode.setPosition(0, 0);
        attackModeEnumNode.setPosition(0, 0);

        menu.getBase().setPosition(Utility.getResolution().x * (0.12f), Utility.getResolution().y * (0.12f));
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
        stockText.setText("" + Utility.getMatchParameters().getStartingStock());
        friendlyFireItem.getItemText().setText("Friendly Fire: " + Utility.getMatchParameters().isFriendlyFire());
        movementModeItem.getItemText().setText(
                "Movement mode: " + Utility.getMatchParameters().getMovementMode().toString());
        movementModeText.setText("" + Utility.getMatchParameters().getMovementMode().description());
        attackModeText.setText("" + Utility.getMatchParameters().getAttackMode().description());
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

    private Action getToggleFriendlyFireAction() {
        return new Action() {

            @Override
            public void execute() {
                boolean isFriendlyFire = Utility.getMatchParameters().isFriendlyFire();
                isFriendlyFire = !isFriendlyFire;
                Utility.getMatchParameters().setFriendlyFire(isFriendlyFire);
                updateText();
            }

        };
    }

    private Action getNextMovementModeAction() {
        return new Action() {

            @Override
            public void execute() {
                MovementMode movementMode = Utility.getMatchParameters().getMovementMode();
                movementMode = movementMode.nextMode();
                Utility.getMatchParameters().setMovementMode(movementMode);
                updateText();
            }

        };
    }

    @SuppressWarnings("unused")
    private Action getNextAttackModeAction() {
        return new Action() {

            @Override
            public void execute() {
                AttackMode attackMode = Utility.getMatchParameters().getAttackMode();
                attackMode = attackMode.nextMode();
                Utility.getMatchParameters().setAttackMode(attackMode);
                updateText();
            }

        };
    }

    private Action getMusicVolumeUpAction() {
        return new Action() {

            @Override
            public void execute() {
                float volume = Utility.getGameParameters().getMusicVolume();
                volume += 0.1f;
                volume = Math.min(1.0f, volume);
                Utility.getGameParameters().setMusicVolume(volume);
                musicVolumeItem.getItemText().setText(
                        "Music Volume " + Utility.getGameParameters().getMusicVolumeString());
            }

        };
    }

    private Action getMusicVolumeDownAction() {
        return new Action() {

            @Override
            public void execute() {
                float volume = Utility.getGameParameters().getMusicVolume();
                volume -= 0.1f;
                volume = Math.max(0, volume);
                Utility.getGameParameters().setMusicVolume(volume);
                musicVolumeItem.getItemText().setText(
                        "Music Volume " + Utility.getGameParameters().getMusicVolumeString());
            }

        };
    }

    private Action getSoundEffectVolumeUpAction() {
        return new Action() {

            @Override
            public void execute() {
                float volume = Utility.getGameParameters().getSoundEffectVolume();
                volume += 0.1f;
                volume = Math.min(1.0f, volume);
                Utility.getGameParameters().setSoundEffectVolume(volume);
                soundVolumeItem.getItemText().setText(
                        "Sound Effect Volume " + Utility.getGameParameters().getSoundEffectVolumeString());
            }

        };
    }

    private Action getSoundEffectVolumeDownAction() {
        return new Action() {

            @Override
            public void execute() {
                float volume = Utility.getGameParameters().getSoundEffectVolume();
                volume -= 0.1f;
                volume = Math.max(0, volume);
                Utility.getGameParameters().setSoundEffectVolume(volume);
                soundVolumeItem.getItemText().setText(
                        "Sound Effect Volume " + Utility.getGameParameters().getSoundEffectVolumeString());
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
                Utility.getActiveScene().removeUpdateable(SettingsMenu.this);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(SettingsMenu.this.getDrawable());
            }

        };
    }

}
