package dangine.menu;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.movement.AttackMode;
import dangine.entity.movement.FacingMode;
import dangine.entity.movement.MovementMode;
import dangine.graphics.DangineStringPicture;
import dangine.menu.DangineMenuItem.Action;
import dangine.scenegraph.SceneGraphNode;
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

    SceneGraphNode facingModeTextNode = new SceneGraphNode();
    DangineStringPicture facingModeText = new DangineStringPicture();
    SceneGraphNode facingModeEnumNode = new SceneGraphNode();
    DangineStringPicture facingModeEnumText = new DangineStringPicture();

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
        menu.addItem(new DangineMenuItem("Change Movement Mode: ", getNextMovementModeAction()));
        menu.getBase().addChild(movementModeTextNode);
        // menu.addItem(new DangineMenuItem("Change Attack Mode: ",
        // getNextAttackModeAction()));
        // menu.getBase().addChild(attackModeTextNode);
        menu.getBase().addChild(new SceneGraphNode());
        menu.addItem(new DangineMenuItem("Change Facing Mode: ", getNextFacingModeAction()));
        menu.getBase().addChild(facingModeTextNode);
        menu.addItem(musicVolumeItem);
        menu.addItem(soundVolumeItem);
        menu.addItem(new DangineMenuItem("Done", getExitMenuAction()));
        menu.getBase().addChild(stockTextNode);
        DangineFormatter.format(menu.getBase().getChildNodes());

        stockTextNode.addChild(stockText);
        movementModeTextNode.addChild(movementModeText);
        movementModeTextNode.addChild(movementModeEnumNode);
        movementModeEnumNode.addChild(movementModeEnumText);
        // attackModeTextNode.addChild(attackModeText);
        // attackModeTextNode.addChild(attackModeEnumNode);
        // attackModeEnumNode.addChild(attackModeEnumText);
        facingModeTextNode.addChild(facingModeText);
        facingModeTextNode.addChild(facingModeEnumNode);
        facingModeEnumNode.addChild(facingModeEnumText);

        stockTextNode.setPosition(60, 0);
        movementModeTextNode.setPosition(-Utility.getResolution().x / 2, movementModeTextNode.getPosition().y);
        attackModeTextNode.setPosition(-Utility.getResolution().x / 2, attackModeTextNode.getPosition().y);
        facingModeTextNode.setPosition(-Utility.getResolution().x / 2, facingModeTextNode.getPosition().y);
        movementModeEnumNode.setPosition(0, -20);
        attackModeEnumNode.setPosition(0, -20);
        facingModeEnumNode.setPosition(0, -20);

        menu.getBase().setPosition(Utility.getResolution().x / 2, Utility.getResolution().y * (0.6f));
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
        movementModeText.setText("" + Utility.getMatchParameters().getMovementMode().description());
        movementModeEnumText.setText("" + Utility.getMatchParameters().getMovementMode().toString());
        attackModeText.setText("" + Utility.getMatchParameters().getAttackMode().description());
        attackModeEnumText.setText("" + Utility.getMatchParameters().getAttackMode().toString());
        facingModeText.setText("" + Utility.getMatchParameters().getFacingMode().description());
        facingModeEnumText.setText("" + Utility.getMatchParameters().getFacingMode().toString());
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

    private Action getNextFacingModeAction() {
        return new Action() {

            @Override
            public void execute() {
                FacingMode facingMode = Utility.getMatchParameters().getFacingMode();
                facingMode = facingMode.nextMode();
                Utility.getMatchParameters().setFacingMode(facingMode);
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
                TitleMenu titleMenu = new TitleMenu();
                Utility.getActiveScene().addUpdateable(titleMenu);
                Utility.getActiveScene().removeUpdateable(SettingsMenu.this);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
                Utility.getActiveScene().getParentNode().removeChild(SettingsMenu.this.getDrawable());
            }

        };
    }

}
