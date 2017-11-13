# chairz
###### /java/seedu/address/logic/commands/Command.java
``` java
    /**
     *  Initialises the mapofTaskCommandsToFormat when it is being used for the first time
     *  This method is seperated from the previous method to allow tabpane to work
     */
    private static void initializeTaskCommandFormatMap() {
        mapOfTaskCommandsToFormats.put(AddTaskCommand.COMMAND_WORD, AddTaskCommand.AUTOCOMPLETE_FORMAT);
        mapOfTaskCommandsToFormats.put(ListTaskCommand.COMMAND_WORD, ListTaskCommand.AUTOCOMPLETE_FORMAT);
        mapOfTaskCommandsToFormats.put(DeleteTaskCommand.COMMAND_WORD, DeleteTaskCommand.AUTOCOMPLETE_FORMAT);
        mapOfTaskCommandsToFormats.put(EditTaskCommand.COMMAND_WORD, EditTaskCommand.AUTOCOMPLETE_FORMAT);
        mapOfTaskCommandsToFormats.put(ClearTaskCommand.COMMAND_WORD, ClearTaskCommand.AUTOCOMPLETE_FORMAT);
    }

```
###### /java/seedu/address/logic/commands/DeleteCommand.java
``` java
        for (Index i : targetIndex) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        String result = "";
        Collections.sort(targetIndex);
        for (Index i : targetIndex) {
            ReadOnlyPerson personToDelete = lastShownList.get(i.getZeroBased());

            try {
                model.deletePerson(personToDelete);
                if (targetIndex.size() == 1) {
                    result = result.concat(personToDelete.toString());
                } else {
                    result = result.concat("\n" + personToDelete.toString());
                }
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }

        }

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, result));
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + String.format(MESSAGE_DELETE_PERSON_SUCCESS, result));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/parser/DeleteCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            args.trim();
            String[] arr = args.split("/", 0);
            ArrayList<Index> index = new ArrayList<>();
            for (String w : arr) {
                index.add(ParserUtil.parseIndex(w));
            }

            return new DeleteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/model/person/Person.java
``` java
    public Person(Name name, Phone phone, Phone2 phone2, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.phone2 = new SimpleObjectProperty<>(phone2);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getPhone2(), source.getEmail(), source.getAddress(),
                source.getTags());
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

```
###### /java/seedu/address/model/person/Person.java
``` java
    public void setPhone2(Phone2 phone2) {
        this.phone2.set(requireNonNull(phone2));
    }

    @Override
    public ObjectProperty<Phone2> phone2Property() {
        return phone2;
    }

```
###### /java/seedu/address/model/person/Person.java
``` java
    @Override
    public Phone2 getPhone2() {
        if (phone2 == null) {
            return new Phone2();
        }
        return phone2.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
    }

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
```
###### /java/seedu/address/model/person/Phone2.java
``` java
/**
 * Represents a Person's second phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone2 {


    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long";
    public static final String PHONE_VALIDATION_REGEX = "\\d{3,}";
    public static final String PHONE2_VALIDATION_REGEX = "-";
    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Phone2(String phone2) throws IllegalValueException {
        if (phone2 == null) {
            this.value = "-";
        } else {
            requireNonNull(phone2);
            String trimmedPhone = phone2.trim();
            if (!isValidPhone(trimmedPhone) && !trimmedPhone.equals("-")) {
                throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
            }
            this.value = trimmedPhone;
        }
    }

    public Phone2() {
        this.value = "-"; }


    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX) || test.matches(PHONE2_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone2 // instanceof handles nulls
                && this.value.equals(((Phone2) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/task/Description.java
``` java
/**
 * Represents the description of a task.
 * Guarantees: immutable; name is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Description should be alphanumeric";
    public static final String DESCRIPTION_VALIDATION_REGEX = ".+";

    public final String descriptionName;

    /**
     * Validates given description for a task.
     *
     * @throws IllegalValueException if the given description string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        requireNonNull(description);
        String descriptionName = description.trim();
        if (!isValidDescription(descriptionName)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.descriptionName = descriptionName;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.descriptionName.equals(((Description) other).descriptionName)); // state check
    }

    @Override
    public int hashCode() {
        return descriptionName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return descriptionName;
    }
}
```
###### /java/seedu/address/model/task/Name.java
``` java
/**
 * Represents a task name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Task names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}

```
###### /java/seedu/address/model/task/Priority.java
``` java
/**
 * Represents the priority level of a task in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority can only contain number 0, 1 or 2";

    public final String value;

    /**
     * Validates given priority level.
     *
     * @throws IllegalValueException if given priority int is invalid.
     */
    public Priority(String  priority) throws IllegalValueException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = trimmedPriority;
    }

    /**
     * Returns true is the given integer is a valid priority level
     */
    public static boolean isValidPriority(String test) {
        return Integer.parseInt(test) <= 2 && Integer.parseInt(test) >= 0;
    }

    @Override
    public String toString() {
        return value; }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && Integer.parseInt(this.value) == Integer.parseInt(((Priority) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/task/Task.java
``` java
/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    //    private ObjectProperty<Name> name;
    //    private ObjectProperty<Date> date;
    private ObjectProperty<Description> description;
    private ObjectProperty<Priority> priority;
    private ObjectProperty<DueDate> duedate;

    public Task(Description description, Priority priority, DueDate duedate) {
        requireAllNonNull(description, priority, duedate);
        //        this.name = new SimpleObjectProperty<>(name);
        //        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleObjectProperty<>(description);
        this.priority = new SimpleObjectProperty<>(priority);
        this.duedate = new SimpleObjectProperty<>(duedate);

    }

    public Task(ReadOnlyTask in) {
        this(in.getDescription(), in.getPriority(), in.getDueDate());
    }

    public void setDescription(Description description) {
        this.description.set(requireNonNull(description));
    }

    public ObjectProperty<Description> descriptionProperty() {
        return description;
    }

    public Description getDescription() {
        return description.get();
    }

    public void setPriority(Priority priority) {
        this.priority.set(requireNonNull(priority));
    }

    public ObjectProperty<Priority> priorityProperty() {
        return priority;
    }

    public Priority getPriority() {
        return priority.get();
    }

    public void setDuedate(DueDate dueddate) {
        this.duedate.set(dueddate);
    }

    @Override
    public ObjectProperty<DueDate> dueDateProperty() {
        return duedate;
    }

    @Override
    public DueDate getDueDate() {
        return duedate.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, priority);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    private void displayTask() {
        taskDisplayed.setStyle("-fx-font-family: monospace; -fx-background-color: #f8ecc2; -fx-font-size: 22px;");

    }


    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_MAPS_SEARCH_URL_PREFIX + person.getAddress().toString()
                .replaceAll(" ", "+").replaceAll(",", "%2C"));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    public TextArea getTaskDisplayed() {
        return taskDisplayed;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    /**
     *  Read and Display the task on the Text Area
     */
    public void displayOnTextArea() {
        try {
            String curr = System.getProperty("user.dir");
            Scanner s = new Scanner(new File(curr + "/taskData1.txt"));
            while (s.hasNext()) {

                taskDisplayed.appendText(s.next() + "\n");

            }

        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }


    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Change the tab based on command input
     */
    private void displayTab(String commandTyped) {
        Map<String, String> commandFormatMap = Command.getMapOfCommandFormats();
        List listOfAliases = Command.getListOfAvailableCommandAliases();
        int index = tabPane.getSelectionModel().getSelectedIndex();
        if (commandFormatMap.containsKey(commandTyped) || listOfAliases.contains(commandTyped)) {
            if (index != 0) {
                tabPane.getSelectionModel().selectFirst();
            }
        } else {
            tabPane.getSelectionModel().selectLast();
        }

    }


    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
```
###### /resources/view/TabView.css
``` css

.tab-pane .tab-header-area .tab-header-background {
    -fx-opacity: 0;
}

.tab-pane
{
    -fx-tab-min-width:120px;
}

.tab{
    -fx-background-insets: 0 1 0 1,0,0;
}
.tab-pane .tab
{
    -fx-background-color: #e6e6e6;

}

.tab-pane .tab:selected
{
    -fx-background-color: #3c3c3c;
}

.tab-pane .tab:hover
{
    -fx-background-color: #3c3c3c;
}

.tab .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: #828282;
    -fx-font-size: 15px;
    -fx-font-weight: bold;
    -fx-font-family: "Helvetica";
}

.tab:selected .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: #fffff0;
}
```
