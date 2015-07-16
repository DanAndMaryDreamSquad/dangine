package dangine.utility;

public class DangineVersioning {

    public static final String description = "game modes yo";
    public static final int major = 9;
    public static final int minor = 0;

    public static String getVersion() {
        return "v0." + major + "." + minor + "-" + description;
    }

}
