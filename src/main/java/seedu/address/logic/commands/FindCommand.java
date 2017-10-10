package seedu.address.logic.commands;

import static seedu.address.logic.parser.optionparser.CommandOptionUtil.PREFIX_OPTION_INDICATOR;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    /* Mode prefix definitions */
    public static final String PREFIX_FIND_IN_DETAIL = PREFIX_OPTION_INDICATOR + "d";
    public static final String PREFIX_FIND_FUZZY_FIND = PREFIX_OPTION_INDICATOR + "u";
    public static final String PREFIX_FIND_BY_NAME = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are matched\n"
            + "Parameters: KEYWORD [OPTION] ARGUMENTS...\n"
            + "\tDefault: " + COMMAND_WORD + " ARGUMENT [ARGUMENTS]\n"
            + "\t\tFinds all persons whose names contain any of the specified keywords (case-sensitive)"
            + "\t\tand displays them as a list with index numbers.\n"
            + "\t\tExample: " + COMMAND_WORD + " alice bob charlie\n"
            + "\tOptions:\n"
            + "\t  " + PREFIX_FIND_IN_DETAIL + " [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAGS]\n"
            + "\t\tFinds all persons who match the given details\n"
            + "\t\tExample: " + COMMAND_WORD + " " + PREFIX_FIND_IN_DETAIL + " n/Bob p/999 t/friend t/classmate\n"
            + "\t  " + PREFIX_FIND_FUZZY_FIND + " ARGUMENT\n"
            + "\t\tFuzzy search for people whose details contain the argument\n"
            + "\t\tExample: " + COMMAND_WORD + " " + PREFIX_FIND_FUZZY_FIND + " @u.nus.edu";


    private final Predicate<ReadOnlyPerson> predicate;

    public FindCommand(Predicate<ReadOnlyPerson> predicate) {
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
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }

    /**
     * Stores the details to find the person with. Each non-empty field value will be used to compare
     * to contacts in the list.
     */
    public static class FindDetailDescriptor {
        private String name;
        private String phone;
        private String email;
        private String address;
        private Set<Tag> tags = new HashSet<>();

        public FindDetailDescriptor() {
        }

        public FindDetailDescriptor(FindDetailDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.tags = toCopy.tags;
        }

        /**
         * @return true if any attribute not null.
         */
        public boolean isValidDescriptor() {
            return getName().isPresent()
                || getPhone().isPresent()
                || getEmail().isPresent()
                || getAddress().isPresent()
                || !tags.isEmpty();
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Optional<String> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Optional<String> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FindDetailDescriptor)) {
                return false;
            }

            // state check
            FindDetailDescriptor e = (FindDetailDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }

        @Override
        public String toString() {
            return "FindDetailDescriptor{" +
                    "name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    ", address='" + address + '\'' +
                    ", tags=" + tags +
                    '}';
        }
    }
}
