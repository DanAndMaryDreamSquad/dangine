package dangine.scene;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.IsUpdateable;
import dangine.menu.CharacterSelectionMenu;
import dangine.menu.ControlsAssigner;
import dangine.menu.PreMatchSettingsMenu;
import dangine.player.DanginePlayer;
import dangine.utility.Utility;

public class CharacterSelect implements IsUpdateable {

    List<CharacterSelectionMenu> menus = new ArrayList<CharacterSelectionMenu>();
    ControlsAssigner controlsAssigner = new ControlsAssigner(true);

    public CharacterSelect() {
        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            CharacterSelectionMenu menu = new CharacterSelectionMenu(player.getPlayerId());
            Utility.getActiveScene().addUpdateable(menu);
            Utility.getActiveScene().getParentNode().addChild(menu.getDrawable());
            menus.add(menu);
        }
        controlsAssigner.withCharacterSelect(this);
        Utility.getActiveScene().addUpdateable(controlsAssigner);
        Utility.getActiveScene().getParentNode().addChild(controlsAssigner.getDrawable());
    }

    @Override
    public void update() {
        boolean done = true;
        for (CharacterSelectionMenu menu : menus) {
            if (!menu.isDone()) {
                done = false;
            }
            if (menu.isEscaping()) {
                Utility.getGameLoop().startTitleMenu();
            }
        }
        if (done) {
            for (CharacterSelectionMenu menu : menus) {
                Utility.getMatchParameters().addPlayerColor(menu.getPlayerId(), menu.getColor());
            }
            removeMenus();
            Utility.getActiveScene().removeUpdateable(this);
            switch (Utility.getMatchParameters().getMatchType()) {
            case TEAM_VERSUS:
            case VERSUS:
            case WIN_BY_TWO:
            case SOCCER:
            case BOT_MATCH:
            case COOP_VS_BOTS:
                break;
            default:
                break;
            }
            PreMatchSettingsMenu preMatchMenu = new PreMatchSettingsMenu();
            Utility.getActiveScene().addUpdateable(preMatchMenu);
            Utility.getActiveScene().getParentNode().addChild(preMatchMenu.getDrawable());
            controlsAssigner.destroy();
        }
    }

    public void onNewPlayer(DanginePlayer newPlayer) {
        CharacterSelectionMenu menu = new CharacterSelectionMenu(newPlayer.getPlayerId());
        Utility.getActiveScene().addUpdateable(menu);
        Utility.getActiveScene().getParentNode().addChild(menu.getDrawable());
        menus.add(menu);
    }

    private void removeMenus() {
        for (CharacterSelectionMenu menu : menus) {
            Utility.getActiveScene().removeUpdateable(menu);
            Utility.getActiveScene().getParentNode().removeChild(menu.getDrawable());
        }
        ControlsAssigner assigner = Utility.getActiveScene().getUpdateable(ControlsAssigner.class);
        Utility.getActiveScene().removeUpdateable(assigner);
    }

}
