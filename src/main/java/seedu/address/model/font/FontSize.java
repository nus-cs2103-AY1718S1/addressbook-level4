package seedu.address.model.font;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author ChenXiaoman
/**
 * Represents the font size of the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidFontSize(String)}
 */
public class FontSize {

    public static final String MESSAGE_FONT_SIZE_CONSTRAINTS =
            "Font size can only be either \"xs\", \"s\","
                    + " \"m\", \"l\",  or \"xl\"";
    public static final String MESSAGE_FONT_SIZE_IS_LARGEST =
            "The current font size is the largest one.";
    public static final String MESSAGE_FONT_SIZE_IS_SMALLEST =
            "The current font size is the smallest one.";

    public static final String[] FONT_SIZE_LIST = {"xs", "s", "m", "l", "xl"};
    public static final String[] FONT_SIZE_CHANGE_SYMBOL = {"+", "-"};

    public static final String FONT_SIZE_XS_LABEL = "xs";
    public static final String FONT_SIZE_S_LABEL = "s";
    public static final String FONT_SIZE_M_LABEL = "m";
    public static final String FONT_SIZE_L_LABEL = "l";
    public static final String FONT_SIZE_XL_LABEL = "xl";

    private static String currentFontSizeLabel = FONT_SIZE_M_LABEL;

    private final String value;

    /**
     * Validates given font size.
     *
     * @throws IllegalValueException if given font size is invalid.
     */
    public FontSize(String fontSize) throws IllegalValueException {
        requireNonNull(fontSize);
        if (isValidFontSizeChangeSymbol(fontSize)) {

            // Get the new font size from "+" or "-" symbol base on current font size
            fontSize = getFontSizeFromChangeSymbol(fontSize);

        } else if (!isValidFontSize(fontSize)) {
            throw new IllegalValueException(MESSAGE_FONT_SIZE_CONSTRAINTS);
        }
        this.value = fontSize;
    }

    /**
     * Get the String value of the font size
     */
    public String getValue() {
        return value;
    }

    /**
     * Get the new font size from "+" or "-" symbol and change the current font size
     */
    private String getFontSizeFromChangeSymbol(String symbol) throws IllegalValueException {
        int fontSizeListLength = FONT_SIZE_LIST.length;

        // Increase the font size
        if (symbol.equals("+")) {

            // Find which is the current font size except the largest one
            for (int i = 0; i < fontSizeListLength - 1; i++) {

                // The current font size is at the ith place in the list
                if (currentFontSizeLabel.equals(FONT_SIZE_LIST[i])) {

                    // Get the next largest font size
                    return FONT_SIZE_LIST[i + 1];
                }
            }

            // Current font size is the largest font size
            throw new IllegalValueException(MESSAGE_FONT_SIZE_IS_LARGEST);

        } else {
            // Decrease the font size

            // Find which is the current font size except the smallest one
            for (int i = 1; i < fontSizeListLength; i++) {

                // The current font size is at the ith place in the list
                if (currentFontSizeLabel.equals(FONT_SIZE_LIST[i])) {
                    // Get the next smaller size
                    return FONT_SIZE_LIST[i - 1];
                }
            }

            // Current font size is the smallest font size
            throw new IllegalValueException(MESSAGE_FONT_SIZE_IS_SMALLEST);
        }
    }

    /**
     * Check whether the change symbol is valid
     */
    private boolean isValidFontSizeChangeSymbol(String symbol) {
        for (String s : FONT_SIZE_CHANGE_SYMBOL) {
            if (symbol.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a given string is a valid font size.
     */
    public static boolean isValidFontSize(String test) {
        for (String s : FONT_SIZE_LIST) {
            if (test.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the current font size
     */
    public static String getCurrentFontSizeLabel() {
        return currentFontSizeLabel;
    }

    /**
     * Set the current font size to a new font size
     * @param newFontSizeLabel
     */
    public static void setCurrentFontSizeLabel(String newFontSizeLabel) {
        requireNonNull(newFontSizeLabel);

        if (isValidFontSize(newFontSizeLabel)) {
            currentFontSizeLabel = newFontSizeLabel;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other.equals(this.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


    /**
     * Get the associate fx format string for a give font size
     * @param inputFontSize
     */
    public static String getAssociateFxFontSizeString(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        String fxFontSizeString = "-fx-font-size: ";
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            fxFontSizeString += "small;";
            break;

        case FONT_SIZE_S_LABEL:
            fxFontSizeString += "medium;";
            break;

        case FONT_SIZE_M_LABEL:
            fxFontSizeString += "large;";
            break;

        case FONT_SIZE_L_LABEL:
            fxFontSizeString += "x-large;";
            break;

        case FONT_SIZE_XL_LABEL:
            fxFontSizeString += "xx-large;";
            break;

        default:
            fxFontSizeString += "large;";
        }
        return fxFontSizeString;
    }

    /**
     * Get the associate fx format string for a give font size of name
     * @param inputFontSize
     */
    public static String getAssociateFxFontSizeStringForName(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        String fxFontSizeString = "-fx-font-size: ";
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            fxFontSizeString += "15;";
            break;

        case FONT_SIZE_S_LABEL:
            fxFontSizeString += "20;";
            break;

        case FONT_SIZE_M_LABEL:
            fxFontSizeString += "25;";
            break;

        case FONT_SIZE_L_LABEL:
            fxFontSizeString += "30;";
            break;

        case FONT_SIZE_XL_LABEL:
            fxFontSizeString += "35;";
            break;

        default:
            fxFontSizeString += "25;";
        }
        return fxFontSizeString;
    }

    /**
     * Get associate image size from a given font size
     * @param inputFontSize
     * @return
     */
    public static int getAssociateImageSizeFromFontSize(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        int imageSize;
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            imageSize = 15;
            break;

        case FONT_SIZE_S_LABEL:
            imageSize = 20;
            break;

        case FONT_SIZE_M_LABEL:
            imageSize = 25;
            break;

        case FONT_SIZE_L_LABEL:
            imageSize = 30;
            break;

        case FONT_SIZE_XL_LABEL:
            imageSize = 35;
            break;

        default:
            imageSize = 25;
        }
        return imageSize;
    }

    /**
     * Get associate photo size from a given font size
     * @param inputFontSize
     * @return
     */
    public static int getAssociatePhotoSizeFromFontSize(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        int photoSize;
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            photoSize = 45;
            break;

        case FONT_SIZE_S_LABEL:
            photoSize = 50;
            break;

        case FONT_SIZE_M_LABEL:
            photoSize = 55;
            break;

        case FONT_SIZE_L_LABEL:
            photoSize = 60;
            break;

        case FONT_SIZE_XL_LABEL:
            photoSize = 65;
            break;

        default:
            photoSize = 55;
        }
        return photoSize;
    }

}
