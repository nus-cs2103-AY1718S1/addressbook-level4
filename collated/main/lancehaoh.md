# lancehaoh
###### /java/seedu/address/commons/util/AudioUtil.java
``` java
    /**
     * Play a .wav format audio file that is stored in src/main/resources/images folder
     *
     * @param clipName name of a .wav format audio file e.g. "music.wav"
     */
    public void playClip(String clipName)
            throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        InputStream is = getClass().getResourceAsStream("/audio/" + clipName);
        AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
    }
}
```
###### /java/seedu/address/commons/util/AutoCompleteUtil.java
``` java
    /**
     * Gets the system commands that start with a particular prefix
     *
     * @param commandText an arbitrary string
     * @return commandList a list of system commands whose prefix is commandText
     */
    public static List<String> autoCompleteCommand(String commandText, List<String> commandList) {
        return commandList
                .stream()
                .filter(command -> command.toString().toLowerCase().startsWith((commandText.toLowerCase())))
                .collect(Collectors.toList());
    }
}
```
###### /java/seedu/address/logic/commands/Command.java
``` java
    private static final List<String> listOfAvailableCommandAliases = Arrays.asList (
            AddCommand.COMMAND_WORD_ALIAS,
            ClearCommand.COMMAND_WORD_ALIAS,
            DeleteCommand.COMMAND_WORD_ALIAS,
            DeleteTagCommand.COMMAND_WORD_ALIAS,
            EditCommand.COMMAND_WORD_ALIAS,
            FindCommand.COMMAND_WORD_ALIAS,
            HistoryCommand.COMMAND_WORD_ALIAS,
            ListCommand.COMMAND_WORD_ALIAS,
            MultiFilterCommand.COMMAND_WORD_ALIAS,
            RedoCommand.COMMAND_WORD_ALIAS,
            SearchCommand.COMMAND_WORD_ALIAS,
            SelectCommand.COMMAND_WORD_ALIAS,
            UndoCommand.COMMAND_WORD_ALIAS
    );

    protected Model model;
    protected CommandHistory history;
    protected UndoRedoStack undoRedoStack;

```
###### /java/seedu/address/logic/commands/Command.java
``` java
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, displaySize);
    }

```
###### /java/seedu/address/logic/commands/Command.java
``` java
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of names of persons.
     *
     * @param displayList used to generate summary
     * @return summary message for names of persons displayed
     */
    public static String getMessageForPersonList(ObservableList<ReadOnlyPerson> displayList) {
        if (displayList.isEmpty()) {
            return Messages.MESSAGE_UNKNOWN_TAG;
        }

        StringBuilder builder = new StringBuilder();
        for (ReadOnlyPerson person : displayList) {
            builder.append(person.getName());
            builder.append("\n");
        }
        return  builder.toString();
    }

```
###### /java/seedu/address/logic/commands/Command.java
``` java
    public static Map<String, String> getMapOfCommandHelp() {
        // Initialize map the first time autocomplete is used
        if (mapOfCommandsToHelp.isEmpty()) {
            initializeCommandHelpMap();
        }
        return mapOfCommandsToHelp;
    }

```
###### /java/seedu/address/logic/commands/Command.java
``` java
    public static Map<String, String> getMapOfCommandFormats() {
        // Initialize map the first time autocomplete is used
        if (mapOfCommandsToFormats.isEmpty()) {
            initializeCommandFormatMap();
        }
        return mapOfCommandsToFormats;
    }
    public static Map<String, String> getMapofTaskCommandFormats() {
        if (mapOfTaskCommandsToFormats.isEmpty()) {
            initializeTaskCommandFormatMap();
        }
        return mapOfTaskCommandsToFormats;
    }

```
###### /java/seedu/address/logic/commands/Command.java
``` java
    public static List<String> getListOfAvailableCommandAliases() {
        return listOfAvailableCommandAliases;
    }

```
###### /java/seedu/address/logic/commands/Command.java
``` java
    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

```
###### /java/seedu/address/logic/commands/Command.java
``` java
    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
    }

```
###### /java/seedu/address/logic/commands/Command.java
``` java
    /**
     *
     * Initialises the mapOfCommandsToFormats when it is being used for the first time
     * (i.e. this method is called only once throughout the program execution)
     *
     */
    private static void initializeCommandFormatMap() {
        mapOfCommandsToFormats.put(AddCommand.COMMAND_WORD, AddCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(ClearCommand.COMMAND_WORD, ClearCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(DeleteCommand.COMMAND_WORD, DeleteCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(DeleteTagCommand.COMMAND_WORD, DeleteTagCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(EditCommand.COMMAND_WORD, EditCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(ExitCommand.COMMAND_WORD, ExitCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(FindCommand.COMMAND_WORD, FindCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(HelpCommand.COMMAND_WORD, HelpCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(HistoryCommand.COMMAND_WORD, HistoryCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(ListCommand.COMMAND_WORD, ListCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(MultiFilterCommand.COMMAND_WORD, MultiFilterCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(RedoCommand.COMMAND_WORD, RedoCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(SearchCommand.COMMAND_WORD, SearchCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(SelectCommand.COMMAND_WORD, SelectCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(UndoCommand.COMMAND_WORD, UndoCommand.AUTOCOMPLETE_FORMAT);
    }
```
###### /java/seedu/address/logic/commands/Command.java
``` java
    /**
     *
     * Initialises the mapOfCommandsToHelp when it is being used for the first time
     * (i.e. this method is called only once throughout the program execution)
     *
     */
    private static void initializeCommandHelpMap() {
        mapOfCommandsToHelp.put(AddTaskCommand.COMMAND_WORD, AddTaskCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(ListTaskCommand.COMMAND_WORD, ListTaskCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(DeleteTaskCommand.COMMAND_WORD, DeleteTaskCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(DeleteTagCommand.COMMAND_WORD, DeleteTagCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(HistoryCommand.COMMAND_WORD, HistoryCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(MultiFilterCommand.COMMAND_WORD, MultiFilterCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(RedoCommand.COMMAND_WORD, RedoCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(SearchCommand.COMMAND_WORD, SearchCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(SelectCommand.COMMAND_WORD, SelectCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(UndoCommand.COMMAND_WORD, UndoCommand.MESSAGE_USAGE);
    }
}
```
###### /java/seedu/address/logic/commands/DeleteTagCommand.java
``` java
    public DeleteTagCommand(Tag tagToDelete) {
        this.tagToDelete = tagToDelete;
    }

```
###### /java/seedu/address/logic/commands/DeleteTagCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() {
        boolean tagWasDeleted = model.deleteTag(tagToDelete);

        // If the specified tag does not exist, return an error message instead of a success message.
        String messageToUser = (!tagWasDeleted ? MESSAGE_NO_SUCH_TAG : MESSAGE_DELETE_TAG_SUCCESS);

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(String.format(messageToUser, tagToDelete));
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
        }
    }
}
```
###### /java/seedu/address/logic/commands/DeleteTaskCommand.java
``` java
    public DeleteTaskCommand(ArrayList<Index> targetIndex) {
        this.targetIndex = targetIndex;
    }

```
###### /java/seedu/address/logic/commands/DeleteTaskCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        for (Index i : targetIndex) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        }

        String result = "";
        Collections.sort(targetIndex);
        for (Index i : targetIndex) {
            ReadOnlyTask taskToDelete = lastShownList.get(i.getZeroBased());

            try {
                model.deleteTask(taskToDelete);
                if (targetIndex.size() == 1) {
                    result = result.concat(taskToDelete.toString());
                } else {
                    result = result.concat("\n" + taskToDelete.toString());
                }
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }

        }

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, result));
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + String.format(MESSAGE_DELETE_TASK_SUCCESS, result));
        }
    }

```
###### /java/seedu/address/logic/commands/DeleteTaskCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteTaskCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ListTaskCommand.java
``` java
    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + MESSAGE_SUCCESS);
        }
    }
}
```
###### /java/seedu/address/logic/commands/MultiFilterCommand.java
``` java
    public MultiFilterCommand(
            NameContainsKeywordsSubstrPredicate namePredicate,
            PhoneContainsKeywordsSubstrPredicate phonePredicate,
            EmailContainsKeywordsSubstrPredicate emailPredicate,
            AddressContainsKeywordsSubstrPredicate addressPredicate) {
        this.namePredicate = namePredicate;
        this.phonePredicate = phonePredicate;
        this.emailPredicate = emailPredicate;
        this.addressPredicate = addressPredicate;
    }

```
###### /java/seedu/address/logic/commands/MultiFilterCommand.java
``` java
    @Override
    public CommandResult execute() {
        // Clear the filter conditions on the filtered person list
        model.clearFiltersOnPersonList();

        // Update filtered person list with multiple criteria
        model.updateFilteredPersonList(new Predicate<ReadOnlyPerson>() {
            @Override
            /*
             * Converts the predicate to each field to a tautology if it was entered by the user
             * i.e. if a field is blank, it is equivalent to not being used in filtering persons
             */
            public boolean test(ReadOnlyPerson readOnlyPerson) {
                return (namePredicate == null || namePredicate.test(readOnlyPerson))
                        && (phonePredicate == null || phonePredicate.test(readOnlyPerson))
                        && (emailPredicate == null || emailPredicate.test(readOnlyPerson))
                        && (addressPredicate == null || addressPredicate.test(readOnlyPerson));
            }
        });

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
        }
    }

```
###### /java/seedu/address/logic/commands/MultiFilterCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MultiFilterCommand)) {
            return false;
        }
        assert(this.namePredicate != null && other != null
                || this.namePredicate == null && ((MultiFilterCommand) other).namePredicate == null);
        assert(this.phonePredicate != null && other != null
                || this.phonePredicate == null && ((MultiFilterCommand) other).phonePredicate == null);
        assert(this.emailPredicate != null && other != null
                || this.emailPredicate == null && ((MultiFilterCommand) other).emailPredicate == null);
        assert(this.addressPredicate != null && other != null
                || this.addressPredicate == null && ((MultiFilterCommand) other).addressPredicate == null);

        return other == this // short circuit if same object
                || ((this.namePredicate == null
                || this.namePredicate.equals(((MultiFilterCommand) other).namePredicate)) // state check
                && (this.phonePredicate == null // short circuit if null
                || this.phonePredicate.equals(((MultiFilterCommand) other).phonePredicate)) // state check
                && (this.emailPredicate == null // short circuit if null
                || this.emailPredicate.equals(((MultiFilterCommand) other).emailPredicate)) // state check
                && (this.addressPredicate == null // short circuit if null
                || this.addressPredicate.equals(((MultiFilterCommand) other).addressPredicate))); // state check
    }
}
```
###### /java/seedu/address/logic/parser/DeleteTagCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns an DeleteTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        try {
            return new DeleteTagCommand(new Tag(args));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/DeleteTaskCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTaskCommand
     * and returns an DeleteTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTaskCommand parse(String args) throws ParseException {
        try {
            args.trim();
            String[] arr = args.split("/", 0);
            ArrayList<Index> index = new ArrayList<>();
            for (String w : arr) {
                index.add(ParserUtil.parseIndex(w));
            }

            return new DeleteTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/parser/MultiFilterCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MultiFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        // Parse and get the values of each field
        Optional<String> nameOptional = argMultimap.getValue(PREFIX_NAME).filter(s -> !s.isEmpty());
        Optional<String> phoneOptional = argMultimap.getValue(PREFIX_PHONE).filter(s -> !s.isEmpty());
        Optional<String> emailOptional = argMultimap.getValue(PREFIX_EMAIL).filter(s -> !s.isEmpty());
        Optional<String> addressOptional = argMultimap.getValue(PREFIX_ADDRESS).filter(s -> !s.isEmpty());

        /* Get filter predicates for each field based on user input
         * If a field has been entered by user, then assign a predicate object
         * Otherwise, assign a null object as a predicate to indicate
         * that field has not been entered
         */
        NameContainsKeywordsSubstrPredicate namePredicate = (!nameOptional.isPresent())
                ? null : new NameContainsKeywordsSubstrPredicate(
                        Arrays.asList(nameOptional.get()));

        PhoneContainsKeywordsSubstrPredicate phonePredicate = (!phoneOptional.isPresent())
                ? null : new PhoneContainsKeywordsSubstrPredicate(
                        Arrays.asList(phoneOptional.get()));

        EmailContainsKeywordsSubstrPredicate emailPredicate = (!emailOptional.isPresent())
                ? null : new EmailContainsKeywordsSubstrPredicate(
                        Arrays.asList(emailOptional.get()));

        AddressContainsKeywordsSubstrPredicate addressPredicate = (!addressOptional.isPresent())
                ? null : new AddressContainsKeywordsSubstrPredicate(
                        Arrays.asList(addressOptional.get()));

        return new MultiFilterCommand(namePredicate, phonePredicate, emailPredicate, addressPredicate);
    }

```
###### /java/seedu/address/logic/parser/MultiFilterCommandParser.java
``` java
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Deletes a tag from the address book
     *
     * @param t tag object
     * @return a variable indicator if at least one tag was deleted
     */
    @Override
    public boolean deleteTag(Tag t) {
        // Get all contacts in AddressBook
        ObservableList<ReadOnlyPerson> persons = addressBook.getPersonList();
        boolean tagWasDeleted = false;

        try {
            for (ReadOnlyPerson unmodifiablePerson : persons) {
                Person updatedPerson = new Person(unmodifiablePerson);
                // Remove the desired tag from every person where applicable
                Set<Tag> tags = new HashSet<>(updatedPerson.getTags());
                if (tags.contains(t)) {
                    tagWasDeleted = true;
                    tags.remove(t);
                    updatedPerson.setTags(tags);
                    addressBook.updatePerson(unmodifiablePerson, updatedPerson);
                }
            }
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (DuplicatePersonException dpe) {
            assert false : "Update will cause two contacts to be the same";
        }
        return tagWasDeleted;
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Launches extended auto complete mechanism in logic
     * when the special key is pressed in the Command Box
     * Extended auto complete fills the command box with the full format of
     * a command rather than just the command word
     */
    private void launchExtendedAutocomplete() {
        String userCommand = commandTextField.getText().split("\\s+")[0];
        Map<String, String> commandFormatMap = Command.getMapOfCommandFormats();
        Map<String, String> commandTaskFormatMap = Command.getMapofTaskCommandFormats();
        Map<String, String> commandHelpMap = Command.getMapOfCommandHelp();
        if (commandFormatMap.containsKey(userCommand) || commandTaskFormatMap.containsKey(userCommand)) {
            if (commandFormatMap.containsKey(userCommand)) {
                commandTextField.setText(commandFormatMap.get(userCommand));
            } else {
                commandTextField.setText(commandTaskFormatMap.get(userCommand));
            }
            raise(new NewResultAvailableEvent(commandHelpMap.get(userCommand)));
        }
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Launches auto complete mechanism in logic
     * when the auto complete hotkey is pressed in the Command Box
     */
    private void launchAutoComplete() {
        List<String> listOfPossibleCommandWords = logic.getPossibleCommands(commandTextField.getText());
        final int numberOfCommandsOnOneLine = 6;
        int count = 0;
        StringBuffer feedbackToUser = new StringBuffer();

        /*
         * Append all possible command words into a single formatted string
         */
        for (String commandWord : listOfPossibleCommandWords) {
            // format the command so that it will be aligned when displayed to user
            String s = String.format("%-20s", commandWord);
            feedbackToUser.append(s);
            count++;
            if (count == numberOfCommandsOnOneLine) {
                count = 0;
                feedbackToUser.append("\n");
            }
        }

        logger.info(listOfPossibleCommandWords.toString());

        if (listOfPossibleCommandWords.size() == 1) {
            /*
             * there is only one possible command for the given prefix
             * directly complete the command in the command box
             */
            commandTextField.setText(listOfPossibleCommandWords.get(0));
            raise(new NewResultAvailableEvent(""));
        } else {
            // Display the list of command words to the user
            if (feedbackToUser.length() > 0) {
                raise(new NewResultAvailableEvent(feedbackToUser.toString()));
            } else {
                // Clear suggestions if there were any when the user types a wrong prefix
                raise(new NewResultAvailableEvent(""));
            }
        }
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            // Parser for checking if the user input command is related to tasks rather than persons
            AddressBookParser parser = new AddressBookParser();
            String userInput = commandTextField.getText();

            CommandResult commandResult = logic.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");

```
###### /java/seedu/address/ui/CommandBox.java
``` java
            Command currentCommand = parser.parseCommand(userInput);

            if (currentCommand instanceof ListTaskCommand || currentCommand instanceof DeleteTaskCommand) {
                // Process and display tasks in a separate text field
                StringBuffer taskFieldOutput = new StringBuffer();

                List<ReadOnlyTask> listOfTask = logic.getFilteredTaskList();

                for (int i = 0; i < listOfTask.size(); i++) {
                    taskFieldOutput.append("Task no. " + (i + 1) + "\n");
                    taskFieldOutput.append(listOfTask.get(i).toString());
                    taskFieldOutput.append("\n");
                }
                taskDisplayed.setText(taskFieldOutput.toString());
            }
            displayTab(userInput.split("\\s+")[0]);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

            // Play success sound
            audio.playClip("command-success.wav");
            logger.info("Result: " + commandResult.feedbackToUser);
        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));

            // Play failure sound
            try {
                audio.playClip("command-failure.wav");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException err) {
                err.printStackTrace();
            }
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
```
