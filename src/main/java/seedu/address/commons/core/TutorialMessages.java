package seedu.address.commons.core;

import java.util.ArrayList;

//@@author Alim95

/**
 * Container for tutorial messages.
 */
public class TutorialMessages {

    public static final int TOTAL_NUM_STEPS = 8;

    /* Introductory Messages */
    public static final String INTRO_BEGIN = "Welcome to Bluebird! Would you like to go through the tutorial?";
    public static final String INTRO_TWO = "This is the command box, where you will enter your commands.";
    public static final String INTRO_THREE = "This is the result display, where "
            + "I will display the outcome of your commands.";
    public static final String INTRO_FOUR = "This is the sort menu, where you can select how to sort the list.";
    public static final String INTRO_FIVE = "This is the search box, where "
            + "you are able to search for the person you want.";
    public static final String INTRO_SIX = "This is the person and task list panel, "
            + "where you will see your list of contacts and tasks";
    public static final String INTRO_END = "Features of Bluebird:\n"
            + "1. Add a contact and task\n"
            + "2. Delete a contact and task\n"
            + "3. Edit a contact and task\n"
            + "4. Find a contact\n"
            + "5. Select a contact\n"
            + "6. Pin a contact\n"
            + "7. Unpin a contact\n"
            + "8. Hide a contact\n"
            + "9. Clear all contacts and tasks\n"
            + "10. List all contacts and tasks\n"
            + "11. Sort list of contacts\n"
            + "12. Help window\n"
            + "13. History of command inputs\n"
            + "14. Undo a command\n"
            + "15. Redo a command\n";

    /* Command usage messages */
    public static final String USAGE_BEGIN = "Let's try out the different commands of Bluebird! Press F2 to view "
            + "the list of commands and enter the commands on the command box to execute it."
            + " A parameter in [ ] means it is optional.";

    /* Concluding message */
    public static final String CONCLUSION = "That's it for the tutorial! If you still need help, you can "
            + "type \"help\" on the command box or press F1 for the user guide.";

    /* List of all introductory messages */
    public static final ArrayList<String> INTRO_LIST = new ArrayList<String>() {
        {
            add(INTRO_TWO);
            add(INTRO_THREE);
            add(INTRO_FOUR);
            add(INTRO_FIVE);
            add(INTRO_SIX);
            add(INTRO_END);
            add(USAGE_BEGIN);
        }
    };
}
