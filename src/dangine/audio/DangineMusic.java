package dangine.audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import dangine.image.ResourceManifest;
import dangine.image.Resources;

public class DangineMusic {

    String name;
    InputStream inputStream;
    String path;

    public DangineMusic(String name, InputStream inputStream, String path) {
        this.name = name;
        this.inputStream = inputStream;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public InputStream getInputStream() {
        InputStream in = null;
        java.io.BufferedInputStream bin = null;
        if (Resources.shouldUseManifest()) {
            in = ResourceManifest.class.getClassLoader().getResourceAsStream(path);
            bin = new BufferedInputStream(in);
        } else {
            try {
                in = new FileInputStream(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bin = new BufferedInputStream(in);
        }
        return bin;
    }

}
