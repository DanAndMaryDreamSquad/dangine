package dangine.graphics;

public class DangineAntiAliasing {

    public static int increment(int level) {
        switch (level) {
        case 0:
            return 2;
        case 2:
            return 4;
        case 4:
            return 8;
        case 8:
            return 16;
        case 16:
            return 16;
        default:
            return 0;
        }
    }

    public static int decrement(int level) {
        switch (level) {
        case 0:
            return 0;
        case 2:
            return 0;
        case 4:
            return 2;
        case 8:
            return 4;
        case 16:
            return 8;
        default:
            return 0;
        }
    }

}
