package seedu.address.model.person;

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
}
