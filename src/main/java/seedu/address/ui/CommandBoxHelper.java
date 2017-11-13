package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

//@@author fongwz
/**
 * The UI component that is responsible for listing out possible commands based on user input in CLI
 */
public class CommandBoxHelper extends UiPart<Region> {

    private static final String FXML = "CommandBoxHelper.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBoxHelper.class);
    private final Logic logic;

    private Character firstChar;
    private String commandString;
    private ArrayList<String> helpList;

    @FXML
    private ListView<HelperCard> commandBoxHelperList;


    public CommandBoxHelper(Logic logic) {
        super(FXML);
        this.logic = logic;
        this.helpList = new ArrayList<String>();
    }

    /**
     * Command to display text within the CommandBoxHelper given user input in the CLI.
     */
    public boolean listHelp(TextField commandText) {
        clearListView();
        try {
            commandString = commandText.getText();
            firstChar = commandText.getText().charAt(0);
        } catch (Exception e) {
            //logger.info("Invalid String or String is empty");
            //logger.info("Hiding command helper");
            //comment out if command box helper working as intended, fills log with unnecessary spam.
            return false;
        }

        for (int i = 0; i < logic.getCommandList().size(); i++) {
            if (checkSubset(logic.getCommandList().get(i), commandString, firstChar)) {
                helpList.add(logic.getCommandList().get(i));
            }
        }
        if (helpList.isEmpty()) {
            return false;
        } else {
            setConnections(FXCollections.observableList(helpList));
            return true;
        }
    }

    /**
     * Checks if commandBoxHelper is empty
     * @return true if empty, false otherwise
     */
    public boolean checkEmpty() {
        return helpList.isEmpty();
    }

    /**
     * Called when user presses down key on command helper
     */
    public void selectDownHelperBox() {
        if (commandBoxHelperList.getSelectionModel().getSelectedItem() == null
                || commandBoxHelperList.getSelectionModel().getSelectedIndex()
                == commandBoxHelperList.getItems().size() - 1) {
            commandBoxHelperList.getSelectionModel().selectFirst();
            commandBoxHelperList.scrollTo(0);
        } else {
            commandBoxHelperList.scrollTo(commandBoxHelperList.getSelectionModel().getSelectedIndex() + 1);
            commandBoxHelperList.getSelectionModel().select(
                    commandBoxHelperList.getSelectionModel().getSelectedIndex() + 1);
        }
    }

    /**
     * Called when user presses up key on command helper
     */
    public void selectUpHelperBox() {
        if (commandBoxHelperList.getSelectionModel().getSelectedItem() == null) {
            commandBoxHelperList.getSelectionModel().selectFirst();
            commandBoxHelperList.scrollTo(0);
        } else if (commandBoxHelperList.getSelectionModel().getSelectedIndex() == 0) {
            commandBoxHelperList.getSelectionModel().selectLast();
            commandBoxHelperList.scrollTo(
                    commandBoxHelperList.getItems().size() - 1
            );
        } else {
            commandBoxHelperList.scrollTo(
                    commandBoxHelperList.getSelectionModel().getSelectedIndex() - 1
            );
            commandBoxHelperList.getSelectionModel().select(
                    commandBoxHelperList.getSelectionModel().getSelectedIndex() - 1);
        }
    }

    /**
     * Checks whether the command helper has been selected
     * @return true if selected, false otherwise
     */
    public boolean isMainSelected() {
        for (int i = 0; i <= helpList.size(); i++) {
            if (commandBoxHelperList.getSelectionModel().isSelected(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter method for each HelperCard in the commandBoxHelperList
     * @return String of each HelperCard
     */
    public String getHelperText() {
        for (int i = 0; i <= logic.getCommandTemplateList().size(); i++) {
            if (logic.getCommandTemplateList().get(i).contains(
                    commandBoxHelperList.getSelectionModel().getSelectedItem().getText())) {
                return logic.getCommandTemplateList().get(i);
            }
        }
        return null;
    }

    /**
     * Checks if commandFieldString is a subset of commandWord and if their first letter matches
     */
    private boolean checkSubset(String commandWord , String commandFieldString, Character firstChar) {
        try {
            if (commandWord.contains(commandFieldString) && firstChar.equals(commandWord.charAt(0))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Empties the list view
     */
    private void clearListView() {
        try {
            helpList.clear();
            ArrayList<HelperCard> selectedItemsCopy = new ArrayList<>(
                    commandBoxHelperList.getSelectionModel().getSelectedItems());
            commandBoxHelperList.getItems().removeAll(selectedItemsCopy);
        } catch (Exception e) {
            //logger.info(e.getMessage() + " no items in the list!");
            // comment out if command helper is working as intended, fills log with unnecessary spam.
        }
    }

    private void setConnections(ObservableList<String> commandList) {
        ObservableList<HelperCard> mappedList = EasyBind.map(
                commandList, (commandString) -> new HelperCard(commandString));
        commandBoxHelperList.setItems(mappedList);
        commandBoxHelperList.setCellFactory(listView -> new CommandListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class CommandListViewCell extends ListCell<HelperCard> {

        @Override
        protected void updateItem(HelperCard command, boolean empty) {
            super.updateItem(command, empty);

            if (empty || command == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(command.getRoot());
            }
        }
    }
}
