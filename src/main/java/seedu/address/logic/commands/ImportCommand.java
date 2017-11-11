package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author limshunyong
/**
 * import contacts from external source (in .vcf format)
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "im";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Import contact details from external source (must be in .vcf format).\n"
            + "Parameters: FILENAME \n"
            + "Example: " + COMMAND_WORD + " contacts.vcf";

    public static final String MESSAGE_SUCCESS = "Contacts successfully imported";
    public static final String MESSAGE_FAILURE = "Error importing contacts. File not found or Filename incorrect.";

    private ArrayList<ReadOnlyPerson> p;

    public ImportCommand(ArrayList<ReadOnlyPerson> list) {
        this.p = list;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        if (p.isEmpty()) {
            return new CommandResult(MESSAGE_FAILURE);
        } else {
            try {
                for (ReadOnlyPerson pp : p) {
                    model.addPerson(pp);
                }
            } catch (DuplicatePersonException de) {
                throw new CommandException(AddCommand.MESSAGE_DUPLICATE_PERSON);
            }
            LoggingCommand loggingCommand = new LoggingCommand();
            loggingCommand.keepLog("", "Import Action");
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
