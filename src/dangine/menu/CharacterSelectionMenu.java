package dangine.menu;

import org.lwjgl.util.Color;

import dangine.debugger.Debugger;
import dangine.entity.HasDrawable;
import dangine.entity.IsDrawable;
import dangine.entity.IsUpdateable;
import dangine.entity.combat.subpower.SubPower;
import dangine.entity.gameplay.MatchStarter.MatchType;
import dangine.graphics.DangineStringPicture;
import dangine.menu.DangineMenuItem.Action;
import dangine.scenegraph.SceneGraphNode;
import dangine.scenegraph.drawable.BloxAnimator;
import dangine.scenegraph.drawable.BloxColorer;
import dangine.scenegraph.drawable.BloxSceneGraph;
import dangine.utility.ScreenUtility;
import dangine.utility.Utility;
import dangine.utility.Vector2f;

public class CharacterSelectionMenu implements IsUpdateable, HasDrawable {

    final int playerId;
    final SceneGraphNode node = new SceneGraphNode();
    final DangineMenu menu = new DangineMenu();
    final DangineSelector selector;
    final SceneGraphNode powerTextNode = new SceneGraphNode();
    final DangineStringPicture powerText = new DangineStringPicture();
    final SceneGraphNode teamTextNode = new SceneGraphNode();
    final DangineStringPicture teamText = new DangineStringPicture();

    BloxSceneGraph blox = new BloxSceneGraph();
    BloxAnimator animator = new BloxAnimator(blox);
    Color color = new Color(Color.RED);
    boolean isDone = false;
    boolean isEscaping = false;
    float timer = 0;
    final float EFFECT_TIME = 3000f;

    public CharacterSelectionMenu(int playerId) {
        this.playerId = playerId;
        selector = new DangineSelector(playerId);
        selector.setOnEscape(getOnEscapeAction());
        animator.idle();
        node.addChild(menu.getDrawable());
        node.addChild(blox.getDrawable());
        // node.setPosition((200 * (1 + playerId)) + 100, 450);
        node.setPosition(75 + (playerId * (Utility.getResolution().x / 6.0f)), 450);
        blox.getBase().setPosition(-50, 0);
        menu.addItem(new DangineMenuItem("Ready", getReadyAction()));
        if (Utility.getMatchParameters().getMatchType().isTeamMode()) {
            menu.addItem(new DangineMenuItem("Team:", getNextTeamAction(), getPrevTeamAction()));
            teamTextNode.addChild(teamText);
            menu.getBase().addChild(teamTextNode);
        } else {
            menu.addItem(new DangineMenuItem("Next Color", getNextColorAction()));
        }
        menu.addItem(new DangineMenuItem("Sub Power", getNextPowerAction()));
        menu.getBase().addChild(powerTextNode);
        if (Utility.getMatchParameters().getMatchType() == MatchType.TEAM_VERSUS) {
        }
        menu.addItem(new DangineMenuItem("Back", getOnEscapeAction()));
        DangineFormatter.format(menu.getBase().getChildNodes());

        powerTextNode.addChild(powerText);
        if (Utility.getMatchParameters().getMatchType().isTeamMode()) {
            int team = Utility.getMatchParameters().getPlayerTeam(playerId);
            color = new Color(BloxColorer.TEAM_COLORS[team]);
        } else {
            color = Utility.getMatchParameters().getPlayerColor(playerId);
        }
        reColor();
        updateText();
    }

    @Override
    public void update() {
        animator.update();
        if (!isDone()) {
            selector.update();
            selector.scan(menu.getItems());
        }
        timer += Utility.getGameTime().getDeltaTimeF();
        if (timer > EFFECT_TIME) {
            timer = 0;
            SubPower power = Utility.getMatchParameters().getPlayerPower(getPlayerId());
            Vector2f position = new Vector2f(0, 0);
            position = ScreenUtility.getWorldPosition(blox.getBase(), position);
            power.createEffect(position.x, position.y);
        }
    }

    @Override
    public IsDrawable getDrawable() {
        return node;
    }

    private void reColor() {
        BloxColorer.color(blox, color);
    }

    private void updateText() {
        powerText.setText("" + Utility.getMatchParameters().getPlayerPower(getPlayerId()));
        teamText.setText("" + Utility.getMatchParameters().getPlayerTeam(getPlayerId()));
    }

    private Action getReadyAction() {
        return new Action() {

            @Override
            public void execute() {
                Debugger.info("Done");
                isDone = true;
                node.removeChild(menu.getDrawable());
            }
        };
    }

    private Action getNextColorAction() {
        return new Action() {

            @Override
            public void execute() {
                int i = BloxColorer.indexOf(color);
                i++;
                if (i >= BloxColorer.COLORS.length) {
                    i = 0;
                }
                color = new Color(BloxColorer.COLORS[i]);
                Utility.getMatchParameters().addPlayerColor(playerId, color);
                reColor();
            }
        };
    }

    private Action getNextPowerAction() {
        return new Action() {

            @Override
            public void execute() {
                SubPower power = Utility.getMatchParameters().getPlayerPower(getPlayerId());
                power = power.nextPower();
                Utility.getMatchParameters().addPlayerPower(getPlayerId(), power);
                updateText();
            }
        };
    }

    private Action getNextTeamAction() {
        return new Action() {

            @Override
            public void execute() {
                int team = Utility.getMatchParameters().getPlayerTeam(getPlayerId());
                team++;
                team = team % 6;
                Utility.getMatchParameters().addPlayerTeam(getPlayerId(), team);
                color = new Color(BloxColorer.TEAM_COLORS[team]);
                reColor();
                updateText();
            }

        };
    }

    private Action getPrevTeamAction() {
        return new Action() {

            @Override
            public void execute() {
                int team = Utility.getMatchParameters().getPlayerTeam(getPlayerId());
                team--;
                team = (team + 6) % 6;
                Utility.getMatchParameters().addPlayerTeam(getPlayerId(), team);
                color = new Color(BloxColorer.TEAM_COLORS[team]);
                reColor();
                updateText();
            }

        };
    }

    private Action getOnEscapeAction() {
        return new Action() {

            @Override
            public void execute() {
                isEscaping = true;
            }

        };
    }

    public boolean isDone() {
        return isDone;
    }

    public boolean isEscaping() {
        return isEscaping;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Color getColor() {
        return color;
    }

}
