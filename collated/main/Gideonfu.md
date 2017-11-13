# Gideonfu
###### \java\seedu\address\commons\core\index\Index.java
``` java
    /**
     * Creates a new {@code Index[]} using a one-based index.
     */
    public static Index[] arrayFromOneBased(int[] oneBasedIndex) {
        Index[] arrayIndex = new Index[oneBasedIndex.length];
        for (int i = 0; i < oneBasedIndex.length; i++) {
            arrayIndex[i] = new Index(oneBasedIndex[i] - 1);
        }
        return arrayIndex;
    }
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getAddressBookList();
        for (Index targetIndex : targetIndex) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        if (targetIndex.length > 1) {
            for (int i = 1; i < targetIndex.length; i++) {
                if (targetIndex[i].getZeroBased() < targetIndex[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_ORDER_PERSONS_INDEX);
                } else if (targetIndex[i].getZeroBased() == targetIndex[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_REPEATED_INDEXES);
                }
            }
        }

        ReadOnlyPerson personToDelete;
        String[] personDeleteMessage = new String[targetIndex.length];
        StringBuilder deleteMessage = new StringBuilder();

        for (int i = (targetIndex.length - 1); i >= 0; i--) {
            int target = targetIndex[i].getZeroBased();
            personToDelete = lastShownList.get(target);
            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            } catch (DuplicatePersonException dpe) {
                assert false : "Duplicate person will not be added to Recycle Bin";
            }
            personDeleteMessage[i] = MESSAGE_DELETE_PERSON_SUCCESS + personToDelete;
        }

        for (String message : personDeleteMessage) {
            deleteMessage.append(message);
            deleteMessage.append("\n");
        }
        EventsCenter.getInstance().post(new PersonDeletedEvent());
        return new CommandResult(deleteMessage.toString().trim());
    }
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
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

        String[] keywords = trimmedArgs.split("\\s+");

        String mainKeyword = keywords[0];
        if (!mainKeyword.equals(FindCommand.KEYWORD_NAME) && !mainKeyword.equals(FindCommand.KEYWORD_ADDRESS)
                && !mainKeyword.equals(FindCommand.KEYWORD_EMAIL) && !mainKeyword.equals(FindCommand.KEYWORD_PHONE)
                && !mainKeyword.equals(FindCommand.KEYWORD_BIRTHDAY) && !mainKeyword.equals(FindCommand.KEYWORD_TAG)
                && !mainKeyword.equals(FindCommand.KEYWORD_ORGANISATION)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (keywords.length == 1) {
            throw new ParseException(FindCommand.MESSAGE_NO_KEYWORD);
        }

        String[] searchKeywords = new String[keywords.length - 1];
        System.arraycopy(keywords, 1, searchKeywords, 0, keywords.length - 1);

        if (mainKeyword.equals(FindCommand.KEYWORD_BIRTHDAY)) {
            for (String keyword : searchKeywords) {
                if (!keyword.matches("(0[1-9]|1[0-2])")) {
                    throw new ParseException(FindCommand.MESSAGE_INVALID_BIRTHDAY_MONTH);
                }
            }
        }

        return new FindCommand(new ContainsKeywordsPredicate(Arrays.asList(searchKeywords), mainKeyword));
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code oneBasedIndex} into an {@code Index[]} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index[] parseDeleteIndex(String oneBasedIndex) throws IllegalValueException {
        String[] parts = oneBasedIndex.split(",");
        String[] trimmedIndex = new String[parts.length];
        int[] trimmedIntIndex = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            trimmedIndex[i] = parts[i].trim();
            if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex[i])) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }
        }

        for (int i = 0; i < trimmedIndex.length; i++) {
            trimmedIntIndex[i] = Integer.parseInt(trimmedIndex[i]);
        }

        return Index.arrayFromOneBased(trimmedIntIndex);
    }
```
###### \java\seedu\address\model\person\ContainsKeywordsPredicate.java
``` java
    @Override
    public boolean test(ReadOnlyPerson person) {
        switch (mainKeyword) {
        case FindCommand.KEYWORD_NAME: return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

        case FindCommand.KEYWORD_ADDRESS: return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));

        case FindCommand.KEYWORD_EMAIL: return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));

        case FindCommand.KEYWORD_PHONE: return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));

        case FindCommand.KEYWORD_BIRTHDAY: return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getBirthday().getMonth(), keyword));

        case FindCommand.KEYWORD_TAG: return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTags().toString(), keyword));

        case FindCommand.KEYWORD_ORGANISATION: return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getOrganisation().value, keyword));

        default : return false;
        }

    }
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemark(String)}
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remark can take any values";

    /*
     * The first character of the remark must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String REMARK_VALIDATION_REGEX = "[^\\s].*";
    public static final String REMARK_NOT_ASSIGNED = "~";

    public final String value;
    /**
     * Validates given remark.
     *
     * @throws IllegalValueException if given remark string is invalid.
     */
    public Remark(String remark) throws IllegalValueException {

        if (remark == null) {
            this.value = REMARK_NOT_ASSIGNED;
        } else {
            if (!isValidRemark(remark)) {
                throw new IllegalValueException(MESSAGE_REMARK_CONSTRAINTS);
            }
            this.value = remark;
        }
    }
    /**
     * Returns true if a given string is a valid person remark.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(REMARK_VALIDATION_REGEX) || test.matches(REMARK_NOT_ASSIGNED);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
