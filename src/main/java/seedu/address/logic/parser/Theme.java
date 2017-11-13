package seedu.address.logic.parser;

//@@author chuaweiwen
/**
 * Represents the theme of the address book.
 */
public class Theme {
    private final String theme;
    private final String filePath;

    public Theme(String theme, String filePath) {
        this.theme = theme;
        this.filePath = filePath;
    }

    public String getTheme() {
        return theme;
    }

    public String getFilePath() {
        return filePath;
    }

    public String toString() {
        return getTheme();
    }

    @Override
    public int hashCode() {
        return theme == null ? 0 : theme.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Theme)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Theme otherTheme = (Theme) obj;
        return otherTheme.getTheme().equals(getTheme()) && otherTheme.getFilePath().equals(getFilePath());
    }
}
//@@author
