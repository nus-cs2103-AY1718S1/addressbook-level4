package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author ritchielq
/**
 * JAXB-friendly adapted version of the NusModule.
 */
public class XmlAdaptedNusModule {

    @XmlAttribute
    private String moduleCode;
    @XmlElement(name = "lesson")
    private List<XmlAdaptedModuleLessons> nusLessons = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedNusModule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedNusModule() {}

    /**
     * Converts a given NusModule Map.Entry into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedNusModule(Map.Entry<String, HashMap<String, String>> source) {
        moduleCode = source.getKey();
        for (Map.Entry<String, String> moduleLesson : source.getValue().entrySet()) {
            nusLessons.add(new XmlAdaptedModuleLessons(moduleLesson));
        }
    }

    /**
     * Converts this jaxb-friendly adapted NusModule object into the model's NusModule Entry
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public HashMap<String, HashMap<String, String>> toNusModulesModelEntry() throws IllegalValueException {
        HashMap<String, HashMap<String, String>> nusModuleEntry = new HashMap<>();
        HashMap<String, String> nusLessonEntry = new HashMap<>();
        for (XmlAdaptedModuleLessons nusLesson : nusLessons) {
            ArrayList<String> tempNusLessonEntry = nusLesson.toNusLesson();
            nusLessonEntry.put(tempNusLessonEntry.get(0), tempNusLessonEntry.get(1));
        }
        nusModuleEntry.put(moduleCode, nusLessonEntry);

        return nusModuleEntry;
    }

}
