package dangine.time;

public class GameTime {
    private static final int TICK_RATE = 33;
    private long startPoint;
    private int currentTick = 0;
    private int totalElaspedTime = 0;
    private int deltaTime = 0;
    private float modulatorFactor = 1.0f;
    private int modulatorTimer = 0;
    private int modulatorDuration = 0;

    public GameTime() {
        reset();
    }

    public void reset() {
        startPoint = System.currentTimeMillis();
    }

    public int updateTime(int deltaTime) {
        this.deltaTime = deltaTime;
        totalElaspedTime = totalElaspedTime + deltaTime;
        modulatorTimer += deltaTime;
        if (modulatorTimer > modulatorDuration) {
            modulatorFactor = 1.0f;
            modulatorDuration = Integer.MAX_VALUE;
        }

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
        return deltaTime * modulatorFactor;
    }

    public int getTotalElapsedTime() {
        return totalElaspedTime;
    }

    public void setModulator(float modulator, int duration) {
        this.modulatorFactor = modulator;
        this.modulatorDuration = duration;
        modulatorTimer = 0;
    }
}
