package dangine.utility;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class StringToFile {

    public static void writeStringToTextFile(String string, String fileNameAndPath) {
        try {
            PrintWriter out = new PrintWriter(fileNameAndPath);
            out.write(string);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
