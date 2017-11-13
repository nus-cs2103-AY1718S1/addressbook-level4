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
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
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
     * Stores the details of the finder.
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
         * Check {@code Person} in DeathNote against {@code this} finder target.
         *
         * @param person in DeathNote to check
         * @return true if other all fields matches
         */
        public boolean match(ReadOnlyPerson person) {
            if (this.name != null && !matchName(this.name, person.getName())) {
                return false;
            }
            if (this.phone != null && !matchPhone(this.phone, person.getPhone())) {
                return false;
            }
            if (this.email != null && !this.email.equals(person.getEmail())) {
                return false;
            }
            if (this.address != null && !this.address.equals(person.getAddress())) {
                return false;
            }
            if (this.website != null && !this.website.equals(person.getWebsite())) {
                return false;
            }
            if (this.birthday != null && !this.birthday.equals(person.getBirthday())) {
                return false;
            }
            if (this.tags != null && !matchTag(this.tags, person.getTags())) {
                return false;
            }

            return true;
        }

        /**
         * Returns true only if the person's name {@code personName} have all the words in {@code finderName}.
         *
         * @param finderName Name specified by the user to be found
         * @param personName Name of a person in DeathNote to be matched
         * @return a boolean value to check whether {@code personName} specify the requirement
         */
        private boolean matchName(Name finderName, Name personName) {
            if (finderName == personName) {
                return true;
            }

            String personNameStr = personName.toString().toUpperCase();

            for (String word : finderName.toString().toUpperCase().split("\\s+")) {
                if (word.equals("")) { continue; }
                if (!personNameStr.contains(word)) {
                    return false;
                }
            }

            return true;
        }

        /**
         * Returns true only if the person's phone {@code personPhone} is a substring of {@code finderPhone}.
         *
         * @param finderPhone Phone specified by the user to be found
         * @param personPhone Phone of a person in DeathNote to be matched
         * @return a boolean value to check whether {@code personPhone} specify the requirement
         */
        private boolean matchPhone(Phone finderPhone, Phone personPhone) {
            return personPhone.toString().contains(finderPhone.toString());
        }

        /**
         * Returns true only if the person's tag {@code personTags} have all the tags specified in {@code finderTags}.
         *
         * @param finderTags Tags specified by the user to be found
         * @param personTags Tags of a person in DeathNote to be matched
         * @return a boolean value to check whether {@code personTags} specify the requirement
         */
        private boolean matchTag(Set<Tag> finderTags, Set<Tag> personTags) {
            for (Tag finderTag : finderTags) {
                if (!personTags.contains(finderTag)) {
                    return false;
                }
            }
            return true;
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
