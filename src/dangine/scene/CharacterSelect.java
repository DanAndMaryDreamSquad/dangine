package dangine.scene;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.IsUpdateable;
import dangine.menu.BotSettingsMenu;
import dangine.menu.CharacterSelectionMenu;
import dangine.menu.TitleMenu;
import dangine.menu.WorldSelectionMenu;
import dangine.player.DanginePlayer;
import dangine.utility.Utility;

public class CharacterSelect implements IsUpdateable {

    List<CharacterSelectionMenu> menus = new ArrayList<CharacterSelectionMenu>();

    public CharacterSelect() {
        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            CharacterSelectionMenu menu = new CharacterSelectionMenu(player.getPlayerId());
            Utility.getActiveScene().addUpdateable(menu);
            Utility.getActiveScene().getParentNode().addChild(menu.getDrawable());
            menus.add(menu);
        }
        // TODO Only one player is allowed right now because the bot cannot
        // handle multiple targets
        // if (Utility.getMatchParameters().getMatchType() ==
        // MatchType.BOT_MATCH) {
        // DanginePlayer player = Utility.getPlayers().getPlayer(0);
        // CharacterSelectionMenu menu = new
        // CharacterSelectionMenu(player.getPlayerId());
        // Utility.getActiveScene().addUpdateable(menu);
        // Utility.getActiveScene().getParentNode().addChild(menu.getDrawable());
        // menus.add(menu);
        // } else {
        // for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
        // CharacterSelectionMenu menu = new
        // CharacterSelectionMenu(player.getPlayerId());
        // Utility.getActiveScene().addUpdateable(menu);
        // Utility.getActiveScene().getParentNode().addChild(menu.getDrawable());
        // menus.add(menu);
        // }
        // }
    }

    @Override
    public void update() {
        boolean done = true;
        for (CharacterSelectionMenu menu : menus) {
            if (!menu.isDone()) {
                done = false;
            }

            if (menu.isEscaping()) {
                removeMenus();
                TitleMenu titleMenu = new TitleMenu();
                Utility.getActiveScene().addUpdateable(titleMenu);
                Utility.getActiveScene().removeUpdateable(this);
                Utility.getActiveScene().getParentNode().addChild(titleMenu.getDrawable());
            }
        }
        if (done) {
            for (CharacterSelectionMenu menu : menus) {
                Utility.getMatchParameters().addPlayerColor(menu.getPlayerId(), menu.getColor());
                Utility.getActiveScene().removeUpdateable(menu);
                Utility.getActiveScene().getParentNode().removeChild(menu.getDrawable());
            }
            Utility.getActiveScene().removeUpdateable(this);
            switch (Utility.getMatchParameters().getMatchType()) {
            case VERSUS:
                WorldSelectionMenu worldMenu = new WorldSelectionMenu();
                Utility.getActiveScene().addUpdateable(worldMenu);
                Utility.getActiveScene().getParentNode().addChild(worldMenu.getDrawable());
                break;
            case BOT_MATCH:
                BotSettingsMenu botMenu = new BotSettingsMenu();
                Utility.getActiveScene().addUpdateable(botMenu);
                Utility.getActiveScene().getParentNode().addChild(botMenu.getDrawable());
                break;
            }

        }
    }

    private void removeMenus() {
        for (CharacterSelectionMenu menu : menus) {
            Utility.getActiveScene().removeUpdateable(menu);
            Utility.getActiveScene().getParentNode().removeChild(menu.getDrawable());
        }
    }

}
