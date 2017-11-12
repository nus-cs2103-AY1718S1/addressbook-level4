# tpq95
###### /java/seedu/address/commons/util/StringUtil.java
``` java
        // Check if the keyword is in dd-MM format
        Pattern p = Pattern.compile("\\d\\d-\\d\\d");
        Matcher m = p.matcher(preppedWord);

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (m.find()) {
                return wordInSentence.contains(preppedWord);
            } else if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }
```
###### /java/seedu/address/logic/commands/tags/DetagCommand.java
``` java
/**
 * Remove same tags from a list of people
 */
public class DetagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "detag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove tags from multiple people. "
            + "Parameter: "
            + "INDEX, [INDEX]... "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " "
            + "1, 2 "
            + PREFIX_TAG + "friends";

    public static final String MESSAGE_DETAG_PERSONS_SUCCESS = "Removed tag: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_MISSING_TAG = "One or more person(s) don't has this tag";
    private final Index[] indices;
    private final Tag tag;

    /**
     * Create a DetagCommand to delete the specific
     * @param indices
     * @param tag
     */
    public DetagCommand(Index[] indices, Tag tag) {
        requireNonNull(indices);
        requireNonNull(tag);
        this.indices = indices;
        this.tag = tag;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyPerson> indexToRemove = new ArrayList();

        for (Index targetIndex: indices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToEdit = lastShownList.get(targetIndex.getZeroBased());

            // Check whether person contains tag
            if (!personToEdit.getTags().contains(tag)) {
                throw new CommandException(MESSAGE_MISSING_TAG);
            }

            // Save the valid persons to list
            indexToRemove.add(personToEdit);
        }

        for (ReadOnlyPerson targetPerson: indexToRemove) {
            try {
                model.deleteTag(targetPerson, tag);
            } catch (TagNotFoundException dsd) {
                throw new CommandException(MESSAGE_MISSING_TAG);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DETAG_PERSONS_SUCCESS, tag.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetagCommand // instanceof handles nulls
                && tag.equals(((DetagCommand) other).tag)); // state check
    }
}
```
###### /java/seedu/address/logic/parser/DetagCommandParser.java
``` java
/**
 * Parse the given input and creates a DetagCommand object
 */
public class DetagCommandParser implements Parser<DetagCommand> {

    public static final String INDEX_SEPARATOR_REGEX = ",";

    /**
     * Parse the given {@code String} of arguments in the context of the DetagCommand
     * @param userInput
     * @return DetagCommand object for execution
     * @throws ParseException
     */
    @Override
    public DetagCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG);

        Index[] parsedIndices;
        Set<Tag> tagList;
        Tag tag;

        try {
            String[] splitIndices = splitIndices(argMultimap);
            int numberOfIndices = splitIndices.length;
            parsedIndices = new Index[numberOfIndices];
            for (int i = 0; i < numberOfIndices; i++) {
                parsedIndices[i] = ParserUtil.parseIndex(splitIndices[i]);
            }
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            tag = tagList.iterator().next();
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetagCommand.MESSAGE_USAGE));
        }

        return new DetagCommand(parsedIndices, tag);
    }

    private String[] splitIndices(ArgumentMultimap argMultimap) {
        return argMultimap.getPreamble().split(INDEX_SEPARATOR_REGEX);
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Remove {@code oldTag} from list of person stated by {@code indices} from
     * {@code AddressBook}
     * @param oldPerson
     * @param oldTag
     * @throws DuplicatePersonException
     * @throws PersonNotFoundException
     * @throws TagNotFoundException
     */
    public void deleteTag(ReadOnlyPerson oldPerson, Tag oldTag)
            throws DuplicatePersonException, PersonNotFoundException, TagNotFoundException {
        Person newPerson = new Person(oldPerson);
        Set<Tag> newTags = new HashSet<>(newPerson.getTags());
        if (!newTags.remove(oldTag)) {
            throw new TagNotFoundException();
        }
        newPerson.setTags(newTags);

        updatePerson(oldPerson, newPerson);
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /** Delete tag of given person */
    void deleteTag(ReadOnlyPerson person, Tag tag) throws PersonNotFoundException,
            DuplicatePersonException, TagNotFoundException;
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void deleteTag(ReadOnlyPerson person, Tag oldTag) throws PersonNotFoundException,
            DuplicatePersonException, TagNotFoundException {
        addressBook.deleteTag(person, oldTag);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/person/exceptions/TagNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified tag
 */
public class TagNotFoundException extends Exception{
}
```
###### /java/seedu/address/model/person/PersonComparator.java
``` java
/**
 * A Person comparator that compares the {@code Name} alphabetically
 */
public class PersonComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Person p1 = (Person) o1;
        Person p2 = (Person) o2;

        String name1 = p1.getName().fullName;
        String name2 = p2.getName().fullName;

        return name1.compareToIgnoreCase(name2);
    }
}
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sort the person according to their name alphabetically and is case-insensitive
     */
    private void sortPersons(ObservableList<Person> persons) {
        Collections.sort(persons, new PersonComparator());
    }
```
###### /java/seedu/address/model/task/TaskComparator.java
``` java
/**
 * A task comparator that compares the days of deadline from today
 */
public class TaskComparator implements Comparator {
    private final String dateNull = "30-12-2999";

    @Override
    public int compare(Object obj, Object obj1) {
        Task d = (Task) obj;
        Task d1 = (Task) obj1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date0 = "";
        String date1 = "";
        int value = 0;

        try {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            if (!d.getDeadline().isEmpty()) {
                Date deadline0 = ParserUtil.parseDate(d.getDeadline().toString());
                date0 = dateFormat.format(deadline0);
            } else {
                date0 = dateNull;
            }
            if (!d1.getDeadline().isEmpty()) {
                Date deadline1 = ParserUtil.parseDate(d1.getDeadline().toString());
                date1 = dateFormat.format(deadline1);
            } else {
                date1 = dateNull;
            }
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        LocalDate deaddate0 = LocalDate.parse(date0, formatter);
        LocalDate deaddate1 = LocalDate.parse(date1, formatter);
        value = deaddate0.compareTo(deaddate1);

        return value;
    }
}
```
###### /java/seedu/address/model/task/UniqueTaskList.java
``` java
    /**
     * Sorts the internal list according to the {@code TaskComparator}.
     * With the format: tasks with expired deadline, task with closer deadlines
     *                  tasks with null deadline...
     */
    private void sortTasks(ObservableList<Task> tasks) {
        Collections.sort(tasks, new TaskComparator());
    }
```
###### /java/seedu/address/ui/CalendarPanel.java
``` java
/**
 * The CalendarPanel panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final Logic logic;
    private final Model model;

    //private DatePicker datePicker;

    @FXML
    private StackPane calendarPane;

    @FXML
    private DatePicker datePicker;

    public CalendarPanel(Logic logic, Model model) {
        super(FXML);
        this.logic = logic;
        this.model = model;
        setDate(model.getAddressBook().getPersonList(), model.getAddressBook().getTaskList());
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Load the calendar which is the datePickerSkin with DateCell from datePicker
     */
    private void loadDefaultPage() {
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        DatePickerContent popupContent = (DatePickerContent) datePickerSkin.getPopupContent();

        popupContent.setPrefHeight(calendarPane.getPrefHeight());
        popupContent.setPrefWidth(calendarPane.getPrefWidth());
        calendarPane.getChildren().add(popupContent);
        //selectDate(popupContent, "30-10-2017");
    }

    /**
     * Load datePicker with various dates, birthday from personList and deadline from taskList
     * @param personList
     * @param taskList
     */
    private void setDate(ObservableList<ReadOnlyPerson> personList, ObservableList<ReadOnlyTask> taskList) {
        datePicker = new DatePicker((LocalDate.now()));
        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory(personList, taskList);
        datePicker.setDayCellFactory(dayCellFactory);
        findDateForSelection();
    }

    /**
     * Makes the dateCell in datePickerSkin editable, and pass on the selected markdate to handler
     */
    private void findDateForSelection() {
        // Make datePicker editable (i.e. i think can select and update value)
        datePicker.setEditable(true);

        // TODO: 26/10/17 Able to not execute findCommand when cell is not colour
        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String currentMode = model.getCommandMode().toString();
                String date = convertDateFromPicker();

                findPersonsWithBirthday(date);
                findTasksWithDeadline(date);
                changeToOriginalMode(currentMode);
            }
        });
    }

    /**
     * Perform a switch back to the original mode of Mobilize
     * @param currentMode
     */
    private void changeToOriginalMode(String currentMode) {
        try {
            logic.execute(ChangeModeCommand.COMMAND_WORD + " " + currentMode);
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String convertDateFromPicker() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return datePicker.getValue().format(formatter);
    }

    /**
     * Execute FindCommand to find tasks with deadline that match selected date
     * @param dateString
     */
    private void findTasksWithDeadline(String dateString) {
        try {
            // Change to TaskManager mode
            logic.execute(ChangeModeCommand.COMMAND_WORD + " tm");
            // Execute FindCommand for dateString
            logic.execute(FindTaskCommand.COMMAND_WORD + " " + dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (CommandException e) {
            e.printStackTrace();
        }

    }

    /**
     * Edit the given date string to contain only dd-MM
     * Execute FindCommand to find persons with birthday that match selected date
     * @param dateString
     */
    private void findPersonsWithBirthday(String dateString) {
        String birthdayString = dateString.substring(0, 5);
        logger.info("Date selected: " + dateString);
        try {
            // Change to AddressBook mode
            logic.execute(ChangeModeCommand.COMMAND_WORD + " ab");
            // Execute FindCommand for dateString
            logic.execute(FindTaskCommand.COMMAND_WORD + " " + birthdayString);
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Go through personList and taskList for birthday and deadline respectively.
     * Update dateCell with different colour and tooltip
     * @param personList
     * @param taskList
     * @return
     */
    private Callback<DatePicker, DateCell> getDayCellFactory(ObservableList<ReadOnlyPerson> personList,
                                                             ObservableList<ReadOnlyTask> taskList) {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        StringBuilder s = new StringBuilder();
                        int bCount = 0;
                        int dCount = 0;
                        StringBuilder colour = new StringBuilder();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                        for (ReadOnlyPerson person: personList) {
                            try {
                                if (!person.getBirthday().isEmpty() && MonthDay.from(item).equals
                                            (MonthDay.from(LocalDate.parse(person.getBirthday().toString(),
                                                    formatter)))) {
                                    if (bCount == 0) {
                                        bCount++;
                                        s.append(person.getName() + "'s Birthday");
                                    } else if (bCount > 0) {
                                        int endIndex = s.indexOf("Birthday");
                                        s.delete(0, endIndex);
                                        bCount++;
                                        s.insert(0, bCount + " ");
                                        if (bCount == 2) {
                                            s.append("s");
                                        }
                                    }
                                    colour = new StringBuilder("-fx-background-color: #f1a3ff;");

                                }
                            } catch (DateTimeParseException exc) {
                                logger.warning("Not parsable: " + person.getBirthday().toString());
                                throw exc;
                            }
                        }
                        for (ReadOnlyTask task: taskList) {
                            String taskDate = "";
                            if (!task.getDeadline().isEmpty()) {
                                try {
                                    Date deadline = ParserUtil.parseDate(task.getDeadline().date);
                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    taskDate = dateFormat.format(deadline);
                                } catch (IllegalValueException e) {
                                    e.printStackTrace();
                                }
                                String finalTaskDate = taskDate;
                                try {
                                    //ensure that Deadline/Startdate is valid, after computer is invented
                                    assert LocalDate.parse(finalTaskDate, formatter).getYear()
                                            >= (LocalDate.now().getYear() - 100);
                                    if (item.equals(LocalDate.parse(finalTaskDate, formatter))) {
                                        if ((bCount == 0) && (dCount == 0)) {
                                            dCount++;
                                            s.append(dCount + " Deadline");
                                        } else if ((bCount > 0) && (dCount == 0)) {
                                            dCount++;
                                            s.append(" + " + dCount + " Deadline");
                                        } else if (dCount > 0) {
                                            dCount++;
                                            int endIndex = s.indexOf(" Deadline");
                                            s.replace(endIndex - 1, endIndex, dCount + "");
                                            if (dCount == 2) {
                                                s.append("s");
                                            }
                                        }

                                        if (bCount == 0) {
                                            colour = new StringBuilder("-fx-background-color: #ff444d;");
                                        } else {
                                            colour = new StringBuilder("-fx-background-color: #feff31;");
                                        }
                                    }
                                } catch (DateTimeParseException exc) {
                                    logger.warning("Not parsable: " + task.getDeadline().date);
                                    throw exc;
                                }
                            }
                        }
                        if (s.length() != 0) {
                            setTooltip(new Tooltip(s.toString()));
                        }
                        setStyle(colour.toString());
                    }
                };
            }
        };
        return dayCellFactory;
    }

}
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    private static String getColourForTag(String tagValue) {
        if (!tagColours.containsKey(tagValue)) {
            tagColours.put(tagValue, colours[random.nextInt(colours.length)]);
        }

        return tagColours.get(tagValue);
    }
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    /**
     * Initializes the tags attached to each person sets the colour of label for the same tag.
     * @param person the person whose tags are being initialized.
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColourForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }
```
###### /java/seedu/address/ui/TaskCard.java
``` java
    /**
     * Change colour of taskcard according to urgency of the task
     */
    private void setColour() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String bkgndColour;
        if (!task.getDeadline().isEmpty()) {
            String taskDate = "";
            try {
                Date deadline = ParserUtil.parseDate(task.getDeadline().date);
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                taskDate = dateFormat.format(deadline);
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
            LocalDate deaddate = LocalDate.parse(taskDate, formatter);
            int range = deaddate.getDayOfYear() - date.getDayOfYear();
            if (range >= GREEN_RANGE) {
                bkgndColour = "#6A8A82";
            } else if (range >= YELLOW_RANGE) {
                bkgndColour = "#A37C27";
            } else if (range >= RED_RANGE) {
                bkgndColour = "#A7414A";
            } else {
                bkgndColour = "#878787";
            }
        } else {
            // for task with no deadline
            bkgndColour = "#563838";
        }
        gridPane.setStyle("-fx-background-color: " + bkgndColour + ";"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-height: 2;"
                + "-fx-border-color: black;");
    }
```
###### /resources/view/CalendarPanel.fxml
``` fxml


<StackPane fx:id="calendarPane" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <DatePicker fx:id="datePicker" visible="false" />
   </children>
</StackPane>
```
###### /resources/view/MainWindow.fxml
``` fxml
                  <StackPane fx:id="calendarPlaceholder" />
```
