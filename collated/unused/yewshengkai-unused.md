# yewshengkai-unused
###### \ButtonsPanel.java
``` java
/*Since having buttons to open windows form and
 executing different command is not graded, these features are removed.*/
/**
 * Panel containing all the buttons.
 */
public class ButtonsPanel extends UiPart<Region> {
    private static final String FXML = "ButtonsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ButtonsPanel.class);

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private Button refreshButton;

    public ButtonsPanel() {
        super(FXML);
        setButtonsImage();
        registerAsAnEventHandler(this);
    }

    public void setButtonsImage() {
        addButton.setGraphic(new ImageView("/docs/images/addIcon.png"));
        deleteButton.setGraphic(new ImageView("/docs/images/deleteIcon.png"));
        editButton.setGraphic(new ImageView("/docs/images/editIcon.png"));
        clearButton.setGraphic(new ImageView("/docs/images/clearIcon.png"));
        redoButton.setGraphic(new ImageView("/docs/images/redoIcon.png"));
        undoButton.setGraphic(new ImageView("/docs/images/undoIcon.png"));
        refreshButton.setGraphic(new ImageView("/docs/images/refreshIcon.png"));
    }

}
```
###### \GroupListPanel.java
``` java
/*Was told group feature with selection on side panel to list person
 is no different than tag feature. Hence, removed implementation.*/
/**
 * Panel containing the list of groups.
 */
public class GroupListPanel extends UiPart<Region> {
    private static final String FXML = "GroupListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupListPanel.class);

    //@TODO Group left list view

    @FXML
    private ListView<PersonCard> groupListView;

    public GroupListPanel(ObservableList<ReadOnlyPerson> GroupList) {
        super(FXML);
        setConnections(GroupList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> GroupList) {
        //@TODO setConnections to show 1. Personals (all person), 2. Groups (all different tags)
        /*
        ObservableList<PersonCard> mappedList = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1));
        personListView.setItems(mappedList);
        personListView.setCellFactory(listView -> new PersonListPanel.PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();*/
    }

    private void setEventHandlerForSelectionChangeEvent() {
        groupListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in group list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            groupListView.scrollTo(index);
            groupListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */

    //@TODO GroupListViewCell to display all tag list
    /*class GroupListViewCell extends ListCell<PersonCard> {
        @Override
        protected void updateItem(PersonCard person, boolean empty) {
            super.updateItem(person, empty);
            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }*/

}
```
###### \MainWindow.java
``` java
/*Future implementation.*/
/**
 * Open a file
 */
@FXML
private void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import from...");
        fileChooser.showOpenDialog(primaryStage);
        }

/**
 * Save file to specific format
 */
@FXML
private void handleExport() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx");
        FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
        FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML Data (*.xml)", "*.xml");
        FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("Text Documents (*.txt)", "*.txt");

        fileChooser.getExtensionFilters().add(excelFilter);
        fileChooser.getExtensionFilters().add(pdfFilter);
        fileChooser.getExtensionFilters().add(xmlFilter);
        fileChooser.getExtensionFilters().add(textFilter);

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Export to..");
        fileChooser.setInitialFileName("iungoAB");
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
        String content = "Prep_data_save_to_excel";
        try {
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
        } catch (Exception ex) {
        System.out.println(ex.toString());
        }

        }
        }
```
