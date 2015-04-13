package dangine.utility;

public class DangineVersioning {

    public static final String description = "map mayhem";
    public static final int major = 3;
    public static final int minor = 0;

    public static String getVersion() {
        return "v0." + major + "." + minor + "-" + description;
    }

}
