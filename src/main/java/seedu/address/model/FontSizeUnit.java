package seedu.address.model;


//@@author cctdaniel
/**
 * A Enumeration class that consists of all possible FontSize
 * Unit in the panel.
 */
public enum FontSizeUnit {
    FONT_SIZE_XSMALL_UNIT, FONT_SIZE_SMALL_UNIT, FONT_SIZE_NORMAL_UNIT, FONT_SIZE_LARGE_UNIT, FONT_SIZE_XLARGE_UNIT;

    private static FontSizeUnit currentFontSizeUnit = FONT_SIZE_NORMAL_UNIT;

    /** Get current FontSize unit */
    public static FontSizeUnit getCurrentFontSizeUnit() {
        return currentFontSizeUnit;
    }

    /** Reset FontSize unit in the panel with the new FontSizeUnit */
    public static void setCurrentFontSizeUnit(FontSizeUnit unit) {
        currentFontSizeUnit = unit;
    }
}
