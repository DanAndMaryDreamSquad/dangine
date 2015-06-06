package dangine.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import dangine.debugger.Debugger;

public class FileToString {

    public static String getStringFromFile(String filename) {
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
        } catch (IOException e) {
            Debugger.warn("Couldn't load " + filename);
            return null;
        }

        StringBuilder builder = new StringBuilder();

        try {
            // Use this for reading the data.
            byte[] buffer = new byte[1000];

            // read fills buffer with data and returns
            // the number of bytes read (which of course
            // may be less than the buffer size, but
            // it will never be more).
            int total = 0;
            int nRead = 0;
            while ((nRead = in.read(buffer)) != -1) {
                // Convert to String so we can display it.
                // Of course you wouldn't want to do this with
                // a 'real' binary file.
                builder.append(new String(buffer));
                System.out.println(new String(buffer));
                total += nRead;
            }

            // Always close files.
            in.close();

            System.out.println("Read " + total + " bytes");
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filename + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return builder.toString();
    }

}
