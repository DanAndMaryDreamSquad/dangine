package dangine.utility;

public class Oscillator {

    float min, max, rate, timer = 0;
    boolean isAbsoluteValued = false;

    public Oscillator(float min, float max, float rate) {
        this.max = max;
        this.min = min;
        this.rate = rate;
    }

    public float update() {
        return update(Utility.getGameTime().getDeltaTimeF());
    }

    public float update(float elapsed) {
        timer += elapsed;
        float percent = (timer % rate) / rate;
        float sin = (float) Math.sin(percent * Math.PI * 2); // -1 to 1
        sin = (sin / 2) + 0.5f; // 0 to 1
        if (isAbsoluteValued) {
            sin = Math.round(sin);
        }
        float range = max - min;
        return min + (range * sin);

    }

    public float updateToCenter() {
        return updateToCenter(Utility.getGameTime().getDeltaTimeF());

    }

    public float updateToCenter(float elapsed) {
        if (timer % rate < rate * 0.25f) {
            timer += Math.abs((timer % rate) - (rate * 0.25f)) * 2;
        } else if (timer % rate > rate * 0.5f && timer % rate < rate * 0.75f) {
            timer += Math.abs((timer % rate) - (rate * 0.75f)) * 2;
        }
        float startTime = timer % rate;
        float endTime = (timer + elapsed) % rate;
        if (MathUtility.isInRange(startTime, endTime, rate / 2)) {
            timer = rate / 2;
            return update(0);
        }
        return update(elapsed);
    }

}
