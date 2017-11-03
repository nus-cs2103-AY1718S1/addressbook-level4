package seedu.address.logic.commands;

//@@author limcel
/**
 * Lists all persons in the address book to the user.
 */
public class ViewScheduleCommand extends Command {

    public static final String COMMAND_WORD = "viewschedules";
    public static final String COMMAND_ALIAS = "viewsch";

    public static final String MESSAGE_SUCCESS = "Listed your schedule. \n";


    @Override
    public CommandResult execute() {
        model.getScheduleList();

        return new CommandResult(MESSAGE_SUCCESS
                + changeToAppropriateUiFormat((model.getAddressBook().getScheduleList()).toString()));
    }

    //====================================== HELPER METHODS =====================================
    /**
     * Converts the schedule list output by replacing all occurrence of "," with ": " for better UI visualisation.
     */
    public static String changeToAppropriateUiFormat(String value) {
        value = value.replace(",", "\n");
        return value;
    }
}
