package seedu.address.model.person;
//@@author yangminxingnus
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's remark in the address book.
 */

public class Remark {
    public static final String REMARK_CONSTRAINTS =
            "Remark should be in the format MODULENAME/MODULETYPE/NUM. Example: CS2101/SEC/2";
    public static final String REMARK_VALIDATION_REGEX = "[\\w\\.]+/[\\w\\.]+/[\\d\\.]";
    private String moduleLists;

    public Remark(String remark) throws IllegalValueException {
        requireNonNull(remark);
        String[] test = remark.split(",");
        for (String t : test) {
            t = t.trim();
            if (!isValidRemark(t)) {
                System.out.println(t);
                throw new IllegalValueException(REMARK_CONSTRAINTS);
            }
        }
        this.moduleLists = remark;
    }

    public void setModuleLists(String mods) {
        this.moduleLists = mods;
    }

    /**
     * Returns true if a given string is a valid Remark.
     */
    public static boolean isValidRemark(String test) {
        return (test.matches(REMARK_VALIDATION_REGEX) || test.equals(""));
    }

    /**
     * Get the moduleLists.
     */
    public String getParsedModuleLists() {
        return parse(moduleLists);
    }

    /**
     * Get the moduleLists.
     */
    public String getModuleLists() {
        return moduleLists;
    }

    /**
     * Parse the modulelist to correct url format.
     */
    private String parse(String moduleLists) {
        String[] mods = moduleLists.split(",");
        String result = "";
        for (String m : mods) {
            String[] helper = m.split("/");
            String mod = helper[0].trim();
            String kind = helper[1].trim();
            String num = helper[2].trim();
            result = result + "&" + mod + "[" + kind + "]" + "=" + num;
        }
        return result;
    }

    @Override
    public String toString() {
        return moduleLists;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Remark // instanceof handles nulls
                && this.moduleLists.equals(((Remark) other).moduleLists)); // state check
    }

    @Override
    public int hashCode() {
        return moduleLists.hashCode();
    }
}
//@@author
