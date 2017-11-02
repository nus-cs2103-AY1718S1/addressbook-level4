package seedu.address.logic.commands;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.Optional;
import java.util.Set;

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose fields contain any of "
            + "the specified keywords (tags are case sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: PREFIX/KEYWORD [PREFIX/MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_PHONE + "91234567 "
            + "[" + PREFIX_EMAIL + "johndoe@example.com" + "]"
            + "[" + PREFIX_NAME + "alice bob charlie" + "]"
            + "[" + PREFIX_TAG + "owesMoney" + "]";

    private final NameContainsKeywordsPredicate Namepredicate;
    private final EmailContainsKeywordsPredicate Emailpredicate;


    public FindCommand(FindPersonDescriptor personDescriptor) {
//        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }

//    public FindCommand parse(String args) throws ParseException {
//        String trimmedArgs = args.trim();
//        if (trimmedArgs.isEmpty()) {
//            throw new ParseException(
//                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
//        }
//
//        String[] nameKeywords = trimmedArgs.split("\\s+");
//
//        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
//    }

    public static class FindPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Mrt mrt;
        private Set<Tag> tags;

        public FindPersonDescriptor() {
        }

        public FindPersonDescriptor(FindCommand.FindPersonDescriptor personDescriptor) {
            this.name = personDescriptor.name;
            this.phone = personDescriptor.phone;
            this.email = personDescriptor.email;
            this.address = personDescriptor.address;
            this.mrt = personDescriptor.mrt;
            this.tags = personDescriptor.tags;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setMrt(Mrt mrt) {
            this.mrt = mrt;
        }

        public Optional<Mrt> getMrt() {
            return Optional.ofNullable(mrt);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FindCommand.FindPersonDescriptor)) {
                return false;
            }

            // state check
            FindCommand.FindPersonDescriptor e = (FindCommand.FindPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getMrt().equals(e.getMrt())
                    && getTags().equals(e.getTags());
        }
    }
}
