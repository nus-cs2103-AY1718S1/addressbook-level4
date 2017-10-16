package seedu.address.logic.commands.configCommands;

import static seedu.address.logic.commands.configCommands.ConfigCommand.ConfigType.IMPORT_CALENDAR;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Imports an external calendar from a URL of iCal format.
 */
public class ImportCalenderCommand extends ConfigCommand {
    public ImportCalenderCommand(String configValue) {
        super(IMPORT_CALENDAR, configValue);
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
