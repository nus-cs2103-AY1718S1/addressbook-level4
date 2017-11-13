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
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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

    private static final Logger logger = LogsCenter.getLogger(ListTagsCommand.class);

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(getFeedbackForUser());
    }

    private String getFeedbackForUser() {

        String feedbackForUser;

        ArrayList<Tag> listOfAllTags = getUniqueListOfTags();

        if (listOfAllTags.isEmpty()) {
            logger.info("------ ListTagsCommand found no tags attached to any contacts");
            feedbackForUser = MESSAGE_FAILURE;
        } else {
            feedbackForUser = MESSAGE_SUCCESS + getListOfAllTagsInString(listOfAllTags);
        }

        assert feedbackForUser != null : "feedbackForUser should contain some message and not be null";

        return feedbackForUser;
    }

    private ArrayList<Tag> getUniqueListOfTags() {
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

        ArrayList<String> listOfAllTagNames = getUniqueListOfTagNames(listOfAllTags);

        sortTagNamesAlphabetically(listOfAllTagNames);

        return convertSortedTagNamesToString(listOfAllTagNames);
    }

    private ArrayList<String> getUniqueListOfTagNames(ArrayList<Tag> listOfAllTags) {
        ArrayList<String> listOfAllTagNames = new ArrayList<String>();

        for (Tag tag : listOfAllTags) {
            listOfAllTagNames.add(tag.getTagName());
        }
        return listOfAllTagNames;
    }

    private void sortTagNamesAlphabetically(ArrayList<String> listOfAllTagNames) {
        Collections.sort(listOfAllTagNames, String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Converts the list of all tag names into a string to display
     * @param listOfAllTagNames the ArrayList containing all Tag names that are attached to at least 1 contact
     * @return a String with all the names of the Tags, formatted appropriately.
     */
    private String convertSortedTagNamesToString(ArrayList<String> listOfAllTagNames) {
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
     * @param trimmedArgs args entered by user
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
     * for example, "family" to "familys" instead of "families".
     * @param originalKeywords keywords entered by user
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
###### \java\seedu\address\model\person\PersonContainsTagsPredicate.java
``` java
package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a Person has a tag with a tag name matches any of the keywords given.
 */
public class PersonContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private static final Logger logger = LogsCenter.getLogger(PersonContainsTagsPredicate.class);

    private final List<String> keywords;
    private final List<String> keywordsToInclude;
    private final List<String> keywordsToExclude;

    public PersonContainsTagsPredicate(List<String> keywords) {
        logger.info("------Creating PersonContainsTagsPredicate");
        this.keywords = keywords;
        keywordsToInclude = new ArrayList<String>();
        keywordsToExclude = new ArrayList<String>();
        separateKeywordsToIncludeAndExclude(keywordsToInclude, keywordsToExclude);
        logger.info("-------Number of keywords to include: " + keywordsToInclude.size() / 2);
        logger.info("-------Number of keywords to exclude: " + keywordsToExclude.size() / 2);

    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        String allTagNames = getStringOfAllTagNamesOfPerson(person);

        assert !(keywordsToExclude.isEmpty() && keywordsToInclude.isEmpty()) : "at least one keyword must be specified";

        boolean onlyKeywordsToExcludeAreSpecified =
                checkIfOnlyKeywordsToExcludeAreSpecified(keywordsToInclude, keywordsToExclude);

        //Return all persons that do not contain the specified tag if only exclusion tags are specified
        if (onlyKeywordsToExcludeAreSpecified) {
            return !(keywordsToExclude.stream()
                    .anyMatch((keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword))));
        }

        assert !keywordsToInclude.isEmpty() : "there must be at least one keyword to include here";

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
            assert !keywordsToInclude.isEmpty() : "there must be at least one keyword to include here";
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
                && this.keywords.equals(((PersonContainsTagsPredicate) other).keywords) //state check
                && this.keywordsToInclude.equals(((PersonContainsTagsPredicate) other).keywordsToInclude)
                && this.keywordsToExclude.equals(((PersonContainsTagsPredicate) other).keywordsToExclude));
    }

}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadPersonPage(ReadOnlyPerson person) {
        logger.info("----[Accessing URL] " + person.getFacebookAddress().value);
        loadPage(person.getFacebookAddress().value);
    }
```
###### \resources\view\BerryBlueTheme.css
``` css
*/
.background {
    -fx-background-color: derive(#1d1d1d, 20%);
    background-color: #1E1F26; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: #1d1d1d;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#1E1F26, 5%);
    -fx-border-color: #1E1F26 #1E1F26 #1E1F26 #1E1F26;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#1d1d1d, 20%);
}

.list-view {
    -fx-background-color: #1E1F26;
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-background-color: #1E1F26;
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #283655;
}

.list-cell:filled:odd {
    -fx-background-color: #4D648D;
}

.list-cell:filled:selected {
    -fx-background-color: #424d5f;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #D0E1F9;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: white;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: derive(#1d1d1d, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#1d1d1d, 20%);
     -fx-border-color: derive(#1d1d1d, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: black;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#1E1F26, 30%);
}

.context-menu {
    -fx-background-color: derive(#1d1d1d, 50%);
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: derive(#1E1F26, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(white, 5%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color:  #1E1F26  #1E1F26  #1E1F26  #1E1F26;
    -fx-background-insets: 0;
    -fx-border-color: black black black black;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color:  #1E1F26 #1E1F26 #1E1F26 #1E1F26;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
