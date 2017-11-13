//@@author HanYaodong
package seedu.address.logic.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.storage.XmlPersonListStorage;

/**
 * Export a person list to a specified save file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Exports persons identified by the index numbers used in the last person listing to a specified save file."
        + "\n" + "Parameters: INDEXES; FILE_PATH\n"
        + "(Indexes must be positive integers or two indexes linked by \"-\" separated by commas or spaces)\n"
        + "Example: " + COMMAND_WORD + " 1-3, 4 6; /Users/[User Name]/Desktop/persons.xml";

    public static final String MESSAGE_EXPORT_PERSON_SUCCESS = "Your contacts: %1$shave been exported to file: %2$s";

    private final List<Index> targetIndexes;
    private final String filePath;

    public ExportCommand(List<Index> targetIndexes, String filePath) {
        this.targetIndexes = targetIndexes;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> personsToSave = null;

        try {
            personsToSave = getPersonsToSave();
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        XmlPersonListStorage xmlPersonListStorage = new XmlPersonListStorage(this.filePath);
        try {
            xmlPersonListStorage.savePersonList(personsToSave);
        } catch (IOException ioe) {
            throw new CommandException(ioe.getMessage());
            //TODO: better error handling
        }
        return new CommandResult(String.format(
            MESSAGE_EXPORT_PERSON_SUCCESS, getPersonNameList(personsToSave), this.filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExportCommand // instanceof handles nulls
            && this.targetIndexes.equals(((ExportCommand) other).targetIndexes) // state check
            && this.filePath.equals(((ExportCommand) other).filePath));
    }

    private List<ReadOnlyPerson> getPersonsToSave() throws PersonNotFoundException {
        List<ReadOnlyPerson> lastShowList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personsToSave = new ArrayList<>();
        for (Index index : this.targetIndexes) {
            if (index.getZeroBased() >= lastShowList.size()) {
                throw new PersonNotFoundException();
            }
            personsToSave.add(lastShowList.get(index.getZeroBased()));
        }
        return personsToSave;
    }

    private String getPersonNameList(List<ReadOnlyPerson> personsToSave) {
        StringBuilder personNameBuilder = new StringBuilder();
        for (ReadOnlyPerson person : personsToSave) {
            personNameBuilder.append(person.getName().fullName).append(", ");
        }
        return personNameBuilder.deleteCharAt(personNameBuilder.lastIndexOf(",")).toString();
    }
}
