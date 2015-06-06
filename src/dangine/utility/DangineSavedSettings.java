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

    float maxVelocity = 0.25f;
    float acceleration = 0.00055f;
    float dashVelocity = 0.50f;
    float dashDuration = 150f;
    float dragAccelerationMultiplier = 2.0f;

    float lightKnockPower = 3.0f;
    float heavyKnockPower = 5.0f;
    float counterKnockPower = 2.0f;

    public DangineSavedSettings(String jsonString) {
        try {
            settings = new JSONObject(jsonString);
            resolutionX = settings.getInt("resolutionX");
            resolutionY = settings.getInt("resolutionY");
            borderlessWindow = settings.getBoolean("borderlessWindow");
            fullscreen = settings.getBoolean("fullscreen");

            musicVolumePercent = settings.getInt("musicVolumePercent");
            soundVolumePercent = settings.getInt("soundVolumePercent");

            maxVelocity = (float) settings.getDouble("maxVelocity");
            acceleration = (float) settings.getDouble("acceleration");
            dashVelocity = (float) settings.getDouble("dashVelocity");
            dashDuration = (float) settings.getDouble("dashDuration");
            dragAccelerationMultiplier = (float) settings.getDouble("dragAccelerationMultiplier");

            lightKnockPower = (float) settings.getDouble("lightKnockPower");
            heavyKnockPower = (float) settings.getDouble("heavyKnockPower");
            counterKnockPower = (float) settings.getDouble("counterKnockPower");
        } catch (JSONException e) {
            Debugger.warn("Loaded settings.txt, but found some corrupted value. Generating default settings.");
            e.printStackTrace();
            setDefaults();
        }
    }

    public DangineSavedSettings() {
        setDefaults();
    }

    private void buildJsonString() {
        settings = new JSONObject();
        try {
            settings.put("resolutionX", resolutionX);
            settings.put("resolutionY", resolutionY);
            settings.put("borderlessWindow", borderlessWindow);
            settings.put("fullscreen", fullscreen);

            settings.put("musicVolumePercent", musicVolumePercent);
            settings.put("soundVolumePercent", soundVolumePercent);

            settings.put("maxVelocity", maxVelocity);
            settings.put("acceleration", acceleration);
            settings.put("dashVelocity", dashVelocity);
            settings.put("dashDuration", dashDuration);
            settings.put("dragAccelerationMultiplier", dragAccelerationMultiplier);

            settings.put("lightKnockPower", lightKnockPower);
            settings.put("heavyKnockPower", heavyKnockPower);
            settings.put("counterKnockPower", counterKnockPower);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDefaults() {
        resolutionX = Display.getDesktopDisplayMode().getWidth();
        resolutionY = Display.getDesktopDisplayMode().getHeight();
        fullscreen = false;
        borderlessWindow = true;

        musicVolumePercent = 50;
        soundVolumePercent = 50;

        maxVelocity = 0.25f;
        acceleration = 0.00055f;
        dashVelocity = 0.50f;
        dashDuration = 150f;
        dragAccelerationMultiplier = 2.0f;

        lightKnockPower = 3.0f;
        heavyKnockPower = 5.0f;
        counterKnockPower = 2.0f;
        save();
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

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getDashVelocity() {
        return dashVelocity;
    }

    public void setDashVelocity(float dashVelocity) {
        this.dashVelocity = dashVelocity;
    }

    public float getDashDuration() {
        return dashDuration;
    }

    public void setDashDuration(float dashDuration) {
        this.dashDuration = dashDuration;
    }

    public float getDragAccelerationMultiplier() {
        return dragAccelerationMultiplier;
    }

    public void setDragAccelerationMultiplier(float dragAccelerationMultiplier) {
        this.dragAccelerationMultiplier = dragAccelerationMultiplier;
    }

    public float getLightKnockPower() {
        return lightKnockPower;
    }

    public void setLightKnockPower(float lightKnockPower) {
        this.lightKnockPower = lightKnockPower;
    }

    public float getHeavyKnockPower() {
        return heavyKnockPower;
    }

    public void setHeavyKnockPower(float heavyKnockPower) {
        this.heavyKnockPower = heavyKnockPower;
    }

    public float getCounterKnockPower() {
        return counterKnockPower;
    }

    public void setCounterKnockPower(float counterKnockPower) {
        this.counterKnockPower = counterKnockPower;
    }
}
