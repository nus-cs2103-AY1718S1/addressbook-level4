package seedu.address.commons.util.timetable;

import java.util.HashMap;

//@@author zacharytang
/**
 * Represents lesson for a specific module parsed from a NUSMods url
 */
public class ModuleInfoFromUrl {

    private String modCode;
    private HashMap<String, String> lessonInfo;

    public ModuleInfoFromUrl(String modCode) {
        this.modCode = modCode;
        lessonInfo = new HashMap<>();
    }

    /**
     * Adds information for a specific lesson
     */
    public void addLesson(String classType, String classNo) {
        lessonInfo.put(classType, classNo);

    }

    public String getModCode() {
        return modCode;
    }

    public HashMap<String, String> getLessonInfo() {
        return lessonInfo;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {

            return true;
        }

        if (!(other instanceof ModuleInfoFromUrl)) {
            return false;
        }

        return this.modCode.equals(((ModuleInfoFromUrl) other).getModCode());
    }
}
