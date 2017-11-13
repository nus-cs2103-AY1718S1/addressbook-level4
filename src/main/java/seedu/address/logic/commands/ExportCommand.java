//@@author conantteo
package seedu.address.logic.commands;

import static seedu.address.commons.util.FileUtil.createIfMissing;
import static seedu.address.commons.util.FileUtil.writeToFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ExportRequestEvent;
import seedu.address.commons.util.IndexArrayUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Vcard;

/**
 * Exports all contacts or specified person identified using it's last displayed index from the address book.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Export all contacts or specified person identified by the index number "
            + "used in the last person listing into a vCard file.\n"
            + "Parameters: all or INDEX [INDEX]... (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2" + " or " + COMMAND_WORD + " all";

    public static final String MESSAGE_EXPORT_PERSON_SUCCESS = "Export Person: %1$s\n"
            + "Please close the app before moving the contacts.vcf file to another location.";

    public static final String DEFAULT_FILE_DIR = "./data/";
    public static final String DEFAULT_FILE_NAME = "contacts.vcf";

    private final Index[] targetIndices;

    public ExportCommand() {
        this.targetIndices = new Index[0];
    }

    public ExportCommand(Index[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> fullList = model.getAddressBook().getPersonList();
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        StringBuilder persons = new StringBuilder();
        List<ReadOnlyPerson> listToExport = new ArrayList<>();

        // Checks if user has specified the index of persons to email
        if (targetIndices.length > 0) {
            for (Index targetIndex : targetIndices) {
                if (targetIndex.getZeroBased() >= lastShownList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                            + targetIndex.getOneBased());
                }
                ReadOnlyPerson personToExport = lastShownList.get(targetIndex.getZeroBased());
                listToExport.add(personToExport);
            }
        } else {
            if (!fullList.isEmpty()) {
                listToExport = fullList;
            } else {
                throw new CommandException(Messages.MESSAGE_NO_PERSONS_FOUND);
            }
        }

        // Constructs the String for all persons names
        for (ReadOnlyPerson person : listToExport) {
            persons.append(", ");
            persons.append(person.getName().toString());
        }
        String allPersons = StringUtil.getSubstringFromIndexTwo(persons.toString());

        // Creates a new vCard file to store all the VCard information.
        File file = new File(DEFAULT_FILE_DIR, DEFAULT_FILE_NAME);
        try {
            createIfMissing(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creates the content to be written into the vCard file
        StringBuilder content = new StringBuilder();
        for (ReadOnlyPerson person : listToExport) {
            Vcard personCard = new Vcard(person);
            content.append(personCard.getCardDetails());
        }

        // Writes the content into the vCard file.
        try {
            writeToFile(file, content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EventsCenter.getInstance().post(new ExportRequestEvent());
        return new CommandResult(String.format(MESSAGE_EXPORT_PERSON_SUCCESS, allPersons));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && IndexArrayUtil.compareIndexArrays(this.targetIndices, (
                (ExportCommand) other).targetIndices)); // state check
    }
}
