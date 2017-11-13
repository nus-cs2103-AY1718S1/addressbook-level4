# KhorSL
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCaseAndCharacters("ABc def", "abc") == true
     *       containsWordIgnoreCaseAndCharacters("ABc def", "DEF") == true
     *       containsWordIgnoreCaseAndCharacters("ABc def.", "DEF") == true
     *       containsWordIgnoreCaseAndCharacters("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCaseAndCharacters(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        preppedWord = preppedWord.replaceAll("[^a-zA-Z0-9\\s]", "");
        preppedWord = preppedWord.trim();

        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1,
                "Word parameter should be a single word");

        String preppedSentence = sentence;
        preppedSentence = preppedSentence.replaceAll("[^a-zA-Z0-9\\s]", "");
        preppedSentence = preppedSentence.trim();
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence : wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a ArrayList of dates that contains in {@code sentence} in the format [DD/MM/YYYY]
     *
     * <br>examples:<pre>
     *              extractDates("20/10/2017")
     *                          -> returns an ArrayList that contains ["20/10/2017"]
     *              extractDates("20/10/2017 20/10/2017")
     *                          -> returns an ArrayList that contains ["20/10/2017", "20/10/2017"]
     *              extractDates("20/10/2017, 20/10/2018")
     *                          -> returns an ArrayList that contains ["20/10/2017", "20/10/2018"]
     *              extractDates("20/10/201720/10/2018")
     *                          -> returns an ArrayList that contains ["20/10/2017", "20/10/2018"]
     *              extractDates("20/10/2017 10:15")
     *                          -> returns an ArrayList that contains ["20/10/2017"]
     *              extractDates("20/10/17")
     *                          -> returns an ArrayList that contains []
     *              </pre>
     *
     * @param sentence cannot be null
     */
    public static ArrayList<String> extractDates(String sentence) {
        requireNonNull(sentence);

        ArrayList<String> extractedDates = new ArrayList<>();
        String preppedSentence = sentence.trim();

        if (!preppedSentence.isEmpty()) {
            Pattern pattern = Pattern.compile("(\\d{2}\\/\\d{2}\\/\\d{4})");
            Matcher matcher = pattern.matcher(preppedSentence);

            while (matcher.find()) {
                String validDateRegex = "^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d$";

                if (matcher.group(1).matches(validDateRegex)) {
                    extractedDates.add(matcher.group(1));
                }
            }
        }

        return extractedDates;
    }

    /**
     * Returns a ArrayList of times that contains in {@code sentence} in the format [HH:mm]
     * <br>examples:<pre>
     *              extractDates("05:50")
     *                          -> returns an ArrayList that contains ["05:50"]
     *              extractDates("21:01 21:03")
     *                          -> returns an ArrayList that contains ["21:01", "21:03"]
     *              extractDates("21:01, 21:03")
     *                          -> returns an ArrayList  that contains ["21:01", "21:03"]
     *              extractDates("10:5010:10")
     *                          -> returns an ArrayList that contains ["10:50", "10:10"]
     *              extractDates("20/10/2017 10:15")
     *                          -> returns an ArrayList that contains ["10:15"]
     *              extractDates("5:15")
     *                          -> returns an ArrayList that contains []
     *              </pre>
     * @param sentence cannot be null
     */
    public static ArrayList<String> extractTimes(String sentence) {
        requireNonNull(sentence);

        ArrayList<String> extractedTimes = new ArrayList<>();
        String preppedSentence = sentence.trim();

        if (!preppedSentence.isEmpty()) {
            Pattern pattern = Pattern.compile("(\\d{2}\\:\\d{2})");
            Matcher matcher = pattern.matcher(preppedSentence);

            while (matcher.find()) {
                String validTimeRegex = "^(0[0-9]|[1][0-9]|[2][0-3])[:](0[0-9]|[1-5][0-9])$";

                if (matcher.group(1).matches(validTimeRegex)) {
                    extractedTimes.add(matcher.group(1));
                }
            }
        }

        return extractedTimes;
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * Format of date: DD/MM/YYY
     * <br>examples:<pre>
     *       containsDate("20/10/2017 15:30", "20/10/2017") == true
     *       containsDate("20/10/2017 15:30", "15:30") == false
     *       containsDate("20/10/2017 15:30", "20//10/2017 14:30") == true
     *       containsDate("20/10/2017 14:30", "20/10") == false //not a full date or time match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsDate(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        preppedSentence = preppedSentence.trim();

        ArrayList<String> extractedDates = extractDates(preppedSentence);
        ArrayList<String> dateInPreppedSentence = new ArrayList<>();
        dateInPreppedSentence.addAll(extractedDates);

        for (String dateInSentence : dateInPreppedSentence) {
            if (dateInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * Format of time: HH:mm
     * <br>examples:<pre>
     *       containsDateAndTime("20/10/2017 15:30", "15:30") == true
     *       containsDateAndTime("20/10/2017 15:30", "20//10/2017 14:30") == true
     *       containsDateAndTime("20/10/2017 14:30", "14:3") == false //not a full date or time match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsTime(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        preppedSentence = preppedSentence.trim();

        ArrayList<String> extractedTimes = extractTimes(preppedSentence);
        ArrayList<String> timeInPreppedSentence = new ArrayList<>();
        timeInPreppedSentence.addAll(extractedTimes);

        for (String timeInSentence : timeInPreppedSentence) {
            if (timeInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }
```
###### \java\seedu\address\logic\commands\AddMultipleCommand.java
``` java
/**
 * Adds multiple persons to the address book.
 */
public class AddMultipleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "multiple";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds multiple person to the address book. "
            + "Parameters: "
            + "FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + "./data/personsToAdd.txt";

    public static final String MESSAGE_PERSON_FORMAT = "Person format in .txt file: "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_AVATAR + "AVATAR IMAGE FILE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_AVATAR + "john_doe.png "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person(s) added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "The persons list contains person(s) that already exists in the address book.";
    public static final String MESSAGE_INVALID_FILE = "Unable to open file '%1$s'";
    public static final String MESSAGE_ERROR_FILE = "Error reading file '%1$s'";

    private ArrayList<Person> toAdd;
    private ArrayList<ReadOnlyPerson> readOnlyPeople;

    /**
     * Creates an AddMultipleCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddMultipleCommand(ArrayList<ReadOnlyPerson> personsList) {
        assert personsList.size() != 0 : "personsList should have more than zero person";
        readOnlyPeople = personsList;
        toAdd = new ArrayList<>();
        for (ReadOnlyPerson person : personsList) {
            toAdd.add(new Person(person));
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        int numberOfPersonsAdded = 0;
        StringBuilder successMessage = new StringBuilder();

        try {
            for (Person personToAdd : toAdd) {
                model.addPerson(personToAdd);
                successMessage.append(System.lineSeparator());
                successMessage.append(personToAdd);
                numberOfPersonsAdded++;
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, successMessage));
        } catch (DuplicatePersonException e) {
            try {
                for (int i = 0; i < numberOfPersonsAdded; i++) {
                    model.deletePerson(readOnlyPeople.get(i));
                }
            } catch (PersonNotFoundException pnfe) {
                assert false : "Unexpected exception " + pnfe.getMessage();
            }
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMultipleCommand // instanceof handles nulls
                && toAdd.equals(((AddMultipleCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_ALIAS
            + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: PREFIX_PERSON_ATTRIBUTE/KEYWORD [MORE_KEYWORDS]... [MORE_PARAMETERS]...\n"
            + "Examples: " + System.lineSeparator()
            + "1) " + COMMAND_WORD + " " + PREFIX_NAME.toString() + "alice bob charlie" + System.lineSeparator()
            + "2) " + COMMAND_WORD + " " + PREFIX_TAG.toString() + "family friends" + System.lineSeparator()
            + "3) " + COMMAND_WORD + " " + PREFIX_NAME.toString() + "alice bob charlie"
            + " " + PREFIX_TAG.toString() + "family friends";

    private final PersonContainsKeywordsPredicate predicate;

    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
```
###### \java\seedu\address\logic\commands\MergeCommand.java
``` java
/**
 * Merge the file given with the default storage file
 */
public class MergeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "merge";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": merge the file given with the default storage file\n"
            + "Parameters: "
            + "XML_FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " ./data/newFile.xml";
    public static final String MESSAGE_SUCCESS = "File merged successfully.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found.";
    public static final String MESSAGE_DATA_CONVERSION_ERROR = "Unable to convert file data.";

    private final String newFilePath;

    public MergeCommand(String newFilePath) {
        this.newFilePath = newFilePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        XmlSerializableAddressBook newFileData;

        try {
            newFileData = XmlFileStorage.loadDataFromSaveFile(new File(newFilePath));
        } catch (FileNotFoundException fne) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (DataConversionException dce) {
            throw new CommandException(MESSAGE_DATA_CONVERSION_ERROR);
        }

        ObservableList<ReadOnlyPerson> newFilePersonList = newFileData.getPersonList();
        model.mergeAddressBook(newFilePersonList);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack, Email emailManager) {
        this.model = model;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeCommand // instanceof handles nulls
                && this.newFilePath.equals(((MergeCommand) other).newFilePath)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddMultipleCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddMultipleCommand object
 */
public class AddMultipleCommandParser implements Parser<AddMultipleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMultipleCommand
     * then parse data from file name given arguments if it exists
     * and returns an AddMultipleCommand object for execution.
     *
     * @param args arguments
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMultipleCommand parse(String args) throws ParseException {
        String filePath = args.trim();
        if (filePath.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMultipleCommand.MESSAGE_USAGE));
        }

        File fileToRead = new File(filePath);
        if (!FileUtil.isFileExists(fileToRead)) {
            throw new ParseException(String.format(AddMultipleCommand.MESSAGE_INVALID_FILE, filePath));
        }

        String data;
        try {
            data = FileUtil.readFromFile(fileToRead);
        } catch (IOException ie) {
            throw new ParseException(String.format(AddMultipleCommand.MESSAGE_ERROR_FILE, filePath));
        }

        String[] lines = data.split(System.lineSeparator());
        ArrayList<ReadOnlyPerson> personsList = parseParticularsIntoPersonsList(lines);

        return new AddMultipleCommand(personsList);
    }

    /**
     * Parses the particulars from {@code argMultimap} into a person
     *
     * @param argMultimap should not be null.
     * @return a person with the parsed attributes
     * @throws ParseException if any of the person particulars contain illegal value
     */
    private ReadOnlyPerson parseParticularsIntoPerson(ArgumentMultimap argMultimap) throws ParseException {
        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Avatar avatar = ParserUtil.parseAvatar(argMultimap.getValue(PREFIX_AVATAR)).get();
            Comment comment = new Comment(""); // add command does not allow adding comments straight away
            Appoint appoint = new Appoint("");

            return new Person(name, phone, email, address, comment, appoint, avatar, tagList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses persons' particulars from {@code lines} and returns {@code personsList}, an ArrayList of ReadOnlyPerson
     *
     * @param lines should not be null. The string array contains the person particulars
     * @return personsList that is being parsed from {@code lines}.
     * @throws ParseException if the compulsory person particulars are not present
     */
    private ArrayList<ReadOnlyPerson> parseParticularsIntoPersonsList(String[] lines) throws ParseException {
        ArrayList<ReadOnlyPerson> personsList = new ArrayList<>();
        for (String eachLine : lines) {
            String toAdd = " " + eachLine;
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(toAdd, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                            PREFIX_TAG, PREFIX_AVATAR);
            if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)) {
                throw new ParseException(String.format(MESSAGE_INVALID_PERSON_FORMAT,
                        AddMultipleCommand.MESSAGE_PERSON_FORMAT));
            }
            ReadOnlyPerson person = parseParticularsIntoPerson(argMultimap);
            personsList.add(person);
        }
        return personsList;
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
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code argsMap} and stores the keywords in {@code mapKeywords}
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private void parseMultiMap(ArgumentMultimap argsMap, HashMap<String, List<String>> mapKeywords, Prefix prefix)
            throws ParseException {
        String trimmedArgs;

        try {
            if (argsMap.getValue(prefix).isPresent()) {
                trimmedArgs = ParserUtil.parseKeywords(argsMap.getValue(prefix)).get().trim();

                if (trimmedArgs.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }

                String[] keywordNameList = trimmedArgs.split("\\s+");
                mapKeywords.put(prefix.toString(), Arrays.asList(keywordNameList));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL,
                PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_COMMENT, PREFIX_APPOINT);

        HashMap<String, List<String>> mapKeywords = new HashMap<>();

        try {
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_NAME);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_TAG);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_EMAIL);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_PHONE);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_ADDRESS);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_COMMENT);
            parseMultiMap(argumentMultimap, mapKeywords, PREFIX_APPOINT);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (mapKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(mapKeywords));
    }

}
```
###### \java\seedu\address\logic\parser\MergeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MergeCommand object
 */
public class MergeCommandParser implements Parser<MergeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MergeCommand
     * and returns an MergeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MergeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MergeCommand.MESSAGE_USAGE));
        }

        return new MergeCommand(trimmedArgs);
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Merges new file data {@code newFilePersonList} to default addressbook storage **/
    void mergeAddressBook(ObservableList<ReadOnlyPerson> newFilePersonList);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void mergeAddressBook(ObservableList<ReadOnlyPerson> newFilePersonList) {
        Boolean isAddressBookChanged = false;
        ObservableList<ReadOnlyPerson> defaultFilePersonList = addressBook.getPersonList();

        for (ReadOnlyPerson newDataPerson : newFilePersonList) {
            boolean isSamePerson = false;
            for (ReadOnlyPerson defaultDataPerson : defaultFilePersonList) {
                if (defaultDataPerson.equals(newDataPerson)) {
                    isSamePerson = true;
                    break;
                }
            }
            if (!isSamePerson) {
                try {
                    addressBook.addPerson(new Person(newDataPerson));
                } catch (DuplicatePersonException dpe) {
                    assert false : "Unexpected exception " + dpe.getMessage();
                }
                isAddressBookChanged = true;
            }
        }

        if (isAddressBookChanged) {
            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            indicateAddressBookChanged();
        }
    }
```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name}, {@code Tag}, {@code Email}, {@code Phone},
 * {@code Address}, {@code Comment} and {@code Appoint} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final HashMap<String, List<String>> keywords;

    public PersonContainsKeywordsPredicate(HashMap<String, List<String>> keywords) {
        this.keywords = keywords;
    }

    /**
     * Checks that {@code person} contain the name predicates in {@code keywords}. Return true if there is any match.
     *
     * @param person should not be null
     * @return result that indicates if {@code person} contain any of the name predicates in {@code keywords}.
     */
    private boolean checkPersonContainsNamePredicate(ReadOnlyPerson person) {
        boolean result = false;
        if (keywords.containsKey(PREFIX_NAME.toString()) && !keywords.get(PREFIX_NAME.toString()).isEmpty()) {
            result = keywords.get(PREFIX_NAME.toString()).stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        }
        return result;
    }

    /**
     * Checks that {@code person} contain the tag predicates in {@code keywords}. Return true if there is any match.
     *
     * @param person should not be null
     * @return result that indicates if {@code person} contain any of the name predicates in {@code keywords}.
     */
    private boolean checkPersonContainsTagPredicate(ReadOnlyPerson person) {
        boolean result = false;
        if (keywords.containsKey(PREFIX_TAG.toString()) && !keywords.get(PREFIX_TAG.toString()).isEmpty()) {
            result = person.containTags(keywords.get(PREFIX_TAG.toString()));
        }
        return result;
    }

    /**
     * Checks that {@code person} contain the email predicates in {@code keywords}. Return true if there is any match.
     *
     * @param person should not be null
     * @return result that indicates if {@code person} contain any of the email predicates in {@code keywords}.
     */
    private boolean checkPersonContainsEmailPredicate(ReadOnlyPerson person) {
        boolean result = false;
        if (keywords.containsKey(PREFIX_EMAIL.toString()) && !keywords.get(PREFIX_EMAIL.toString()).isEmpty()) {
            result = keywords.get(PREFIX_EMAIL.toString()).stream()
                    .anyMatch(keyword -> person.getEmail().value.contains(keyword));
        }
        return result;
    }

    /**
     * Checks that {@code person} contain the phone predicates in {@code keywords}. Return true if there is any match.
     *
     * @param person should not be null
     * @return result that indicates if {@code person} contain any of the phone predicates in {@code keywords}.
     */
    private boolean checkPersonContainsPhonePredicate(ReadOnlyPerson person) {
        boolean result = false;
        if (keywords.containsKey(PREFIX_PHONE.toString()) && !keywords.get(PREFIX_PHONE.toString()).isEmpty()) {
            result = keywords.get(PREFIX_PHONE.toString()).stream()
                    .anyMatch(keyword -> person.getPhone().value.contains(keyword));
        }
        return result;
    }

    /**
     * Checks that {@code person} contain the address predicates in {@code keywords}. Return true if there is any match.
     *
     * @param person should not be null
     * @return result that indicates if {@code person} contain any of the address predicates in {@code keywords}.
     */
    private boolean checkPersonContainsAddressPredicate(ReadOnlyPerson person) {
        boolean result = false;
        if (keywords.containsKey(PREFIX_ADDRESS.toString()) && !keywords.get(PREFIX_ADDRESS.toString()).isEmpty()) {
            result = keywords.get(PREFIX_ADDRESS.toString()).stream()
                    .anyMatch(keyword -> person.getAddress().value.toLowerCase().contains(keyword.toLowerCase()));
        }
        return result;
    }

    /**
     * Checks that {@code person} contain the comment predicates in {@code keywords}. Return true if there is any match.
     *
     * @param person should not be null
     * @return result that indicates if {@code person} contain any of the comment predicates in {@code keywords}.
     */
    private boolean checkPersonContainsCommentPredicate(ReadOnlyPerson person) {
        boolean result = false;
        if (keywords.containsKey(PREFIX_COMMENT.toString()) && !keywords.get(PREFIX_COMMENT.toString()).isEmpty()) {
            result = keywords.get(PREFIX_COMMENT.toString()).stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCaseAndCharacters(
                            person.getComment().value, keyword));
        }
        return result;
    }

    /**
     * Checks that {@code person} contain the appoint predicates in {@code keywords}. Return true if there is any match.
     *
     * @param person should not be null
     * @return result that indicates if {@code person} contain any of the appoint predicates in {@code keywords}.
     */
    private boolean checkPersonContainsAppointPredicate(ReadOnlyPerson person) {
        boolean result = false;
        if (keywords.containsKey(PREFIX_APPOINT.toString()) && !keywords.get(PREFIX_APPOINT.toString()).isEmpty()) {
            result = keywords.get(PREFIX_APPOINT.toString()).stream()
                    .anyMatch(keyword -> StringUtil.containsDate(person.getAppoint().value, keyword));

            result = result || keywords.get(PREFIX_APPOINT.toString()).stream()
                    .anyMatch(keyword -> StringUtil.containsTime(person.getAppoint().value, keyword));
        }
        return result;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return checkPersonContainsNamePredicate(person)
                || checkPersonContainsPhonePredicate(person)
                || checkPersonContainsAddressPredicate(person)
                || checkPersonContainsAppointPredicate(person)
                || checkPersonContainsEmailPredicate(person)
                || checkPersonContainsTagPredicate(person)
                || checkPersonContainsCommentPredicate(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    private final Logger logger = LogsCenter.getLogger(this.getClass());
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        final List<ReadOnlyPerson> persons = new ArrayList<>();
        for (XmlAdaptedPerson rop : this.persons) {
            try {
                persons.add(rop.toModelType());
            } catch (NullPointerException | IllegalValueException e) {
                logger.warning("Illegal data found in storage.");
            }
        }
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(persons));
    }
```
