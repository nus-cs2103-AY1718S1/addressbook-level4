package seedu.address.model;

//@@author cctdaniel
/**
 * A Enumeration class that consists of all possible Theme
 * Unit in the panel.
 */
public enum ThemeUnit {
    THEME_LIGHT_UNIT, THEME_DARK_UNIT;

    private static ThemeUnit currentThemeUnit = THEME_LIGHT_UNIT;

    /** Get current Theme unit */
    public static ThemeUnit getCurrentThemeUnit() {
        return currentThemeUnit;
    }

    /** Reset Theme unit in the panel with the new ThemeUnit */
    public static void setCurrentThemeUnit(ThemeUnit unit) {
        currentThemeUnit = unit;
    }
}
