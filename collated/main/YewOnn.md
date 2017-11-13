# YewOnn
###### \java\seedu\address\commons\events\ui\LocateMrtCommandEvent.java
``` java
public class LocateMrtCommandEvent extends BaseEvent {

    private ReadOnlyPerson person;

    public LocateMrtCommandEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
```
###### \java\seedu\address\logic\commands\FindByPhoneCommand.java
``` java
public class FindByPhoneCommand extends Command {

    public static final String COMMAND_WORD = "findByPhone";
    public static final String COMMAND_ALIAS = "fbp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose phone numbers contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "91234567";

    private final PhoneContainsKeywordsPredicate predicate;

    public FindByPhoneCommand(PhoneContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindByPhoneCommand // instanceof handles nulls
                && this.predicate.equals(((FindByPhoneCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\model\person\PhoneContainsKeywordsPredicate.java
``` java
public class PhoneContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
