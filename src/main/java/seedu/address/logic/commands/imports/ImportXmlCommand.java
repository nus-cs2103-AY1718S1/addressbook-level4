package seedu.address.logic.commands.imports;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Imports data from a xml file (in BoNUS-specific format) to the application.
 */
public class ImportXmlCommand extends ImportCommand {

    public ImportXmlCommand(String path) {
        super(path, ImportType.XML
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
