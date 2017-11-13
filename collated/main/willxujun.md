# willxujun
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        //loops indices to edit multiple contacts
        for (Index index: indices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (Index index: indices) {
            ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
            Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updateTags(updatedTags, editPersonDescriptor);
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    /**
     * clears, adds and removes tags according to {@code editPersonDescriptor}.
     * @param updatedTags
     * @param editPersonDescriptor
     */
    public static void updateTags(Set<Tag> updatedTags, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(editPersonDescriptor);
        editPersonDescriptor.getTagsToAdd().ifPresent(tagsToAdd -> {
            if (tagsToAdd.isEmpty()) {
                updatedTags.clear();
            }
        });
        editPersonDescriptor.getTagsToRemove().ifPresent(tagsToRemove -> {
            if (tagsToRemove.isEmpty()) {
                updatedTags.clear();
            }
        });
        editPersonDescriptor.getTagsToAdd().ifPresent(updatedTags::addAll);
        editPersonDescriptor.getTagsToRemove().ifPresent(updatedTags::removeAll);
    }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setTagsToAdd(Set<Tag> tagsToAdd) {
            this.tagsToAdd = tagsToAdd;
        }

        public Optional<Set<Tag>> getTagsToAdd() {
            return Optional.ofNullable(tagsToAdd);
        }

        public void setTagsToRemove(Set<Tag> tagsToRemove) {
            this.tagsToRemove = tagsToRemove;
        }

        public Optional<Set<Tag>> getTagsToRemove() {
            return Optional.ofNullable(tagsToRemove);
        }
```
###### \java\seedu\address\logic\commands\SearchCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name, phone or tag contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String MESSAGE_USAGE = ": Finds all persons whose names, phones or tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n";

    private final NamePhoneTagContainsKeywordsPredicate predicate;

    public SearchCommand(NamePhoneTagContainsKeywordsPredicate predicate) {
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
                || (other instanceof SearchCommand // instanceof handles nulls
                && this.predicate.equals(((SearchCommand) other).predicate)); // state check
    }

}
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_REMOVE_TAG = new Prefix("-t/");
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_ADD_TAG)).ifPresent(editPersonDescriptor::setTagsToAdd);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_REMOVE_TAG))
                    .ifPresent(editPersonDescriptor::setTagsToRemove);
```
###### \java\seedu\address\logic\parser\SearchParser.java
``` java
/**
 * Represents a parser that parses input from the search bar
 */
public class SearchParser implements Parser<Command> {

    /**
     * returns a Command as parsed
     * @param args
     * @return a SearchCommand of the search word args if search bar input is not empty, a ListCommand if empty
     * @throws ParseException that should never be thrown because there is no restriction on search keywords
     */
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListCommand();
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchCommand(new NamePhoneTagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\model\person\NamePhoneTagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} or {@code Phone} or {@code Tags}
 * matches any of the keywords given.
 */
public class NamePhoneTagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NamePhoneTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /*
    Tests name, primaryPhone, secondary phones in UniquePhoneList and tags sequentially.
     */
    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                    .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                || keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword))
                || keywords.stream()
                    .anyMatch(keyword -> person.getPhones().stream()
                            .anyMatch(phone -> StringUtil.containsWordIgnoreCase(phone.value, keyword)))
                || keywords.stream()
                    .anyMatch(keyword -> person.getTags().stream()
                            .anyMatch(tag -> tag.tagName.equalsIgnoreCase(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NamePhoneTagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NamePhoneTagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns an immutable phone set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Phone> getPhones() {
        return Collections.unmodifiableSet(uniquePhoneList.get().toSet());
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    public TextField getCommandTextField() {
        return commandTextField;
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        SearchBox searchBox = new SearchBox(logic);
        searchBoxPlaceholder.getChildren().add(searchBox.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        commandBox.getCommandTextField().requestFocus();

        setBackground(topContainer,
                System.getProperty("user.dir") + "/docs/images/background.jpg",
                "/images/background.jpg",
                1280, 800);

        /*
        ChangeListener for caret focus.
        Switches focus to searchBox upon switching out of commandBox.
         */
        commandBox.getCommandTextField().focusedProperty().addListener(
                new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        if (oldValue == true) {
                            searchBox.getTextField().requestFocus();
                        }
                    }
                }
        );

        searchBox.getTextField().focusedProperty().addListener(
                new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        if (oldValue == true) {
                            commandBox.getCommandTextField().requestFocus();
                        }
                    }
                }
        );
```
###### \java\seedu\address\ui\SearchBox.java
``` java
/**
 * The UI component that is responsible for receiving user search command.
 */
public class SearchBox extends UiPart<Region> {

    private static final String FXML = "SearchBox.fxml";

    private final Logger logger = LogsCenter.getLogger(SearchBox.class);
    private final Logic logic;

    private String searchBuffer;

    @FXML
    private TextField searchTextField;

    public SearchBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        searchBuffer = "";
    }

    public TextField getTextField() {
        return searchTextField;
    }

    /**
     * Captures user input in searchBox.
     */
    @FXML
    private void handleKeyTyped(KeyEvent keyEvent) {
        String s = keyEvent.getCharacter();
        if (isDeleteOrBackspace(s)) {
            if (!searchBuffer.isEmpty()) {
                searchBuffer = searchBuffer.substring(0, searchBuffer.length() - 1);
            } else {
                return;
            }
        } else {
            searchBuffer = searchBuffer + s;
        }
        try {
            CommandResult commandResult = logic.executeSearch(searchBuffer);
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            logger.info("Invalid search: " + searchTextField.getText());
            raise(new NewResultAvailableEvent((e.getMessage())));
        }
    }

    private boolean isDeleteOrBackspace (String toTest) {
        return toTest.equals("\u0008") || toTest.equals("\u007F");
    }

}
```
###### \resources\view\SearchBox.fxml
``` fxml
<StackPane styleClass="anchor-pane" stylesheets="@DarkTheme.css" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
   <TextField fx:id="searchTextField" onKeyTyped="#handleKeyTyped" promptText="Search..." />
</StackPane>
```
