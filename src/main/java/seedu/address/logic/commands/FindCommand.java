package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Website;
import seedu.address.model.tag.Tag;


/**
 * Finds and lists all persons in address book whose fields matches all of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_SHORT = "f";
    public static final String COMMAND_ALIAS = "summon";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Find a list of person that satisfies "
        + "all the characteristic provided.\n"
        + "Parameters: "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_PHONE + "PHONE] "
        + "[" + PREFIX_EMAIL + "EMAIL] "
        + "[" + PREFIX_ADDRESS + "ADDRESS] "
        + "[" + PREFIX_WEBSITE + "WEBSITE] "
        + "[" + PREFIX_BIRTHDAY + "DD/MM/YYYY] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_NO_FIELD_PROVIDED = "At least one field to find must be provided.";

    //@@author chrisboo
    private FindPersonDescriptor findPersonDescriptor;

    public FindCommand(FindPersonDescriptor findPersonDescriptor) {
        requireNonNull(findPersonDescriptor);

        this.findPersonDescriptor = new FindPersonDescriptor(findPersonDescriptor);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(person -> findPersonDescriptor.match(person));
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCommand // instanceof handles nulls
            && this.findPersonDescriptor.equals(((FindCommand) other).findPersonDescriptor)); // state check
    }

    /**
     * Stores the details to find of the person.
     */
    public static class FindPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Birthday birthday;
        private Website website;
        private Set<Tag> tags;

        public FindPersonDescriptor() {
        }

        public FindPersonDescriptor(FindPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.birthday = toCopy.birthday;
            this.website = toCopy.website;
            this.tags = toCopy.tags;
        }

        /**
         * @return false if no fields are provided
         */
        public boolean allNull() {
            return this.name == null
                && this.phone == null
                && this.email == null
                && this.address == null
                && this.birthday == null
                && this.website == null
                && this.tags == null;
        }

        /**
         * @param other to check
         * @return true if other matches all fields
         */
        public boolean match(Object other) {
            if (!(other instanceof Person)) {
                return false;
            }
            if (this.name != null && !match(this.name, ((Person) other).getName())) {
                return false;
            }
            if (this.phone != null && !match(this.phone, ((Person) other).getPhone())) {
                return false;
            }
            if (this.email != null && !this.email.equals(((Person) other).getEmail())) {
                return false;
            }
            if (this.address != null && !this.address.equals(((Person) other).getAddress())) {
                return false;
            }
            if (this.website != null && !this.website.equals(((Person) other).getWebsite())) {
                return false;
            }
            if (this.birthday != null && !this.birthday.equals(((Person) other).getBirthday())) {
                return false;
            }
            if (this.tags != null && !match(this.tags, ((Person) other).getTags())) {
                return false;
            }

            return true;
        }

        /**
         * @param predicate
         * @param test
         * @return true if test contains predicate
         */
        private boolean match(Name predicate, Name test) {
            if (predicate == test) {
                return true;
            }
            if (predicate == null || test == null) {
                return false;
            }

            String[] splitPredicate = predicate.toString().toUpperCase().split("\\s+");

            for (String keyword : splitPredicate) {
                if (keyword.equals("")) {
                    continue;
                }
                if (test.toString().toUpperCase().contains(keyword)) {
                    return true;
                }
            }

            return false;
        }

        /**
         *
         * @param predicate
         * @param test
         * @return true if predicate is a substring of test
         */
        private boolean match(Phone predicate, Phone test) {
            if (predicate == test) {
                return true;
            }
            if (predicate == null || test == null) {
                return false;
            }

            return test.toString().contains(predicate.toString());
        }

        /**
         * @param predicate
         * @param test
         * @return true if test contains a tag that is among the predicate
         */
        private boolean match(Set<Tag> predicate, Set<Tag> test) {
            for (Tag predicateTag : predicate) {
                for (Tag testTag : test) {
                    if (predicateTag.equals(testTag)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            if (phone.value != null) {
                this.phone = phone;
            }
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            if (email.value != null) {
                this.email = email;
            }
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            if (address.value != null) {
                this.address = address;
            }
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setBirthday(Birthday birthday) {
            if (birthday.value != null) {
                this.birthday = birthday;
            }
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }

        public void setWebsite(Website website) {
            if (website.value != null) {
                this.website = website;
            }
        }

        public Optional<Website> getWebsite() {
            return Optional.ofNullable(website);
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
            if (!(other instanceof FindPersonDescriptor)) {
                return false;
            }

            // state check
            FindPersonDescriptor e = (FindPersonDescriptor) other;

            return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getBirthday().equals(e.getBirthday())
                && getWebsite().equals(e.getWebsite())
                && getTags().equals(e.getTags());
        }
    }
    //@@author
}
