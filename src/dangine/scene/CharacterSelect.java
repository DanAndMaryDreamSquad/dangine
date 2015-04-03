package dangine.scene;

import java.util.ArrayList;
import java.util.List;

import dangine.entity.IsUpdateable;
import dangine.entity.gameplay.MatchStarter;
import dangine.menu.ColorSelectionMenu;
import dangine.menu.TitleMenu;
import dangine.player.DanginePlayer;
import dangine.utility.Utility;

public class CharacterSelect implements IsUpdateable {

    List<ColorSelectionMenu> menus = new ArrayList<ColorSelectionMenu>();

    public CharacterSelect() {
        for (DanginePlayer player : Utility.getPlayers().getPlayers()) {
            ColorSelectionMenu menu = new ColorSelectionMenu(player.getPlayerId());
            Utility.getActiveScene().addUpdateable(menu);
            Utility.getActiveScene().getParentNode().addChild(menu.getDrawable());
            menus.add(menu);
        }
    }

    @Override
    public void update() {
        boolean done = true;
        for (ColorSelectionMenu menu : menus) {
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
            for (ColorSelectionMenu menu : menus) {
                Utility.getMatchParameters().addPlayerColor(menu.getPlayerId(), menu.getColor());
                Utility.getActiveScene().removeUpdateable(menu);
            }
            MatchStarter matchStarter = new MatchStarter();
            Utility.getActiveScene().addUpdateable(matchStarter);
        }
    }

    private void removeMenus() {
        for (ColorSelectionMenu menu : menus) {
            Utility.getActiveScene().removeUpdateable(menu);
            Utility.getActiveScene().getParentNode().removeChild(menu.getDrawable());
        }
    }

}
