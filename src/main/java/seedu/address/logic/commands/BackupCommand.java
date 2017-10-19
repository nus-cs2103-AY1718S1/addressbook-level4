package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";
    public static final String COMMAND_ALIAS = "bak";
    public static final String MESSAGE_SUCCESS = "Backup created.";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            storage.backupAddressBook(storage.readAddressBook().get());
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, Storage storage) {
        super.setData(model, history, undoRedoStack, storage);
        this.storage = storage;
    }
}
