package seedu.address.model.person;

//@@author seankwekjk
/**
 *Represents a Remark in the AddressBook.
 */

public class Remark {
    private String remarkText;

    /**
     * Validates given Remark.
     */
    public Remark(String text) {
        try {
            this.remarkText = text.trim();
        } catch (NullPointerException npe) {
            remarkText = ("");
        }
    }

    /**
     * Returns remarkText for checking operations
     */
    public String getRemarkText() {
        return remarkText;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + remarkText + ']';
    }
}
