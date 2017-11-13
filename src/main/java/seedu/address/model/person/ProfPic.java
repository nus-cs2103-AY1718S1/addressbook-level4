package seedu.address.model.person;

//@@author nassy93

/**
 * Stores the filepath to a person's current profile picture
 *
 */
public class ProfPic {
    public final String path;

    public ProfPic(String val) {
        path = val;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfPic // instanceof handles nulls
                && this.path.equals(((ProfPic) other).path)); // state check
    }
}
