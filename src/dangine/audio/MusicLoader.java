package dangine.audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dangine.debugger.Debugger;
import dangine.image.DirectoryRecursor;
import dangine.image.ResourceManifest;
import dangine.image.Resources;

public class MusicLoader {

    public static final String DEFAULT_MUSIC_DIRECTORY = "src" + System.getProperty("file.separator") + "assets"
            + System.getProperty("file.separator") + "music";
    public static List<String> filePlusDirectories;

    public static Map<String, DangineMusic> loadMusics(ResourceManifest manifest) {
        return loadMusics(manifest.getMusics());
    }

    public static Map<String, DangineMusic> loadMusics() {
        filePlusDirectories = DirectoryRecursor.listFileNames(DEFAULT_MUSIC_DIRECTORY);
        return loadMusics(filePlusDirectories);
    }

    private static Map<String, DangineMusic> loadMusics(List<String> filenames) {
        Map<String, DangineMusic> musics = new HashMap<String, DangineMusic>();
        for (String filename : filenames) {
            MusicLoader.addMusicWithFilename(filename, musics);
        }
        return musics;
    }

    private static void addMusicWithFilename(String filename, Map<String, DangineMusic> musics) {

        if (!filename.contains(".ogg")) {
            return;
        }

        DangineMusic sound = loadOggDangineMusic(filename);
        musics.put(sound.getName(), sound);

    }

    public static DangineMusic loadOggDangineMusic(String filename) {
        DangineMusic music = null;
        InputStream in = null;
        java.io.BufferedInputStream bin = null;
        String path = "";
        try {
            if (Resources.shouldUseManifest()) {
                Debugger.info("manifesting music via " + filename);
                filename = filename.replace('\\', '/');
                in = ResourceManifest.class.getClassLoader().getResourceAsStream(filename);
                bin = new BufferedInputStream(in);
                path = filename;
            } else {
                Debugger.info("eclipse load music via " + filename);
                in = new FileInputStream(filename);
                bin = new BufferedInputStream(in);
                path = filename;
            }
            Debugger.info("Input Stream? " + bin);
            Debugger.info("bytes in " + filename + " " + bin.available() + " ");
            // WaveData waveFile = WaveData.create(bin);
            String name = translateFilePathToSoundName(filename);
            music = new DangineMusic(name, in, path);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return music;
    }

    public static String translateFilePathToSoundName(String filename) {
        // wipe off the directory
        int last = filename.lastIndexOf(System.getProperty("file.separator"));
        String soundname = filename.substring(last + 1, filename.length());

        last = soundname.lastIndexOf("/");
        soundname = soundname.substring(last + 1, soundname.length());

        // Get rid of file extension
        soundname = soundname.replace(".ogg", "");

        // just in case, ha ha ha
        soundname = soundname.toLowerCase();
        return soundname;
    }

    public static List<String> getFilePlusDirectories() {
        return filePlusDirectories;
    }
}
