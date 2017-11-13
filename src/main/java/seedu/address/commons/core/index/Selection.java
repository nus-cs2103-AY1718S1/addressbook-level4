package seedu.address.commons.core.index;

//@@author martyn-wong
/**
 * To get or set the selection state of the application
 */
public class Selection {

    private static boolean isPersonSelected = false;
    private static boolean isMeetingChosen = false;

    public static void setPersonSelected() {
        isPersonSelected = true;
    }

    public static void setPersonNotSelected() {
        isPersonSelected = false;
    }

    public static void setMeetingChosen() {
        isMeetingChosen = true;
    }

    public static void setMeetingNotChosen() {
        isMeetingChosen = false;
    }

    public static boolean getSelectionStatus() {
        return isPersonSelected;
    }

    public static boolean getMeetingStatus() {
        return isMeetingChosen;
    }
}
