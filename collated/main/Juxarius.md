# Juxarius
###### \java\seedu\address\commons\events\ui\InsuranceClickedEvent.java
``` java
/**
 * Represents a click on one of the names in Insurance Profile
 */
public class InsuranceClickedEvent extends BaseEvent {


    private final ReadOnlyInsurance insurance;

    public InsuranceClickedEvent(ReadOnlyInsurance insurance) {
        this.insurance = insurance;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }


    public ReadOnlyInsurance getInsurance() {
        return insurance;
    }
}

```
###### \java\seedu\address\commons\events\ui\JumpToListRequestEvent.java
``` java
    public final PanelChoice panelChoice;

    public JumpToListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
        this.panelChoice = PanelChoice.PERSON;
    }

    public JumpToListRequestEvent(Index targetIndex, PanelChoice panelChoice) {
        this.targetIndex = targetIndex.getZeroBased();
        this.panelChoice = panelChoice;
    }
```
###### \java\seedu\address\commons\util\AppUtil.java
``` java
    /**
     * Choice of panel to be selected
     */
    public enum PanelChoice {
        PERSON, INSURANCE
    }
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Deletes the person/insurance identified by the index number used in the last listing.\n"
            + "An additional argument left/l/right/r/person/p/insurance/i can be added to indicate\n"
            + "choice of left or right panel. Choice is person panel by default.\n"
            + "Parameters: INDEX (must be a positive integer) [PANEL_CHOICE]\n"
            + "Example: " + COMMAND_WORD + " 1 insurance";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_INSURANCE_SUCCESS = "Deleted Insurance %1$s";

    private final Index targetIndex;
    private final PanelChoice panelChoice;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.panelChoice = PanelChoice.PERSON;
    }


    public DeleteCommand(Index targetIndex, PanelChoice panelChoice) {
        this.targetIndex = targetIndex;
        this.panelChoice = panelChoice;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyInsurance> insuranceList = model.getFilteredInsuranceList();

        if (panelChoice == PanelChoice.PERSON && targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (panelChoice == PanelChoice.INSURANCE && targetIndex.getZeroBased() >= insuranceList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INSURANCE_DISPLAYED_INDEX);
        }

        String commandResultMessage = "";
        try {
            if (panelChoice == PanelChoice.PERSON) {
                ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
                model.deletePerson(personToDelete);
                commandResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
            } else if (panelChoice == PanelChoice.INSURANCE) {
                ReadOnlyInsurance insuranceToDelete = insuranceList.get(targetIndex.getZeroBased());
                model.deleteInsurance(insuranceToDelete);
                commandResultMessage = String.format(MESSAGE_DELETE_INSURANCE_SUCCESS,
                        insuranceToDelete.getInsuranceName());
            }
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (InsuranceNotFoundException infe) {
            assert false : "The target insurance cannot be missing";
        }
        return new CommandResult(commandResultMessage);
    }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Set<Tag> updatedTags = personToEdit.getTags();

        if (editPersonDescriptor.getTagsToDel().isPresent()) {
            for (Tag tag : editPersonDescriptor.getTagsToDel().get()) {
                if (tag.getTagName().equals("all")) {
                    updatedTags.clear();
                }
            }
            updatedTags.removeAll(editPersonDescriptor.getTagsToDel().get());
        }

        if (editPersonDescriptor.getTags().isPresent()) {
            updatedTags.addAll(editPersonDescriptor.getTags().get());
        }
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedDateOfBirth, updatedGender, updatedTags, personToEdit.getLifeInsuranceIds());
    }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setTagsToDel(Set<Tag> tagsToDel) {
            this.tagsToDel = tagsToDel;
        }

        public Optional<Set<Tag>> getTagsToDel() {
            return Optional.ofNullable(tagsToDel);
        }
```
###### \java\seedu\address\logic\commands\SelectCommand.java
``` java
    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.panelChoice = PanelChoice.PERSON;
    }

    public SelectCommand(Index targetIndex, PanelChoice panelChoice) {
        this.targetIndex = targetIndex;
        this.panelChoice = panelChoice;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyInsurance> insuranceList = model.getFilteredInsuranceList();

        if (panelChoice == PanelChoice.PERSON && targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (panelChoice == PanelChoice.INSURANCE && targetIndex.getZeroBased() >= insuranceList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INSURANCE_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex, panelChoice));
        return new CommandResult(String.format(
                panelChoice == PanelChoice.PERSON ? MESSAGE_SELECT_PERSON_SUCCESS : MESSAGE_SELECT_INSURANCE_SUCCESS,
                targetIndex.getOneBased()));
    }
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;

        } catch (EmptyFieldException efe) {
            // index check was bypassed, this checks the index before filling empty prefix
            if (efe.getIndex().getOneBased() > model.getFilteredPersonList().size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            commandText = getAutoFilledCommand(commandText, efe.getIndex());
            throw efe;
        } catch (MissingPrefixException mpe) {
            // this assertion is because this exception should only be thrown by the addli command
            // to change if this exception is used elsewhere
            final String inputToTest = commandText;
            assert Arrays.stream(AddLifeInsuranceCommand.COMMAND_WORDS).anyMatch(commandWord ->
                inputToTest.contains(commandWord));
            commandText = getCommandWithFilledPrefixes(commandText);
            throw mpe;
        } finally {
            history.add(commandText);
        }
    }

    /**
     * Fill in the prefixes that require filling
     * @param commandText original input command text
     * @return command with empty prefixes
     */
    private String getCommandWithFilledPrefixes(String commandText) {
        String filledText = commandText + " ";
        for (Prefix prefix : PREFIXES_INSURANCE) {
            if (!commandText.contains(prefix.getPrefix())) {
                filledText += prefix.getPrefix() + " ";
            }
        }
        return filledText;
    }

    /**
     * Replaces the given command text with filled command text
     * @param commandText original input command text
     * @param index index of person to edit
     * @return filled command
     */
    private String getAutoFilledCommand(String commandText, Index index) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(index.getZeroBased());
        for (Prefix prefix : PREFIXES_PERSON) {
            String prefixInConcern = prefix.getPrefix();
            if (commandText.contains(prefixInConcern)) {
                String replacementText = prefixInConcern + person.getDetailByPrefix(prefix) + " ";
                commandText = commandText.replaceFirst(prefixInConcern, replacementText);
            }
        }
        if (commandText.contains(" " + PREFIX_TAG.getPrefix())) {
            String formattedTags = PREFIX_TAG.getPrefix()
                    + person.getDetailByPrefix(PREFIX_TAG).replaceAll(" ", " " + PREFIX_TAG) + " ";
            commandText = commandText.replaceFirst(PREFIX_TAG.getPrefix(), formattedTags);
        }
        if (commandText.contains(PREFIX_DELTAG.getPrefix())) {
            String formattedTags = PREFIX_DELTAG.getPrefix()
                    + person.getDetailByPrefix(PREFIX_DELTAG).replaceAll(" ", " " + PREFIX_DELTAG) + " ";
            commandText = commandText.replaceFirst(PREFIX_DELTAG.getPrefix(), formattedTags);
        }
        return commandText.trim();
    }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
    /**
     * Enumerator list to define the types of commands.
     */
    private enum CommandType {
        ADD,
        ADDLI,
        CLEAR,
        DEL,
        EDIT,
        EXIT,
        FIND,
        PFIND,
        HELP,
        HISTORY,
        LIST,
        PRINT,
        REDO,
        UNDO,
        SELECT,
        WHY,
        NONE
    }

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static HashMap<String, CommandType> commandWordMap = new HashMap<>();

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        CommandType commandType = getCommandType(commandWord.toLowerCase());

        switch (commandType) {

        case ADD:
            return new AddCommandParser().parse(arguments);

        case ADDLI:
            return new AddLifeInsuranceCommandParser().parse(arguments);

        case EDIT:
            return new EditCommandParser().parse(arguments);

        case SELECT:
            return new SelectCommandParser().parse(arguments);

        case DEL:
            return new DeleteCommandParser().parse(arguments);

        case CLEAR:
            return new ClearCommand();

        case FIND:
            return new FindCommandParser().parse(arguments);

        case PFIND:
            return new PartialFindCommandParser().parse(arguments);

        case LIST:
            return new ListCommand();

        case HISTORY:
            return new HistoryCommand();

        case EXIT:
            return new ExitCommand();

        case HELP:
            return new HelpCommand();

        case UNDO:
            return new UndoCommand();

        case REDO:
            return new RedoCommand();

        case PRINT:
            return new PrintCommandParser().parse(arguments);

        case WHY:
            return new WhyCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Set up a map for O(1) command word to commandType access
     */
    private void setUpCommandWordMap() {
        Arrays.stream(AddCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.ADD));
        Arrays.stream(AddLifeInsuranceCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.ADDLI));
        Arrays.stream(ClearCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.CLEAR));
        Arrays.stream(DeleteCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.DEL));
        Arrays.stream(EditCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.EDIT));
        Arrays.stream(ExitCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.EXIT));
        Arrays.stream(FindCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.FIND));
        Arrays.stream(PartialFindCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.PFIND));
        Arrays.stream(HelpCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.HELP));
        Arrays.stream(HistoryCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.HISTORY));
        Arrays.stream(ListCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.LIST));
        Arrays.stream(PrintCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.PRINT));
        Arrays.stream(RedoCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.REDO));
        Arrays.stream(UndoCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.UNDO));
        Arrays.stream(SelectCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.SELECT));
        Arrays.stream(WhyCommand.COMMAND_WORDS)
                .forEach(commandWord -> commandWordMap.put(commandWord, CommandType.WHY));
    }


    /**
     * Searches the map for the commandWord
     * @param commandWord
     * @return enumerated value for the switch statement to process
     */

    private CommandType getCommandType(String commandWord) {
        if (commandWordMap.isEmpty()) {
            setUpCommandWordMap();
        }
        return commandWordMap.getOrDefault(commandWord, CommandType.NONE);
    }
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Set<Prefix> PREFIXES_PERSON = new HashSet<>(Arrays.asList(
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_DOB, PREFIX_GENDER));
    public static final Set<Prefix> PREFIXES_INSURANCE = new LinkedHashSet<>(Arrays.asList(
            PREFIX_NAME, PREFIX_OWNER, PREFIX_INSURED, PREFIX_BENEFICIARY,
            PREFIX_PREMIUM, PREFIX_CONTRACT_FILE_NAME, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE));
}
```
###### \java\seedu\address\logic\parser\DateParser.java
``` java
/**
 * Parses a string into a LocalDate
 */
public class DateParser {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date input must have at least 2 arguments.";
    public static final String MESSAGE_INVALID_MONTH = "Month input is invalid.";
    public static final String MESSAGE_INVALID_DAY = "Day input is invalid.";
    public static final String MESSAGE_INVALID_YEAR = "Year input is invalid.";

    public static final String[] MONTH_NAME_SHORT = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final String[] MONTH_NAME_LONG = {"january", "february", "march",
        "april", "may", "june", "july", "august", "september", "october", "november", "december"};
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");

    /**
     * Parses the given {@code String} of arguments to produce a LocalDate object. Input string
     * must be in the Day-Month-Year format where the month can be a number or the name
     * and the year can be input in 2-digit or 4-digit format.
     * @throws IllegalValueException if the format is not correct.
     */
    /**
     * Parses input dob string
     */
    public LocalDate parse(String dob) throws IllegalValueException {
        List<String> arguments = Arrays.asList(dob.split("[\\s-/.,]"));
        if (arguments.size() < 2) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        String day = arguments.get(0);
        String month = arguments.get(1);
        String year = arguments.size() > 2 ? arguments.get(2) : String.valueOf(LocalDate.now().getYear());
        return LocalDate.parse(getValidDay(day) + " " + getValidMonth(month) + " " + getValidYear(year),
                DATE_FORMAT);
    }

    /**
     *
     * @param year 2 or 4 digit string
     * @return
     * @throws IllegalValueException
     */
    public String getValidYear(String year) throws IllegalValueException {
        int currYear = LocalDate.now().getYear();
        if (year.length() > 4) {
            year = year.substring(0, 4);
        }
        if (!year.matches("\\d+") || (year.length() != 2 && year.length() != 4)) {
            throw new IllegalValueException(MESSAGE_INVALID_YEAR);
        } else if (year.length() == 2) {
            int iYear = Integer.parseInt(year);
            // Change this if condition to edit your auto-correcting range for 2-digit year inputs
            if (iYear > currYear % 100) {
                return Integer.toString(iYear + (currYear / 100 - 1) * 100);
            } else {
                return Integer.toString(iYear + currYear / 100 * 100);
            }
        } else {
            return year;
        }
    }

    public String getValidDay(String day) throws IllegalValueException {
        if (Integer.parseInt(day) > 31) {
            throw new IllegalValueException(MESSAGE_INVALID_DAY);
        }
        if (day.length() == 1) {
            return "0" + day;
        } else if (day.length() == 2) {
            return day;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_DAY);
        }
    }

    public String getValidMonth(String month) throws IllegalValueException {
        int iMonth;
        if (month.matches("\\p{Alpha}+")) {
            iMonth = getMonth(month);
        } else {
            iMonth = Integer.parseInt(month);
        }
        if (iMonth > 12 || iMonth < 1) {
            throw new IllegalValueException(MESSAGE_INVALID_MONTH);
        } else {
            return MONTH_NAME_SHORT[iMonth - 1];
        }
    }

    /**
     * finds int month from string month name
     */
    public int getMonth(String monthName) throws IllegalValueException {
        for (int i = 0; i < MONTH_NAME_LONG.length; i++) {
            if (monthName.toLowerCase().equals(MONTH_NAME_LONG[i].toLowerCase())
                    || monthName.toLowerCase().equals(MONTH_NAME_SHORT[i].toLowerCase())) {
                return i + 1;
            }
        }
        throw new IllegalValueException(MESSAGE_INVALID_MONTH);
    }

    /**
     * Takes a LocalDate and produces it in a nice format
     * @param date
     * @return
     */
    public static String dateString(LocalDate date) {
        return date.format(DATE_FORMAT);
    }
}
```
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            String[] inputs = args.split("\\s");
            String indexString = Arrays.stream(inputs).filter(s -> s.matches("\\d+"))
                    .findFirst().orElseThrow(() -> new IllegalValueException("No index found!"));
            Optional<String> panelString = Arrays.stream(inputs).filter(s -> s.matches("\\p{Alpha}+")).findFirst();
            PanelChoice panelChoice;
            if (inputs.length < 2) {
                throw new IllegalValueException("Too little input arguments!");
            } else if (inputs.length > 2 && panelString.isPresent()) {
                panelChoice = ParserUtil.parsePanelChoice(panelString.get().toLowerCase());
            } else {
                panelChoice = PanelChoice.PERSON;
            }
            Index index = ParserUtil.parseIndex(indexString);
            return new DeleteCommand(index, panelChoice);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_DOB, PREFIX_GENDER, PREFIX_TAG, PREFIX_DELTAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
            parseDetagsForEdit(argMultimap.getAllValues(PREFIX_DELTAG)).ifPresent(editPersonDescriptor::setTagsToDel);
        } catch (EmptyFieldException efe) {
            throw new EmptyFieldException(efe, index);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * return an EmptyFieldException which will trigger an autofill
     */
    private Optional<Set<Tag>> parseDetagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        if (tags.size() == 1 && tags.contains("")) {
            throw new EmptyFieldException(PREFIX_DELTAG);
        }
        return Optional.of(ParserUtil.parseTags(tags));
    }

}
```
###### \java\seedu\address\logic\parser\exceptions\EmptyFieldException.java
``` java
/**
 * Signifies that a certain field is empty but the prefix is specified.
 */

public class EmptyFieldException extends ParseException {

    private Prefix emptyFieldPrefix;
    private Index index;
    /**
     * @param message should contain information on the empty field.
     */
    public EmptyFieldException(String message) {
        super(message);
    }
    /**
     * @param emptyFieldPrefix contains the prefix of the field that is empty.
     */
    public EmptyFieldException(Prefix emptyFieldPrefix) {
        super(emptyFieldPrefix.getPrefix() + " field is empty");
        this.emptyFieldPrefix = emptyFieldPrefix;
    }

    /**
     * @param index is the oneBasedIndex of the person in concern
     */
    public EmptyFieldException(EmptyFieldException efe, Index index) {
        super(efe.getMessage());
        this.emptyFieldPrefix = efe.getEmptyFieldPrefix();
        this.index = index;
    }

    public Prefix getEmptyFieldPrefix() {
        return emptyFieldPrefix;
    }

    public Index getIndex() {
        return index;
    }
}
```
###### \java\seedu\address\logic\parser\exceptions\MissingPrefixException.java
``` java

/**
 * To signal a request for autofilling prefixes in add insurance command due to the multitude of prefixes required
 */
public class MissingPrefixException extends ParseException {
    /**
     * Signal to inform autofilling of prefixes and/or re positioning of caret
     * @param message
     */
    public MissingPrefixException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java

    /**
     * @param input String which indicates the user's choice of panel
     * @return PanelChoice enumerator to indicate to the program the user's choice
     * @throws IllegalValueException
     */
    public static PanelChoice parsePanelChoice(String input) throws IllegalValueException {
        if (Arrays.stream(SELECT_ARGS_INSURANCE).anyMatch(key -> key.equals(input))) {
            return PanelChoice.INSURANCE;
        } else if (Arrays.stream(SELECT_ARGS_PERSON).anyMatch(key -> key.equals(input))) {
            return PanelChoice.PERSON;
        } else {
            throw new IllegalValueException("Invalid panel choice");
        }
    }
```
###### \java\seedu\address\logic\parser\SelectCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * Complicated code is due to the intention of giving the user the freedom to place arguments in any format
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        try {
            String[] inputs = args.split("\\s");
            String indexString = Arrays.stream(inputs).filter(s -> s.matches("\\d+"))
                    .findFirst().orElseThrow(() -> new IllegalValueException("No index found!"));
            Optional<String> panelString = Arrays.stream(inputs).filter(s -> s.matches("\\p{Alpha}+")).findFirst();
            PanelChoice panelChoice;
            if (inputs.length < 2) {
                throw new IllegalValueException("Too little input arguments!");
            } else if (inputs.length > 2 && panelString.isPresent()) {
                panelChoice = ParserUtil.parsePanelChoice(panelString.get().toLowerCase());
            } else {
                panelChoice = PanelChoice.PERSON;
            }
            Index index = ParserUtil.parseIndex(indexString);
            return new SelectCommand(index, panelChoice);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * @param target insurance to be deleted
     * @throws InsuranceNotFoundException
     */
    public void deleteInsurance(ReadOnlyInsurance target) throws InsuranceNotFoundException {
        if (lifeInsuranceMap.remove(target)) {
            syncWithUpdate();
        } else {
            throw new InsuranceNotFoundException();
        }
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Function to update the overall links between insurances and persons after a change in LISA
     */
    private void syncWithUpdate() {
        syncMasterLifeInsuranceMapWith(persons);
        try {
            syncMasterPersonListWith(lifeInsuranceMap);
        } catch (InsuranceNotFoundException infe) {
            assert false : "AddressBooks should not have duplicate insurances";
        }
    }

    private void clearAllPersonsInsuranceIds() {
        persons.forEach(p -> p.clearLifeInsuranceIds());
    }
```
###### \java\seedu\address\model\insurance\LifeInsurance.java
``` java
    private LocalDate signingDate;
    private LocalDate expiryDate;
    private StringProperty signingDateString;
    private StringProperty expiryDateString;
```
###### \java\seedu\address\model\insurance\Premium.java
``` java
    @Override
    public String toString() {
        return "S$ " + String.format("%.2f", value);
    }
```
###### \java\seedu\address\model\insurance\UniqueLifeInsuranceList.java
``` java

    /**
     * sort insurance in descending order according to premium,
     * change the sign in the return statement to make it ascending
     */
    public void sortInsurances() {
        internalList.sort((insurance1, insurance2) -> {
            if (insurance1.getPremium().equals(insurance2.getPremium())) {
                return 0;
            } else {
                return insurance1.getPremium().toDouble() < insurance2.getPremium().toDouble() ? 1 : -1;
            }
        });
    }
```
###### \java\seedu\address\model\insurance\UniqueLifeInsuranceMap.java
``` java

    /**
     * @param insurance insurance to be deleted
     * @return
     */
    public boolean remove(ReadOnlyInsurance insurance) {
        requireNonNull(insurance);
        boolean removeSuccess = internalMap.remove(insurance.getId(), insurance);
        syncMappedListWithInternalMap();
        return removeSuccess;
    }

```
###### \java\seedu\address\model\person\Address.java
``` java
        if (address.isEmpty()) {
            throw new EmptyFieldException(PREFIX_ADDRESS);
        }
```
###### \java\seedu\address\model\person\DateOfBirth.java
``` java
    public static final String MESSAGE_DOB_CONSTRAINTS =
            "Please enter in Day Month Year format where the month can be a number or the name"
                    + " and the year can be input in 2-digit or 4-digit format.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DOB_VALIDATION_REGEX = "\\d+[\\s-./,]\\p{Alnum}+[\\s-./,]\\d+.*";

    public final LocalDate dateOfBirth;
    private boolean dateSet;

    /**
     * Initialise a DateOfBirth object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public DateOfBirth() {
        this.dateOfBirth = LocalDate.now();
        this.dateSet = false;
    }
```
###### \java\seedu\address\model\person\DateOfBirth.java
``` java
        this.dateOfBirth = new DateParser().parse(dob);
        this.dateSet = true;
    }
```
###### \java\seedu\address\model\person\DateOfBirth.java
``` java
    @Override
    public String toString() {
        return dateSet ? dateOfBirth.format(DateParser.DATE_FORMAT) : "";
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
        } catch (EmptyFieldException efe) {
            initHistory();
            // autofill function triggered
            logger.info("Autofill triggered: " + commandTextField.getText());
            historySnapshot.next();
            commandTextField.setText(historySnapshot.previous());
            commandTextField.positionCaret(commandTextField.getText().length());
            raise(new NewResultAvailableEvent("Autofilled!", false));

        } catch (MissingPrefixException mpe) {
            initHistory();
            logger.info("Autofilling Prefixes!");
            historySnapshot.next();
            String filledCommand = historySnapshot.previous();
            int unfilledPrefixPosition = filledCommand.indexOf("/ ") + 1;
            commandTextField.setText(filledCommand.trim());
            commandTextField.positionCaret(unfilledPrefixPosition);
            raise(new NewResultAvailableEvent(mpe.getMessage(), true));
        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }
```
###### \java\seedu\address\ui\InsuranceCard.java
``` java
    private void setPremiumLevel(Double premium) {
        if (premium > 500.0) {
            insuranceName.getStyleClass().add("gold-insurance-header");
            index.getStyleClass().add("gold-insurance-header");
        } else if (premium > 100.0) {
            insuranceName.getStyleClass().add("silver-insurance-header");
            index.getStyleClass().add("silver-insurance-header");
        } else {
            insuranceName.getStyleClass().add("normal-insurance-header");
            index.getStyleClass().add("normal-insurance-header");
        }
    }
```
###### \java\seedu\address\ui\InsuranceIdLabel.java
``` java
    private void setPremiumLevel(Double premium) {
        if (premium > 500.0) {
            insuranceId.getStyleClass().add("gold-insurance-header");
        } else if (premium > 100.0) {
            insuranceId.getStyleClass().add("silver-insurance-header");
        } else {
            insuranceId.getStyleClass().add("normal-insurance-header");
        }
    }
```
###### \java\seedu\address\ui\InsuranceListPanel.java
``` java
    @Subscribe
    private void handleSwitchToProfilePanelRequestEvent(SwitchToProfilePanelRequestEvent event) {
        insuranceListView.getSelectionModel().clearSelection();
    }

    /**
     * Scrolls to the {@code InsuranceCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            insuranceListView.scrollTo(index);
            insuranceListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        if (event.panelChoice == PanelChoice.INSURANCE) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            scrollTo(event.targetIndex);
        }
    }

    @Subscribe
    private void handleInsuranceClickedEvent(InsuranceClickedEvent event) {
        ObservableList<InsuranceCard> insurances = insuranceListView.getItems();
        for (int i = 0; i < insurances.size(); i++) {
            if (insurances.get(i).getInsurance().getId().equals(event.getInsurance().getId())) {
                insuranceListView.scrollTo(i);
                insuranceListView.getSelectionModel().select(i);
                break;
            }
        }
    }
    // weird phenomenon that a filteredList does not contain elements in the original list and cannot be used
    // in the select command
```
###### \java\seedu\address\ui\InsuranceProfilePanel.java
``` java

/**
 * Profile panel for insurance when the respective insurance is selected
 */
public class InsuranceProfilePanel extends UiPart<Region> {
    private static final String FXML = "InsuranceProfilePanel.fxml";
    private static final String PDFFOLDERPATH = "data/";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private File insuranceFile;
    private ReadOnlyInsurance insurance;

    @FXML
    private ScrollPane insuranceScrollPane;
    @FXML
    private AnchorPane insuranceProfilePanel;
    @FXML
    private Label insuranceName;
    @FXML
    private Label owner;
    @FXML
    private Label insured;
    @FXML
    private Label beneficiary;
    @FXML
    private Label premium;
    @FXML
    private Label signingDate;
    @FXML
    private Label expiryDate;
    @FXML
    private Label contractName;

    public InsuranceProfilePanel() {
        super(FXML);
        insuranceScrollPane.setFitToWidth(true);
        insuranceProfilePanel.prefWidthProperty().bind(insuranceScrollPane.widthProperty());
        insuranceProfilePanel.prefHeightProperty().bind(insuranceScrollPane.heightProperty());
        setAllToNull();
        registerAsAnEventHandler(this);
    }

```
###### \java\seedu\address\ui\InsuranceProfilePanel.java
``` java
    private void setPremiumLevel(Double premium) {
        insuranceName.getStyleClass().clear();
        insuranceName.getStyleClass().add("insurance-profile-header");
        if (premium > 500.0) {
            insuranceName.getStyleClass().add("gold-insurance-header");
        } else if (premium > 100.0) {
            insuranceName.getStyleClass().add("silver-insurance-header");
        } else {
            insuranceName.getStyleClass().add("normal-insurance-header");
        }
    }

    @Subscribe
    private void handleSwitchToInsurancePanelRequestEvent(InsurancePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        enableNameToProfileLink(event.getInsurance());
        initializeContractFile(event.getInsurance());
        bindListeners(event.getInsurance());
        setPremiumLevel(event.getInsurance().getPremium().toDouble());
        raise(new SwitchToInsurancePanelRequestEvent());
    }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    @Subscribe
    private void handleInsurancePanelSelectionChangedEvent(InsurancePanelSelectionChangedEvent event) {
        personListView.getSelectionModel().clearSelection();
    }

    @Subscribe
    private void handlePersonNameClickedEvent(PersonNameClickedEvent event) {
        FilteredList<PersonCard> filtered = personListView.getItems().filtered(p ->
                p.person.getName().toString().equals(event.getName())
        );
        if (filtered.size() < 1) {
            return;
        } else {
            personListView.scrollTo(filtered.get(0));
            personListView.getSelectionModel().select(filtered.get(0));
        }
    }
```
###### \resources\view\DarkTheme.css
``` css
.profile-header {
    -fx-font-size: 35pt;
    -fx-font-weight: bolder;
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.profile-field {
    -fx-font-size: 15pt;
    -fx-font-family: "Segoe UI SemiLight";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

#insuranceProfilePanel .static-labels {
    -fx-font-size: 15pt;
    -fx-font-family: "Segoe UI SemiBold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

#insuranceProfilePanel .dynamic-labels, .valid-file, .missing-file {
    -fx-font-size: 15pt;
    -fx-font-family: "Segoe UI SemiLight";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.static-insurance-labels {
    -fx-font-size: 13pt;
    -fx-font-family: "Segoe UI Bold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

#insuranceListView #owner:hover, #insured:hover, #beneficiary:hover {
    -fx-font-size: 13pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: #ff4500;
    -fx-opacity: 1;
}

#insuranceProfilePanel #owner:hover, #insuranceProfilePanel #insured:hover, #insuranceProfilePanel #beneficiary:hover, .valid-file:hover {
    -fx-text-fill: #ff4500;
}

.insurance-profile-header {
    -fx-font-size: 30pt;
    -fx-font-family: "Impact";
    -fx-opacity: 1;
}

#insuranceProfilePanel .gold-insurance-header {
    -fx-text-fill: #daa520;
}

#insuranceProfilePanel .silver-insurance-header {
    -fx-text-fill: #a9a9a9;
}

#insuranceProfilePanel .normal-insurance-header {
    -fx-text-fill: white;
}

#insuranceListView .insurance-header {
    -fx-font-size: 17pt;
    -fx-font-family: "Impact";
    -fx-opacity: 1;
}

#insuranceListView .gold-insurance-header {
    -fx-text-fill: #daa520;
}

#insuranceListView .silver-insurance-header {
    -fx-text-fill: #a9a9a9;
}

#insuranceListView .normal-insurance-header {
    -fx-text-fill: white;
}
```
###### \resources\view\DarkTheme.css
``` css
#insuranceListView {
    -fx-background-color: derive(#1d1d1d, 20%);
}
```
###### \resources\view\InsuranceCard.fxml
``` fxml
         <VBox alignment="CENTER_LEFT" GridPane.columnIndex="1">
            <children>
               <HBox>
                  <children>
                     <Label fx:id="owner" styleClass="particular-link" text="\$owner" />
                  </children>
               </HBox>
               <HBox>
                  <children>
                     <Label fx:id="insured" styleClass="particular-link" text="\$insured" />
                  </children>
               </HBox>
               <HBox>
                  <children>
                     <Label fx:id="beneficiary" styleClass="particular-link" text="\$beneficiary" />
                  </children>
               </HBox>
               <HBox>
                  <children>
                     <Label fx:id="premium" styleClass="particular-link" text="\$premium" />
                  </children>
               </HBox>
            </children>
         </VBox>
```
###### \resources\view\InsuranceProfilePanel.fxml
``` fxml
<StackPane prefHeight="439.0" prefWidth="556.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <ScrollPane fx:id="insuranceScrollPane" styleClass="anchor-pane">
        <content>
            <AnchorPane fx:id="insuranceProfilePanel" styleClass="anchor-pane">
               <children>
                  <Label fx:id="insuranceName" layoutX="5.0" layoutY="5.0" styleClass="insurance-profile-header" text="\$insuranceName" />
                  <HBox layoutX="28.0" layoutY="63.0" prefHeight="342.0" prefWidth="500.0" spacing="10.0">
                     <children>
                        <VBox prefHeight="201.0" prefWidth="131.0">
                           <children>
                              <Label styleClass="static-labels" text="Owner" />
                              <Label styleClass="static-labels" text="Insured" />
                              <Label styleClass="static-labels" text="Beneficiary" />
                              <Label styleClass="static-labels" text="Contract File" />
                              <Label styleClass="static-labels" text="Premium" />
                              <Label styleClass="static-labels" text="Signing Date" />
                              <Label styleClass="static-labels" text="Expiry Date" />
                           </children>
                        </VBox>
                        <VBox prefHeight="201.0" prefWidth="250.0">
                           <children>
                              <Label fx:id="owner" styleClass="dynamic-labels" text="\$owner" />
                              <Label fx:id="insured" styleClass="dynamic-labels" text="\$insured" />
                              <Label fx:id="beneficiary" styleClass="dynamic-labels" text="\$beneficiary" />
                              <Label fx:id="contractName" styleClass="dynamic-labels" text="\$contractName" />
                              <Label fx:id="premium" styleClass="dynamic-labels" text="\$premium" />
                              <Label fx:id="signingDate" styleClass="dynamic-labels" text="\$signingDate" />
                              <Label fx:id="expiryDate" styleClass="dynamic-labels" text="\$expiryDate" />
                           </children>
                        </VBox>
                     </children>
                  </HBox>
               </children>
            </AnchorPane>
        </content>
      </ScrollPane>
   </children>
</StackPane>
```
