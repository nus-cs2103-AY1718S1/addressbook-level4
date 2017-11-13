package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author ritchielq
/**
 * Represents a Person's NusModules in the address book.
 * Guarantees: immutable; is valid as declared in isValidNusModules
 */
public class NusModules {

    public static final String MESSAGE_NUS_MODULE_CONSTRAINTS =
            "Person NUS modules must start with two or three letters followed by four digits. "
                    + "It may be followed by a uppercase alphabet";

    public static final String MESSAGE_LESSON_TYPE_CONSTRAINTS =
            "Lesson type must be 4 characters";

    /*
     * The first two or three characters must be uppercase alphabets, the following four characters must be digits.
     * It may be followed by a uppercase alphabet.
     */
    public static final String NUS_MODULE_VALIDATION_REGEX = "[A-Z]{2,3}[0-9]{4}[A-Z]?";

    /*
     * Lesson type must be 3 uppercase alphabets. Followed by a number of alphabet
     */
    public static final String LESSON_TYPE_VALIDATION_REGEX = "[A-Z]{3}[A-Z0-9]?";

    public static final int VALID_NUS_MODULE = 0;
    public static final int FAILED_NUS_MODULE_VALIDATION_REGEX = 1;
    public static final int FALIED_LESSON_TYPE_VALIDATION_REGEX = 2;

    public final HashMap<String, HashMap<String, String>> value;

    public NusModules() {
        this.value = new HashMap<>();
    }

    /**
     * Validates given nusModules.
     *
     * @throws IllegalValueException if given nusModules string is invalid.
     */
    public NusModules(HashMap<String, HashMap<String, String>> nusModules) throws IllegalValueException {
        requireNonNull(nusModules);
        switch (isValidNusModules(nusModules)) {
        case FAILED_NUS_MODULE_VALIDATION_REGEX:
            throw new IllegalValueException(MESSAGE_NUS_MODULE_CONSTRAINTS);
        case FALIED_LESSON_TYPE_VALIDATION_REGEX:
            throw new IllegalValueException(MESSAGE_LESSON_TYPE_CONSTRAINTS);
        default:
            break;
        }

        this.value = nusModules;
    }

    /**
     * Returns VALID_NUS_MODULE if a given HashMap is a valid person nusModules.
     * Returns FAILED_NUS_MODULE_VALIDATION_REGEX if it fails NUS_MODULE_VALIDATION_REGEX
     * Returns FALIED_LESSON_TYPE_VALIDATION_REGEX if it fails LESSON_TYPE_VALIDATION_REGEX
     */
    public static int isValidNusModules(HashMap<String, HashMap<String, String>> test) {
        for (Map.Entry<String, HashMap<String, String>> module : test.entrySet()) {
            String moduleCode = module.getKey();
            // If fail either, return 1 and 2 respectively
            if (!moduleCode.matches(NUS_MODULE_VALIDATION_REGEX)) {
                return FAILED_NUS_MODULE_VALIDATION_REGEX;
            }
            for (Map.Entry<String, String> lessons : module.getValue().entrySet()) {
                String lessonType = lessons.getKey();
                if (!lessonType.matches(LESSON_TYPE_VALIDATION_REGEX)) {
                    return FALIED_LESSON_TYPE_VALIDATION_REGEX;
                }
            }
        }

        return VALID_NUS_MODULE;
    }

    /**
     * Copies value to create and return modified NusModules by:
     * If NusModule does not have the module, add it
     * If it has, add or overwrite it's lessons with newLessons
     */
    public NusModules addModule(String moduleCode, HashMap<String, String> newLessons) throws IllegalValueException {

        // Copy oldNusModules value into new HashMap
        HashMap<String, HashMap<String, String>> newNusModulesHashMap = new HashMap<>();
        for (Map.Entry<String, HashMap<String, String>> oldNusModule : value.entrySet()) {
            newNusModulesHashMap.put(oldNusModule.getKey(), oldNusModule.getValue());
        }

        // Adding in new module or overwriting them.
        if (newNusModulesHashMap.containsKey(moduleCode)) {
            HashMap<String, String> currLessons = newNusModulesHashMap.get(moduleCode);
            for (Map.Entry<String, String> newLesson : newLessons.entrySet()) {
                currLessons.put(newLesson.getKey(), newLesson.getValue());
            }
        } else {
            newNusModulesHashMap.put(moduleCode, newLessons);
        }

        NusModules newNusModules = new NusModules(newNusModulesHashMap);
        return newNusModules;
    }

    /**
     * Copies value to create and return modified NusModules with
     * given module removed (if it exist)
     */
    public NusModules removeModule(String moduleCode) throws IllegalValueException {
        // Copy oldNusModules value into new HashMap
        HashMap<String, HashMap<String, String>> newNusModulesHashMap = new HashMap<>();
        for (Map.Entry<String, HashMap<String, String>> oldNusModule : value.entrySet()) {
            newNusModulesHashMap.put(oldNusModule.getKey(), oldNusModule.getValue());
        }

        newNusModulesHashMap.remove(moduleCode);
        NusModules newNusModules = new NusModules(newNusModulesHashMap);
        return newNusModules;
    }

    @Override
    public String toString() {
        String nusModuleString = "";
        for (Map.Entry<String, HashMap<String, String>> module : value.entrySet()) {
            String moduleCode = module.getKey();
            for (Map.Entry<String, String> lessons : module.getValue().entrySet()) {
                String lessonType = lessons.getKey();
                String lessonSlot = lessons.getValue();

                nusModuleString += moduleCode + "[" + lessonType + "]=" + lessonSlot + "&";
            }
            if (module.getValue().isEmpty()) {
                nusModuleString += moduleCode + "&";
            }
        }
        return nusModuleString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NusModules // instanceof handles nulls
                && this.value.toString().equals(((NusModules) other).value.toString())); // Compare toString()
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
