package dangine.image;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class ResourceManifest implements Serializable {

    final static String MANIFEST_LOCATION = "src/assets/resources.starmanifest";

    public void save() {
        FileOutputStream fout;
        ObjectOutputStream oos;
        try {
            fout = new FileOutputStream(MANIFEST_LOCATION);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ResourceManifest load() {
        InputStream stream = ResourceManifest.class.getClassLoader().getResourceAsStream(
                "src/assets/resources.starmanifest");
        ObjectInputStream objStream = null;
        try {
            objStream = new ObjectInputStream(stream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResourceManifest manifest;
        try {
            manifest = (ResourceManifest) objStream.readObject();
            return manifest;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static final long serialVersionUID = 7187080574081090778L;

    List<String> images;

    public ResourceManifest(List<String> images) {
        this.images = images;
    }

    private void writeObject(java.io.ObjectOutputStream out) {
        try {
            out.writeObject(images);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void readObject(java.io.ObjectInputStream in) {
        try {
            images = (List<String>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> getImages() {
        return images;
    }
}
