package seedu.address.commons.core.index;

/**
 * To get or set the selection state of the application
 */
public class Selection {

    private static boolean isPersonSelected = false;

    public static void setPersonSelected() {
        isPersonSelected = true;
    }

    public static void setPersonNotSelected() {
        isPersonSelected = false;
    }

    public static boolean getSelectionStatus() {
        return isPersonSelected;
    }
}
