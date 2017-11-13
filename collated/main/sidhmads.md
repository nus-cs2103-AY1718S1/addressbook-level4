# sidhmads
###### \java\seedu\address\commons\events\ui\FindLocationRequestEvent.java
``` java
public class FindLocationRequestEvent extends BaseEvent {

    public final List<ReadOnlyPerson> targetPersons;

    public FindLocationRequestEvent(List<ReadOnlyPerson> targetPersons) {
        this.targetPersons = targetPersons;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_EMAIL_APP = "Opened Email Application";
    public static final String MESSAGE_NOT_SENT = "Please enter a valid name/tag with a valid Email ID.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":  people in the Address Book.\n"
            + "The 'to' field is compulsory\n"
            + "The 'to' field can take both names and tags and it also supports more than one parameter.\n"
            + "Parameters: "
            + PREFIX_EMAIL_TO + "NAMES or TAGS "
            + PREFIX_EMAIL_SUBJECT + "PHONE "
            + PREFIX_EMAIL_BODY + "EMAIL \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL_TO + "John Doe Friends Family "
            + PREFIX_EMAIL_SUBJECT + " TEST SUBJECT "
            + PREFIX_EMAIL_BODY + " TEST BODY ";

    private final NameContainsKeywordsPredicate predicate;
    private final String subject;
    private final String body;

    public EmailCommand(NameContainsKeywordsPredicate predicate, String subject, String body) {
        this.predicate = predicate;
        this.subject = subject;
        this.body = body;
    }

    /**
     * Compiles the recipients, subject and body together.
     * Opens the desktop mail app and fill in those compiled details.
     */
    private void sendEmail(String emailTo) throws ParseException {
        if (emailTo.equalsIgnoreCase("")) {
            throw new ParseException("Invalid recipient email.");
        }
        Desktop desktop = Desktop.getDesktop();
        String url = "";
        URI mailTo;
        try {
            url = "mailTo:" + emailTo + "?subject=" + this.subject
                    + "&body=" + this.body;
            mailTo = new URI(url);
            desktop.mail(mailTo);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandResult execute() {
        String emailTo = model.updateEmailRecipient(predicate);
        try {
            sendEmail(emailTo);
        } catch (ParseException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_NOT_SENT);
        }
        return new CommandResult(MESSAGE_EMAIL_APP);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.predicate.equals(((EmailCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\LocationCommand.java
``` java
public class LocationCommand extends Command {

    public static final String COMMAND_WORD = "map";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": If only one index found,"
            + " it finds the location of selected person."
            + "If more than one indexes are present, a map with the route that joins all the locations will be shown."
            + "Parameters: INDEXES (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + "1 2 3 4";

    public static final String MESSAGE_FIND_LOCATION_SUCCESS = "Opened Map";

    private final List<String> targetIndex;

    public LocationCommand(List<String> targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personToFind = new ArrayList<>();

        for (String index: this.targetIndex) {
            try {
                Index person =  ParserUtil.parseIndex(index);
                personToFind.add(lastShownList.get(person.getZeroBased()));
            } catch (IllegalValueException ive) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

        }
        try {
            model.findLocation(personToFind);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException("The target person cannot be missing");
        }

        return new CommandResult(MESSAGE_FIND_LOCATION_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationCommand // instanceof handles nulls
                && this.targetIndex.equals(((LocationCommand) other).targetIndex)); // state check
    }

}
```
###### \java\seedu\address\logic\commands\RemoveTagCommand.java
``` java
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag identified from all person if no index stated."
            + "Else, removes only from those selected indexes.\n"
            + "Example with index: " + COMMAND_WORD + " rm/1 2 t/friends\n"
            + "Example without index: " + COMMAND_WORD + " t/friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final ArrayList<Index> indexes;
    private final Set<Tag> tags;

    public RemoveTagCommand(ArrayList<Index> indexes, Set<Tag> tags) {

        this.indexes = indexes;
        this.tags = tags;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        ObservableList<ReadOnlyPerson> personToRemoveTag = FXCollections.observableArrayList();
        for (Index i : this.indexes) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToRemoveTag.add(lastShownList.get(i.getZeroBased()));
        }
        try {
            model.removeTag(personToRemoveTag, this.tags);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target Tag cannot be missing";
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (IllegalValueException ive) {
            throw new CommandException("The Tag is invalid!");
        }
        if (this.indexes.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, this.tags.toString()));
        } else {
            return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS,
                    this.tags.toString()) + String.format("from %s", personToRemoveTag.toString()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.tags.equals(((RemoveTagCommand) other).tags))
                && this.indexes.equals(((RemoveTagCommand) other).indexes); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String COMMAND_SUCCESS = "Sorted Successfully";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the persons based on their name\n "
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD;

    public SortCommand() { }

    @Override
    public CommandResult execute() {
        model.sortPersons();
        return new CommandResult(COMMAND_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand); // instanceof handles nulls
    }
}
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user's input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMAIL_TO, PREFIX_EMAIL_SUBJECT, PREFIX_EMAIL_BODY);

        if (!arePrefixesPresent(argMultimap, PREFIX_EMAIL_TO)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }

        try {

            String[] toList = ParserUtil.parseEmailToCommand(argMultimap.getAllValues(PREFIX_EMAIL_TO));
            String subject = String.join("", argMultimap.getAllValues(PREFIX_EMAIL_SUBJECT)).replace(" ", "%20");
            String body = String.join("", argMultimap.getAllValues(PREFIX_EMAIL_BODY)).replace(" ",
                    "%20").replace("\"", "\'");

            return new EmailCommand(new NameContainsKeywordsPredicate(Arrays.asList(toList)), subject, body);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\LocationCommandParser.java
``` java
public class LocationCommandParser implements Parser<LocationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LocationCommand
     * and returns an LocationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LocationCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocationCommand.MESSAGE_USAGE));
        }
        String[] indexKeywords = trimmedArgs.split("\\s+");
        return new LocationCommand(Arrays.asList(indexKeywords));

    }

}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParser.java
``` java
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMOVE_INDEX, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
        ArrayList<Index> number = new ArrayList<>();
        try {
            if (arePrefixesPresent(argMultimap, PREFIX_REMOVE_INDEX)) {
                number = ParserUtil.parseIndexes(argMultimap.getAllValues(PREFIX_REMOVE_INDEX));
            }
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            return new RemoveTagCommand(number, tagList);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE)
            );
        }
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts the persons list based on the name
     * @return
     */
    public void sortPersons() {
        persons.sort();
    }
```
###### \java\seedu\address\model\Model.java
``` java
    void removeTag(ObservableList<ReadOnlyPerson> persons, Set<Tag> tag)
            throws PersonNotFoundException, IllegalValueException;
```
###### \java\seedu\address\model\Model.java
``` java
    void findLocation(List<ReadOnlyPerson> person) throws PersonNotFoundException;
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * * Updates the filter of the filtered emailTo list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    String updateEmailRecipient(Predicate<ReadOnlyPerson> predicate);

    void sortPersons();
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void removeTag(ObservableList<ReadOnlyPerson> persons, Set<Tag> tag)
            throws PersonNotFoundException, IllegalValueException {
        int counter = 0;
        if (persons.isEmpty()) {
            persons.setAll(addressBook.getPersonList());
        }
        for (ReadOnlyPerson person : persons) {
            if (!Collections.disjoint(person.getTags(), tag)) {
                Person newPerson = new Person(person);
                Set<Tag> newTags = new HashSet<>(person.getTags());
                for (Tag t: tag) {
                    newTags.remove(t);
                }
                newPerson.setTags(newTags);
                updatePerson(person, newPerson);
                counter++;
            }
        }
        if (counter == 0) {
            throw new IllegalValueException("The Tag is invalid!");
        }
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void findLocation(List<ReadOnlyPerson> person) throws PersonNotFoundException {
        if (person.size() == 0) {
            throw new PersonNotFoundException();
        }
        raise(new FindLocationRequestEvent(person));
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Sorts the persons in the address book by name
     */
    public void sortPersons() {
        addressBook.sortPersons();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public String updateEmailRecipient(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredEmailTo.setPredicate(predicate);
        List<String> validPeeps = new ArrayList<>();
        for (ReadOnlyPerson person : filteredEmailTo) {
            if (person.getEmail() != null && !person.getEmail().value.equalsIgnoreCase("INVALID_EMAIL@INVALID.COM")
                    && !validPeeps.contains(person.getEmail().value)) {
                validPeeps.add(person.getEmail().value);
            }
        }
        return String.join(",", validPeeps);
    }
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean validNames =  keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        boolean validTags = false;
        if (validNames) {
            return validNames;
        }
        for (Tag tag: person.getTags()) {
            validTags = keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword));
            if (validTags) {
                break;
            }
        }
        return validTags;
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the list based on name.
     *
     */
    public void sort() {
        Collections.sort(internalList, Comparator.comparing(person -> person.getName().fullName));
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads Google Maps.
     */
    private void loadPersonLocation(List<ReadOnlyPerson> person) {
        String url = (person.size() == 1) ? GOOGLE_MAPS_URL_PLACE : GOOGLE_MAPS_URL_DIR;
        String result = "";
        for (ReadOnlyPerson ppl: person) {
            String add = ppl.getAddress().toString();
            String[] address = add.split("\\s");
            for (String s: address) {
                if (!s.contains("#")) {
                    result += s;
                    result += "+";
                }
            }
            result += "/";
        }
        loadPage(url + result);
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Event listener for Find Location Events
     * @param event
     */
    @Subscribe
    private void handleFindLocationRequestEvent(FindLocationRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Getting location"));
        loadPersonLocation(event.targetPersons);
    }
```
