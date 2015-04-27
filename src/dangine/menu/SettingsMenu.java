package dangine.menu;

import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.movement.AttackMode;
import dangine.entity.movement.FacingMode;
import dangine.entity.movement.MovementMode;
import dangine.menu.DangineMenuItem.Action;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.DangineText;
import dangine.utility.Utility;

public class SettingsMenu implements IsUpdateable, HasDrawable {

    DangineMenu menu = new DangineMenu();
    DangineSelector selector = new DangineSelector();
    SceneGraphNode stockTextNode = new SceneGraphNode();
    DangineText stockText = new DangineText();
    SceneGraphNode movementModeTextNode = new SceneGraphNode();
    DangineText movementModeText = new DangineText();
    SceneGraphNode movementModeEnumNode = new SceneGraphNode();
    DangineText movementModeEnumText = new DangineText();

    SceneGraphNode attackModeTextNode = new SceneGraphNode();
    DangineText attackModeText = new DangineText();
    SceneGraphNode attackModeEnumNode = new SceneGraphNode();
    DangineText attackModeEnumText = new DangineText();

    SceneGraphNode facingModeTextNode = new SceneGraphNode();
    DangineText facingModeText = new DangineText();
    SceneGraphNode facingModeEnumNode = new SceneGraphNode();
    DangineText facingModeEnumText = new DangineText();

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
