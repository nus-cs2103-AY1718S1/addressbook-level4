//@@author ChenXiaoman
package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

import seedu.address.model.font.FontSize;
import seedu.address.model.theme.Theme;

/**
 * A Serializable class that contains the GUI settings.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;
    private static final String DEFAULT_FONT_SIZE = FontSize.FONT_SIZE_M_LABEL;
    private static final String DEFAULT_THEME = Theme.BRIGHT_THEME;

    private Double windowWidth;
    private Double windowHeight;
    private Point windowCoordinates;
    private String fontSize;
    private String theme;

    public GuiSettings() {
        this.windowWidth = DEFAULT_WIDTH;
        this.windowHeight = DEFAULT_HEIGHT;
        this.windowCoordinates = null; // null represent no coordinates
        this.fontSize = DEFAULT_FONT_SIZE;
        this.theme = DEFAULT_THEME;
    }

    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition, String fontSize,
                       String theme) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
        this.fontSize = fontSize;
        this.theme = theme;
    }

    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
        this.fontSize = DEFAULT_FONT_SIZE;
        this.theme = DEFAULT_THEME;
    }

    public Double getWindowWidth() {
        return windowWidth;
    }

    public Double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates;
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getTheme() {
        return theme;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings) other;

        return Objects.equals(windowWidth, o.windowWidth)
                && Objects.equals(windowHeight, o.windowHeight)
                && Objects.equals(windowCoordinates.x, o.windowCoordinates.x)
                && Objects.equals(windowCoordinates.y, o.windowCoordinates.y)
                && Objects.equals(fontSize, o.fontSize)
                && Objects.equals(theme, o.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, fontSize, theme);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates + "\n");
        sb.append("Font size: " + fontSize + "\n");
        sb.append("Theme: " + theme);
        return sb.toString();
    }
}
