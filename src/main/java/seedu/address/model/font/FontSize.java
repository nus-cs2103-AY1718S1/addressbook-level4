//@@author ChenXiaoman
package seedu.address.model.font;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

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

    private static final String JAVAFX_FONT_SIZE_PREFIX = "-fx-font-size: ";
    private static final String JAVAFX_FONT_SIZE_XS = "small";
    private static final String JAVAFX_FONT_SIZE_S = "medium";
    private static final String JAVAFX_FONT_SIZE_M = "large";
    private static final String JAVAFX_FONT_SIZE_L = "x-large";
    private static final String JAVAFX_FONT_SIZE_XL = "xx-large";

    private static final String NAME_LABEL_SIZE_XS = "15";
    private static final String NAME_LABEL_SIZE_S = "20";
    private static final String NAME_LABEL_SIZE_M = "25";
    private static final String NAME_LABEL_SIZE_L = "30";
    private static final String NAME_LABEL_SIZE_XL = "35";

    private static final int IMAGE_SIZE_XS = 15;
    private static final int IMAGE_SIZE_S = 20;
    private static final int IMAGE_SIZE_M = 25;
    private static final int IMAGE_SIZE_L = 30;
    private static final int IMAGE_SIZE_XL = 35;

    private static final int PHOTO_SIZE_XS = 45;
    private static final int PHOTO_SIZE_S = 50;
    private static final int PHOTO_SIZE_M = 55;
    private static final int PHOTO_SIZE_L = 60;
    private static final int PHOTO_SIZE_XL = 65;

    private static String currentFontSizeLabel = FONT_SIZE_M_LABEL;

    private final String value;

    /**
     * Validates given font size.
     *
     * @param fontSize
     * @throws IllegalValueException if given font size is invalid.
     */
    public FontSize(String fontSize) throws IllegalValueException {
        requireNonNull(fontSize);
        if (isValidChangeFontSizeSymbol(fontSize)) {

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
     * Get a increased/decreased font size from "+" or "-"  symbol
     *
     * @param symbol
     * @return increased/decreased font size based on current font size
     * @throws IllegalValueException
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
     *
     * @param symbol
     * @return validity of a symbol
     */
    private boolean isValidChangeFontSizeSymbol(String symbol) {
        for (String s : FONT_SIZE_CHANGE_SYMBOL) {
            if (symbol.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a given string is a valid font size
     *
     * @param test
     * @return validity of a the String
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
     * Get the current font size
     *
     * @return the current font size
     */
    public static String getCurrentFontSizeLabel() {
        return currentFontSizeLabel;
    }

    /**
     * Set the current font size to a given new font size
     *
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
     * Get the associate JavaFX format String for a give font size
     *
     * @param inputFontSize
     */
    public static String getAssociateFxFontSizeString(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        String fxFontSizeString = JAVAFX_FONT_SIZE_PREFIX;
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            fxFontSizeString += JAVAFX_FONT_SIZE_XS;
            break;

        case FONT_SIZE_S_LABEL:
            fxFontSizeString += JAVAFX_FONT_SIZE_S;
            break;

        case FONT_SIZE_M_LABEL:
            fxFontSizeString += JAVAFX_FONT_SIZE_M;
            break;

        case FONT_SIZE_L_LABEL:
            fxFontSizeString += JAVAFX_FONT_SIZE_L;
            break;

        case FONT_SIZE_XL_LABEL:
            fxFontSizeString += JAVAFX_FONT_SIZE_XL;
            break;

        default:
            fxFontSizeString += JAVAFX_FONT_SIZE_M;
        }

        fxFontSizeString += ";";
        return fxFontSizeString;
    }

    /**
     * Get the associate JavaFX format String for a give font size of name
     *
     * @param inputFontSize
     * @return JavaFX format String
     */
    public static String getAssociateFxFontSizeStringForName(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        String fxFontSizeString = JAVAFX_FONT_SIZE_PREFIX;
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            fxFontSizeString += NAME_LABEL_SIZE_XS;
            break;

        case FONT_SIZE_S_LABEL:
            fxFontSizeString += NAME_LABEL_SIZE_S;
            break;

        case FONT_SIZE_M_LABEL:
            fxFontSizeString += NAME_LABEL_SIZE_M;
            break;

        case FONT_SIZE_L_LABEL:
            fxFontSizeString += NAME_LABEL_SIZE_L;
            break;

        case FONT_SIZE_XL_LABEL:
            fxFontSizeString += NAME_LABEL_SIZE_XL;
            break;

        default:
            fxFontSizeString += NAME_LABEL_SIZE_M;
        }

        fxFontSizeString += ";";
        return fxFontSizeString;
    }

    /**
     * Get associate image size from a given font size
     *
     * @param inputFontSize
     * @return image size
     */
    public static int getAssociateImageSizeFromFontSize(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        int imageSize;
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            imageSize = IMAGE_SIZE_XS;
            break;

        case FONT_SIZE_S_LABEL:
            imageSize = IMAGE_SIZE_S;
            break;

        case FONT_SIZE_M_LABEL:
            imageSize = IMAGE_SIZE_M;
            break;

        case FONT_SIZE_L_LABEL:
            imageSize = IMAGE_SIZE_L;
            break;

        case FONT_SIZE_XL_LABEL:
            imageSize = IMAGE_SIZE_XL;
            break;

        default:
            imageSize = IMAGE_SIZE_M;
        }
        return imageSize;
    }

    /**
     * Get associate photo size from a given font size
     *
     * @param inputFontSize
     * @return photo size
     */
    public static int getAssociatePhotoSizeFromFontSize(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        int photoSize;
        switch (inputFontSize) {
        case FONT_SIZE_XS_LABEL:
            photoSize = PHOTO_SIZE_XS;
            break;

        case FONT_SIZE_S_LABEL:
            photoSize = PHOTO_SIZE_S;
            break;

        case FONT_SIZE_M_LABEL:
            photoSize = PHOTO_SIZE_M;
            break;

        case FONT_SIZE_L_LABEL:
            photoSize = PHOTO_SIZE_L;
            break;

        case FONT_SIZE_XL_LABEL:
            photoSize = PHOTO_SIZE_XL;
            break;

        default:
            photoSize = PHOTO_SIZE_M;
        }
        return photoSize;
    }

}
