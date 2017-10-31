package seedu.address.commons.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the Theme settings.
 */
public class ThemeSettings implements Serializable {

    private static final String DEFAULT_THEME = "view/ThemeDay.css";
    private static final String DEFAULT_THEME_EXTENSIONS = "view/ThemeDayExtensions.css";

    private String theme;
    private String themeExtensions;

    public ThemeSettings() {
        this.theme = DEFAULT_THEME;
        this.themeExtensions = DEFAULT_THEME_EXTENSIONS;
    }

    public ThemeSettings(String theme, String themeExtensions) {
        this.theme = theme;
        this.themeExtensions = themeExtensions;
    }

    public String getTheme() {
        return theme;
    }

    public String getThemeExtensions() {
        return themeExtensions;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ThemeSettings)) { // this handles null as well.
            return false;
        }

        ThemeSettings o = (ThemeSettings) other;

        return Objects.equals(theme, o.theme)
                && Objects.equals(themeExtensions, o.themeExtensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theme, themeExtensions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Theme : " + theme + "\n");
        sb.append("Theme Extensions : " + themeExtensions);
        return sb.toString();
    }
}
