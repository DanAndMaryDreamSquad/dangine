package dangine.debugger;

public class Debugger {

    public static void info() {
        info("");
    }

    public static void info(String line) {
        System.out.println("INFO - " + line);
    }

    public static void warn(String line) {
        System.out.println("WARN - " + line);
    }

}
