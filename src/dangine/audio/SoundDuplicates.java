package dangine.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dangine.debugger.Debugger;

public class SoundDuplicates {

    Map<String, List<DangineSound>> nameToDuplicates = new HashMap<String, List<DangineSound>>();
    Map<String, Integer> nameToIndex = new HashMap<String, Integer>();

    public void duplicateSounds() {
        duplicateSound("chime1b", 5);
    }

    private void duplicateSound(String name, int times) {
        DangineSound sound = DangineSounds.getSoundByName(name);
        if (nameToDuplicates.containsKey(name)) {
            Debugger.warn("Already duplicates of sound " + name);
        }
        nameToDuplicates.put(name, new ArrayList<DangineSound>());
        Debugger.info("Duplicating " + name + " " + times + " times");
        for (int i = 0; i < times; i++) {
            nameToDuplicates.get(name).add(sound.copy());
        }
        nameToDuplicates.get(name).add(sound);
        nameToIndex.put(name, 0);

    }

    public boolean hasDuplicates(String name) {
        return nameToDuplicates.containsKey(name);
    }

    public DangineSound getNextCopy(String name) {
        int index = nameToIndex.get(name);
        List<DangineSound> duplicates = nameToDuplicates.get(name);
        nameToIndex.put(name, (index + 1) % duplicates.size());
        return duplicates.get(index);
    }

    public List<DangineSound> getAllCopies(String name) {
        return nameToDuplicates.get(name);
    }

    public void destroyDuplicates() {
        for (String name : nameToDuplicates.keySet()) {
            for (DangineSound sound : nameToDuplicates.get(name)) {
                sound.destroy();
            }
        }

    }

}
