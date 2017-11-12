package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.asana.StoreAccessToken;

import java.io.IOException;

//@@author Sri-vatsa
/**
 * Sets Unique key produced by Asana on Asana's webpage
 */
public class SetUniqueKeyCommand extends Command{

    public static final String COMMAND_WORD = "setKey";
    public static final String COMMAND_ALIAS = "sk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " DIGIT/ALPHANUMERICS";


    public static final String MESSAGE_SUCCESS = "Asana setup successful!";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " DIGIT/ALPHANUMERICS";
    private final String userAccessCode;

    public SetUniqueKeyCommand(String code) {
        userAccessCode = code;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {
            new StoreAccessToken(userAccessCode);
        } catch (IOException e) {
            throw new CommandException("Please try again with a valid code from Asana");
        } catch (IllegalArgumentException iae) {
            throw new CommandException("Please try again with a valid code from Asana");
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetUniqueKeyCommand // instanceof handles nulls
                && this.userAccessCode.equals(((SetUniqueKeyCommand) other).userAccessCode)); // state check
    }

}
