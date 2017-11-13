# AngularJiaSheng
###### /java/seedu/address/commons/util/StringUtil.java
``` java

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");
        String preppedSentence = sentence.trim();
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence : wordsInPreppedSentence) {
            if (wordInSentence.toLowerCase().contains(preppedWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsExactWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;

        return preppedSentence.equalsIgnoreCase(preppedWord);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     *
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
```
###### /java/seedu/address/commons/util/WebLinkUtil.java
``` java

import java.util.HashMap;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 * Hashmap implementation
 */
public class WebLinkUtil {

    /*tag names for the categorized web links*/
    public static final String FACEBOOK_TAG = "facebook";
    public static final String TWITTER_TAG = "twitter";
    public static final String INSTAGRAM_TAG = "instagram";

    /*Keywords that can be used to match website to certain categories. */
    private static final String INSTAGRAM_MATCH_REGEX = "(?i)^^.*(instagram.com|instagram|insta).*$";
    private static final String FACEBOOK_MATCH_REGEX = "(?i)^^.*(facebook.com|fb.com/|facebook).*$";
    private static final String TWITTER_MATCH_REGEX = "(?i)^^.*(twitter.com|t.co|twitter).*$";

    private HashMap<String, String> matchingWebsites = new HashMap<>();


    public WebLinkUtil() {
        matchingWebsites.put(FACEBOOK_MATCH_REGEX, FACEBOOK_TAG);
        matchingWebsites.put(INSTAGRAM_MATCH_REGEX, INSTAGRAM_TAG);
        matchingWebsites.put(TWITTER_MATCH_REGEX, TWITTER_TAG);
    }

    public HashMap<String, String> getMatchingWebsites() {
        return matchingWebsites;
    }
}
```
###### /java/seedu/address/logic/commands/DeleteTagCommand.java
``` java
/**
 * Deletes a tag from all contacts in the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tagDelete";
    public static final String COMMAND_ALIAS = "td";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all the tag specified in all person in the list.\n"
            + "Parameters: tag (must be one of the existing tag on any one person\n"
            + "Example: " + COMMAND_WORD + " classmate";

```
###### /java/seedu/address/logic/commands/FindCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names, "
            + "phone number, address, tags and weblink contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice 987 clementi";

```
###### /java/seedu/address/logic/parser/DeleteTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a deleteTagCommand object.
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns an DeleteTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        try {
            Tag tag = new Tag(args);
            return new DeleteTagCommand(tag);
        } catch (IllegalValueException ab) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/EditCommandParser.java
``` java
    /**
     * Parses {@code Collection<String> webLinks} into a {@code Set<weblink>} if {@code webLinks} is non-empty.
     * If {@code webLinks} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<WebLinks>} containing zero tags.
     */
    private Optional<Set<WebLink>> parseWebLinkForEdit(Collection<String> webLinks) throws IllegalValueException {
        assert webLinks != null;

        if (webLinks.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> webLinkSet = webLinks.size() == 1
                && webLinks.contains("") ? Collections.emptySet() : webLinks;
        return Optional.of(ParserUtil.parseWebLink(webLinkSet));
    }
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static String parseWebname(String webname) throws IllegalValueException {
        requireNonNull(webname);
        return webname.trim();
    }

    /**
     * Parses {@code Collection<String> webLinks} into a {@code Set<weblink>}.
     */
    public static Set<WebLink> parseWebLink(Collection<String> webLinks) throws IllegalValueException {
        requireNonNull(webLinks);
        final Set<WebLink> webLinkSet = new HashSet<>();
        for (String inputWebLinkString : webLinks) {
            if (checkRepeatedWebLinkInCategory(webLinkSet, inputWebLinkString)) {
                webLinkSet.add(new WebLink(inputWebLinkString));
            } else {
                throw new IllegalValueException("Only one link per category: facebook ,"
                        + "instagram or twitter");
            }
        }
        return webLinkSet;
    }

    /**
     * Checks whether webLinkSet to be passed contains weblinks from the same category.
     */
    public static boolean checkRepeatedWebLinkInCategory (Set<WebLink> webLinkSet, String inputWebLinkString)
            throws IllegalValueException {
        boolean duplicateCheck = TRUE;
        WebLink inputWebLink = new WebLink(inputWebLinkString);
        String inputWebLinkTag = inputWebLink.toStringWebLinkTag();
        if (webLinkSet.isEmpty()) {
            return duplicateCheck;
        } else {

            for (Iterator<WebLink> iterateInternalList = webLinkSet.iterator(); iterateInternalList.hasNext(); ) {
                WebLink webLinkForChecking = iterateInternalList.next();
                String webLinkTagForChecking = webLinkForChecking.toStringWebLinkTag();
                if (inputWebLinkTag.equals(webLinkTagForChecking)) {
                    duplicateCheck = FALSE;
                    break;
                }
            }
            return duplicateCheck;
        }
    }

}
```
###### /java/seedu/address/model/person/ContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name}, Phone, Address, Email, Tag, WebLink,
 * matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public ContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return (containsKeyWordInName(person) || containsKeyWordInAddress(person)
                || containsKeyWordInPhone(person) || containsKeyWordInTag(person)
                || containsKeyWordInWebLink(person) || containsKeyWordInEmail(person));
    }

    private boolean containsKeyWordInName(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword
            -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    private boolean containsKeyWordInPhone(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword
            -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    private boolean containsKeyWordInAddress(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword
            -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
    }

    private boolean containsKeyWordInTag(ReadOnlyPerson person) {
        return person.getTags().stream().anyMatch(s -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(s.toStringFilter(), keyword)));
    }

    private boolean containsKeyWordInWebLink(ReadOnlyPerson person) {
        return person.getWebLinks().stream().anyMatch(s -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(s.toStringWebLink(), keyword)));
    }

    private boolean containsKeyWordInEmail(ReadOnlyPerson person) {
        return person.getEmail().stream().anyMatch(s -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(s.toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((ContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/FilterKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name, Phone Address, email, tag or weblink}
 * matches all of the keywords given.
 */
public class FilterKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public FilterKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {

        String combinedReferenceList = person.getAsOneString();

        return !keywords.isEmpty() && keywords.stream().allMatch(keyword
            -> StringUtil.containsWordIgnoreCase(combinedReferenceList, keyword) && !keyword.contains("[")
                && !keyword.contains("]"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((FilterKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/Person.java
``` java

    /**
     * Replaces this person's web links with the web links in the argument tag set.
     */
    public void setWebLinks(Set<WebLink> replacement) {
        webLinks.set(new UniqueWebLinkList(replacement));
    }

    /**
     * returns a ArrayList of string websites for UI usage.
     *
     * @code WebLinkUtil for the list of webLinkTags can be used as category.
     */
    public ArrayList<String> listOfWebLinkByCategory(String category) {
        ArrayList<String> outputWebLinkList = new ArrayList<String>();
        for (Iterator<WebLink> iterateWebLinkSet = getWebLinks().iterator(); iterateWebLinkSet.hasNext(); ) {
            WebLink checkWebLink = iterateWebLinkSet.next();
            String webLinkAddedToList = checkWebLink.toStringWebLink();
            String checkWebLinkTag = checkWebLink.toStringWebLinkTag();
            if (checkWebLinkTag.equals(category)) {
                outputWebLinkList.add(webLinkAddedToList);
            }
        }
        return outputWebLinkList;
    }

```
###### /java/seedu/address/model/person/ReadOnlyPerson.java
``` java
    /**
     * Formats the person as text, showing all contact details, without any additional text or descriptions.
     */
    default String getAsOneString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" ")
                .append(getPhone())
                .append(" ")
                .append(getEmail())
                .append(" ")
                .append(getAddress())
                .append(" ")
                .append(getRemark())
                .append(" ");
        getTags().forEach(builder::append);
        builder.append(" ");
        getWebLinks().forEach(builder::append);
        return builder.toString();
    }

```
###### /java/seedu/address/model/person/weblink/UniqueWebLinkList.java
``` java
/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see WebLink#equals(Object)
 */

public class UniqueWebLinkList implements Iterable<WebLink> {

    private final ObservableList<WebLink> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty WebLinkList.
     */
    public UniqueWebLinkList() {}

    /**
     * Creates a UniqueTagList using given webLinks.
     * Enforces no nulls.
     */
    public UniqueWebLinkList(Set<WebLink> webLinks) {
        requireAllNonNull(webLinks);
        internalList.addAll(webLinks);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * @throws multipleLinkForOneCategoryException
     */

    /**
     * Returns all tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<WebLink> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the WebLinks in this list with those in the argument WebLinks list.
     */
    public void setWebLinks(Set<WebLink> webLinks) {
        requireAllNonNull(webLinks);
        internalList.setAll(webLinks);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every webLink in the argument list exists in this object.
     */
    public void mergeFrom(UniqueWebLinkList from) {
        final Set<WebLink> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(webLinks -> !alreadyInside.contains(webLinks))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent weblink as the given argument.
     */
    public boolean contains(WebLink toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a webLink to the list.
     *
     * @throws DuplicateWebLinkException if the webLink to add is a duplicate of an existing Tag in the list.
     */
    public void add(WebLink toAdd) throws DuplicateWebLinkException {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateWebLinkException();
        }

        internalList.add(toAdd);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<WebLink> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<WebLink> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueWebLinkList // instanceof handles nulls
                && this.internalList.equals(((UniqueWebLinkList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueWebLinkList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateWebLinkException extends DuplicateDataException {
        protected DuplicateWebLinkException() {
            super("Operation would result in duplicate web Links");
        }
    }

}
```
###### /java/seedu/address/model/person/weblink/WebLink.java
``` java
/**
 * Represents a WebLink in the address book.
 * only accept twitter, facebook and instagram links
 * WebLinkTag defines the category of this webLink.
 */
public class WebLink {

    private static final String MESSAGE_WEB_LINK_CONSTRAINTS = "No spaces should be allowed in the weblink input.";
    private static final String DEFAULT_TAG = "others";

    private  String webLinkInput;
    private  String webLinkTag;

    /**
     * Validates given web link.
     *
     * @throws IllegalValueException if the given webLink name string is invalid.
     */
    public WebLink(String name) throws IllegalValueException {

        requireNonNull(name);
        this.webLinkInput = name.trim();
        this.webLinkTag = DEFAULT_TAG;

        HashMap<String, String> webLinkTagMap = new WebLinkUtil().getMatchingWebsites();
        Iterator<String> keySetIterator = webLinkTagMap.keySet().iterator();

        while (keySetIterator.hasNext()) {
            String webLinkMatchingRegex = keySetIterator.next();
            if (webLinkInput.matches(webLinkMatchingRegex)) {
                this.webLinkTag = webLinkTagMap.get(webLinkMatchingRegex);
                break;
            }
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WebLink // instanceof handles nulls
                && this.webLinkInput.equals(((WebLink) other).webLinkInput)); // state check
    }

    @Override
    public int hashCode() {
        return webLinkInput.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + webLinkInput + ']';
    }

    /**
     * Format state as text for viewing.
     */
    public String toStringWebLink() {
        return webLinkInput;
    }

    /**
     * Format state as text for viewing.
     */
    public String toStringWebLinkTag() {
        return webLinkTag;
    }
}
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
    public static Set<WebLink> getWebLinkSet(String... strings) throws IllegalValueException {
        HashSet<WebLink> webLinks = new HashSet<>();
        for (String s : strings) {
            webLinks.add(new WebLink(s));
        }
        return webLinks;
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedWebLink.java
``` java
/**
 * JAXB-friendly adapted version of the WebLink.
 */
public class XmlAdaptedWebLink {

    @XmlValue
    private String webLinkInput;

    /**
     * Constructs an XmlAdaptedWebLink
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedWebLink() {}

    /**
     * Converts a given webLink into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedWebLink(WebLink source) {
        webLinkInput = source.toStringWebLink();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public WebLink toModelType() throws IllegalValueException {
        return new WebLink(webLinkInput);
    }
}
```
