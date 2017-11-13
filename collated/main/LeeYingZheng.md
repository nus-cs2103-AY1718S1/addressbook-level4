# LeeYingZheng
###### \java\seedu\address\commons\events\ui\ShowFacebookRequestEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the Facebook Log In page.
 */
public class ShowFacebookRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code tagList} contains the {@code word}.
     *   case sensitive and a full word match is required.
     *   <br>examples:<pre>
     *       containsTag(tagList, "abc") == false where tagList does not contain a [abc] tag
     *       containsTag(tagList, "DEF") == true where tagList contains a [DEF] tag
     *       containsTag(tagList, "AB") == false where tagList contains only [ABC] tag
     *       </pre>
     * @param tagList cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsTag(Set<Tag> tagList, String word) {
        requireNonNull(tagList);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        CharSequence space = " ";
        //check if there is more than one tag searched.
        //more than 1 tag searched. split into a list of searches.
        if (preppedWord.contains(space)) {
            String[] separateTags = word.split(" ");
            List<String> tagFilters = Arrays.asList(separateTags);
            for (Tag tag : tagList) {
                if (haveMatchedTags(tagFilters, tag)) {
                    return true;
                }
            }
            return false;
        }
        //only 1 tag searched. Check if tagList contains word as a tag
        try {
            Tag checkTag = new Tag(preppedWord);
            return tagList.contains(checkTag);

        } catch (IllegalValueException e) {
            return false;
        }
    }


    /**
     * Checks if any of the words in tagFilters match with the tag word.
     * Used by containsTag method.
     */
    private static boolean haveMatchedTags(List<String> tagFilters, Tag tag) {
        String encapsulatedTag = tag.toString();
        return tagFilters.contains(encapsulatedTag.substring(1, encapsulatedTag.length() - 1));
    }
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORDVAR_1 = "add";
    public static final String COMMAND_WORDVAR_2 = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Adds a person to the address book. Command is case-insensitive. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_BIRTHDAY + "BIRTHDAY "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_BIRTHDAY + "050595 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney \n"
            + "Example 2: " + COMMAND_WORDVAR_2.toUpperCase() + " "
            + PREFIX_NAME + "Kosaki Yamekuri "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "Yamekos@example.com "
            + PREFIX_ADDRESS + "255, Bedok Ave 2, #03-18 "
            + PREFIX_BIRTHDAY + "121287 "
            + PREFIX_TAG + "colleague "
            + PREFIX_TAG + "lunch-appointment";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_DUPLICATE_FIELD = "This person's %1$s is already in use."
            + "Would you like to continue? YES or NO?";
    public static final String NAME_FIELD = "name";
    public static final String PHONE_FIELD = "phone";
    public static final String ADDRESS_FIELD = "address";
    public static final String EMAIL_FIELD = "email";

    private static boolean isWaitingforReply;
    private CommandResult result;

    private final Person toAdd;
    private String duplicateFields = "";

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddCommand(ReadOnlyPerson person) {
        toAdd = new Person(person);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        //resets AddCommand
        if (isWaitingforReply) {
            isWaitingforReply = false;
        }

        /* Check if the person to add contains any duplicate fields.
         * If so, ReplyCommand to store the AddCommand to wait for further instructions.
         */
        try {
            checkDuplicateField(toAdd);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        /* checkDuplicateField method finds duplicate fields in the list of contacts, informs users and prompts them
         * if they wish to proceed with the add feature. ReplyCommand stores the person toAdd and wait for further
         * instructions.
         */
        if (isWaitingforReply) {
            ReplyCommand.storeAddCommandParameter(toAdd);
            return result;

        } else { //no duplicates found. AddCommand proceeds as normal.
            try {
                model.addPerson(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
            } catch (DuplicatePersonException e) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }
        }
    }

    /**
     * Check for duplicate fields shared with {@code toAdd} in current UniCity contacts. Once duplicate(s) are found,
     * isWaitingforReply is set to true and a message is crafted to prompt users whether they want to continue
     * with the operation.
     */
    private void checkDuplicateField(Person toAdd) throws DuplicatePersonException {
        requireNonNull(model);
        List<ReadOnlyPerson> currentContacts = model.getFilteredPersonList();

        if (currentContacts.contains(toAdd)) {
            throw new DuplicatePersonException();
        }

        for (ReadOnlyPerson contact: currentContacts) {
            if (duplicateFields.contains(NAME_FIELD) && duplicateFields.contains(PHONE_FIELD) &&
                    duplicateFields.contains(EMAIL_FIELD) && duplicateFields.contains(ADDRESS_FIELD)) {
                //method found duplicates for all fields, no further concatenation of message. break from loop.
                break;
            }

            /* The following conditional statements checks if duplicateFields already covers, or have yet to cover, any
             * of the fields. It will keep checking the fields which have not been listed as a duplicate until it hits
             * one or the loop ends.
             */

            if (!duplicateFields.contains(NAME_FIELD)) {
                checkDuplicateName(toAdd, contact);
            }

            if (!duplicateFields.contains(PHONE_FIELD)) {
                checkDuplicatePhone(toAdd, contact);

            }

            if (!duplicateFields.contains(ADDRESS_FIELD)) {
                checkDuplicateAddress(toAdd, contact);
            }

            if (!duplicateFields.contains(EMAIL_FIELD)) {
                checkDuplicateEmail(toAdd, contact);
            }

        }

        result = new CommandResult(String.format(MESSAGE_DUPLICATE_FIELD, duplicateFields.trim()));
    }

    private void checkDuplicateEmail(Person toAdd, ReadOnlyPerson contact) {
        if ((toAdd.getEmail().toString().trim().equalsIgnoreCase(contact.getEmail().toString().trim()))
                && (!toAdd.getEmail().toString().trim().equals(DEFAULT_EMAIL))) {
            isWaitingforReply = true;
            if (duplicateFields.equals("")) {
                duplicateFields = EMAIL_FIELD;
            } else {
                duplicateFields += ", " + EMAIL_FIELD;
            }
        }
    }

    private void checkDuplicateAddress(Person toAdd, ReadOnlyPerson contact) {
        if ((toAdd.getAddress().toString().trim().equalsIgnoreCase(contact.getAddress().toString().trim()))
                && (!toAdd.getAddress().toString().trim().equals(DEFAULT_ADDRESS))) {
            isWaitingforReply = true;
            if (duplicateFields.equals("")) {
                duplicateFields = ADDRESS_FIELD;
            } else {
                duplicateFields += ", " + ADDRESS_FIELD;
            }
        }
    }

    private void checkDuplicatePhone(Person toAdd, ReadOnlyPerson contact) {
        if (toAdd.getPhone().toString().trim().equalsIgnoreCase(contact.getPhone().toString().trim())) {
            isWaitingforReply = true;
            if (duplicateFields.equals("")) {
                duplicateFields = PHONE_FIELD;
            } else {
                duplicateFields += ", " + PHONE_FIELD;
            }

        }
    }

    private void checkDuplicateName(Person toAdd, ReadOnlyPerson contact) {
        if (toAdd.getName().toString().trim().equalsIgnoreCase(contact.getName().toString().trim())) {
            isWaitingforReply = true;
            if (duplicateFields.equals("")) {
                duplicateFields = NAME_FIELD;
            } else {
                duplicateFields += ", " + NAME_FIELD;
            }
        }
    }

    public static boolean isWaitingforReply() { return isWaitingforReply; }

    public static void reply() {
        isWaitingforReply = false;
    }

```
###### \java\seedu\address\logic\commands\ClearCommand.java
``` java
    public static final String COMMAND_WORDVAR_1 = "clear";
    public static final String COMMAND_WORDVAR_2 = "c";
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    public static final String COMMAND_WORDVAR_1 = "delete";
    public static final String COMMAND_WORDVAR_2 = "d";
```
###### \java\seedu\address\logic\commands\FacebookCommand.java
``` java
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowFacebookRequestEvent;

/**
 * Displays Facebook Log In page.
 */
public class FacebookCommand extends Command {



    public static final String COMMAND_WORDVAR_1 = "facebook";
    public static final String COMMAND_WORDVAR_2 = "fb";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + ": Shows facebook log in page.\n"
            + "Example: "
            + COMMAND_WORDVAR_1;

    public static final String SHOWING_FACEBOOK_MESSAGE = "Opened facebook window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowFacebookRequestEvent());
        return new CommandResult(SHOWING_FACEBOOK_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Filters and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORDVAR = "filter";


    public static final String MESSAGE_USAGE = COMMAND_WORDVAR
            + ": Filters all persons whose tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers."
            + " Command is case-insensitive. \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example 1: " + COMMAND_WORDVAR + " friend colleague \n";

    private final TagContainsKeywordsPredicate predicate;

    public FilterCommand(TagContainsKeywordsPredicate predicate) {
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
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String COMMAND_WORDVAR_1 = "find";
    public static final String COMMAND_WORDVAR_2 = "f";
```
###### \java\seedu\address\logic\commands\HistoryCommand.java
``` java
    public static final String COMMAND_WORDVAR_1 = "history";
    public static final String COMMAND_WORDVAR_2 = "h";
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
    public static final String COMMAND_WORDVAR_1 = "list";
    public static final String COMMAND_WORDVAR_2 = "l";
```
###### \java\seedu\address\logic\commands\RedoCommand.java
``` java
    public static final String COMMAND_WORDVAR_1 = "redo";
    public static final String COMMAND_WORDVAR_2 = "r";
```
###### \java\seedu\address\logic\commands\ReplyCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.AddCommand.MESSAGE_SUCCESS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Replies prompt of duplicate fields from AddCommand and EditCommand.
 */
public class ReplyCommand extends Command {

    public static final String COMMAND_WORDVAR_YES = "yes";
    public static final String COMMAND_WORDVAR_NO = "no";
    public static final String MESSAGE_COMMAND_ROLLBACK = "Command not executed.";
    public static final String MESSAGE_COMMAND_INVALID = "No command to confirm execution.";
    private static final String MESSAGE_COMMAND_MISHANDLED = "Command handled inappropriately!";

    private static Person storedPerson;

    private String toReply;

    /**
     * Creates an ReplyCommand to to reply {@code String} to AddCommand/EditCommand Prompt
     */
    public ReplyCommand(String reply) {
        toReply = reply;
    }

    /**
     * Executes ReplyCommand.
     */
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        if (AddCommand.isWaitingforReply()) {
            return handleAddCommand();
        } else {
            return new CommandResult(MESSAGE_COMMAND_INVALID);
        }
    }

    /**
     * Handle replies to AddCommand prompts
     */
    private CommandResult handleAddCommand() throws CommandException {

        if (toReply.equalsIgnoreCase(COMMAND_WORDVAR_YES)) {

            AddCommand.reply();
            try {
                model.addPerson(storedPerson);
                return new CommandResult(String.format(MESSAGE_SUCCESS, storedPerson));
            } catch (DuplicatePersonException e) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

        } else {
            AddCommand.reply();
            return new CommandResult(MESSAGE_COMMAND_ROLLBACK);
        }
    }

    /**
     * Stores person to add.
     */
    public static void storeAddCommandParameter(Person person) {
        storedPerson = person;
    }

}
```
###### \java\seedu\address\logic\commands\SelectCommand.java
``` java
    public static final String COMMAND_WORDVAR_1 = "select";
    public static final String COMMAND_WORDVAR_2 = "s";
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
    /**
     * Parses user input into command for execution.
     * Logic editted: User can type abbreviated and case-insensitive commands. Comment to check travis test.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        if (commandWord.equalsIgnoreCase(AddCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(AddCommand.COMMAND_WORDVAR_2)) {
            return new AddCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(RemoveTagCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(RemoveTagCommand.COMMAND_WORDVAR_2)) {
            return new RemoveTagCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(AddTagCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(AddTagCommand.COMMAND_WORDVAR_2)) {
            return new AddTagCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(EditCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(EditCommand.COMMAND_WORDVAR_2)) {
            return new EditCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(SelectCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(SelectCommand.COMMAND_WORDVAR_2)) {
            return new SelectCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(FacebookCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(FacebookCommand.COMMAND_WORDVAR_2)) {
            return new FacebookCommand();

        } else if (commandWord.equalsIgnoreCase(DeleteCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(DeleteCommand.COMMAND_WORDVAR_2)) {
            return new DeleteCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(ClearCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(ClearCommand.COMMAND_WORDVAR_2)) {
            return new ClearCommand();

        } else if (commandWord.equalsIgnoreCase(FilterCommand.COMMAND_WORDVAR)) {
            return new FilterCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(FindCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(FindCommand.COMMAND_WORDVAR_2)) {
            return new FindCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(ListCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(ListCommand.COMMAND_WORDVAR_2)) {
            return new ListCommand();

        } else if (commandWord.equalsIgnoreCase(LocateCommand.COMMAND_WORDVAR)) {
            return new LocateCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(HistoryCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(HistoryCommand.COMMAND_WORDVAR_2)) {
            return new HistoryCommand();

        } else if (commandWord.equalsIgnoreCase(ExitCommand.COMMAND_WORD)) {
            return new ExitCommand();

        } else if (commandWord.equalsIgnoreCase(HelpCommand.COMMAND_WORD)) {
            return new HelpCommand();

        } else if (commandWord.equalsIgnoreCase(UndoCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(UndoCommand.COMMAND_WORDVAR_2)) {
            return new UndoCommand();

        } else if (commandWord.equalsIgnoreCase(UndoCommand.COMMAND_WORDVAR_3)) {
            return new UndoCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(RedoCommand.COMMAND_WORDVAR_1)
                || commandWord.equalsIgnoreCase(RedoCommand.COMMAND_WORDVAR_2)) {
            return new RedoCommand();

        } else if (commandWord.equalsIgnoreCase(RedoCommand.COMMAND_WORDVAR_3)) {
            return new RedoCommandParser().parse(arguments);

        } else if (commandWord.equalsIgnoreCase(ReplyCommand.COMMAND_WORDVAR_YES)
                || commandWord.equalsIgnoreCase(ReplyCommand.COMMAND_WORDVAR_NO)) {
            return new ReplyCommand(commandWord);

```
###### \java\seedu\address\logic\parser\FilterCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FilterCommand(new TagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\model\tag\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsTag(person.getTags(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    //different search pages for different commands
    //GoogleCommand
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";

    //FacebookCommand
    public static final String FACEBOOK_URL = "https://www.facebook.com";
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    public void loadFacebookPage() {
        loadPage(FACEBOOK_URL);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the Facebook window in BrowserPanel.
     */
    @FXML
    public void handleFacebook() {
        browserPanel.loadFacebookPage();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowFacebookEvent(ShowFacebookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleFacebook();
    }
}
```
