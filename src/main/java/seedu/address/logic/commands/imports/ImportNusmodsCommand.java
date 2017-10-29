package seedu.address.logic.commands.imports;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Imports data from the URL of a NUSMods timetable.
 *
 * @see <a href="https://nusmods.com/">https://nusmods.com/</a>
 */
public class ImportNusmodsCommand extends ImportCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports data from NUSMods timetable URL.\n"
            + "Examples:\n"
            + COMMAND_WORD + " --nusmods https://nusmods.com/timetable/2017-2018/sem1?CS2103T[TUT]=C03";

    public ImportNusmodsCommand(String path) {
        super(path, ImportType.NUSMODS);
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
