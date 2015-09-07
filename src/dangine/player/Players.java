package dangine.player;

import java.util.ArrayList;
import java.util.List;

public class Players {

    List<DanginePlayer> players = new ArrayList<DanginePlayer>();

    public DanginePlayer getPlayer(int id) {
        return players.get(id);
    }

    public DanginePlayer newPlayer() {
        DanginePlayer player = new DanginePlayer(players.size());
        players.add(player);
        return player;
    }

    public void removePlayer() {
        players.remove(players.size() - 1);
    }

    public void updateInput() {
        for (DanginePlayer player : players) {
            player.updateInput();
        }
    }

    public List<DanginePlayer> getPlayers() {
        return players;
    }

    public void removeAllPlayers() {
        while (!players.isEmpty()) {
            removePlayer();
        }
    }

}
