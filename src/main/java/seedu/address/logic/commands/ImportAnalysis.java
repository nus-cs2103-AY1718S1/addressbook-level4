package seedu.address.logic.commands;

/**
 *
 */
public class ImportAnalysis {
    private Integer numContacts;
    private boolean illegalValue;
    private boolean duplicateContacts;

    public ImportAnalysis() {
        numContacts = 0;
        illegalValue = false;
        duplicateContacts = false;
    }

    public void setIllegalValue(boolean illegalValue) {
        this.illegalValue = illegalValue;
    }

    public void setDuplicateContacts(boolean duplicateContacts) {
        this.duplicateContacts = duplicateContacts;
    }

    public void setNumContacts(Integer numContacts) {
        this.numContacts = numContacts;
    }

    public boolean isDuplicateContacts() {
        return duplicateContacts;
    }

    public boolean isIllegalValue() {
        return illegalValue;
    }

    public Integer getNumContacts() {
        return numContacts;
    }
}
