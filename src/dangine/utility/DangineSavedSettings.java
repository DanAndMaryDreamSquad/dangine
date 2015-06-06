package dangine.utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;

import dangine.debugger.Debugger;

public class DangineSavedSettings {
    public static DangineSavedSettings INSTANCE;

    final static String LOCATION = "settings.txt";

    public void save() {
        this.buildJsonString();
        StringToFile.writeStringToTextFile(settings.toString(), LOCATION);
    }

    public static DangineSavedSettings load() {
        String jsonString = FileToString.getStringFromFile(LOCATION);
        DangineSavedSettings settings;
        if (jsonString == null) {
            Debugger.warn("Couldn't load " + LOCATION + ", generating default settings");
            settings = new DangineSavedSettings();
        } else {
            Debugger.info("Loaded " + LOCATION + " :\n " + jsonString);
            settings = new DangineSavedSettings(jsonString);
        }
        return settings;
    }

    JSONObject settings;
    int resolutionX;
    int resolutionY;
    int musicVolumePercent;
    int soundVolumePercent;
    boolean borderlessWindow;
    boolean fullscreen;

    public DangineSavedSettings(String jsonString) {
        try {
            settings = new JSONObject(jsonString);
            resolutionX = settings.getInt("resolutionX");
            resolutionY = settings.getInt("resolutionY");
            borderlessWindow = settings.getBoolean("borderlessWindow");
            fullscreen = settings.getBoolean("fullscreen");

            musicVolumePercent = settings.getInt("musicVolumePercent");
            soundVolumePercent = settings.getInt("soundVolmePercent");
        } catch (JSONException e) {
            Debugger.warn("Loaded settings.txt, but found some corrupted value. Generating default settings.");
            e.printStackTrace();
            setDefaults();
        }
    }

    public DangineSavedSettings() {
        setDefaults();
    }

    private void setDefaults() {
        resolutionX = Display.getDesktopDisplayMode().getWidth();
        resolutionY = Display.getDesktopDisplayMode().getHeight();
        fullscreen = false;
        borderlessWindow = true;

        musicVolumePercent = 50;
        soundVolumePercent = 50;
        save();
    }

    private void buildJsonString() {
        settings = new JSONObject();
        try {
            settings.put("resolutionX", resolutionX);
            settings.put("resolutionY", resolutionY);
            settings.put("borderlessWindow", borderlessWindow);
            settings.put("fullscreen", fullscreen);

            settings.put("musicVolumePercent", musicVolumePercent);
            settings.put("soundVolmePercent", soundVolumePercent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getResolutionX() {
        return resolutionX;
    }

    public void setResolutionX(int resolutionX) {
        this.resolutionX = resolutionX;
    }

    public int getResolutionY() {
        return resolutionY;
    }

    public void setResolutionY(int resolutionY) {
        this.resolutionY = resolutionY;
    }

    public boolean isBorderlessWindow() {
        return borderlessWindow;
    }

    public void setBorderlessWindow(boolean borderlessWindow) {
        this.borderlessWindow = borderlessWindow;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public int getMusicVolumePercent() {
        return musicVolumePercent;
    }

    public void setMusicVolumePercent(int musicVolumePercent) {
        this.musicVolumePercent = musicVolumePercent;
    }

    public int getSoundVolumePercent() {
        return soundVolumePercent;
    }

    public void setSoundVolumePercent(int soundVolumePercent) {
        this.soundVolumePercent = soundVolumePercent;
    }
}
