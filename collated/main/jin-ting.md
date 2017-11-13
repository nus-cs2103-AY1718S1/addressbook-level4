# jin-ting
###### \java\seedu\address\commons\events\ui\ShowCalendarRequestEvent.java
``` java
/**
 * An event requesting to view the help page.
 */
public class ShowCalendarRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\util\PredicateUtil.java
``` java
/**
 * Helper functions for handling strings for findCommand.
 */
public class PredicateUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code searchWord}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsAttributeIgnoreCase("ABc def", "abc") == true
     *       containsAttributeIgnoreCase("ABc def", "DEF") == true
     *       containsAttributeIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence   cannot be null
     * @param searchWord cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsAttributeIgnoreCase(String sentence, String searchWord) {
        keywordsPredicateCheckForNull(sentence, searchWord);

        String preppedWord = searchWord.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String[] wordsInPreppedSentence = sentence.split("\\s+");

        for (String wordInSentence : wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the {@code emailsentence} contains the {@code searchWord}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsEmailgnoreCase("abc@example.com", "e/example") == true
     *       containsEmailgnoreCase(("abc@example.com", "example") == false
     *       containsEmailgnoreCase(("abc@example.com", "e/   EXAMPLE") == true//Case insensitive
     *       </pre>
     *
     * @param emailSet   cannot be null
     * @param searchWord cannot be null, cannot be empty, must be a valid  email domain
     */
    public static boolean containsEmailIgnoreCase(Collection<Email> emailSet, String searchWord) {
        keywordsPredicateCheckForNull(emailSet, searchWord);

        String preppedWord = searchWord.trim();
        checkArgument(!preppedWord.isEmpty(), "Email parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Email parameter should be a single word");

        for (Email email : emailSet) {
            String emailString = email.value;
            String preppedEmailSentence = emailString.substring(emailString.indexOf('@') + 1);
            String finalPreppedEmailSentence = preppedEmailSentence.substring(0, preppedEmailSentence.indexOf('.'));

            if (finalPreppedEmailSentence.equalsIgnoreCase(preppedWord.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns true if the {@code phoneSentence} contains the {@code searchWord}.
     * Ignores case, but numbers must be either 4 digits or 8 digits.
     * <br>examples:<pre>
     *      containsPhoneIgnoreCase("99998888", "p/8888") == true
     *      containsPhoneIgnoreCase("99998888", "e/9999 8888") == true
     *      containsPhoneIgnoreCase(("99998888", "999 8888") == false
     *       </pre>
     *
     * @param phoneSentence cannot be null
     * @param searchWord    cannot be null, cannot be empty, must be 4 digits or 8 digits only
     */
    public static boolean containsPhoneIgnoreCase(String phoneSentence, String searchWord) {
        keywordsPredicateCheckForNull(phoneSentence, searchWord);

        String preppedPhone = searchWord.trim();
        checkArgument(!preppedPhone.isEmpty(), "Phone parameter cannot be empty");
        checkArgument(preppedPhone.split("\\s+").length == 1, "Phone numbers parameter should be a single word");
        String[] phonePreppedSentence = phoneSentence.split("(?<=\\G.{4})");


        for (String phoneInSentence : phonePreppedSentence) {
            if (phoneInSentence.matches(preppedPhone)) {
                return true;

            }
        }
        return false;
    }


    /**
     * Returns true if the {@code addressSentence } contains the {@code searchWord}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *      containsAddressIgnoreCase("123, Jurong West Ave 6, #08-111", "a/#08-111") == true
     *      containsAddressIgnoreCase("123, Jurong West Ave 6, #08-111", "a/#08-111","123") == true
     *      containsAddressIgnoreCase("123, Jurong West Ave 6, #08-111", "e/JUrong") == true
     *      containsAddressIgnoreCase("123, Jurong West Ave 6, #08-111", "e/JUrongWest") == false
     *       </pre>
     *
     * @param addressSentence cannot be null
     * @param searchWord      cannot be null, cannot be empty, must be 4 digits or 8 digits
     */
    public static boolean containsAddressIgnoreCase(String addressSentence, String searchWord) {
        keywordsPredicateCheckForNull(addressSentence, searchWord);

        String preppedAddress = searchWord.trim();
        checkArgument(!preppedAddress.isEmpty(), "Address parameter cannot be empty");
        checkArgument(preppedAddress.split("\\s+").length == 1, "Address parameter should be a single word");


        List<String> tempAddress = Arrays.asList(addressSentence.replaceAll(",", "").split("\\s+"));
        return tempAddress.stream().anyMatch(preppedAddress::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code tagSet } contains the {@code searchWord}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *      containsTagIgnoreCase("friends", "a/FRIend") == true
     *      containsTagIgnoreCase("friends", "e/FRIENd") == false
     *       </pre>
     *
     * @param tagSet     cannot be null
     * @param searchWord cannot be null, cannot be empty, must be at least a a single word
     */
    public static boolean containsTagIgnoreCase(Collection<Tag> tagSet, String searchWord) {
        keywordsPredicateCheckForNull(tagSet, searchWord);

        String preppedTag = searchWord.trim();
        checkArgument(!preppedTag.isEmpty(), "Tag parameter cannot be empty");
        checkArgument(preppedTag.split("\\s+").length == 1, "Tag parameter should be a single word");

        for (Tag tag : tagSet) {
            if (preppedTag.equalsIgnoreCase(tag.tagName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Returns true if the {@code tagSet } contains the {@code searchWord}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *      containsTagIgnoreCase("friends", "a/FRIend") == true
     *      containsTagIgnoreCase("friends", "e/FRIENd") == false
     *       </pre>
     *
     * @param scheduleSet cannot be null
     * @param searchWord  cannot be null, cannot be empty, must be at least a a single word
     */
    public static boolean containsScheduleIgnoreCase(Collection<Schedule> scheduleSet, String searchWord) {
        keywordsPredicateCheckForNull(scheduleSet, searchWord);

        String preppedSchedule = searchWord.trim();
        checkArgument(!preppedSchedule.isEmpty(), "Schedule parameter cannot be empty");
        checkArgument(preppedSchedule.split("\\s+").length == 1, "Schedule parameter should be a single word");

        for (Schedule schedule : scheduleSet) {
            String[] preppedActivity = schedule.getActivity().toString().split("\\s+");
            for (String wordsInPreppedActivity : preppedActivity) {
                if (wordsInPreppedActivity.equalsIgnoreCase(preppedSchedule)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check for if sentence or searchWord is null .
     */
    private static void keywordsPredicateCheckForNull(Object sentence, String searchWord) {
        requireNonNull(sentence);
        requireNonNull(searchWord);
    }


}
```
###### \java\seedu\address\logic\commands\CalendarCommand.java
``` java
/**
 * Locates a person's address by showing its location on Google Maps.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cl";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (alias: " + COMMAND_ALIAS + ")"
            + ":Display calendar)\n"
            + MESSAGE_GET_MORE_HELP;

    public static final String MESSAGE_DISPLAY_CALENDAR_SUCCESS = "Displaying Calendar...";


    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new ShowCalendarRequestEvent());
        return new CommandResult(MESSAGE_DISPLAY_CALENDAR_SUCCESS);

    }

}
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java

/**
 * Opening email application to send email to contatcs.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "m";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (alias: " + COMMAND_ALIAS + ")"
            + ": Opening up email platform to send email to person(s)\n"
            + "Parameters: INDEX\n"
            + MESSAGE_GET_MORE_HELP;

    public static final String MESSAGE_DISPLAY_EMAIL_SUCCESS = "Sending email to %1$s";

    private Set<String> recipientSet = new HashSet<>();

    private final Set<Index> targetIndices;

    public EmailCommand(Set<Index> targetIndices) {
        requireNonNull(targetIndices);
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson targetRecipient = lastShownList.get(targetIndex.getZeroBased());
            for (Email email : targetRecipient.getEmails()) {
                recipientSet.add(email.toString());
            }
        }

        String recipientList = String.join(",", recipientSet);
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                try {
                    URI mailto = new URI("mailto:" + recipientList + "?subject=Hello%20World");
                    desktop.mail(mailto);
                } catch (URISyntaxException | IOException e) {
                    e.printStackTrace();

                }
            }

        }

        return new CommandResult(String.format(MESSAGE_DISPLAY_EMAIL_SUCCESS, recipientList));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand); // instanceof handles nulls
    }

}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "(alias: " + COMMAND_ALIAS + ")"
            + ": Finds all persons who match any of "
            + "the specified searched keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + MESSAGE_GET_MORE_HELP;

    private final Predicate<ReadOnlyPerson> predicate;

    public FindCommand(Predicate<ReadOnlyPerson> predicate) {
        this.predicate = predicate;

    }
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of input arguments
     * and returns a new EmailCommand object
     * /@throws ParseException if the user input does not conform the expected format
     */

    public EmailCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] indicesInString = trimmedArgs.split("\\s+");


        try {
            Set<Index> indices = new HashSet<>();
            for (String indexString : indicesInString) {
                Index index = ParserUtil.parseIndex(indexString);
                indices.add(index);
            }

            return new EmailCommand(indices);

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Used to identify prefix and args.
     */
    private static final Pattern FIND_COMMAND_FORMAT = Pattern.compile("(?<prefix>\\w+/)(?<arguments>.*)");

    private Predicate<ReadOnlyPerson> predicate;

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * to allocate the given {@code} of arguments fpr each predicate.
     * @return FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final Matcher matcher = FIND_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        final String prefix = matcher.group("prefix");
        final String arguments = matcher.group("arguments");

        String[] keywords = arguments.trim().split("\\s+");

        switch (prefix) {
        case ("a/"):
            predicate = new AddressContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("act/"):
            predicate = new ScheduleContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("c/"):
            predicate = new CountryContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("e/"):
            predicate = new EmailContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("n/"):
            predicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("t/"):
            predicate = new TagContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("p/"):
            if (!validPhoneNumbers(keywords)) {
                throw new ParseException(PhoneContainsKeywordsPredicate.MESSAGE_PHONE_VALIDATION);
            }
            String[] keywordsPhone = arguments.replaceAll("\\s", "").split("(?<=\\G.{4})");
            predicate = new PhoneContainsKeywordsPredicate(Arrays.asList(keywordsPhone));
            break;

        default:
            throw new ParseException(MESSAGE_UNKNOWN_FINDCOMMAND);



        }
        return new FindCommand(predicate);
    }


    public static boolean validPhoneNumbers(String[] phoneNumbers) {
        return Stream.of(phoneNumbers).allMatch(length -> length.matches("\\d{4}") || length.matches("\\d{8}"));
    }
}
```
###### \java\seedu\address\model\person\AddressContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> PredicateUtil.containsAddressIgnoreCase(person.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

}

```
###### \java\seedu\address\model\person\CountryContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class CountryContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    private final List<String> keywords;

    public CountryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> PredicateUtil.containsAttributeIgnoreCase(person.getCountry().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CountryContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((CountryContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> PredicateUtil.containsAttributeIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\PhoneContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    public static final String MESSAGE_PHONE_VALIDATION =
            "Phone numbers can only contain numbers, and should be at 4 or 8 digits long";

    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> PredicateUtil.containsPhoneIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\ScheduleContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tags} matches any of the keywords given.
 */
public class ScheduleContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public ScheduleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> PredicateUtil.containsScheduleIgnoreCase(person.getSchedules(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((ScheduleContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tags} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> PredicateUtil.containsTagIgnoreCase(person.getTags(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    /**
     * @see #backupAddressBook
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath() + "-backup.xml");
    }
}
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Opens the Calendar window in the browser panel.
     */
    public void loadCalendar() {
        loadPage("https://www.timeanddate.com/calendar/");
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleCalendarRequestEvent(ShowCalendarRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCalendar();
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    private static String[] colors = {"#009B77", "DD4124", "D65076", "#45BBAC", "#EFC050",
        "#5B5EA6", "#9B2335", "#55B4B0", "#E15D44", "#7FCDCD",
        "#BC243C", "#C3447A", "#98B4D4", "#DFCFBE"};
    private static HashMap<String, String> tagColors = new HashMap<>();
    private static Random random = new Random();

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label country;
    @FXML
    private Label address;
    @FXML
    private VBox emails;
    @FXML
    private FlowPane schedules;

    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        setStylesToNameAndId();
        initEmails(person);
        initTags(person);
        initSchedules(person);
        bindListeners(person);
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }

        return tagColors.get(tagValue);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        country.textProperty().bind(Bindings.convert(person.countryProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));

        person.emailProperty().addListener((observable, oldValue, newValue) -> {
            emails.getChildren().clear();
            initEmails(person);
        });

```
