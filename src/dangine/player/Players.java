package dangine.player;

import java.util.ArrayList;
import java.util.List;

public class Players {

    List<DanginePlayer> players = new ArrayList<DanginePlayer>();

    public DanginePlayer getPlayer(int id) {
        return players.get(id);
    }

    public void newPlayer() {
        players.add(new DanginePlayer());
    }

    public void updateInput() {
        for (DanginePlayer player : players) {
            player.updateInput();
        }
    }

}
