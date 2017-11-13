package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.commands.DisplayListFilteredEvent;
import seedu.address.model.person.ContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name/address/email/phone, depending on the main keyword,
 * contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Finds all persons whose names/addresses/emails/phones/tags, depending on the main keyword, "
            + "contain any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers. Also able to find birthday month.\n"
            + "Parameters: MAIN_KEYWORD KEYWORD[MORE_KEYWORDS]...\n"
            + "MAIN_KEYWORD: [name] / [address] / [email] / [phone] / [birthday] / [tag] / [org]\n"
            + "Example: " + COMMAND_WORD + " name " + "alice bob charlie\n"
            + "Example: " + COMMAND_WORD + " address " + "Blk 20 Lorong 1 Serangoon Gardens\n"
            + "Example: " + COMMAND_WORD + " email " + "example1 example2\n"
            + "Example: " + COMMAND_WORD + " phone " + "123456789 987654321\n"
            + "Example: " + COMMAND_WORD + " org " + "Apple Inc.";

    public static final String KEYWORD_NAME = "name";
    public static final String KEYWORD_ADDRESS = "address";
    public static final String KEYWORD_EMAIL = "email";
    public static final String KEYWORD_PHONE = "phone";
    public static final String KEYWORD_BIRTHDAY = "birthday";
    public static final String KEYWORD_TAG = "tag";
    public static final String KEYWORD_ORGANISATION = "org";
    public static final String MESSAGE_NO_KEYWORD = "At least one keyword must be provided.";
    public static final String MESSAGE_INVALID_BIRTHDAY_MONTH = "Birthday month provided must be in the format 'mm'. "
            + "i.e. 01 - 12";

    private final ContainsKeywordsPredicate predicate;

    public FindCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new DisplayListFilteredEvent());
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
