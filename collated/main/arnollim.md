# arnollim
###### /java/seedu/address/logic/commands/AddCommand.java
``` java
    /**
     * Returns the Command String of that added this person into the addressbook
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(toAdd.getName())
                .append(" Phone: ")
                .append(toAdd.getPhone())
                .append("Email: ")
                .append(toAdd.getEmail())
                .append("Address: ")
                .append(toAdd.getAddress())
                .append("DateOfBirth: ")
                .append(toAdd.getDateOfBirth())
                .append("Gender: ")
                .append(toAdd.getGender())
                .append(" Tags: ");
        toAdd.getTags().forEach(builder::append);
        String person = builder.toString();
        return COMMAND_WORD + " " + person;
    }
```
###### /java/seedu/address/logic/commands/AddLifeInsuranceCommand.java
``` java
    @Override
    public String toString() {
        return COMMAND_WORD;
    }
```
###### /java/seedu/address/logic/commands/ClearCommand.java
``` java
    @Override
    public String toString() {
        return COMMAND_WORD;
    }
```
###### /java/seedu/address/logic/commands/DeleteCommand.java
``` java
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(COMMAND_WORD + " ")
                .append(this.targetIndex.getOneBased());
        String command = builder.toString();
        return command;
    }
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
    @Override
    public String toString() {
        return COMMAND_WORD + " " + index.getOneBased();
    }
```
###### /java/seedu/address/logic/commands/PrintCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.insurance.UniqueLifeInsuranceList;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Prints the list of contacts, along with any associated
 * insurance policies where the contact is involved in,
 * into a printable, readable .txt file.
 */
public class PrintCommand extends Command {

    public static final String[] COMMAND_WORDS = {"print"};
    public static final String COMMAND_WORD = "print";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves the addressbook into a .txt file named by you for your viewing.\n"
            + "Example: " + COMMAND_WORD + " filename\n"
            + "file can then be found in the in data/ folder as data/filename.txt";

    public static final String MESSAGE_SUCCESS = "Address Book has been saved!\n"
            + "Find your Address Book in the %1$s.txt file you created in data/%1$s.txt.";

    private final String fileName;

    public PrintCommand(String fileName) {
        requireNonNull(fileName);

        this.fileName = fileName;
    }


    @Override
    public CommandResult execute() {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        List<String> lines = new ArrayList<>();
        String timeStamp = new SimpleDateFormat("dd/MM/YYYY" + " " + "HH:mm:ss").format(new Date());
        lines.add("LISA was last updated on: " + timeStamp + "\n\n");

        lines.add("There are " + lastShownList.size() + " contacts in LISA\n\n");

        int personIndex = 1;
        for (ReadOnlyPerson person: lastShownList) {
            String entry = personIndex + ". " + person.getAsParagraph();
            lines.add(entry);
            lines.add("\n" + person.getName().fullName +
                    " is a personnel involved in the following insurance policies:\n");

            UniqueLifeInsuranceList insurances = person.getLifeInsurances();
            int insuranceIndex = 1;
            for (ReadOnlyInsurance insurance: insurances) {
                lines.add("Insurance Policy " + insuranceIndex + ": =========");
                String owner = insurance.getOwner().getName();
                String insured = insurance.getInsured().getName();
                String beneficiary = insurance.getBeneficiary().getName();
                String premium = insurance.getPremium().toString();
                String signingDate = insurance.getSigningDateString();
                String expiryDate = insurance.getExpiryDateString();
                lines.add("Owner: " + owner + "\n"
                        + "Insured: " + insured + "\n"
                        + "Beneficiary: " + beneficiary + "\n"
                        + "Premium: " + premium + "\n"
                        + "Signing Date: " + signingDate + "\n"
                        + "Expiry Date: " + expiryDate
                );
                lines.add("===========================\n");
                insuranceIndex++;
            }
            lines.add("--------End of " + person.getName().fullName + "'s profile");
            lines.add("\n");
            personIndex++;
        }

        Path file = Paths.get("data/" + fileName + ".txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.fileName));
    }

}
```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
        String commandString = undoRedoStack.peekUndo().toString();
        String feedbackToUser = parseCommand(commandString);
        undoRedoStack.popUndo().undo();
        return new CommandResult(feedbackToUser);
```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
    /**
     * Parses the output command to display the previously undone command
     */
    public static String parseCommand(String commandString) {
        String output = String.format(FULL_MESSAGE_SUCCESS, commandString);
        return output;
    }
```
###### /java/seedu/address/logic/commands/WhyCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;



/**
 * Format full help instructions for every command for display.
 */
public class WhyCommand extends Command {

    public static final String[] COMMAND_WORDS = {"why"};
    public static final String COMMAND_WORD = "why";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tells you why.\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_WHY_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String SHOWING_WHY_MESSAGE = "Because %1$s lives in %2$s";
    public static final String SHOWING_WHY_MESSAGE_2 = "Because %1$s is born in %2$s";
    public static final String SHOWING_WHY_MESSAGE_3 = "Because %1$s's email is %2$s";

    private final Index targetIndex;

    public WhyCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }


        ReadOnlyPerson personToAnswer = lastShownList.get(targetIndex.getZeroBased());
        Name name = personToAnswer.getName();
        Address address = personToAnswer.getAddress();
        String reason = personToAnswer.getReason();
        return new CommandResult(reason);
    }
}
```
###### /java/seedu/address/logic/parser/PrintCommandParser.java
``` java
/**
 * Parses input arguments and identifies the desired filename to return a new PrintCommand
 */
public class PrintCommandParser implements Parser<PrintCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PrintCommand
     * and returns a PrintCommand Object with the specified file name
     * @throws ParseException if the user input does not conform the expected format
     * which requires at a valid string
     */
    public PrintCommand parse(String args) throws ParseException {
        try {
            String filename = ParserUtil.parseFilePath(args);
            return new PrintCommand(filename);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrintCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/parser/WhyCommandParser.java
``` java
package seedu.address.logic.parser;

//import static java.util.Objects.requireNonNull;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.WhyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * WhyCommandParser: Adapted from DeleteCommandParser due to similarities
 */
public class WhyCommandParser implements Parser<WhyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ReasonCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WhyCommand parse(String args) throws ParseException {
        /**
         Parsing
         */
        try {
            Index index = ParserUtil.parseIndex(args);
            return new WhyCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhyCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/UndoRedoStack.java
``` java
    /**
     * Peeks and returns the Command at the top of the Undo Stack.
     */
    public UndoableCommand peekUndo() {
        UndoableCommand toUndo = undoStack.peek();
        return toUndo;
    }
    /**
     * Peeks and returns the command at the top of the Redo Stack.
     */
    public UndoableCommand peekRedo() {
        UndoableCommand toRedo = redoStack.peek();
        return toRedo;
    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    @Override
    public String getReason() {
        Address address = this.getAddress();
        Name name = this.getName();
        Email email = this.getEmail();
        DateOfBirth dob = this.getDateOfBirth();
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(3);
        if (randomInt == 0) {
            this.reason = String.format(SHOWING_WHY_MESSAGE, name, address);
        } else if (randomInt == 1) {
            this.reason = String.format(SHOWING_WHY_MESSAGE_2, name, dob);
        } else if (randomInt == 2) {
            this.reason = String.format(SHOWING_WHY_MESSAGE_3, name, email);
        }
        return reason;
    }
```
###### /java/seedu/address/model/person/ReadOnlyPerson.java
``` java
    default String getAsParagraph() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName() + "\n")
                .append("\nPhone: ")
                .append(getPhone())
                .append("\nEmail: ")
                .append(getEmail())
                .append("\nAddress: ")
                .append(getAddress())
                .append("\nDateOfBirth: ")
                .append(getDateOfBirth())
                .append("\nGender: ")
                .append(getGender())
                .append("\nTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
```
###### /java/seedu/address/model/person/Reason.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's reason for "why" in the address book.
 */
public class Reason {

    public static final String SHOWING_WHY_MESSAGE = "Because %1$s lives in %2$s";
    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Person reason can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Reason(String reason) throws IllegalValueException {
        requireNonNull(reason);
        if (!isValidReason(reason)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.value = reason;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidReason(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && this.value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    public void sortPersons() throws DuplicatePersonException {
        ObservableList<Person> listToSort = FXCollections.observableArrayList(internalList);
        listToSort.sort((ReadOnlyPerson first, ReadOnlyPerson second)-> {
            int x = String.CASE_INSENSITIVE_ORDER.compare(first.getName().fullName, second.getName().fullName);
            if (x == 0) {
                x = (first.getName().fullName).compareTo(second.getName().fullName);
            }
            return x;
        });
        UniquePersonList listToReplace = new UniquePersonList();
        for (ReadOnlyPerson person : listToSort) {
            listToReplace.add(person);
        }
        setPersons(listToReplace);
    }
```
