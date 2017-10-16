package seedu.address.logic.commands.configs;

import static seedu.address.logic.commands.configs.ConfigCommand.ConfigType.IMPORT_CALENDAR;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Imports an external calendar from a URL of ICS/iCal format.
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
