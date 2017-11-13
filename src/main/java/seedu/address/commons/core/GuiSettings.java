package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A Serializable class that contains the GUI settings.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;

    private Double windowWidth;
    private Double windowHeight;
    private Point windowCoordinates;
    private Map<Tag, String> tagColours;

    public GuiSettings() {
        this.windowWidth = DEFAULT_WIDTH;
        this.windowHeight = DEFAULT_HEIGHT;
        this.windowCoordinates = null; // null represent no coordinates
        this.tagColours = SampleDataUtil.getSampleTagColours();
    }

    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition,
                       Map<Tag, String> tagColours) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
        this.tagColours = tagColours;
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

    //@@author Xenonym
    public Map<Tag, String> getTagColours() {
        return Collections.unmodifiableMap(tagColours);
    }

    public void setTagColours(Map<Tag, String> newTagColours) {
        tagColours = newTagColours;
    }
    //@@author

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
                && Objects.equals(tagColours, o.tagColours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, tagColours);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates + "\n");
        sb.append("Tag colours: " + tagColours);
        return sb.toString();
    }
}
