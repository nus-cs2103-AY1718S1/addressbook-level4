# nguyenvanhoang7398
###### \java\seedu\address\logic\commands\AddMultipleByTsvCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.StringJoiner;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Command to add multiple contacts at once
 */
public class AddMultipleByTsvCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addMulTsv";
    public static final String COMMAND_ALIAS = "addMT";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds multiple people to the address book "
            + "given a tsv (tab separated value) txt file containing their contact information. "
            + "Parameters: TSV_PATH\n"
            + "Example: " + COMMAND_WORD + " D:/Contacts.txt";

    public static final String MESSAGE_SUCCESS = "%d new person (people) added";
    public static final String MESSAGE_DUPLICATE_PERSON = "%d new person (people) duplicated";
    public static final String MESSAGE_NUMBER_OF_ENTRIES_FAILED = "%d entry (entries) failed: ";
    public static final String MESSAGE_FILE_NOT_FOUND = "The system cannot find the file specified";

    private final ArrayList<Person> toAdd;
    private final ArrayList<Integer> failedEntries;
    private final boolean isFileFound;

    public AddMultipleByTsvCommand(ArrayList<ReadOnlyPerson> toAddPeople, ArrayList<Integer> failedEntries,
                                   boolean isFileFound) {
        this.toAdd = new ArrayList<Person>();
        this.failedEntries = failedEntries;
        this.isFileFound = isFileFound;
        for (ReadOnlyPerson person: toAddPeople) {
            toAdd.add(new Person(person));
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        if (!isFileFound) {
            return new CommandResult(MESSAGE_FILE_NOT_FOUND);
        }
        int numAdded = 0;
        int numDuplicated = 0;

        for (Person person: toAdd) {
            try {
                model.addPerson(person);
                numAdded++;
            } catch (DuplicatePersonException e) {
                numDuplicated++;
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, numAdded) + ", "
                + String.format(MESSAGE_DUPLICATE_PERSON, numDuplicated) + ", "
                + String.format(MESSAGE_NUMBER_OF_ENTRIES_FAILED, failedEntries.size())
                + joinFailedEntries(failedEntries));
    }

    /**
     * Join the list of integers of failed entries into string
     * @param failedEntries
     * @return
     */
    private static String joinFailedEntries(ArrayList<Integer> failedEntries) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Integer entry: failedEntries) {
            joiner.add(entry.toString());
        }
        return joiner.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMultipleByTsvCommand // instanceof handles nulls
                && toAdd.equals(((AddMultipleByTsvCommand) other).toAdd));
    }

}
```
###### \java\seedu\address\logic\commands\FindTagCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */

public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_ALIAS = "fitg";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified tags (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " friends family\n";

    private final TagsContainKeywordsPredicate predicate;

    public FindTagCommand(TagsContainKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagCommand) other).predicate)); // state check
    }

}
```
###### \java\seedu\address\logic\ContactTsvReader.java
``` java
package seedu.address.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Occupation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Website;
import seedu.address.model.tag.Tag;

/**
 * Reads tsv file
 */
public class ContactTsvReader {
    private String contactTsvFilePath;
    private ArrayList<ReadOnlyPerson> toAddPeople;
    private ArrayList<Integer> failedEntries;

    public ContactTsvReader(String contactTsvFilePath) {
        this.contactTsvFilePath = contactTsvFilePath;
    }

    public ArrayList<ReadOnlyPerson> getToAddPeople() {
        return toAddPeople;
    }

    public ArrayList<Integer> getFailedEntries() {
        return failedEntries;
    }

    /**
     * Read contacts from the given file path and update toAddPeople and failedEntries
     * @throws ParseException
     * @throws IOException
     */
    public void readContactFromFile() throws ParseException, IOException {

        BufferedReader bufferedReader;
        toAddPeople = new ArrayList<ReadOnlyPerson>();
        failedEntries = new ArrayList<>();

        String line;
        bufferedReader = new BufferedReader(new FileReader(contactTsvFilePath));
        int i = 0;

        // How to read file in java line by line?
        while ((line = bufferedReader.readLine()) != null) {
            if (i != 0) {
                try {
                    ArrayList<String> columns = tsvLinetoArrayList(line);
                    Name name = ParserUtil.parseName(checkEmptyAndReturn(retrieveColumnField(columns, 0)))
                            .get();
                    Occupation occupation = ParserUtil.parseOccupation(checkEmptyAndReturn(retrieveColumnField(columns,
                            1))).get();
                    Phone phone = ParserUtil.parsePhone(checkEmptyAndReturn(retrieveColumnField(columns, 2)))
                            .get();
                    Email email = ParserUtil.parseEmail(checkEmptyAndReturn(retrieveColumnField(columns, 3)))
                            .get();
                    Address address = ParserUtil.parseAddress(checkEmptyAndReturn(retrieveColumnField(columns, 4)))
                            .get();
                    Website website = ParserUtil.parseWebsite(checkEmptyAndReturn(retrieveColumnField(columns, 5)))
                            .get();
                    Set<Tag> tagList = ParserUtil.parseTags(new ArrayList<String>(
                            Arrays.asList(retrieveColumnField(columns, 6)
                                    .replaceAll("^[,\"\\s]+", "")
                                    .replace("\"", "")
                                    .split("[,\\s]+"))));
                    Remark remark = new Remark("");
                    ReadOnlyPerson toAddPerson = new Person(name, occupation, phone, email, address, remark, website,
                            tagList);
                    toAddPeople.add(toAddPerson);
                } catch (IllegalValueException ive) {
                    throw new ParseException(ive.getMessage(), ive);
                } catch (NoSuchElementException nsee) {
                    failedEntries.add(i);
                }
            }
            i++;
        }

        bufferedReader.close();
    }

    /**
     * Check if a given string is empty and return Optional object accordingly
     * @param valueStr
     * @return
     */
    private static Optional<String> checkEmptyAndReturn(String valueStr) {
        Optional<String> result = valueStr.length() < 1 ? Optional.empty() : Optional.of(valueStr);
        System.out.println(result);
        return result;
    }

    /**
     * Convert a line in tsv file to list of string values of fields
     * @param line
     * @return
     */
    private static ArrayList<String> tsvLinetoArrayList(String line) {
        final String emptyFieldValue = "";
        ArrayList<String> result = new ArrayList<String>();

        if (line != null) {
            String[] splitData = line.split("\t");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                    result.add(splitData[i].trim());
                } else {
                    result.add(emptyFieldValue);
                }
            }
        }

        return result;
    }

    /**
     * Retrieve string value of a field given the columns and its index
     * @param columns
     * @param index
     * @return
     */
    private static String retrieveColumnField(ArrayList<String> columns, int index) {
        final String outOfBoundValue = "";

        try {
            return columns.get(index).replace("\"", "");
        } catch (IndexOutOfBoundsException e) {
            return outOfBoundValue;
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddMultipleByTsvCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.logic.ContactTsvReader;
import seedu.address.logic.commands.AddMultipleByTsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Parser for AddMultipleByTsvCommand
 */
public class AddMultipleByTsvCommandParser implements Parser<AddMultipleByTsvCommand> {

    /**
     * Parse arguments given by AddressBookParser
     * @param args
     * @return
     * @throws ParseException
     */
    public AddMultipleByTsvCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMultipleByTsvCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        String contactTsvFilePath = nameKeywords[0];
        ContactTsvReader contactTsvReader = new ContactTsvReader(contactTsvFilePath);
        boolean isFileFound;
        ArrayList<ReadOnlyPerson> toAddPeople = new ArrayList<ReadOnlyPerson>();
        ArrayList<Integer> failedEntries = contactTsvReader.getFailedEntries();

        try {
            contactTsvReader.readContactFromFile();
            toAddPeople = contactTsvReader.getToAddPeople();
            failedEntries = contactTsvReader.getFailedEntries();
            isFileFound = true;
        } catch (IOException e) {
            isFileFound = false;
        }

        return new AddMultipleByTsvCommand(toAddPeople, failedEntries, isFileFound);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case AddMultipleByTsvCommand.COMMAND_WORD:
        case AddMultipleByTsvCommand.COMMAND_ALIAS:
            return new AddMultipleByTsvCommandParser().parse(arguments);
```
###### \java\seedu\address\model\person\TagsContainKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */

public class TagsContainKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * @param setTags
     * @return
     */

    private String stringifySetTags(Set<Tag> setTags) {
        StringBuilder setTagsString = new StringBuilder();
        final String delimiter = " ";

        Iterator<Tag> tagIterator = setTags.iterator();
        while (tagIterator.hasNext()) {
            Tag checkingTag = tagIterator.next();
            setTagsString.append(checkingTag.tagName);
            setTagsString.append(delimiter);
        }

        return setTagsString.toString();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(stringifySetTags(person.getTags()), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagsContainKeywordsPredicate) other).keywords)); // state check
    }
}
```
