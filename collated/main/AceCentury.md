# AceCentury
###### /java/seedu/address/logic/commands/DeleteByNameCommand.java
``` java

package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.CaseInsensitiveExactNamePredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using the person's exact name. Case insensitive.
 * IMPORTANT: Does not depend on last shown list.
 */

public class DeleteByNameCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletebyname";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the case-insensitive input NAME.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " John";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_MULTIPLE_PERSON_WITH_SAME_NAME = "There are multiple persons with the same name"
            + " detected who are listed below for your convenience. "
            + "Please use the general delete method shown below. \n" + DeleteCommand.MESSAGE_USAGE;

    public static final String MESSAGE_SUGGESTED_PERSONS = "Could not find the person to delete. "
            + "Here are some suggestions. You may edit the input name or use the general delete method shown below.\n"
            + DeleteCommand.MESSAGE_USAGE;

    private final Name nameToBeDeleted;

    private List<ReadOnlyPerson> personList;

    private List<ReadOnlyPerson> filteredPersonList;

    public DeleteByNameCommand(Name nameToBeDeleted) {
        this.nameToBeDeleted = nameToBeDeleted;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        this.personList = model.getAddressBook().getPersonList();
        ReadOnlyPerson personToDelete = obtainPersonToDelete();

        if (personToDelete == null) { // No matching name found
            provideSuggestions();
        }

        try {
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    /**
     * Helper function to obtain the person with the matching name to delete.
     */
    private ReadOnlyPerson obtainPersonToDelete() {
        Stream<ReadOnlyPerson> filteredPersonStream = personList.stream()
                .filter(person -> person.getName().toString().toLowerCase()
                        .equals(nameToBeDeleted.toString().toLowerCase()));

        this.filteredPersonList = filteredPersonStream.collect(Collectors.toList());

        if (filteredPersonList.isEmpty() || filteredPersonList.size() > 1) { // If no match or similar names exist.
            return null;
        }

        ReadOnlyPerson personToDelete = filteredPersonList.get(0);
        return personToDelete;
    }

    /**
     * Helper function to show suggestions (where possible) if no person with a matching name exists.
     */
    private void provideSuggestions() throws CommandException {
        if (filteredPersonList.size() > 1) { // More than 1 person with exact name
            model.updateFilteredPersonList(new CaseInsensitiveExactNamePredicate(nameToBeDeleted));
            throw new CommandException(MESSAGE_MULTIPLE_PERSON_WITH_SAME_NAME);
        } else {
            List<String> keywords = Arrays.asList(nameToBeDeleted.toString().split(" "));
            NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);
            Stream<ReadOnlyPerson> suggestedPersonStream = personList.stream().filter(predicate);
            List<ReadOnlyPerson> suggestedPersonList = suggestedPersonStream.collect(Collectors.toList());

            if (!suggestedPersonList.isEmpty()) { // Show suggested persons to delete
                model.updateFilteredPersonList(predicate);
                throw new CommandException(MESSAGE_SUGGESTED_PERSONS);
            } else { // No such person found
                throw new CommandException(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByNameCommand // instanceof handles nulls
                && this.nameToBeDeleted.equals(((DeleteByNameCommand) other).nameToBeDeleted)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ExportCommand.java
``` java

package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Exports the current Address Book data into a CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_EXPORT_SUCCESS = "Successfully exported data.";

    public static final String MESSAGE_EMPTY_ADDRESS_BOOK = "Export failed. Current Address Book is empty.";

    public static final String DATA_FILE_PATH = "AddressBookData.csv";

    private List<ReadOnlyPerson> currentData;

    @Override
    public CommandResult execute() throws CommandException {
        this.currentData = model.getAddressBook().getPersonList();

        if (currentData.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_ADDRESS_BOOK);
        }

        if (fileExists()) {
            deleteFile();
        }

        createFile();
        writeData();
        return new CommandResult(MESSAGE_EXPORT_SUCCESS);
    }

    /** Constructing the headers for CSV */
    private String constructHeaders() {
        ReadOnlyPerson samplePerson = currentData.get(0);
        List<String> propertyNames = samplePerson.getPropertyNamesAsList();

        final StringBuilder builder = new StringBuilder();

        for (String field : propertyNames) {
            builder.append(field);
            builder.append(",");
        }
        builder.append("Tags");
        builder.append("\n");

        return builder.toString();
    }

    /** Function to write data to the CSV */
    private void writeData() throws CommandException {
        final StringBuilder builder = new StringBuilder();

        File csvData = new File(DATA_FILE_PATH);
        String headers = constructHeaders();

        for (ReadOnlyPerson person : currentData) {
            String personData = generatePersonData(person);
            builder.append(personData);
        }

        String dataToWrite = builder.toString();

        try {
            PrintWriter pw = new PrintWriter(csvData);
            pw.write(headers);
            pw.write(dataToWrite);
            pw.close();
        } catch (IOException ioe) {
            throw new CommandException(ioe.getMessage());
        }

    }

    /**
     * Function to generate Person data as text.
     * Mostly hardcoded for now, until a better implementation can be found.
     */
    private String generatePersonData(ReadOnlyPerson person) {
        String name = person.getName().toString();
        String phone = person.getPhone().toString();
        String email = person.getEmail().toString();
        String address = person.getAddress().toString();
        address = address.replace(',', ';');
        String nokName = person.getNokName().toString();
        String nokPhone = person.getNokPhone().toString();
        String tags = parseTagsToString(person);

        final StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(",");
        builder.append(phone);
        builder.append(",");
        builder.append(email);
        builder.append(",");
        builder.append(address);
        builder.append(",");
        builder.append(nokName);
        builder.append(",");
        builder.append(nokPhone);
        builder.append(",");
        builder.append(tags);
        builder.append("\n");

        return builder.toString();
    }

    /** Function to convert tags into a suitable String format for CSV */
    private String parseTagsToString(ReadOnlyPerson person) {
        Set<Tag> tags = person.getTags();
        final StringBuilder builder = new StringBuilder();
        for (Tag tag : tags) {
            String convertedTag = tag.toString();
            builder.append(convertedTag);
        }

        return builder.toString();
    }

    /** Function to create the CSV */
    private void createFile() throws CommandException {
        try {
            File csvData = new File(DATA_FILE_PATH);
            csvData.createNewFile();
        } catch (IOException ioe) {
            throw new CommandException(ioe.getMessage());
        }
    }

    private boolean fileExists() {
        File csvData = new File(DATA_FILE_PATH);
        return csvData.exists();
    }

    private void deleteFile() {
        File csvData = new File(DATA_FILE_PATH);
        csvData.delete();
    }

}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        final String commandWord = matcher.group("commandWord").toLowerCase();
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case ExportCommand.COMMAND_WORD:
            return new ExportCommand();
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case DeleteByNameCommand.COMMAND_WORD:
            return new DeleteByNameCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/DeleteByNameCommandParser.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteByNameCommand object
 */
public class DeleteByNameCommandParser implements Parser<DeleteByNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteByNameCommand
     * and returns a DeleteByNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteByNameCommand parse(String args) throws ParseException {
        try {
            Name name = new Name(args);
            return new DeleteByNameCommand(name);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/model/person/CaseInsensitiveExactNamePredicate.java
``` java

package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} is an exact match (case-insensitve).
 */
public class CaseInsensitiveExactNamePredicate implements Predicate<ReadOnlyPerson> {
    private final Name name;

    public CaseInsensitiveExactNamePredicate(Name name) {
        this.name = name;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (person.getName().toString().toLowerCase()
                .equals(name.toString().toLowerCase())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CaseInsensitiveExactNamePredicate // instanceof handles nulls
                && this.name.equals(((CaseInsensitiveExactNamePredicate) other).name)); // state check
    }
}
```
###### /java/seedu/address/model/person/ReadOnlyPerson.java
``` java
    /**
     * Returns a List of Strings containing all the property names of a Person.
     */
    default List<String> getPropertyNamesAsList() {
        List<String> propertyNames = new ArrayList<String>();

        propertyNames.add("Name");
        propertyNames.add("Phone");
        propertyNames.add("Email");
        propertyNames.add("Address");
        propertyNames.add("NokName");
        propertyNames.add("NokPhone");

        return propertyNames;
    }


```
