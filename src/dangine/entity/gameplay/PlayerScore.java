package dangine.entity.gameplay;

import dangine.utility.Utility;

public class PlayerScore {

    int stock = Utility.getMatchParameters().getStartingStock();
    final int playerId;

    public PlayerScore(int playerId) {
        this.playerId = playerId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPlayerId() {
        return playerId;
    }

}
