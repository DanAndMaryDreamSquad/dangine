package dangine.utility;

public class DangineVersioning {

    public static final String description = "OpenGL 3.2 Obligations";
    public static final int major = 5;
    public static final int minor = 0;

    public static String getVersion() {
        return "v0." + major + "." + minor + "-" + description;
    }

}
