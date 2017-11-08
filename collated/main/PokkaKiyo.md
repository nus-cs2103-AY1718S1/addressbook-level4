# PokkaKiyo
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setFacebookAddress(FacebookAddress facebookAddress) {
            this.facebookAddress = facebookAddress;
        }

        public Optional<FacebookAddress> getFacebookAddress() {
            return Optional.ofNullable(facebookAddress);
        }
```
###### \java\seedu\address\logic\commands\FindPersonsWithTagsCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsTagsPredicate;

/**
 * Finds and lists all persons in address book who has a tag that contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindPersonsWithTagsCommand extends Command {

    public static final String COMMAND_WORD = "findtags";
    public static final String COMMAND_ALIAS1 = "findtag";
    public static final String COMMAND_ALIAS2 = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose has tags containing any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " classmates colleagues";

    private final PersonContainsTagsPredicate predicate;

    public FindPersonsWithTagsCommand(PersonContainsTagsPredicate predicate) {
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
                || (other instanceof FindPersonsWithTagsCommand // instanceof handles nulls
                && this.predicate.equals(((FindPersonsWithTagsCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ListTagsCommand.java
``` java
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * List all tags in the addressbook to the user
 */
public class ListTagsCommand extends Command {
    public static final String COMMAND_WORD = "listtags";
    public static final String COMMAND_WORD_ALIAS1 = "lt";
    public static final String COMMAND_WORD_ALIAS2 = "ltags";

    public static final String MESSAGE_FAILURE = "You do not have any tags!";
    public static final String MESSAGE_SUCCESS = "You have the following tags: ";


    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(getFeedbackForUser());
    }

    private String getFeedbackForUser() {

        String feedbackForUser;

        ArrayList<Tag> listOfAllTags = getAllTagsWithNoDuplicates();

        if (listOfAllTags.isEmpty()) {
            feedbackForUser = MESSAGE_FAILURE;
        } else {
            feedbackForUser = MESSAGE_SUCCESS + getListOfAllTagsInString(listOfAllTags);
        }

        return feedbackForUser;
    }

    private ArrayList<Tag> getAllTagsWithNoDuplicates() {
        ArrayList<Tag> listOfAllTags = new ArrayList<Tag>();

        for (ReadOnlyPerson p : model.getAddressBook().getPersonList()) {
            for (Tag tag : p.getTags()) {
                if (!listOfAllTags.contains(tag)) {
                    listOfAllTags.add(tag);
                }
            }
        }
        return listOfAllTags;
    }

    private String getListOfAllTagsInString(ArrayList<Tag> listOfAllTags) {

        ArrayList<String> listOfAllTagNames = getAllTagNamesWithNoDuplicates(listOfAllTags);

        sortListOfAllTagNamesAlphabetically(listOfAllTagNames);

        return getSortedTagNamesAsString(listOfAllTagNames);
    }

    private ArrayList<String> getAllTagNamesWithNoDuplicates(ArrayList<Tag> listOfAllTags) {
        ArrayList<String> listOfAllTagNames = new ArrayList<String>();

        for (Tag tag : listOfAllTags) {
            listOfAllTagNames.add(tag.getTagName());
        }
        return listOfAllTagNames;
    }

    private void sortListOfAllTagNamesAlphabetically(ArrayList<String> listOfAllTagNames) {
        Collections.sort(listOfAllTagNames);
    }

    private String getSortedTagNamesAsString(ArrayList<String> listOfAllTagNames) {
        StringBuilder tagNamesInListOfAllTags = new StringBuilder("");
        for (String tagName : listOfAllTagNames) {
            tagNamesInListOfAllTags.append("[").append(tagName).append("] ");
        }

        return tagNamesInListOfAllTags.toString().trim();
    }

}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            FacebookAddress facebookAddress = new FacebookAddress("");
            Optional<String> facebookAddressOptional = argMultimap.getValue(PREFIX_FACEBOOKADDRESS);
            if (facebookAddressOptional.isPresent()) {
                facebookAddress = ParserUtil.parseFacebookAddress(argMultimap.getValue(PREFIX_FACEBOOKADDRESS)).get();
            }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindPersonsWithTagsCommand.COMMAND_WORD:
        case FindPersonsWithTagsCommand.COMMAND_ALIAS1:
        case FindPersonsWithTagsCommand.COMMAND_ALIAS2:
            return new FindPersonsWithTagsCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ListTagsCommand.COMMAND_WORD:
        case ListTagsCommand.COMMAND_WORD_ALIAS1:
        case ListTagsCommand.COMMAND_WORD_ALIAS2:
            return new ListTagsCommand();
```
###### \java\seedu\address\logic\parser\FindPersonsWithTagsCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.logic.commands.FindPersonsWithTagsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsTagsPredicate;

/**
 * Parses input arguments and creates a new FindPersonWithTagsCommand object
 */
public class FindPersonsWithTagsCommandParser implements Parser<FindPersonsWithTagsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonWithTagsCommand
     * and returns an FindPersonWithTagsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonsWithTagsCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        checkForNonEmptyArguments(trimmedArgs);

        String[] tagKeywords = getListOfTagKeywords(trimmedArgs);

        ArrayList<String> tagKeywordsImproved = getImprovedList(tagKeywords);

        return new FindPersonsWithTagsCommand(new PersonContainsTagsPredicate(tagKeywordsImproved));
    }

    private String[] getListOfTagKeywords(String trimmedArgs) {
        return trimmedArgs.split("\\s+");
    }

    /**
     * Checks if the given String of arguments is empty
     * @param trimmedArgs
     * @throws ParseException if the provided String does not conform to expected format
     */
    private void checkForNonEmptyArguments(String trimmedArgs) throws ParseException {
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindPersonsWithTagsCommand.MESSAGE_USAGE));
        }
    }

    /**
     * From the original list of keywords, generate more keywords
     * corresponding to the singular/plural form of the word,
     * but might generate words that are not in proper English,
     * for example, "memory" to "memorys" instead of "memories".
     * @param originalKeywords
     * @return a new list of keywords that includes the original keywords and
     * singular/plural differences
     */
    private ArrayList<String> getImprovedList(String[] originalKeywords) {
        ArrayList<String> improvedListOfKeywords = new ArrayList<String>();
        for (String originalKeyword : originalKeywords) {
            improvedListOfKeywords.add(originalKeyword);
            if (originalKeyword.endsWith("s")) {
                improvedListOfKeywords.add(originalKeyword.substring(0, originalKeyword.length() - 1));
            } else {
                improvedListOfKeywords.add (originalKeyword.concat("s"));
            }
        }
        return improvedListOfKeywords;
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> facebookAddress} into an {@code Optional<FacebookAddress>}
     * if {@code facebookAddress} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<FacebookAddress> parseFacebookAddress (Optional<String> facebookAddress)
            throws IllegalValueException {
        requireNonNull(facebookAddress);
        return facebookAddress.isPresent()
                ? Optional.of(new FacebookAddress(facebookAddress.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\person\FacebookAddress.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.net.URL;

/**
 * Represents a person's Facebook address
 * Guarantees: immutable; is always valid
 */
public class FacebookAddress {

    public static final String MESSAGE_FACEBOOKADDRESS_CONSTRAINTS =
            "Person Facebook address can take any values, can even be blank";

    public final String value;

    public FacebookAddress(String facebookAddress) {
        requireNonNull(facebookAddress);

        this.value = getInUrlFormIfNeeded(facebookAddress);
    }

    /**
     * If user's input is not in valid URL format, assume that the input is the facebook profile name of the contact,
     * and thus, append the necessary URL prefixes
     * @param facebookAddress
     * @return
     */
    private String getInUrlFormIfNeeded(String facebookAddress) {
        if (isValidUrl(facebookAddress)) {
            return facebookAddress;
        } else {
            return "https://www.facebook.com/" + facebookAddress;
        }
    }

    /**
     * Checks if a given string is in valid URL format
     * @param facebookAddress
     * @return
     */
    private boolean isValidUrl(String facebookAddress) {
        try {
            URL url = new URL(facebookAddress);
            url.toURI();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof FacebookAddress //instanceof handles nulls
                && this.value.equals(((FacebookAddress) other).value));

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }
```
###### \java\seedu\address\model\person\PersonContainsTagsPredicate.java
``` java
package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a Person has a tag with a tag name matches any of the keywords given.
 */
public class PersonContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsTagsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {

        String allTagNames = getStringOfAllTagNamesOfPerson(person);

        final List<String> keywordsToInclude = new ArrayList<String>();
        final List<String> keywordsToExclude = new ArrayList<String>();
        separateKeywordsToIncludeAndExclude(keywordsToInclude, keywordsToExclude);

        boolean onlyKeywordsToExcludeAreSpecified =
                checkIfOnlyKeywordsToExcludeAreSpecified(keywordsToInclude, keywordsToExclude);

        //if only tags to be excluded are specified, return all persons that do not contain the specified tag,
        //even if the person does not have any tags
        if (onlyKeywordsToExcludeAreSpecified) {
            return !(keywordsToExclude.stream()
                    .anyMatch((keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword))));
        }

        return keywordsToInclude.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword))
                && !(keywordsToExclude.stream()
                .anyMatch((keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword))));
    }

    /**
     * Checks if there are only keywords to exclude but not keywords to include
     * @param keywordsToInclude list of keywords to include for finding tags
     * @param keywordsToExclude list of keywords to explicitly exclude for finding tags
     * @return true if keywordsToInclude is empty and keywordsToExclude is not empty, false otherwise
     */
    private boolean checkIfOnlyKeywordsToExcludeAreSpecified(
            List<String> keywordsToInclude, List<String> keywordsToExclude) {

        if (keywordsToInclude.isEmpty() && !keywordsToExclude.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Obtains and updates the appropriate list of keywords to include and exclude
     * @param keywordsToInclude list of keywords to include for search
     * @param keywordsToExclude list of keywords to explicitly exclude for search
     */
    private void separateKeywordsToIncludeAndExclude(List<String> keywordsToInclude, List<String> keywordsToExclude) {

        for (String eachKeyword : keywords) {
            if (!eachKeyword.startsWith("-")) {
                keywordsToInclude.add(eachKeyword);
            } else {
                keywordsToExclude.add(eachKeyword.substring(1));
            }
        }

    }

    private String getStringOfAllTagNamesOfPerson(ReadOnlyPerson person) {
        Set<Tag> personTags = getAllTagsOfPerson(person);

        StringBuilder allTagNames = new StringBuilder();
        for (Tag tag : personTags) {
            allTagNames.append(tag.getTagName() + " ");
        }

        return allTagNames.toString().trim();
    }

    private Set<Tag> getAllTagsOfPerson(ReadOnlyPerson person) {
        return person.getTags();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsTagsPredicate) other).keywords)); // state check
    }

}
```
