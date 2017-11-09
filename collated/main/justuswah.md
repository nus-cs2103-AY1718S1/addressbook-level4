# justuswah
###### \java\seedu\address\logic\commands\RemoveTagCommand.java
``` java
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified tag from all contacts in the AddressBook\n"
            + "Parameters: (TAG_NAME) \n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Tag Removed";

    private final Tag toRemove;

    public RemoveTagCommand(Tag toRemove) {
        this.toRemove = toRemove;
    }

    /**Executes the RemoveTag Method
     *
     *
     */

    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        if (!model.getAddressBook().getTagList().contains(toRemove)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG);
        } else {
            model.removeTag(toRemove);
            return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS));
        }
    }


}
```
###### \java\seedu\address\logic\commands\SmsCommand.java
``` java
public class SmsCommand extends Command {

    public static final String COMMAND_WORD = "sms";
    public static final String ACCOUNT_SID = "ACed7baf2459e41d773a5f9c2232d4d975";
    public static final String AUTH_TOKEN = "6a26cc5c91ff355ebf48fe019700920b";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sends a phone SMS message to the phone number of the contact identified by the index number\n"
            + "Parameters: INDEX (must be a positive integer), MESSAGE (can be string of any length)\n"
            + "Example: " + COMMAND_WORD + " 1" + " text/hello there";

    private final Index targetIndex;
    private String text = null;

    public SmsCommand(Index targetIndex, String text) {
        this.targetIndex = targetIndex;
        this.text = text;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            return new CommandResult(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        String receivingNumber = lastShownList.get(targetIndex.getZeroBased()).getPhone().toString();

        try {

            Message message = Message
                    .creator(new PhoneNumber("+65" + receivingNumber), new PhoneNumber("+12082157763"), text)
                    .create();
        } catch (ApiException ae) {
            return new CommandResult(MESSAGE_SMS_NUMBER_UNAUTHORIZED);
        }

        return new CommandResult(MESSAGE_SMS_PERSON_SUCCESS);

    }

}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new AnyParticularContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParser.java
``` java
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        try {
            Tag toRemove = new Tag (args);
            return new RemoveTagCommand(toRemove);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\SmsCommandParser.java
``` java
public class SmsCommandParser implements Parser<SmsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SMSCommand
     * and returns an SMSCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SmsCommand parse(String args) throws ParseException {
        Index index;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SMS_TEXT);

        try {
            index = ParserUtil.parseIndex(firstWord(args));

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
        }

        String text = argMultimap.getValue(PREFIX_SMS_TEXT).toString();
        return new SmsCommand(index, text);

    }

    public static String firstWord(String input) {
        return input.split(" ")[1]; // Create array of words and return the 1st word
    }
}



```
