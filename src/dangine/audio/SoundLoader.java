package dangine.audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.WaveData;

import dangine.debugger.Debugger;
import dangine.image.DirectoryRecursor;
import dangine.image.ResourceManifest;
import dangine.image.Resources;

public class SoundLoader {

    public static final String DEFAULT_SOUND_DIRECTORY = "src" + System.getProperty("file.separator") + "assets"
            + System.getProperty("file.separator") + "sounds";
    public static List<String> filePlusDirectories;

    public static Map<String, DangineSound> loadSounds(ResourceManifest manifest) {
        return loadSounds(manifest.getSounds());
    }

    public static Map<String, DangineSound> loadSounds() {
        filePlusDirectories = DirectoryRecursor.listFileNames(DEFAULT_SOUND_DIRECTORY);
        return loadSounds(filePlusDirectories);
    }

    private static Map<String, DangineSound> loadSounds(List<String> filenames) {
        Map<String, DangineSound> sounds = new HashMap<String, DangineSound>();
        for (String filename : filenames) {
            SoundLoader.addSoundWithFilename(filename, sounds);
        }
        return sounds;
    }

    private static void addSoundWithFilename(String filename, Map<String, DangineSound> images) {

        if (!filename.contains(".wav")) {
            return;
        }

        DangineSound sound = loadWaveDangineSound(filename);
        images.put(sound.getName(), sound);

    }

    public static DangineSound loadWaveDangineSound(String filename) {
        DangineSound sound = null;
        InputStream in = null;
        java.io.BufferedInputStream bin = null;
        try {
            if (Resources.shouldUseManifest()) {
                Debugger.info("manifesting sound via " + filename);
                filename = filename.replace('\\', '/');
                in = ResourceManifest.class.getClassLoader().getResourceAsStream(filename);
                bin = new BufferedInputStream(in);
            } else {
                Debugger.info("eclipse load sound via " + filename);
                in = new FileInputStream(filename);
                bin = new BufferedInputStream(in);
            }
            Debugger.info("Input Stream? " + bin);
            Debugger.info("bytes in " + filename + " " + bin.available() + " ");
            WaveData waveFile = WaveData.create(bin);
            String name = translateFilePathToSoundName(filename);
            sound = new DangineSound(name, waveFile);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sound;
    }

    public static String translateFilePathToSoundName(String filename) {
        // wipe off the directory
        int last = filename.lastIndexOf(System.getProperty("file.separator"));
        String soundname = filename.substring(last + 1, filename.length());

        last = soundname.lastIndexOf("/");
        soundname = soundname.substring(last + 1, soundname.length());

        // Get rid of file extension
        soundname = soundname.replace(".wav", "");

        // just in case, ha ha ha
        soundname = soundname.toLowerCase();
        return soundname;
    }

    public static List<String> getFilePlusDirectories() {
        return filePlusDirectories;
    }
}
