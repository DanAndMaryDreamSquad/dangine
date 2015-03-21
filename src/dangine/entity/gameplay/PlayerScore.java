package dangine.entity.gameplay;

public class PlayerScore {

    int stock = 2;
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
