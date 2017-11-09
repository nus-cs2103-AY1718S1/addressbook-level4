# dennaloh
###### /java/seedu/address/logic/commands/person/EmailCommand.java
``` java
/**
 * Emails a contact from the address book.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "em";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails the person identified "
            + "by the index number used in the last person listing. Needs Outlook or Apple Mail as default app.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Opened email to %1$s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the person in the filtered person list to email
     */
    public EmailCommand (Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Desktop desktop;
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEmail = lastShownList.get(targetIndex.getZeroBased());
        String email = personToEmail.getEmail().getValue();

        try {
            if (Desktop.isDesktopSupported()
                    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                URI mailto = new URI("mailto:" + email);
                desktop.mail(mailto);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEmail));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/person/FindTagCommand.java
``` java
/**
 * Finds and lists all persons in address book who has tags which contains any of the argument keywords.
 * Keyword matching is not case sensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_ALIAS = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who has tags which contains any of "
            + "the specified keywords (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends neighbours ";

    private final TagContainsKeywordsPredicate predicate;

    public FindTagCommand(TagContainsKeywordsPredicate predicate) {
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
###### /java/seedu/address/logic/commands/person/GMapCommand.java
``` java
/**
 * Opens Google Maps in browser with address of person identified using it's last displayed index from the address book
 * as the destination.
 */
public class GMapCommand extends Command {

    public static final String COMMAND_WORD = "gmap";
    public static final String COMMAND_ALIAS = "gm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens Google Maps in default browser with the address "
            + "of the person identified by the index number used in the last person listing being the Destination.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Opened Google Maps to get to %1$s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the person in the filtered person list to get directions to
     */
    public GMapCommand (Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Desktop desktop = Desktop.getDesktop();
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToGetDirectionsTo = lastShownList.get(targetIndex.getZeroBased());

        String url = model.getGMapUrl(personToGetDirectionsTo);

        try {
            if (Desktop.isDesktopSupported()) {
                URI googleMaps = new URI(url);
                desktop.browse(googleMaps);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToGetDirectionsTo));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GMapCommand // instanceof handles nulls
                && this.targetIndex.equals(((GMapCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case FindTagCommand.COMMAND_WORD:
        case FindTagCommand.COMMAND_ALIAS:
            return new FindTagCommandParser().parse(arguments);

        case EmailCommand.COMMAND_WORD:
        case EmailCommand.COMMAND_ALIAS:
            return new EmailCommandParser().parse(arguments);

        case GMapCommand.COMMAND_WORD:
        case GMapCommand.COMMAND_ALIAS:
            return new GMapCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/person/EmailCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EmailCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
        }
    }
}
```
###### /java/seedu/address/logic/parser/person/FindTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns an FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
```
###### /java/seedu/address/logic/parser/person/GMapCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class GMapCommandParser implements Parser<GMapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GMapCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new GMapCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
        }
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Returns URL for google maps using the person's address
     * @param key
     * @return URL
     */
    public String getUrl (ReadOnlyPerson key) {

        String address = key.getAddress().toString();
        String replacedAddress = address.replaceAll(" ", "+");
        StringBuilder sb = new StringBuilder();
        sb.append("http://maps.google.com/maps?saddr=");
        sb.append("&daddr=");
        sb.append(replacedAddress);
        String url = sb.toString();

        return url;
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /** Gets URL for google maps. */
    String getGMapUrl(ReadOnlyPerson target);
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized String getGMapUrl(ReadOnlyPerson target) {
        String url = addressBook.getUrl(target);
        return url;
    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    /**
     * Returns the set of tags joined into a string
     * @return
     */
    public String joinTagsToString() {
        Set<Tag> tags = getTags();
        StringBuilder sb = new StringBuilder();
        for (Tag t : tags) {
            sb.append(t.tagName);
            sb.append(" ");
        }
        return sb.toString();
    }
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorts the persons based on name
     *
     */
    public void sortPersons() {
        internalList.sort((e1, e2) -> e1.getName().toString().compareToIgnoreCase(e2.getName().toString()));
    }
```
###### /java/seedu/address/model/property/TagContainsKeywordsPredicate.java
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
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.joinTagsToString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
