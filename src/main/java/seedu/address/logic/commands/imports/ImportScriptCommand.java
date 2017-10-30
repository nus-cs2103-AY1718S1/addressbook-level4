package seedu.address.logic.commands.imports;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Imports data from a BoNUS script file (end with {@code .bo}) to the application.
 */
public class ImportScriptCommand extends ImportCommand {

    public ImportScriptCommand(String path) {
        super(path, ImportType.SCRIPT);
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
