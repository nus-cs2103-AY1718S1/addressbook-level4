package seedu.address.logic.commands;

import java.io.IOException;

import java.net.URISyntaxException;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.asana.AuthenticateAsanaUser;

//@@author Sri-vatsa
/**
 * Initiates Authorisation with Asana on Asana's website
 */
public class SetupAsanaCommand extends Command {
    public static final String COMMAND_WORD = "setupAsana";
    public static final String COMMAND_ALIAS = "sa";

    public static final boolean CONFIGURED = true;

    public static final String MESSAGE_SUCCESS = "1. Login & allow OurAB to access your Asana account\n"
            + "2. Copy from the site, the code: DIGIT/ALPHANUMERICS\n"
            + "Example: 0/123a689ny8912h324h78s\n"
            + "3. Type: setKey 0/ALPHANUMERICS";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD;

    @Override
    public CommandResult execute() throws CommandException {

        try {

            new AuthenticateAsanaUser();

        } catch (URISyntaxException e) {
            throw new CommandException("Failed to redirect to Asana's page. Please try again later!");
        } catch (IOException e) {
            throw new CommandException("Asana setup failed due to bad input. Please try again later!");
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
