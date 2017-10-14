package seedu.address.commons.core;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Container for tutorial messages.
 */
public class TutorialMessages {

    public static final int NUM_STEPS = 11;

    /* Introductory Messages */
    public static final String INTRO_BEGIN = "Welcome to Bluebird! Would you like to go through the tutorial?";
    public static final String INTRO_TWO = "This is the command box, where you will enter your commands.";
    public static final String INTRO_THREE = "This is the result display, where "
            + "I will display the outcome of your commands.";
    public static final String INTRO_FOUR = "This is the sort menu, where you can select how to sort the list.";
    public static final String INTRO_FIVE = "This is the search bar, where "
            + "you are able to search for the person you want.";
    public static final String INTRO_SIX = "This is the person list panel, where you will see your list of contacts";
    public static final String INTRO_END = "Functions of Bluebird:\n"
            + "1. Add\n"
            + "2. Delete\n"
            + "3. Edit\n"
            + "4. List\n"
            + "5. Sort\n"
            + "6. COMMAND\n"
            + "7. COMMAND\n";

    /* Command usage messages */
    public static final String PROMPT_BEGIN = "Let's try out the different commands of Bluebird! Follow the format in the "
            + "command box to learn how to use each commands. A parameter in [ ] means it is optional!";

    public static final String PROMPT_TWO = "add: Adds a person to the address book. "
            + "Example: add "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String PROMPT_THREE = "edit: Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Example: edit 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String PROMPT_FOUR = "delete: Deletes the person identified "
            + "by the index number used in the last person listing.\n"
            + "Example: delete 1";

    /* Command prompt messages */
    public static final String COMMAND_ADD_USAGE = "add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]";
    public static final String COMMAND_EDIT_USAGE = "edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]";
    public static final String COMMAND_DELETE_USAGE = "delete INDEX";

    /* Concluding message */
    public static final String CONCLUSION = "That's it for the tutorial! If you still need help, you can "
            + "type \"help\" on the command box or press F1 for the user guide.";

    /* Default prompt message */
    public static final String DEFAULT_PROMPT = "Enter command here....";
}
