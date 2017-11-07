package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.parser.AutoCorrectCommand;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD_ALIAS = "l";
    public static final String AUTOCOMPLETE_FORMAT = COMMAND_WORD;
    public static final String MESSAGE_USAGE = "";
    public static final String MESSAGE_SUCCESS = "Listed all persons";

    private AutoCorrectCommand autoCorrectCommand = new AutoCorrectCommand();

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + MESSAGE_SUCCESS);
        }
    }
}
