package dangine.utility;

import org.json.JSONException;
import org.json.JSONObject;

import dangine.debugger.Debugger;

public class DangineSavedControls {
    public static DangineSavedControls INSTANCE;

    final static String LOCATION = "controls.txt";

    public void save() {
        this.buildJsonString();
        StringToFile.writeStringToTextFile(settings.toString(), LOCATION);
    }

    public static DangineSavedControls load() {
        String jsonString = FileToString.getStringFromFile(LOCATION);
        DangineSavedControls settings;
        if (jsonString == null) {
            Debugger.warn("Couldn't load " + LOCATION + ", generating default settings");
            settings = new DangineSavedControls();
        } else {
            Debugger.info("Loaded " + LOCATION + " :\n " + jsonString);
            settings = new DangineSavedControls(jsonString);
        }
        return settings;
    }

    JSONObject settings;
    String keyboardLeftsideUp;
    String keyboardLeftsideDown;
    String keyboardLeftsideLeft;
    String keyboardLeftsideRight;
    String keyboardLeftsideOne;
    String keyboardLeftsideTwo;
    String keyboardLeftsideThree;

    String keyboardRightsideUp;
    String keyboardRightsideDown;
    String keyboardRightsideLeft;
    String keyboardRightsideRight;
    String keyboardRightsideOne;
    String keyboardRightsideTwo;
    String keyboardRightsideThree;

    public DangineSavedControls(String jsonString) {
        try {
            settings = new JSONObject(jsonString);
            // resolutionX = settings.getInt("resolutionX");
            // resolutionY = settings.getInt("resolutionY");
        } catch (JSONException e) {
            Debugger.warn("Loaded " + LOCATION + ", but found some corrupted value. Generating default settings.");
            e.printStackTrace();
            setDefaults();
        }
    }

    public DangineSavedControls() {
        setDefaults();
    }

    private void buildJsonString() {
        settings = new JSONObject();
        // try {
        // settings.put("resolutionX", resolutionX);
        // settings.put("resolutionY", resolutionY);
        // } catch (JSONException e) {
        // e.printStackTrace();
        // }
    }

    private void setDefaults() {
        // resolutionX = Display.getDesktopDisplayMode().getWidth();
        // resolutionY = Display.getDesktopDisplayMode().getHeight();
        save();
    }

}
