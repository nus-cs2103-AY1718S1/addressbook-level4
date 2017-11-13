package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Remark;

/**
 * Builder of a remark
 */
public class RemarkBuilder {

    public static final String DEFAULT_MODS = "CS2103T/LEC/1,CS2105/LEC/1";

    private Remark remark;

    public RemarkBuilder() throws IllegalValueException {
        this.remark = new Remark("");
    }

    /**
     * Build with specific details
     */
    public Remark withDetails(String details) {

        this.remark.setModuleLists(details);

        return this.remark;
    }
}
