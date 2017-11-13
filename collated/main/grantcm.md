# grantcm
###### /java/seedu/address/commons/events/ui/GroupPanelSelectionChangedEvent.java
``` java
    public GroupPanelSelectionChangedEvent(GroupCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public GroupCard getNewSelection() {
        return newSelection;
    }
```
###### /java/seedu/address/logic/commands/CommandCollection.java
``` java
    private static Set<String> commandSet = Stream.of(
        AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD, RemarkCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
        UndoCommand.COMMAND_WORD, GroupCommand.COMMAND_WORD, FilterGroupCommand.COMMAND_WORD,
        ExportCommand.COMMAND_WORD
    ).collect(Collectors.toSet());

    private static Map<String, String> commandMap;
    static {
        commandMap = new HashMap<String, String> ();
        commandMap.put(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_PARAMETERS);
        commandMap.put(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_PARAMETERS);
        commandMap.put(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_PARAMETERS);
        commandMap.put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_PARAMETERS);
        commandMap.put(RemarkCommand.COMMAND_WORD, RemarkCommand.MESSAGE_PARAMETERS);
        commandMap.put(SelectCommand.COMMAND_WORD, SelectCommand.MESSAGE_PARAMETERS);
        commandMap.put(GroupCommand.COMMAND_WORD, GroupCommand.MESSAGE_PARAMETERS);
        commandMap.put(FilterGroupCommand.COMMAND_WORD, FilterGroupCommand.MESSAGE_PARAMETERS);
        commandMap.put(ExportCommand.COMMAND_WORD, ExportCommand.MESSAGE_USAGE);
    }

    public CommandCollection (){}

    public Map<String, String> getCommandMap() {
        return commandMap;
    }

    public Set<String> getCommandSet() {
        return commandSet;
    }

```
###### /java/seedu/address/logic/commands/FilterGroupCommand.java
``` java
    /**
     * Updates the filtered list to display only people with the proper group predicate
     */
    public CommandResult executeUndoableCommand() throws CommandException {

        if (model.groupExists(new Group(filterName))) {
            model.updateFilteredPersonList(predicate);
            if (model.getFilteredPersonList().size() == 0) {
                model.updateFilteredListToShowAll();
                return new CommandResult(MESSAGE_GROUP_IS_EMPTY);
            } else {
                return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
            }
        } else {
            return new CommandResult(MESSAGE_GROUP_DOESNT_EXIST);
        }


    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterGroupCommand // instanceof handles nulls
                && predicate.equals(((FilterGroupCommand) other).predicate));
    }
```
###### /java/seedu/address/logic/commands/GroupCommand.java
``` java
    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        String groupName = args.get(0);
        Group group = new Group (groupName);
        try {
            if (!model.groupExists (group)) {
                model.addGroup (group);
                editPersonGroups(args, group);
            } else if (args.size() > 1) {
                editPersonGroups(args, group);
            } else {
                model.deleteGroup(group);
            }

            return new CommandResult (MESSAGE_SUCCESS);

        } catch (DuplicateGroupException | GroupNotFoundException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupCommand // instanceof handles nulls
                && args.equals(((GroupCommand) other).args));
    }

    /**
     * Iterates through the person list from the model and updates them to add the group
     * @param args persons to add group to
     * @param group to add to person
     */
    private void editPersonGroups (List<String> args, Group group) throws CommandException {
        for (int i = 1; i < args.size(); i++) {
            for (Iterator<ReadOnlyPerson> it = model.getFilteredPersonList().iterator(); it.hasNext(); ) {
                ReadOnlyPerson p = it.next();
                if (p.getName().fullName.toLowerCase().contains(args.get(i).toLowerCase())) {
                    ReadOnlyPerson newPerson = new Person(p.getName(), p.getPhone(), p.getEmail(),
                            p.getAddress(), p.getTags(), p.getExpiryDate(), p.getRemark(), group, p.getImage());
                    try {
                        model.updatePerson(p, newPerson);
                    } catch (DuplicatePersonException | PersonNotFoundException e) {
                        throw new CommandException(MESSAGE_FAILURE);
                    }
                    break;
                }
            }
        }
    }
```
###### /java/seedu/address/logic/parser/FilterGroupCommandParser.java
``` java
    @Override
    public FilterGroupCommand parse (String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterGroupCommand.MESSAGE_USAGE));
        }

        return new FilterGroupCommand(trimmedArgs);
    }
```
###### /java/seedu/address/logic/parser/GroupCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the GroupCommand
     * and returns an GroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
        }

        String[] groupKeyWords = trimmedArgs.split("\\s+");

        return new GroupCommand(makeNameList(groupKeyWords));
    }

    /**
     *
     * @param input array of arguments
     * @return just a list of the names in the argument
     */
    private List<String> makeNameList (String[] input) {
        List<String> nameList = new ArrayList<>();

        nameList.addAll(Arrays.asList(input));

        return nameList;
    }
```
###### /java/seedu/address/logic/trie/CommandTrie.java
``` java
    public CommandTrie() {
        commandCollection = new CommandCollection();
        commandSet = commandCollection.getCommandSet();
        commandMap = commandCollection.getCommandMap();
        for (String command : commandSet) {
            this.insert(command);
        }
    }

    /**
     * Indicates whether or note a node is a leaf
     */
    public boolean isLeaf(Node current) {
        return !current.hasNext() && !current.hasChild();
    }

    /**
     * @param input key to autocomplete
     * @return input if the command is not found, otherwise String representation of command word
     */
    public String attemptAutoComplete(String input) throws NullPointerException {
        StringBuilder output = new StringBuilder();

        if (commandSet.contains(input)) {
            //Don't need to traverse trie
            if (commandMap.containsKey(input)) {
                output.append(" ");
                output.append(commandMap.get(input));
            }
        } else {
            //Need to search trie
            char[] inputArray = input.toLowerCase().toCharArray();
            Node temp = root;
            int i = 0;

            while (!isLeaf(temp)) {
                if (i < inputArray.length) {
                    if (temp.getKey() == inputArray[i]) {
                        output.append(inputArray[i]);
                        temp = temp.getChild();
                        i++;
                    } else {
                        temp = temp.getNext();
                    }
                } else if (temp.hasNext()) {
                    return input;
                } else {
                    output.append(temp.getKey());
                    temp = temp.getChild();
                }
            }
            output.append(temp.getKey());
        }
        return output.toString();
    }

    /**
     * Insert function for trie
     *
     * @param input key
     */
    public void insert(String input) {
        char[] inputArray = input.toCharArray();

        if (root == null) {
            root = new Node(inputArray[0], null, null);
            Node temp = root;
            for (int i = 1; i < inputArray.length; i++) {
                temp.setChild(new Node(inputArray[i], null, null));
                temp = temp.getChild();
            }
        } else {
            Node temp = root;
            int i = 0;
            //Navigate to leaf of trie
            while (temp.hasNext()) {
                if (temp.getKey() == inputArray[i]) {
                    temp = temp.getChild();
                    i++;
                } else {
                    temp = temp.getNext();
                }
            }

            for (; i < inputArray.length - 1; ) {
                if (temp.getKey() == inputArray[i] && temp.hasChild()) {
                    //Navigate to existing child
                    i++;
                    temp = temp.getChild();
                } else if (temp.getKey() == inputArray[i] && !temp.hasChild()) {
                    //Add child
                    i++;
                    temp.setChild(new Node(inputArray[i], null, null));
                    temp = temp.getChild();
                } else {
                    //Add next
                    temp.setNext(new Node(inputArray[i], null, null));
                    temp = temp.getNext();
                }
            }
        }
    }

    /**
     * @return a list of all possible strings for the given input
     */
    public List<String> getOptions(String input) {
        List<String> options = new ArrayList<String>();
        char[] inputArray = input.toLowerCase().toCharArray();
        Node temp = root;
        int i = 0;

        while (!isLeaf(temp) && i < inputArray.length) {
            if (temp.getKey() == inputArray[i]) {
                i++;
                temp = temp.getChild();
            } else {
                temp = temp.getNext();
            }
        }

        return recursiveGetOptions(temp, input);
    }

    /**
     * Recursive helper function for traversing the trie and getting possible options
     */
    private List<String> recursiveGetOptions(Node start, String stub) {
        List<String> options = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        output.append(stub);

        while (!isLeaf(start)) {
            if (start.hasNext()) {
                options.addAll(recursiveGetOptions(start.getNext(), output.toString()));
                if (start.hasChild()) {
                    output.append(start.getKey());
                    options.addAll(recursiveGetOptions(start.getChild(), output.toString()));
                }
                return options;
            } else {
                output.append(start.getKey());
                start = start.getChild();
            }
        }
        output.append(start.getKey());
        options.add(output.toString());
        return options;
    }

    public Set<String> getCommandSet() {
        return commandSet;
    }

```
###### /java/seedu/address/logic/trie/Node.java
``` java
    public Node(char key , Node next, Node child) {
        requireNonNull(key);
        this.key = key;
        this.next = next;
        this.child = child;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasChild() {
        return child != null;
    }

    public char getKey() {
        return key;
    }

    public Node getChild() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
```
###### /java/seedu/address/storage/XmlAdaptedGroup.java
``` java
    /**
     * Converts group to into JAXB usable object
     */
    public XmlAdaptedGroup (Group group) {
        this.groupName = group.getGroupName();
    }

    /**
     * Convert from jax-b back to model's Group object
     *
     * @throws IllegalValueException
     */
    public Group toModelType() throws IllegalValueException {
        return new Group (groupName);
    }
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Handles the Tab button pressed event.
     */
    private void handleAutoComplete() {
        String input = commandTextField.getText();
        try {
            String command = commandTrie.attemptAutoComplete(input);
            if (input.equals(command)) {
                //Multiple options for autocomplete
                setStyleToIndicateCommandFailure();
                showAutoCompleteOptions(commandTrie.getOptions(input));
            } else if (commandSet.contains(command)) {
                //Able to autocomplete to a correct command
                this.replaceText(command);
                logger.info("Autocomplete successful with input: " + input + " to " + command);
            } else if (commandSet.contains(input)) {
                //Add parameters
                this.replaceText(input + command);
                logger.info("Autocomplete successful with input: " + input + " to " + input + command);
            }
        } catch (NullPointerException e) {
            //No command exists in trie or no trie exists
            setStyleToIndicateCommandFailure();
            logger.info("Autocomplete failed with input: " + input);
        }
    }

    /**
     * Handles the construction of the ContextMenu for autocomplete failure
     * @param options representing potential completion options
     */
    private void showAutoCompleteOptions(List<String> options) {
        for (String option : options) {
            MenuItem item = new MenuItem(option);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    replaceText(item.getText());
                    autoCompleteBox.getItems().clear();
                }
            });
            autoCompleteBox.getItems().add(item);
        }
        logger.info("Autocomplete returned possible options.");
        autoCompleteBox.show(commandTextField, Side.BOTTOM, 0.0, 0.0);
    }
```
###### /java/seedu/address/ui/GroupCard.java
``` java
    public GroupCard (Group group) {
        super(FXML);
        this.group = group;
        name.setText(group.getGroupName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        GroupCard card = (GroupCard) other;
        return name.getText().equals(card.name.getText())
                && group.equals(card.group);
    }
```
###### /java/seedu/address/ui/GroupListPanel.java
``` java
    public GroupListPanel(ObservableList<Group> groupList) {
        super(FXML);
        setConnections(groupList);
        registerAsAnEventHandler(this);
    }

    private void setConnections (ObservableList<Group> groupList) {
        ObservableList<GroupCard> groupCards = EasyBind.map(
                groupList, (group) -> new GroupCard(group));
        groupListView.setItems(groupCards);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        groupListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.info("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new GroupPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code GroupCard}.
     */
    class GroupListViewCell extends ListCell<GroupCard> {

        @Override
        protected void updateItem(GroupCard group, boolean empty) {
            super.updateItem(group, empty);

            if (empty || group == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(group.getRoot());
            }
        }
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleGroupSelectedEvent (GroupPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandBox.handleCommandInputChanged("filter " + event.getNewSelection().group.groupName);
    }
```
###### /resources/view/GroupListCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
        <padding>
            <Insets top="5" right="10" bottom="5" left="10" />
        </padding>
        <Label fx:id="name" text="\$first" styleClass="cell_big_label" />
</HBox>
```
###### /resources/view/GroupListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="groupListView" VBox.vgrow="ALWAYS" orientation="HORIZONTAL" />
</VBox>
```
###### /resources/view/MainWindow.fxml
``` fxml
    <VBox fx:id="groupList" SplitPane.resizableWithParent="false" prefWidth="700" minWidth="700" maxWidth="700"
          maxHeight="55" minHeight="55">
      <padding>
        <Insets top="10" right="10" bottom="10" left="10" />
      </padding>
      <StackPane fx:id="groupListPanelPlaceholder" VBox.vgrow="ALWAYS"/>
    </VBox>
```
