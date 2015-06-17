package dangine.utility;

import java.util.Random;

public class MathUtility {
    static Random rand = new Random();

    public static int randomInt(int min, int max) {
        return rand.nextInt((max + 1) - min) + min;
    }

    public static boolean randomBoolean() {
        return rand.nextInt(2) == 0;
    }

    public static float randomFloat(float min, float max) {
        float range = max - min;
        return min + (rand.nextFloat() * range);
    }

    public static boolean isInRange(float min, float max, float value) {
        return (value >= min && value <= max);
    }

    public static float rangify(float start, float end, float percent) {
        return ((end - start) * percent) + start;
    }

    public static float logFunction(float percent) {
        float n = (9.0f * percent) + 1.0f;
        return (float) Math.log10(n);
    }

}
