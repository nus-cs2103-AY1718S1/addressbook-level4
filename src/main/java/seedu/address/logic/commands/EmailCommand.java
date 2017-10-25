package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * The UI component that is responsible for emailing the selected person.
 */
public class EmailCommand extends Command {
    
    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "em";
    public static final String MESSAGE_SUCCESS = "Email opened!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private int emailIndex;
    
    public EmailCommand(int emailIndex) {
        this.emailIndex = emailIndex;
    }
    
    public void openEmail() {}
    
    
    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        

        ReadOnlyPerson personToDelete = lastShownList.get(emailIndex);
        
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
}
