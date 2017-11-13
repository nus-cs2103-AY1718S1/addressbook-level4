package seedu.address.commons.util.timetable;

import java.util.ArrayList;

//@@author zacharytang
/**
 * Represents all timetable information parsed from a NUSMods url
 */
public class TimetableInfoFromUrl {

    private ArrayList<ModuleInfoFromUrl> moduleInfo;

    public TimetableInfoFromUrl() {
        moduleInfo = new ArrayList<>();
    }

    public ArrayList<ModuleInfoFromUrl> getModuleInfoList() {
        return moduleInfo;
    }

    /**
     * Gets lessons for a specific module code. If module does not exist, creates a new ModuleInfoFromUrl object
     */
    public ModuleInfoFromUrl getModuleInfo(String moduleCodeToGet) {
        for (ModuleInfoFromUrl currentModule : moduleInfo) {
            if (currentModule.getModCode().equals(moduleCodeToGet)) {
                return currentModule;
            }
        }

        return new ModuleInfoFromUrl(moduleCodeToGet);
    }

    /**
     * Adds lesson information for a specific module to the timetable information.
     */
    public void addModuleInfo(ModuleInfoFromUrl moduleInfoToAdd) {
        if (moduleInfo.contains(moduleInfoToAdd)) {
            moduleInfo.set(moduleInfo.indexOf(moduleInfoToAdd), moduleInfoToAdd);
        } else {
            moduleInfo.add(moduleInfoToAdd);
        }
    }
}
