package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.function.Predicate;

import seedu.address.model.person.AddressContainsKeywordsSubstrPredicate;
import seedu.address.model.person.EmailContainsKeywordsSubstrPredicate;
import seedu.address.model.person.NameContainsKeywordsSubstrPredicate;
import seedu.address.model.person.PhoneContainsKeywordsSubstrPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Finds and lists all persons in address book whose contact fields contain any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class MultiFilterCommand extends Command {
    public static final String COMMAND_WORD = "multifilter";
    public static final String COMMAND_WORD_ALIAS = "mf";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons whose names, email, address, contact or tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John "
            + PREFIX_PHONE + "999 "
            + " finds a contact with name \"John\" and has a contact that contains the string \"999\"";

    private NameContainsKeywordsSubstrPredicate namePredicate;
    private PhoneContainsKeywordsSubstrPredicate phonePredicate;
    private EmailContainsKeywordsSubstrPredicate emailPredicate;
    private AddressContainsKeywordsSubstrPredicate addressPredicate;

    public MultiFilterCommand(
            NameContainsKeywordsSubstrPredicate namePredicate,
            PhoneContainsKeywordsSubstrPredicate phonePredicate,
            EmailContainsKeywordsSubstrPredicate emailPredicate,
            AddressContainsKeywordsSubstrPredicate addressPredicate) {
        this.namePredicate = namePredicate;
        this.phonePredicate = phonePredicate;
        this.emailPredicate = emailPredicate;
        this.addressPredicate = addressPredicate;
    }

    @Override
    public CommandResult execute() {
        // Clear the filter conditions on the filtered person list
        model.clearFiltersOnPersonList();

        // Update filtered person list with multiple criteria
        model.updateFilteredPersonList(new Predicate<ReadOnlyPerson>() {
            @Override
            /*
             * Converts the predicate to each field to a tautology if it was entered by the user
             * i.e. if a field is blank, it is equivalent to not being used in filtering persons
             */
            public boolean test(ReadOnlyPerson readOnlyPerson) {
                return (namePredicate == null || namePredicate.test(readOnlyPerson))
                        && (phonePredicate == null || phonePredicate.test(readOnlyPerson))
                        && (emailPredicate == null || emailPredicate.test(readOnlyPerson))
                        && (addressPredicate == null || addressPredicate.test(readOnlyPerson));
            }
        });

        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MultiFilterCommand)) {
            return false;
        }
        assert(this.namePredicate != null && other != null
                || this.namePredicate == null && ((MultiFilterCommand) other).namePredicate == null);
        assert(this.phonePredicate != null && other != null
                || this.phonePredicate == null && ((MultiFilterCommand) other).phonePredicate == null);
        assert(this.emailPredicate != null && other != null
                || this.emailPredicate == null && ((MultiFilterCommand) other).emailPredicate == null);
        assert(this.addressPredicate != null && other != null
                || this.addressPredicate == null && ((MultiFilterCommand) other).addressPredicate == null);

        return other == this // short circuit if same object
                || ((this.namePredicate == null
                || this.namePredicate.equals(((MultiFilterCommand) other).namePredicate)) // state check
                && (this.phonePredicate == null // short circuit if null
                || this.phonePredicate.equals(((MultiFilterCommand) other).phonePredicate)) // state check
                && (this.emailPredicate == null // short circuit if null
                || this.emailPredicate.equals(((MultiFilterCommand) other).emailPredicate)) // state check
                && (this.addressPredicate == null // short circuit if null
                || this.addressPredicate.equals(((MultiFilterCommand) other).addressPredicate))); // state check
    }
}
