package dangine.utility;

public class DangineVersioning {

    public static final String description = "combat revamp";
    public static final int major = 2;
    public static final int minor = 1;

    public static String getVersion() {
        return "v0." + major + "." + minor + "-" + description;
    }

}
