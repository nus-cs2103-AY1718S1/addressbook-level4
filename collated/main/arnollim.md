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
        String feedbackToUser = COMMAND_WORD + " " + index.getOneBased();
        return feedbackToUser;
    }
```
###### /java/seedu/address/logic/commands/PrintCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_FILEPATH;

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
            + "file can then be found in the in data/ folder as data/filename.txt\n"
            + MESSAGE_INVALID_FILEPATH;
    public static final String MESSAGE_SUCCESS = "Address Book has been saved!\n"
            + "Find your Address Book in the %1$s.txt file you created "
            + "in the same directory as the application file path!";

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

        //First line in the .txt file is the time and date printed, so that the user will know the recency
        //of the printed address book
        lines.add("LISA was last updated on: " + timeStamp + "\n\n");
        //Next, feedback the total number of contacts at said time and date
        lines.add("There are " + lastShownList.size() + " contacts in LISA\n\n");

        int personIndex = 1;

        //iterating through each person in LISA
        for (ReadOnlyPerson person: lastShownList) {
            String entry = personIndex + ". " + person.getAsParagraph();
            lines.add(entry);
            lines.add("\n" + person.getName().fullName
                    + " is a personnel involved in the following insurance policies:\n");

            UniqueLifeInsuranceList insurances = person.getLifeInsurances();
            int insuranceIndex = 1;

            //within each person, iterate through all the insurance policies associated
            //with this person
            for (ReadOnlyInsurance insurance: insurances) {
                String insuranceHeader = "Insurance Policy " + insuranceIndex
                        + ": " + insurance.getInsuranceName() + " ======";
                lines.add(insuranceHeader);
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

                //insuranceEnd is just a printed line "=====" which ties with the length
                //of insuranceHeader to make the txt file more organised.
                String insuranceEnd = "";
                int headerLength = insuranceHeader.length();
                for (int i = 1; i <= headerLength; i++) {
                    insuranceEnd = insuranceEnd + "=";
                }
                lines.add(insuranceEnd);

                lines.add("\n");
                insuranceIndex++;
            }
            lines.add("--------End of " + person.getName().fullName + "'s profile");
            lines.add("\n");
            personIndex++;
        }

        Path file = Paths.get(fileName + ".txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        String feedbackToUser = String.format(MESSAGE_SUCCESS, this.fileName);
        return new CommandResult(feedbackToUser);
    }

}
```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
        String commandString = undoRedoStack.peekUndo().toString();
        String feedbackToUser = parseUndoCommand(commandString);
        undoRedoStack.popUndo().undo();

        return new CommandResult(feedbackToUser);
```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
    /**
     * Parses the output command to display the previously undone command
     */
    public static String parseUndoCommand(String commandString) {
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
import seedu.address.model.person.ReadOnlyPerson;


/**
 * Format the feedback to the user when asked "why" about a certain person index.
 */
public class WhyCommand extends Command {

    public static final String[] COMMAND_WORDS = {"why"};
    public static final String COMMAND_WORD = "why";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tells you why.\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String SHOWING_WHY_MESSAGE = "Because %1$s lives in %2$s";
    public static final String SHOWING_WHY_MESSAGE_2 = "Because %1$s is born in %2$s";
    public static final String SHOWING_WHY_MESSAGE_3 = "Because %1$s's email is %2$s";
    public static final String SHOWING_WHY_MESSAGE_4 = "Because %1$s appreciates you";
    public static final String SHOWING_WHY_MESSAGE_5 = "Because %1$s is a really cool person";
    public static final String SHOWING_WHY_MESSAGE_6 = "Because %1$s can make the world a better place";
    public static final String SHOWING_WHY_MESSAGE_NO_ADDRESS = "Because %1$s has no address";
    public static final String SHOWING_WHY_MESSAGE_NO_DOB = "Because %1$s has no date of birth";
    public static final String SHOWING_WHY_MESSAGE_NO_EMAIL = "Because %1$s has no email";

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
        String reason = personToAnswer.getReason();
        return new CommandResult(reason);
    }
}
```
###### /java/seedu/address/logic/parser/PrintCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_FILEPATH;

import java.nio.file.InvalidPathException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PrintCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and identifies the desired filename to return a new PrintCommand
 */
public class PrintCommandParser implements Parser<PrintCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PrintCommand
     * and returns a PrintCommand Object with the specified file name
     * @throws ParseException if the user input does not conform the expected format
     * which requires at a valid string
     * @throws ParseException if the user input includes illegal filepath characters
     */
    public PrintCommand parse(String args) throws ParseException {
        try {
            String filename = ParserUtil.parseFilePath(args);
            return new PrintCommand(filename);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrintCommand.MESSAGE_USAGE));
        } catch (InvalidPathException ipe) {
            throw new ParseException (
                    String.format(MESSAGE_INVALID_FILEPATH, PrintCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/parser/WhyCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.WhyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * WhyCommandParser: Parses the User input into a valid Why Command
 */
public class WhyCommandParser implements Parser<WhyCommand> {
    /**
     * Parses the given {@code Index} of arguments in the context of the WhyCommnad
     * and returns a WhyCommand Object with the specified index.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WhyCommand parse(String args) throws ParseException {
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
        int randomInt = randomGenerator.nextInt(6);
        /**
         * There are 6 easter egg messages that can be returned as feedback to the user. Which message is returned is
         * randomly generated as decided by randomInt.
         */
        if (randomInt == 0) {

            if (address.toString() == "") {
                this.reason = String.format(SHOWING_WHY_MESSAGE_NO_ADDRESS, name);
            } else {
                this.reason = String.format(SHOWING_WHY_MESSAGE, name, address);
            }

        } else if (randomInt == 1) {

            if (dob.toString() == "") {
                this.reason = String.format(SHOWING_WHY_MESSAGE_NO_DOB, name);
            } else {
                this.reason = String.format(SHOWING_WHY_MESSAGE_2, name, dob);
            }

        } else if (randomInt == 2) {
            if (email.value == "") {
                this.reason = String.format(SHOWING_WHY_MESSAGE_NO_EMAIL, name);
            } else {
                this.reason = String.format(SHOWING_WHY_MESSAGE_3, name, email);
            }

        } else if (randomInt == 3) {
            this.reason = String.format(SHOWING_WHY_MESSAGE_4, name);
        } else if (randomInt == 4) {
            this.reason = String.format(SHOWING_WHY_MESSAGE_5, name);
        } else if (randomInt == 5) {
            this.reason = String.format(SHOWING_WHY_MESSAGE_6, name);
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
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
        sortPersons();
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
        replacement.sortPersons();
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorts the internal list of people in alphabetical order
     * rather than in order of date added. This is more useful to the user especially when scrolling
     * the addressbook manually to search for contacts
     */
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
