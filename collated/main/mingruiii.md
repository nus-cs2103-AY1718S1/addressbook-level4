# mingruiii
###### /java/seedu/address/logic/commands/AddCommand.java
``` java
    public static final String COMMAND_HOTKEY = "Ctrl+Equals";
    public static final String FORMAT = "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS";
```
###### /java/seedu/address/logic/commands/DeleteCommand.java
``` java
    public static final String COMMAND_HOTKEY = "Ctrl+D";
    public static final String COMMAND_HOTKEY_ALTERNATIVE = "Ctrl+Minus";
    public static final String FORMAT = "delete INDEX";
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
    public static final String COMMAND_HOTKEY = "Ctrl+E";
    public static final String FORMAT = "edit INDEX [Field(s) you want to change]";
```
###### /java/seedu/address/logic/commands/EmailCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Launches the mail composing window of the default mail client.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "mail", "mailto", "m"));
    public static final String COMMAND_HOTKEY = "Ctrl+M";
    public static final String FORMAT = "email INDEX [s/SUBJECT]";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Launch the mail composing window of the default mail client.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SUBJECT + "SUBJECT]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_SUBJECT + "Hello";
    public static final String MESSAGE_SUCCESS = "Opening email composer";
    public static final String MESSAGE_NOT_SUPPORTED = "Function not supported";
    private final Index targetIndex;
    private final String subject;
    private URI mailtoUri = null;

    public EmailCommand(Index targetIndex, String subject) {
        this.targetIndex = targetIndex;
        this.subject = subject;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getLatestPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson recipient  = lastShownList.get(targetIndex.getZeroBased());

        String recipientEmailAddress = recipient.getEmail().value;

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
            Desktop desktop = Desktop.getDesktop();
            try {
                mailtoUri = new URI("mailto:" + recipientEmailAddress + "?subject=" + subject);
                desktop.mail(mailtoUri);
                selectRecipient(targetIndex);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            throw new CommandException(String.format(MESSAGE_NOT_SUPPORTED));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex))
                && this.subject.equals(((EmailCommand) other).subject); // state check
    }

    /**
     * Selects the {@code recipient} for the contact details to be displayed
     */
    private void selectRecipient(Index index) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
    }
}
```
###### /java/seedu/address/logic/commands/FindCommand.java
``` java
    public static final String COMMAND_HOTKEY = "Ctrl+f";
    public static final String FORMAT = "find KEYWORD(S)";
```
###### /java/seedu/address/logic/commands/NewRolodexCommand.java
``` java
    public static final String COMMAND_HOTKEY = "Ctrl+N";
    public static final String FORMAT = "new FILEPATH";
```
###### /java/seedu/address/logic/commands/OpenRolodexCommand.java
``` java
    public static final String COMMAND_HOTKEY = "Ctrl+O";
    public static final String FORMAT = "open FILEPATH";
```
###### /java/seedu/address/logic/commands/RemarkCommand.java
``` java
    public static final String FORMAT = "remark INDEX r/REMARK";
    public static final String COMMAND_HOTKEY = "Ctrl+R";
```
###### /java/seedu/address/logic/commands/SelectCommand.java
``` java
    public static final String COMMAND_HOTKEY = "Ctrl+S";
    public static final String FORMAT = "select INDEX";
```
###### /java/seedu/address/logic/parser/CliSyntax.java
``` java
    public static final Prefix PREFIX_SUBJECT = new Prefix("s/");
```
###### /java/seedu/address/logic/parser/EmailCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.ParserUtil.isParsableIndex;
import static seedu.address.logic.parser.ParserUtil.parseFirstIndex;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstIndex;
import static seedu.address.model.ModelManager.getLastRolodexSize;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseArgsException;

/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseArgsException if the user input does not conform the expected format, but is in a suggestible format
     */
    public EmailCommand parse(String args) throws ParseArgsException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        try {
            String subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT)).orElse("");
            subject = subject.replaceAll(" ", "%20");
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            return new EmailCommand(index, subject);
        } catch (IllegalValueException ive) {
            throw new ParseArgsException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns a formatted argument string given unformatted
     * {@code commandWord} and {@code rawArgs}
     * or a {@code null} {@code String} if not formattable.
     */
    public static String parseArguments(String commandWord, String rawArgs) {
        // Check if index (number) exists, removes Email prefix (if it exists) and re-adds it before returning.
        if (isParsableIndex(rawArgs, getLastRolodexSize())) {
            String indexString = Integer.toString(parseFirstIndex(rawArgs, getLastRolodexSize()));
            String subject = parseRemoveFirstIndex(rawArgs, getLastRolodexSize())
                    .trim().replace(PREFIX_SUBJECT.toString(), "");
            return " " + indexString + " " + PREFIX_SUBJECT + subject;
        } else if (isParsableIndex(commandWord, getLastRolodexSize())) {
            String indexString = Integer.toString(parseFirstIndex(commandWord, getLastRolodexSize()));
            String subject = rawArgs.trim().replace(PREFIX_SUBJECT.toString(), "");
            return " " + indexString + " " + PREFIX_SUBJECT + subject;
        }
        return null;
    }

}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }
```
###### /java/seedu/address/logic/parser/RolodexParser.java
``` java
            } else if (EmailCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
                return new EmailCommandParser().parse(arguments);
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    private static final int MILLISECONDS_PAUSE_BEFORE_PRESSING_CTRL = 100;
    //Start and end position of the placeholder 'NAME' in the full format of add command
    private static final int START_OF_ADD_FIRST_FIELD = 6;
    private static final int END_OF_ADD_FIRST_FIELD = 10;
    //Start and end position of the placeholder 'INDEX' in the full format of remark command
    private static final int START_OF_REMARK_FIRST_FIELD = 7;
    private static final int END_OF_REMARK_FIRST_FIELD = 12;
    private static final String[] LIST_OF_ALL_COMMANDS = {AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, EmailCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
        NewRolodexCommand.COMMAND_WORD, OpenRolodexCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
        RemarkCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD, StarWarsCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD};
    //The distance from the start of the prefix to the start of placeholder is two
    private static final int JUMP_TO_START_OF_PLACEHOLDER = 2;
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    private int anchorPosition;
    private String selectedText = "";
    private String input;
    private FxRobot robot;
    private Set<String> setOfAutoCompleteCommands = new HashSet<>();
    private boolean needToUpdateSelection;
    private boolean isFirstTab;
```
###### /java/seedu/address/ui/CommandBox.java
``` java
        TextFields.bindAutoCompletion(commandTextField, sr -> {
            Set<String> suggestedCommands = new HashSet<>();
            for (String command: LIST_OF_ALL_COMMANDS) {
                if (!sr.getUserText().isEmpty() && !command.equals(sr.getUserText())
                        && command.startsWith(sr.getUserText().trim().toLowerCase())) {

                    suggestedCommands.add(command);
                }
            }
            return suggestedCommands;
        });

        setOfAutoCompleteCommands.addAll(AddCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(DeleteCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(EditCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(EmailCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(FindCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(NewRolodexCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(OpenRolodexCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(RemarkCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(SelectCommand.COMMAND_WORD_ABBREVIATIONS);

        // calls #processInput() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> processInput());
```
###### /java/seedu/address/ui/CommandBox.java
``` java
        case TAB:

            if (keyEvent.isShiftDown()) {
                //nagivate to the previous field when shift+tab is pressed
                selectPreviousField();
            } else {
                autocomplete();
            }

            //press control key to make the text selection highlight appear in command box
            pressCtrl();
            break;
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * press tab key, used in KeyListener class to trigger auto-completion
     */
    public void pressTab() {
        robot.push(KeyCode.TAB);
    }

    /**
     * press control key, used to display selected text
     */
    private void pressCtrl() {
        //add pause to prevent pressing tab before the input is updated
        pause = new PauseTransition();
        pause.setDuration(Duration.millis(MILLISECONDS_PAUSE_BEFORE_PRESSING_CTRL));
        pause.setOnFinished(event -> {
            robot.push(KeyCode.CONTROL);
        });
        pause.play();
    }
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Processes input as user is typing
     */
    public void processInput() {
        setFocus();
        input = commandTextField.getText();
        updateKeyboardIcon();
        setStyleToDefault();

        if (isFirstTab) {
            //only do this after the first tab which is used to display the full command format
            autoSelectFirstField();
        }

        if (needToUpdateSelection) {
            updateSelection();
        }
    }

    /**
     * Updates the text selection in command box
     */
    public void updateSelection() {
        commandTextField.selectRange(anchorPosition, anchorPosition + selectedText.length());
        selectedText = "";
        needToUpdateSelection = false;
    }
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Automatically selects the first field that the user needs to key in
     */
    public void autoSelectFirstField() {
        switch (input) {
        case AddCommand.FORMAT:
            commandTextField.selectRange(START_OF_ADD_FIRST_FIELD, END_OF_ADD_FIRST_FIELD);
            break;
        case RemarkCommand.FORMAT:
            commandTextField.selectRange(START_OF_REMARK_FIRST_FIELD, END_OF_REMARK_FIRST_FIELD);
            break;
        case EditCommand.FORMAT:
        case EmailCommand.FORMAT:
        case FindCommand.FORMAT:
        case SelectCommand.FORMAT:
        case DeleteCommand.FORMAT:
        case NewRolodexCommand.FORMAT:
        case OpenRolodexCommand.FORMAT:
            int indexOfFirstSpace = input.indexOf(" ");
            commandTextField.selectRange(indexOfFirstSpace + 1, input.length());
            break;
        default:
            //input is not an auto-complete command
            //do nothing
        }

        isFirstTab = false;
    }

    private boolean isAutoCompleteCommand(String command) {
        return setOfAutoCompleteCommands.contains(command);
    }

    private boolean isAddCommandFormat(String input) {
        return input.startsWith("add")
                && input.contains("n/") && input.contains("p/") && input.contains("e/") && input.contains("a/");
    }

    private boolean isRemarkCommandFormat(String input) {
        return input.startsWith("remark") && input.contains("r/");
    }

    /**
     * Selects the word following the current caret position
     */
    private void changeSelectionToNextField() {
        commandTextField.selectNextWord();
        anchorPosition = commandTextField.getAnchor();
        selectedText = commandTextField.getSelectedText().trim();
    }

    /**
     * Displays the full format of the command
     * In the case of add or remark command, if the user is trying to navigate to the next field (full format
     * is already diaplayed), auto-select the next field
     */
    private void autocomplete() {
        input = commandTextField.getText().trim().toLowerCase();
        if (isAutoCompleteCommand(input)) {
            displayFullFormat(input);
            needToUpdateSelection = false;
            isFirstTab = true;

        } else if (isAddCommandFormat(input)) {
            needToUpdateSelection = true;

            int positionOfNameField = input.indexOf(PREFIX_NAME.toString());
            int positionOfPhoneField = input.indexOf(PREFIX_PHONE.toString());
            int positionOfEmailField = input.indexOf(PREFIX_EMAIL.toString());
            int positionOfAddressField = input.indexOf(PREFIX_ADDRESS.toString());
            int positionOfCurrentCaret = commandTextField.getCaretPosition();
            int[] fieldsPositionArray = {positionOfNameField, positionOfPhoneField,
                positionOfEmailField, positionOfAddressField};

            selectNextField(positionOfCurrentCaret, fieldsPositionArray);

        } else if (isRemarkCommandFormat(input)) {
            needToUpdateSelection = true;

            int positionOfIndex = input.indexOf(" ") - 1;
            int positionOfRemarkFiels = input.indexOf(PREFIX_REMARK.toString());
            int positionOfCurrentCaret = commandTextField.getCaretPosition();
            int[] fieldsPositionArray = {positionOfIndex, positionOfRemarkFiels};

            selectNextField(positionOfCurrentCaret, fieldsPositionArray);
        }
    }

    /**
     * Changes text selection to the previous field
     * Only applies to add command
     * (Remark command only has two fields, using tab to toggle between the two fields is enough)
     */
    private void selectPreviousField() {
        input = commandTextField.getText().trim().toLowerCase();
        if (isAddCommandFormat(input)) {

            needToUpdateSelection = true;

            int positionOfNameField = input.indexOf(PREFIX_NAME.toString());
            int positionOfPhoneField = input.indexOf(PREFIX_PHONE.toString());
            int positionOfEmailField = input.indexOf(PREFIX_EMAIL.toString());
            int positionOfAddressField = input.indexOf(PREFIX_ADDRESS.toString());
            int positionOfEnd = input.length();
            int positionOfCurrentCaret = commandTextField.getCaretPosition();

            int[] fieldsPositionArray = {positionOfNameField, positionOfPhoneField, positionOfEmailField,
                positionOfAddressField, positionOfEnd};
            selectPreviousField(positionOfCurrentCaret, fieldsPositionArray);
        }
    }

    /**
     * Checks the current position is in between which two fields
     * And navigates to the previous field
     * @param fieldsPositionArray array of field positions in the order of left to right
     */
    private void selectPreviousField(int currentPosition, int[] fieldsPositionArray) {
        boolean changedCaretPosition = false;
        for (int i = 1; i < fieldsPositionArray.length - 1; i++) {
            //check if the current position is in between field[i] and field[i + 1], if so, change selection
            //to the placeholder of field[i - 1]
            if (currentPosition > fieldsPositionArray[i] && currentPosition <= fieldsPositionArray[i + 1]) {
                commandTextField.positionCaret(fieldsPositionArray[i - 1] + JUMP_TO_START_OF_PLACEHOLDER);
                changeSelectionToNextField();
                changedCaretPosition = true;
            }
        }
        if (!changedCaretPosition) {
            //if caret position is not changed in the above for loop,
            //which means the caret is currently at the last field, then change selection to the second last field
            //so that continuously pressing shift+tab will go in a cycle
            commandTextField.positionCaret(fieldsPositionArray[fieldsPositionArray.length - 2]
                    + JUMP_TO_START_OF_PLACEHOLDER);
            changeSelectionToNextField();
        }
    }

    /**
     * Checks the current position is in between which two fields
     * And navigates to the next field
     * @param fieldsPositionArray array of field positions in the order of left to right
     *                            last element is the end position of text input
     */
    private void selectNextField(int currentPosition, int[] fieldsPositionArray) {
        boolean changedCaretPosition = false;
        for (int i = 0; i < fieldsPositionArray.length - 1; i++) {
            //check if the current position is in between field[i] and field[i + 1], if so, change selection
            //to the placeholder of field[i + 1]
            if (currentPosition > fieldsPositionArray[i] && currentPosition < fieldsPositionArray[i + 1]) {
                commandTextField.positionCaret(fieldsPositionArray[i + 1] + JUMP_TO_START_OF_PLACEHOLDER);
                changeSelectionToNextField();
                changedCaretPosition = true;
            }
        }
        if (!changedCaretPosition) {
            //if caret position is not changed in the above for loop,
            //which means the caret is currently at the last field, then change selection to the first field
            //so that continuously pressing tab will go in a cycle
            commandTextField.positionCaret(fieldsPositionArray[0] + JUMP_TO_START_OF_PLACEHOLDER);
            changeSelectionToNextField();
        }
    }

    /**
     * Displays the full command format in command box
     * @param command input by the user
     */
    private void displayFullFormat(String command) {
        if (AddCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(AddCommand.FORMAT);
        } else if (EditCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(EditCommand.FORMAT);
        } else if (EmailCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(EmailCommand.FORMAT);
        } else if (FindCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(FindCommand.FORMAT);
        } else if (NewRolodexCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(NewRolodexCommand.FORMAT);
        } else if (OpenRolodexCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(OpenRolodexCommand.FORMAT);
        } else if (RemarkCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(RemarkCommand.FORMAT);
        } else if (SelectCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(SelectCommand.FORMAT);
        } else if (DeleteCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(DeleteCommand.FORMAT);
        }
    }
```
###### /java/seedu/address/ui/KeyListener.java
``` java
        } else if (KEY_COMBINATION_OPEN_FILE.match(keyEvent)) {
            displayCommandFormat(OpenRolodexCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_NEW_FILE.match(keyEvent)) {
            displayCommandFormat(NewRolodexCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_ADD.match(keyEvent)) {
            displayCommandFormat(AddCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_EDIT.match(keyEvent)) {
            displayCommandFormat(EditCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_EMAIL.match(keyEvent)) {
            displayCommandFormat(EmailCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_FIND.match(keyEvent)) {
            displayCommandFormat(FindCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_SELECT.match(keyEvent)) {
            displayCommandFormat(SelectCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_DELETE.match(keyEvent) || KEY_COMBINATION_DELETE_ALTERNATIVE.match(keyEvent)) {
            displayCommandFormat(DeleteCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_REMARK.match(keyEvent)) {
            displayCommandFormat(RemarkCommand.COMMAND_WORD);

```
###### /java/seedu/address/ui/KeyListener.java
``` java
    /**
     * Displays the full command format for commands that require multiple fields
     * Pressing the hotkey is the same as entering the command and press tab
     */
    private void displayCommandFormat(String command) {
        //simulate entering the command word and press tab
        commandBox.replaceText(command);
        commandBox.pressTab();
    }
```
###### /java/seedu/address/ui/util/KeyListenerUtil.java
``` java
    public static final KeyCombination KEY_COMBINATION_OPEN_FILE =
            KeyCombination.valueOf(OpenRolodexCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_NEW_FILE =
            KeyCombination.valueOf(NewRolodexCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_ADD = KeyCombination.valueOf(AddCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_EDIT = KeyCombination.valueOf(EditCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_FIND = KeyCombination.valueOf(FindCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_SELECT = KeyCombination.valueOf(SelectCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_DELETE = KeyCombination.valueOf(DeleteCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_DELETE_ALTERNATIVE =
            KeyCombination.valueOf(DeleteCommand.COMMAND_HOTKEY_ALTERNATIVE);
    public static final KeyCombination KEY_COMBINATION_REMARK = KeyCombination.valueOf(RemarkCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_EMAIL = KeyCombination.valueOf(EmailCommand.COMMAND_HOTKEY);
```
###### /java/seedu/address/ui/util/KeyListenerUtil.java
``` java
                    KEY_COMBINATION_ADD,
                    KEY_COMBINATION_EDIT,
                    KEY_COMBINATION_FIND,
                    KEY_COMBINATION_SELECT,
                    KEY_COMBINATION_DELETE,
                    KEY_COMBINATION_DELETE_ALTERNATIVE,
                    KEY_COMBINATION_REMARK,
                    KEY_COMBINATION_EMAIL
```
