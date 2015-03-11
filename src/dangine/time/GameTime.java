package dangine.time;

public class GameTime {
    private static final int TICK_RATE = 33;
    private long startPoint;
    private int currentTick = 0;
    private int totalElaspedTime = 0;
    private int deltaTime = 0;

    public GameTime() {
        reset();
    }

    public void reset() {
        startPoint = System.currentTimeMillis();
    }

    public int updateTime(int deltaTime) {
        this.deltaTime = deltaTime;
        totalElaspedTime = totalElaspedTime + deltaTime;
        currentTick = (int) ((System.currentTimeMillis() - startPoint) / TICK_RATE);
        return currentTick;
    }

    public int getTick() {
        return currentTick;
    }

    public int getDeltaTime() {
        return deltaTime;
    }

    public float getDeltaTimeF() {
        return deltaTime;
    }

    public int getTotalElapsedTime() {
        return totalElaspedTime;
    }
}
