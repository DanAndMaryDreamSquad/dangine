package dangine.image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryRecursor {

    public static List<String> listFileNames(String dir) {
        return listFileNames(dir, new ArrayList<String>());
    }

    private static List<String> listFileNames(String directory, List<String> files) {
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files.add(directory + "/" + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                listFileNames(directory + "/" + listOfFiles[i].getName(), files);
            }
        }
        return files;
    }

}
