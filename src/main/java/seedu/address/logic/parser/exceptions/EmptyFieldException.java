package seedu.address.logic.parser.exceptions;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.Prefix;

//@@author Juxarius
/**
 * Signifies that a certain field is empty but the prefix is specified.
 */

public class EmptyFieldException extends ParseException {

    private Prefix emptyFieldPrefix;
    private Index index;
    /**
     * @param message should contain information on the empty field.
     */
    public EmptyFieldException(String message) {
        super(message);
    }
    /**
     * @param emptyFieldPrefix contains the prefix of the field that is empty.
     */
    public EmptyFieldException(Prefix emptyFieldPrefix) {
        super(emptyFieldPrefix.getPrefix() + " field is empty");
        this.emptyFieldPrefix = emptyFieldPrefix;
    }

    /**
     * @param index is the oneBasedIndex of the person in concern
     */
    public EmptyFieldException(EmptyFieldException efe, Index index) {
        super(efe.getMessage());
        this.emptyFieldPrefix = efe.getEmptyFieldPrefix();
        this.index = index;
    }

    public Prefix getEmptyFieldPrefix() {
        return emptyFieldPrefix;
    }

    public Index getIndex() {
        return index;
    }
}
