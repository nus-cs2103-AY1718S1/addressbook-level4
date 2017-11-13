# Jeremy
###### /resources/view/MainWindow.fxml
``` fxml
    <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border" VBox.vgrow="NEVER">
        <padding>
            <Insets left="20.0" right="20.0" top="20.0" />
        </padding>
        <VBox.margin>
            <Insets />
        </VBox.margin>
    </StackPane>

    <StackPane fx:id="statusbarPlaceholder" prefHeight="13.0" prefWidth="692.0" VBox.vgrow="ALWAYS" />
</VBox>
```
###### /resources/view/MedNusTheme.css
``` css
background {
    -fx-background-color: red;
    background-color: #DDDDDD; /* Used in the default.html file */
}

.label { /* Used in status bar font size, tag font family */
    -fx-font-size: 10pt;
    -fx-font-family: sans-serif;
    -fx-text-fill: red;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: sans-serif;
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: sans-serif;
    -fx-border-color: transparent;
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: black;
    -fx-control-inner-background: red;
    -fx-background-color: red;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color: red;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider { /* Used for split pane divider color */
    -fx-background-color: white;
    -fx-border-color: transparent transparent transparent white;
}

.split-pane { /* Borders left pane. Borders right pane. */
    -fx-background-color: white;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled(even) { /* Used for cell background */
    -fx-background-color: #F2F2F2;
}

.list-cell:filled(odd) { /* Used for cell background */
    -fx-background-color: #F2F2F2;
}

.list-cell:filled:selected { /* Used for cell background when selected */
    -fx-background-color: #CCCCCC;
}

.list-cell:filled:selected #cardPane { /* Used for border on selected cell */
    -fx-border-color: transparent;
    -fx-border-width: 0;
}

.list-cell .label {
    -fx-text-fill: white;
}

.cell_big_label { /* Used for people's names */
    -fx-font-family: sans-serif;
    -fx-font-size: 16px;
    -fx-text-fill: #333333;
}

.cell_small_label { /* Used for people's details */
    -fx-font-family: sans-serif;
    -fx-font-size: 13px;
    -fx-text-fill: #666666;
}

.anchor-pane { /* Effectively the command bar background */
     -fx-background-color: #CE4E4E;
     -fx-background-radius: 10px;
}

.anchor-pane-status { /* Effectively the status bar background */}

.pane-with-border { /* Command pane border background color */
     -fx-background-color: white;
}

.status-bar {
    -fx-background-color: white;
    -fx-text-fill: black;
}

.result-display { /* Used for command results */
    -fx-font-family: sans-serif;
    -fx-font-size: 14pt;
    -fx-text-fill: #808080;
}

.result-display .label {
    /* -fx-text-fill: black !important; */
}

.status-bar .label { /* Status bar font */
    -fx-font-family: sans-serif;
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: green;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane { /* Status bar border */
    -fx-background-color: white;
    -fx-background-radius: 0px;
}

.grid-pane .anchor-pane { /* Status bar background */
    -fx-background-color: white;
}

.context-menu { /* Used for menu expansion button background */
    -fx-background-color: #393E41;
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar { /* Used for menu bar colour */
    -fx-background-color: #CE4E4E;
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: sans-serif;
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: red;
    -fx-border-width: 2;
    -fx-background-color: blue;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: black;
    -fx-border-radius: 20;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: red;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: red;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: blue;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: black;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: black;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: green;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: black;
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar { /* Scroll bar column background color */
    -fx-background-color: #CCCCCC;
}

.scroll-bar .thumb { /* Scroll bar background color */
    -fx-background-color: #B3B3B3;
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane { /*Background of Listed contacts*/
    -fx-background-color: #E25555;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: black;
}

#commandTextField { /* Command box */
    -fx-background-color: #CE4E4E;
    -fx-background-insets: 0;
    -fx-font-family: sans-serif;
    -fx-font-size: 14pt;
    -fx-text-fill: white;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content { /* Result box background color and radius */
    -fx-background-color: #CCCCCC;
    -fx-padding: 10px;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #4F6D7A;
    -fx-padding: 2 7 2 7;
    -fx-border-radius: 2;
    -fx-background-radius: 10;
    -fx-font-size: 11;
}
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Handles KeyPress Commands that are not keyed with Shift button held down.
     *
     * @param keyEvent Key event pressed by user.
     */
    private void handleStandardPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            keyEvent.consume();
            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        case ESCAPE:
            keyEvent.consume();
            commandTextField.setText("");
            break;
        case ALT:
            keyEvent.consume();
            shiftCaretLeftByWord();
            break;
        case CONTROL:
            keyEvent.consume();
            shiftCaretRightByWord();
            break;
        case RIGHT:
            boolean isCaretWithin = commandTextField.getCaretPosition() < commandTextField.getText().length();
            if (isCaretWithin) {
                break;
            }
            addsNextPrefix();
            break;
        default:
        }
    }

    /**
     * Handles KeyPress Commands that are keyed with Shift button held down.
     *
     * @param keyEvent Key event pressed by user with shift pressed.
     */
    private void handleShiftPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case ALT:
            keyEvent.consume();
            commandTextField.positionCaret(0);
            break;
        case CONTROL:
            keyEvent.consume();
            commandTextField.positionCaret(commandTextField.getText().length());
            break;
        case DELETE:
        case BACK_SPACE:
            keyEvent.consume();
            deleteByChunk();
            break;
        default:
        }
    }

    /**
     * Deletes the word or a chunk of blank spaces on the left.
     * Does not matter if caret is at end of text or between lines. Method will automatically
     * detect and execute.
     * 1. If Caret is at far left, break.
     * 2. If Caret is at far right, check if left side is blank or word and execute appropriately.
     * 3. If " " is present on the left of Caret, delete all blank spaces before.
     * 4. If Caret is between word, execute normal delete method.
     * 5. If Character is on the left and " " is on the right, delete chunk on left.
     */
    private void deleteByChunk() {
        int originalCaretPosition = commandTextField.getCaretPosition();
        int newCaretPosition = commandTextField.getCaretPosition();
        int mostRight = commandTextField.getText().length();
        if (newCaretPosition == 0) {
            return;
        } else if (newCaretPosition == mostRight) {
            newCaretPosition = farRightDeleteChunk(newCaretPosition);
        } else if (isEmptyBefore(newCaretPosition)) {
            newCaretPosition = shiftLeftIgnoringSpaces(newCaretPosition);
        } else if (!isEmptyBefore(newCaretPosition) && !isEmptyAfter(newCaretPosition)) {
            newCaretPosition -= 1;
        } else {
            newCaretPosition = shiftLeftIgnoringWords(newCaretPosition);
        }
        setNewWord(newCaretPosition, originalCaretPosition);
        commandTextField.positionCaret(newCaretPosition);
    }

    /**
     * Deletes chunk in the situation where caret is at the far right.
     *
     * @param newCaretPosition Passes in the existing caret position.
     * @return newCaretPosition shifted left by chunk.
     */
    private int farRightDeleteChunk(int newCaretPosition) {
        if (isEmptyBefore(newCaretPosition)) {
            return shiftLeftIgnoringSpaces(newCaretPosition);
        }
        return shiftLeftIgnoringWords(newCaretPosition);
    }

    /**
     * Sets a new word with all string elements between the two parameters removed.
     *
     * @param newCaretPosition Left boundary of the word.
     * @param originalCaretPosition Right boundary of the word.
     */
    private void setNewWord(int newCaretPosition, int originalCaretPosition) {
        String before = commandTextField.getText().substring(0, newCaretPosition);
        String answer;
        if (atEitherEnds(originalCaretPosition)) {
            answer = before;
        } else {
            String after = commandTextField.getText().substring(originalCaretPosition);
            answer = before + after;
        }
        commandTextField.setText(answer);
    }

    /**
     * Checks if caret is at either ends.
     *
     * @param originalCaretPosition Caret to evaluate.
     * @return True if caret is either at far left or far right.
     */
    private boolean atEitherEnds(int originalCaretPosition) {
        boolean atFarLeft = (originalCaretPosition == 0);
        boolean atFarRight = (originalCaretPosition == commandTextField.getText().length());
        return atFarLeft || atFarRight;
    }


    /**
     * Shifts the caret left to the left of the first character of the next word
     * <p>
     * 1. If Caret is at far left, break
     * <p>
     * 2. If Char is present on left of Caret, shift left until
     * a) Caret is at far left or
     * b) "_" is found
     * <p>
     * 3. If "_" is present on left of Caret, shift left until 2. Condition holds
     * Run Step 2
     */
    private void shiftCaretLeftByWord() {
        int newCaretPosition = commandTextField.getCaretPosition();
        if (newCaretPosition == 0) {
            return;
        } else if (isEmptyBefore(newCaretPosition)) {
            newCaretPosition = shiftLeftIgnoringSpaces(newCaretPosition);
        }
        newCaretPosition = shiftLeftIgnoringWords(newCaretPosition);
        commandTextField.positionCaret(newCaretPosition);
    }


    /**
     * Shifts the caret right to the right of the last character of the next word
     * <p>
     * 1. If Caret is at far right, break
     * <p>
     * 2. If Char is present on right of Caret, shift right until
     * a) Caret is at far right or
     * b) "_" is found
     * <p>
     * 3. If "_" is present on right of Caret, shift right until 2. Condition holds
     * Run Step 2
     */
    private void shiftCaretRightByWord() {
        int newCaretPosition = commandTextField.getCaretPosition();
        int maxAchievablePosition = commandTextField.getText().length();
        if (newCaretPosition == maxAchievablePosition) {
            return;
        } else if (isEmptyAfter(newCaretPosition)) {
            newCaretPosition = shiftRightIgnoringSpaces(newCaretPosition, maxAchievablePosition);
        }
        newCaretPosition = shiftRightIgnoringWords(newCaretPosition, maxAchievablePosition);
        commandTextField.positionCaret(newCaretPosition);
    }

    /**
     * Shifts the caret left, ignoring all empty spaces.
     * <p>
     * Note: Will not implement exception throwing here as shiftCaretLeftByWord is set up in such a way
     * that pre-conditions as follows are met. Do not want to write code which will affect test coverage
     * which is impossible to resolve.
     * <p>
     * Pre-Condition 1: Current caret position must have an empty space string on the left.
     * It must never be called if there is a possibility of the string before.
     * it being not an empty space.
     * <p>
     * Pre-Condition 2: newCaretPosition should never be in the situation where there is a possibility
     * of it being 0.
     *
     * @param newCaretPosition Current caret position.
     * @return New caret position.
     */
    private int shiftLeftIgnoringSpaces(int newCaretPosition) {
        int caretHolder = newCaretPosition;
        for (int i = caretHolder; i > 0; i--) {
            if (!isEmptyBefore(caretHolder)) {
                break;
            }
            caretHolder -= 1;
        }
        return caretHolder;
    }

    /**
     * Shifts the caret right, ignoring all empty space.
     * <p>
     * Note: Will not implement exception throwing here as shiftCaretRightByWord is set up in such a way
     * that pre-conditions as follows are met. Do not want to write code which will affect test coverage
     * which is impossible to resolve.
     * <p>
     * Pre-Condition 1: Current caret position must have an empty space string on the right.
     * It must never be called if there is a possibility of the string after
     * it being not an empty space.
     * <p>
     * Pre-Condition 2: newCaretPosition should never be in the situation where there is a possibility
     * of it being at most right position.
     *
     * @param newCaretPosition Current caret position.
     * @param maxAchievablePosition Right most bound of word.
     * @return New caret position.
     */
    private int shiftRightIgnoringSpaces(int newCaretPosition, int maxAchievablePosition) {
        int caretHolder = newCaretPosition;
        for (int i = caretHolder; i < maxAchievablePosition; i++) {
            if (!isEmptyAfter(caretHolder)) {
                break;
            }
            caretHolder += 1;
        }
        return caretHolder;
    }

    /**
     * Shifts the caret left, ignoring all char.
     * <p>
     * Note: Will not implement exception throwing here as shiftCaretLeftByWord is set up in such a way
     * that pre-conditions as follows are met. Do not want to write code which will affect test coverage
     * which is impossible to resolve.
     * <p>
     * Pre-Condition 1: Current caret position must have an empty space string on the left.
     * It must never be called if there is a possibility of the string before
     * it being not an empty space.
     * <p>
     * Pre-Condition 2: newCaretPosition should never be in the situation where there is a possibility
     * of it being 0.
     *
     * @param newCaretPosition Current caret position.
     * @return New caret position.
     */
    private int shiftLeftIgnoringWords(int newCaretPosition) {
        int caretHolder = newCaretPosition;
        for (int i = caretHolder; i > 0; i--) {
            if (isEmptyBefore(caretHolder)) {
                break;
            }
            caretHolder -= 1;
        }
        return caretHolder;
    }

    /**
     * Shifts the caret right, ignoring all char.
     * <p>
     * Note: Will not implement exception throwing here as shiftCaretRightByWord is set up in such a way
     * that pre-conditions as follows are met. Do not want to write code which will affect test coverage
     * which is impossible to resolve.
     * <p>
     * Pre-Condition 1: Current caret position must have an empty space string on the right.
     * It must never be called if there is a possibility of the string before
     * it being not an empty space.
     * <p>
     * Pre-Condition 2: newCaretPosition should never be in the situation where there is a possibility
     * of it being at most right position.
     *
     * @param newCaretPosition Current caret position.
     * @param maxAchievablePosition Right most caret position.
     * @return New caret position.
     */
    private int shiftRightIgnoringWords(int newCaretPosition, int maxAchievablePosition) {
        int caretHolder = newCaretPosition;
        for (int i = caretHolder; i < maxAchievablePosition; i++) {
            if (isEmptyAfter(caretHolder)) {
                break;
            }
            caretHolder += 1;
        }
        return caretHolder;
    }

    /**
     * Checks if string element before currentCaretPosition index is empty.
     *
     * @param currentCaretPosition Current caret position.
     * @return True if string element before currentCaretPosition index is empty.
     */
    private boolean isEmptyBefore(int currentCaretPosition) {
        Character charBefore = commandTextField.getText().charAt(currentCaretPosition - 1);
        String convertToString = Character.toString(charBefore);
        return (" ".equals(convertToString));
    }

    /**
     * Checks if string element after currentCaretPosition index is empty.
     *
     * @param currentCaretPosition Current caret position.
     * @return True if string element after currentCaretPosition index is empty.
     */
    private boolean isEmptyAfter(int currentCaretPosition) {
        Character charAfter = commandTextField.getText().charAt(currentCaretPosition);
        String convertToString = Character.toString(charAfter);
        return (" ".equals(convertToString));
    }

    /**
     * Adds the next prefix required for the input
     */
    private void addsNextPrefix() {
        String finalText;
        if (containsPrefix("name")) {
            finalText = concatPrefix(PREFIX_NAME);
        } else if (containsPrefix("phone")) {
            finalText = concatPrefix(PREFIX_PHONE);
        } else if (containsPrefix("email")) {
            finalText = concatPrefix(PREFIX_EMAIL);
        } else if (containsPrefix("address")) {
            finalText = concatPrefix(PREFIX_ADDRESS);
        } else if (containsPrefix("bloodtype")) {
            finalText = concatPrefix(PREFIX_BLOODTYPE);
        } else if (containsPrefix("remark")) {
            finalText = concatPrefix(PREFIX_REMARK);
        } else if (containsPrefix("date")) {
            finalText = concatPrefix(PREFIX_DATE);
        } else if (containsPrefix("all")) {
            finalText = concatPrefix(PREFIX_TAG);
        } else {
            return;
        }
        commandTextField.setText(finalText);
        commandTextField.positionCaret(finalText.length());
    }

    /**
     * Checks if add or edit KeyWord is in the input text. Also checks if prefix is in the input text.
     *
     * @param element String to be evaluated.
     * @return True if contains add or edit keyword and relevant prefixes.
     */
    private boolean containsPrefix(String element) {
        switch (element) {
        case "name":
            return (!containsName() && (addPollSuccessful() || editPollSuccessful()));
        case "phone":
            return (!containsPhone() && (addPollSuccessful() || editPollSuccessful()));
        case "email":
            return (!containsEmail() && (addPollSuccessful() || editPollSuccessful()));
        case "address":
            return (!containsAddress() && (addPollSuccessful() || editPollSuccessful()));
        case "bloodtype":
            return (!containsBloodtype() && (addPollSuccessful() || editPollSuccessful()));
        case "remark":
            return (!containsRemark() && (addPollSuccessful() || editPollSuccessful()));
        case "date":
            return (!containsDate() && (addPollSuccessful() || editPollSuccessful()));
        default:
            return (containsAllCompulsoryPrefix() && (addPollSuccessful() || editPollSuccessful()));
        }
    }

    /**
     * Checks if sentence starts with " add " or " a ".
     * Accounts for blank space in front.
     *
     * @return True if if sentence starts with " add " or " a ".
     */
    private boolean addPollSuccessful() {
        String stringToEvaluate = commandTextField.getText().trim();
        if (stringToEvaluate.length() == 0) {
            return false;
        } else if (stringToEvaluate.length() == 1) {
            return stringToEvaluate.equalsIgnoreCase(AddCommand.COMMAND_ALIAS);
        } else if (stringToEvaluate.length() == 2) {
            return false;
        } else if (stringToEvaluate.length() == 3) {
            return containsAInFirstTwoChar(stringToEvaluate)
                    || stringToEvaluate.equalsIgnoreCase(AddCommand.COMMAND_WORD);
        } else {
            return containsAInFirstTwoChar(stringToEvaluate)
                    || containsAddInFirstFourChar(stringToEvaluate);
        }
    }

    /**
     * Checks if sentence starts with " edit " or " e " and is followed by a valid INDEX.
     * Accounts for blank spaces in front.
     *
     * @return True if sentence starts with " edit " or " e " and is followed by a valid INDEX.
     */
    private boolean editPollSuccessful() {
        String stringToEvaluate = commandTextField.getText().trim();
        if (stringToEvaluate.length() < 3 || !stringToEvaluate.contains(" ")) {
            return false;
        }
        String[] splittedString = stringToEvaluate.split(" ");
        boolean containsEditWord = splittedString[0].equalsIgnoreCase("edit");
        boolean containsEditShorthand = splittedString[0].equalsIgnoreCase("e");
        boolean containsEditCommand = containsEditShorthand || containsEditWord;
        String regex = "[0-9]+";
        boolean containsOnlyNumbers = splittedString[1].matches(regex);
        return containsEditCommand && containsOnlyNumbers;
    }

    /**
     * Checks if the first two elements of the string are "a ".
     *
     * @param stringToEvaluate String to check.
     * @return True if the first two elements of the string are "a ".
     */
    private boolean containsAInFirstTwoChar(String stringToEvaluate) {
        return (Character.toString(stringToEvaluate.charAt(0)).equalsIgnoreCase(AddCommand.COMMAND_ALIAS)
                && Character.toString(stringToEvaluate.charAt(1)).equals(" "));
    }

    /**
     * Checks if the first four elements of the string are "add ".
     *
     * @param stringToEvaluate String to check.
     * @return True if the first four elements of the string are "add ".
     */
    private boolean containsAddInFirstFourChar(String stringToEvaluate) {
        return (stringToEvaluate.substring(0, 3).equalsIgnoreCase(AddCommand.COMMAND_WORD)
                && Character.toString(stringToEvaluate.charAt(3)).equals(" "));
    }

    /**
     * Checks if the commandTextField all prefixes excluding tag.
     *
     * @return True if all prefixes are present.
     */
    private boolean containsAllCompulsoryPrefix() {
        return containsAddress() && containsEmail() && containsBloodtype()
                && containsName() && containsPhone() && containsRemark()
                && containsDate();
    }

    /**
     * Adds prefix string to existing text input.
     *
     * @param prefix Prefix to add.
     * @return Text input concatenated with prefix.
     */
    private String concatPrefix(Prefix prefix) {
        return commandTextField.getText().concat(" ").concat(prefix.getPrefix());
    }

    /**
     * @return True if existing input has Bloodtype Prefix String.
     */
    private boolean containsBloodtype() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_BLOODTYPE.getPrefix());
    }

    /**
     * @return True if existing input has Remark Prefix String.
     */
    private boolean containsRemark() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_REMARK.getPrefix());
    }

    /**
     * @return True if existing input has Date Prefix String.
     */
    private boolean containsDate() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_DATE.getPrefix());
    }

    /**
     * @return True if existing input has Address Prefix String.
     */
    private boolean containsAddress() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_ADDRESS.getPrefix());
    }

    /**
     * @return True if existing input has Email Prefix String.
     */
    private boolean containsEmail() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_EMAIL.getPrefix());
    }

    /**
     * @return True if existing input has Phone Prefix String.
     */
    private boolean containsPhone() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_PHONE.getPrefix());
    }

    /**
     * @return True if existing input has Name Prefix String.
     */
    private boolean containsName() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_NAME.getPrefix());
    }
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * @return the text field for testing purposes
     */
    public TextField getCommandTextField() {
        return commandTextField;
    }
}
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
            Phone phone = (!arePrefixesPresent(argMultimap, PREFIX_PHONE))
                    ? new Phone(NON_COMPULSORY_PHONE)
                    : ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = (!arePrefixesPresent(argMultimap, PREFIX_EMAIL))
                    ? new Email(NON_COMPULSORY_EMAIL)
                    : ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = (!arePrefixesPresent(argMultimap, PREFIX_ADDRESS))
                    ? new Address(NON_COMPULSORY_ADDRESS)
                    : ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Bloodtype bloodType = (!arePrefixesPresent(argMultimap, PREFIX_BLOODTYPE))
                    ? new Bloodtype(NON_COMPULSORY_BLOODTYPE)
                    : ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE)).get();
            Remark remark = (!arePrefixesPresent(argMultimap, PREFIX_REMARK))
                    ? new Remark("") : ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
```
###### /java/seedu/address/logic/parser/ListByTagCommandParser.java
``` java

/**
 * Parses input arguments and creates a new ListByTagCommand object
 */
public class ListByTagCommandParser implements Parser<ListByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListByTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        String[] tagKeyWords = trimmedArgs.split("\\s+");
        List<String> evaluateList = Arrays.asList(tagKeyWords);

        if (trimmedArgs.isEmpty() || isInvalidArgs(evaluateList)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListByTagCommand.MESSAGE_USAGE));
        }
        return new ListByTagCommand(new TagContainsKeywordsPredicate(evaluateList));
    }

    /**
     * Checks if tag list argument is invalid.
     * Tag list is invalid if:
     * 1. List starts or ends with "AND" or "OR".
     * 2. "AND" or "OR" are clustered together.
     *
     * @param evaluateList list of input text to be evaluated.
     * @return True if list argument is invalid.
     */
    private boolean isInvalidArgs(List<String> evaluateList) {
        boolean multipleAndOrCluster = hasManyAndOrClustered(evaluateList);
        boolean startWithAndOr = startsWithAndOr(evaluateList);
        boolean endWithAndOr = endsWithAndOr(evaluateList);
        return multipleAndOrCluster || startWithAndOr || endWithAndOr;
    }

    /**
     * Checks if list starts with "AND" or "OR".
     *
     * @param evaluateList list of input text to be evaluated.
     * @return True if list starts with "AND" or "OR.
     */
    private boolean startsWithAndOr(List<String> evaluateList) {
        String firstString = evaluateList.get(0);
        boolean startWithAndOr = isAnd(firstString) || isOr(firstString);
        return startWithAndOr;
    }

    /**
     * Checks if list ends with "AND" or "OR".
     *
     * @param evaluateList list of input text to be evaluated.
     * @return True if list ends with "AND" or "OR.
     */
    private boolean endsWithAndOr(List<String> evaluateList) {
        String lastString = evaluateList.get(evaluateList.size() - 1);
        boolean hasAnd = isAnd(lastString);
        boolean hasOr = isOr(lastString);
        boolean endWithAndOr = hasAnd || hasOr;
        return endWithAndOr;
    }

    /**
     * Checks if "AND" or "OR" strings are clustered together.
     *
     * @param evaluateList list of input text to be evaluated.
     * @return True if "AND" or "OR" strings are clustered together.
     */
    private boolean hasManyAndOrClustered(List<String> evaluateList) {
        String previousString = "";
        boolean multipleAndOrCluster = false;
        for (String str : evaluateList) {
            if (areBothAndOr(previousString, str)) {
                multipleAndOrCluster = true;
                break;
            }
            previousString = str;
        }
        return multipleAndOrCluster;
    }

    /**
     * Checks if both input strings are "and" or "or".
     *
     * @param before Word before.
     * @param after  Word after.
     * @return True if both contains either "and" or "or".
     */
    private boolean areBothAndOr(String before, String after) {
        boolean isAndOrBefore = isAnd(before) || isOr(before);
        boolean isAndOrAfter = isAnd(after) || isOr(after);
        return isAndOrAfter && isAndOrBefore;
    }

    /**
     * Checks if string is "and".
     *
     * @param string String to be evaluated.
     * @return True if string is "and".
     */
    private boolean isAnd(String string) {
        return "and".equalsIgnoreCase(string);
    }

    /**
     * Checks if string is "or".
     *
     * @param string String to be evaluated.
     * @return True if string is "or".
     */
    private boolean isOr(String string) {
        return "or".equalsIgnoreCase(string);
    }
}
```
###### /java/seedu/address/logic/parser/RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     *
     * @param args input args entered by user.
     * @return Remark command with user defined index and remark.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public RemarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index indexInput;

        if (!isPrefixPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
        try {
            indexInput = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(indexInput, new Remark(remark));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap ArgumentMultimap parsed from input arguments and remark prefix.
     * @param prefixes Remark prefix.
     * @return True if none of the prefixes contains empty optional values.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java

    /**
     * Returns the correct list feature based on word after list
     *
     * @param arguments full user input arguments.
     * @return the command based on arguments provided.
     * @throws ParseException If the user input does not conform the expected format.
     */
    private Command listEvaluator(String arguments) throws ParseException {
        String[] argSplit = arguments.trim().split(" ");
        String firstArg = argSplit[0];
        int firstArgLength = firstArg.length();
        Command returnThisCommand;
        switch (firstArg) {
        case "":
            returnThisCommand = new ListCommand();
            break;
        case ListByTagCommand.COMMAND_SELECTOR:
            returnThisCommand = new ListByTagCommandParser().parse(arguments.substring(firstArgLength + 1));
            break;
        case ListAscendingNameCommand.COMMAND_ALIAS:
        case ListAscendingNameCommand.COMMAND_WORD:
            returnThisCommand = (argSplit.length == 1) ? new ListAscendingNameCommand() : new ListFailureCommand();
            break;
        case ListDescendingNameCommand.COMMAND_ALIAS:
        case ListDescendingNameCommand.COMMAND_WORD:
            returnThisCommand = (argSplit.length == 1) ? new ListDescendingNameCommand() : new ListFailureCommand();
            break;
        case ListReverseCommand.COMMAND_ALIAS:
        case ListReverseCommand.COMMAND_WORD:
            returnThisCommand = (argSplit.length == 1) ? new ListReverseCommand() : new ListFailureCommand();
            break;
        default:
            returnThisCommand = new ListFailureCommand();
        }
        return returnThisCommand;
    }
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     *
     * @param remark String to be evaluated for remark prefix.
     * @return Remark command if remark prefix is present else returns Optional.empty().
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }
```
###### /java/seedu/address/logic/commands/ListReverseCommand.java
``` java
/**
 * Reverses existing displayed list
 */
public class ListReverseCommand extends Command {

    public static final String COMMAND_WORD = "reverse";
    public static final String COMMAND_ALIAS = "rev"; // shorthand equivalent alias
    public static final String COMPILED_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String COMPILED_SHORTHAND_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_ALIAS;

    public static final String MESSAGE_USAGE = COMPILED_COMMAND
            + ": Reverses existing displayed list \n"
            + "Example: " + COMPILED_COMMAND + "\n"
            + "Shorthand Example: " + COMPILED_SHORTHAND_COMMAND;

    public static final String MESSAGE_SUCCESS = "Displayed list reversed";

    /**
     * Returns success message and reverses list.
     *
     * @return Success Message.
     */
    @Override
    public CommandResult execute() {
        model.listNameReversed();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/RemarkCommand.java
``` java
/**
 * Adds/Remove a remark from a person identified using it's last displayed index from the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. \n"
            + "Parameters: INDEX" + " "
            + PREFIX_REMARK
            + "Likes to drink coffee \n"
            + "Example 1: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to drink coffee \n"
            + "Removing Remarks: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK;

    // The first argument is referenced by "1$", the second by "2$"
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Remark successfully added";
    public static final String MESSAGE_REMOVE_REMARK_SUCCESS = "Remark successfully deleted";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index personIndex;
    private final Remark remark;

    /**
     * Returns nothing. Setter class to set index and remark of contact.
     *
     * @param inputIndex Index on filtered list to add the remark to.
     * @param inputRemark Remark to give to the contact.
     */
    public RemarkCommand(Index inputIndex, Remark inputRemark) {
        requireNonNull(inputIndex);
        requireNonNull(inputRemark);

        this.personIndex = inputIndex;
        this.remark = inputRemark;
    }

    /**
     * Returns success message and adds a remark to a contact.
     *
     * @return Success Message.
     * @throws CommandException If index is invalid.
     * @throws CommandException If there is a duplicate person when updating data.
     * @throws AssertionError If person is missing from data.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(personIndex.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getBloodType(),
                personToEdit.getTags(), this.remark, personToEdit.getRelationship(), personToEdit.getAppointments());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.getFilteredPersonList();
        return new CommandResult(outputCorrectTypeOfSuccessMessage(editedPerson));
    }

    /**
     * Checks if 'other' is the same object or an instance of this object.
     *
     * @param other Another object for evaluation.
     * @return True if 'other' is the same object or an instance of this object.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkCommand // instanceof handles nulls
                && this.personIndex.equals(((RemarkCommand) other).personIndex))
                && this.remark.equals(((RemarkCommand) other).remark); // state check
    }

    /**
     * Returns the appropriate success message depending on whether a remark is added or removed.
     *
     * @param editedPerson ReadOnlyPerson that is edited.
     * @return Remove Remark message if a remark is removed or success remark message if remark is added.
     */
    private String outputCorrectTypeOfSuccessMessage(ReadOnlyPerson editedPerson) {
        if (editedPerson.getRemark().toString().isEmpty()) {
            return String.format(MESSAGE_REMOVE_REMARK_SUCCESS, editedPerson);
        }
        return String.format(MESSAGE_REMARK_PERSON_SUCCESS, editedPerson);
    }

}
```
###### /java/seedu/address/logic/commands/ListFailureCommand.java
``` java
/**
 * Prints failure message if invalid arguments are passed after a list command
 *
 * Command is created for list failure detection instead of command parser because list methods
 * are not taking in any arguments and thus there is nothing for the command to parse.
 * Another option to throw this message is to save it in the Messages.java class but since
 * commands are returned in the AddressBookParser.class, it would be more convenient to catch
 * all errors pertaining to the list features and throw the message via a command class instead.
 */
public class ListFailureCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_FAILURE = "Invalid input detected. Valid list variations: \n"
            + COMMAND_WORD + " \n"
            + COMMAND_WORD + " " + ListByTagCommand.COMMAND_SELECTOR + " [TAG/s] \n"
            + COMMAND_WORD + " " + ListAscendingNameCommand.COMMAND_ALIAS + "\n"
            + COMMAND_WORD + " " + ListAscendingNameCommand.COMMAND_WORD + "\n"
            + COMMAND_WORD + " " + ListDescendingNameCommand.COMMAND_ALIAS + "\n"
            + COMMAND_WORD + " " + ListDescendingNameCommand.COMMAND_WORD + "\n"
            + COMMAND_WORD + " " + ListReverseCommand.COMMAND_ALIAS + "\n"
            + COMMAND_WORD + " " + ListReverseCommand.COMMAND_WORD + "\n";

    /**
     * Returns a failure message to indicate invalid command available in list package.
     *
     * @return Failure Message.
     */
    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_FAILURE);
    }

}
```
###### /java/seedu/address/logic/commands/ListDescendingNameCommand.java
``` java
/**
 * Finds and lists all persons in address book in descending order by name
 */
public class ListDescendingNameCommand extends Command {

    public static final String COMMAND_WORD = "descending";
    public static final String COMMAND_ALIAS = "dsc"; // shorthand equivalent alias
    public static final String COMPILED_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String COMPILED_SHORTHAND_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_ALIAS;

    public static final String MESSAGE_USAGE = COMPILED_COMMAND
            + ": Finds and lists contacts by name in descending order \n"
            + "Example: " + COMPILED_COMMAND + "\n"
            + "Shorthand Example: " + COMPILED_SHORTHAND_COMMAND;

    public static final String MESSAGE_SUCCESS = "Listed persons by name in descending order";

    /**
     * Returns a success message and filters display list by name in descending order.
     *
     * @return Success message.
     */
    @Override
    public CommandResult execute() {
        model.listNameDescending();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### /java/seedu/address/logic/commands/ListByTagCommand.java
``` java
/**
 * Finds and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class ListByTagCommand extends Command {

    public static final String COMMAND_WORD = "list tag";
    public static final String COMMAND_SELECTOR = "tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [AND/OR] [KEYWORD]...\n"
            + "Example: " + COMMAND_WORD + " colleague and family";

    public static final String MESSAGE_SUCCESS = "Listed all persons with specified tags";

    private final TagContainsKeywordsPredicate predicate;

    public ListByTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Returns a success message and updates the filtered person list.
     *
     * @return new CommandResult(MESSAGE_SUCCESS).
     */
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Checks if 'other' is the same object or an instance of this object.
     *
     * @param other Another object type for comparison.
     * @return True if 'other' is the same object or an instance of this object.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListByTagCommand // instanceof handles nulls
                && this.predicate.equals(((ListByTagCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ListAscendingNameCommand.java
``` java
/**
 * Finds and lists all persons in address book in ascending order by name.
 */
public class ListAscendingNameCommand extends Command {

    public static final String COMMAND_WORD = "ascending";
    public static final String COMMAND_ALIAS = "asc"; // shorthand equivalent alias
    public static final String COMPILED_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String COMPILED_SHORTHAND_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_ALIAS;

    public static final String MESSAGE_USAGE = COMPILED_COMMAND
            + ": Finds and lists contacts by name in ascending order \n"
            + "Example: " + COMPILED_COMMAND + "\n"
            + "Shorthand Example: " + COMPILED_SHORTHAND_COMMAND;

    public static final String MESSAGE_SUCCESS = "Listed persons by name in ascending order";

    /**
     * Returns a success message and filters the list by name in ascending order.
     *
     * @return CommandResult(MESSAGE_SUCCESS).
     */
    @Override
    public CommandResult execute() {
        model.listNameAscending();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### /java/seedu/address/storage/StorageManager.java
``` java

    /**
     * Saves a backup of the address book.
     *
     * @param addressBook Address book to be saved. Cannot be null.
     * @throws IOException if address book is invalid.
     */
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath() + "-backup.xml");
    }
```
###### /java/seedu/address/storage/XmlAddressBookStorage.java
``` java

    /**
     * Saves a backup address book.
     *
     * @param addressBook Address book to be saved. Cannot be null.
     * @throws IOException If input address book is invalid.
     */
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath + "-backup.xml");
    }

}
```
###### /java/seedu/address/model/person/TagContainsKeywordsPredicate.java
``` java

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the tag keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Checks if keywords contains "AND" or "OR".
     *
     * @param person ReadOnlyPerson to be evaluated.
     * @return Predicate depending if "AND" or "OR" are present.
     */
    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean containsAndOr = keywords.toString().toLowerCase().contains("and")
                || keywords.toString().toLowerCase().contains("or");
        if (containsAndOr) {
            return predicateWithAndOr(person);
        }
        return predicateWithoutAndOr(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

    /**
     * Returns the predicate for the case where the input text does not contain "AND" / "OR".
     * Default Logic: Treated as "AND".
     * Filters users who has ALL input tags.
     *
     * @param person ReadOnlyPerson to be evaluated.
     * @return True if person has all tags in keywords list.
     */
    private boolean predicateWithoutAndOr(ReadOnlyPerson person) {
        int foundTags = 0;
        for (String keyword : keywords) {
            foundTags += checkPersonTags(keyword, person);
        }
        return (foundTags == keywords.size());
    }

    /**
     * Returns the predicate for the case where the input text contains and / or.
     * If "AND" or "OR" is not specified, value treated as "AND".
     *
     * @param person ReadOnlyPerson to be evaluated.
     * @return True if person has all required tags.
     */
    private boolean predicateWithAndOr(ReadOnlyPerson person) {
        List<List<String>> finalPredicate = generateTagNestledList();
        for (List<String> listOfTags : finalPredicate) {
            if (evaluateListOfTags(listOfTags, person)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if person contains all tags in input list
     *
     * @param listOfTags List of tags to be checked.
     * @param person ReadOnlyPerson to be checked against.
     * @return True if ReadOnlyPerson has all tags in the provided list.
     */
    private boolean evaluateListOfTags(List<String> listOfTags, ReadOnlyPerson person) {
        int foundTags = 0;
        for (String tag : listOfTags) {
            foundTags += checkPersonTags(tag, person);
        }
        return foundTags == listOfTags.size();
    }

    /**
     * Returns a nestled list, containing all the list of tags which are separated by "OR"
     * or joined by "AND".
     *
     * @return Nestled list separated by "AND" or "OR".
     */
    private List<List<String>> generateTagNestledList() {
        List<List<String>> finalPredicate = new ArrayList<>();
        List<String> indivPredicate = new ArrayList<>();
        Stack<String> myStack = createStack(keywords);
        while (!myStack.empty()) {
            String peekWord = myStack.peek();
            if ("and".equalsIgnoreCase(peekWord)) {
                myStack.pop();
                indivPredicate.add(myStack.pop());
            } else if ("or".equalsIgnoreCase(peekWord)) {
                myStack.pop();
                finalPredicate.add(indivPredicate);
                indivPredicate = new ArrayList<>();
            } else {
                indivPredicate.add(myStack.pop());
            }
        }
        finalPredicate.add(indivPredicate);
        return finalPredicate;
    }

    /**
     * Instantiates an empty stack and pushes a list of keywords into the stack.
     *
     * @param keywords Keywords to be pushed into stack for "AND" "OR" evaluation.
     * @return Stack containing all the keywords passed by user.
     */
    private Stack<String> createStack(List<String> keywords) {
        Stack<String> myStack = new Stack<>();
        for (String keyword : keywords) {
            myStack.push(keyword);
        }
        return myStack;
    }

    /**
     * Checks if person contains the input tag.
     *
     * @param tag Tag to search for.
     * @param person Person to search.
     * @return 1 if person has tag. 0 if person does not have tag.
     */
    private int checkPersonTags (String tag, ReadOnlyPerson person) {
        for (Tag tags: person.getTags()) {
            if (tags.tagName.equalsIgnoreCase(tag)) {
                return 1;
            }
        }
        return 0;
    }
}
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * @return the list as an unmodifiable list and sorted by name in ascending order
     */
    public ObservableList<ReadOnlyPerson> asObservableListSortedByNameAsc() {
        internalList.sort((o1, o2) -> {
            int output = (o1.getName().fullName.compareToIgnoreCase(o2.getName().fullName) >= 0) ? 1 : -1;
            return output;
        });
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    /**
     * @return the list as an unmodifiable list and sorted by name in descending order
     */
    public ObservableList<ReadOnlyPerson> asObservableListSortedByNameDsc() {
        internalList.sort((o1, o2) -> {
            int output = (o1.getName().fullName.compareToIgnoreCase(o2.getName().fullName) <= 0) ? 1 : -1;
            return output;
        });
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    /**
     * @return a reversed list
     */
    public ObservableList<ReadOnlyPerson> asObservableListReversed() {
        FXCollections.reverse(internalList);
        return FXCollections.unmodifiableObservableList(mappedList);
    }
```
###### /java/seedu/address/model/person/Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; can take in any strings including blanks
 */
public class Remark {

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/AddressBook.java
``` java

    /**
     * Filters person list by name in ascending order.
     *
     * @return Filtered UniquePersonList.
     */
    public ObservableList<ReadOnlyPerson> getPersonListSortByNameAscending() {
        return persons.asObservableListSortedByNameAsc();
    }

    /**
     * Filters person list by name in descending order.
     *
     * @return Filtered UniquePersonList.
     */
    public ObservableList<ReadOnlyPerson> getPersonListSortByNameDescending() {
        return persons.asObservableListSortedByNameDsc();
    }

    /**
     * Filters person list in reverse order.
     *
     * @return Filtered UniquePersonList.
     */
    public ObservableList<ReadOnlyPerson> getPersonListReversed() {
        return persons.asObservableListReversed();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Returns an unmodifiable filtered ReadOnlyPerson list, filtered by name in ascending order.
     *
     * @return an unmodifiable view of the list of ReadOnlyPerson that has nonNull name,
     * in increasing chronological order.
     */
    @Override
    public ObservableList<ReadOnlyPerson> listNameAscending() {
        ObservableList<ReadOnlyPerson> list = addressBook.getPersonListSortByNameAscending();
        return FXCollections.unmodifiableObservableList(list);
    }

    /**
     * Returns an unmodifiable filtered ReadOnlyPerson list, filtered by name in descending order.
     *
     * @return an unmodifiable view of the list of ReadOnlyPerson that has nonNull name,
     * in decreasing chronological order.
     */
    @Override
    public ObservableList<ReadOnlyPerson> listNameDescending() {
        ObservableList<ReadOnlyPerson> list = addressBook.getPersonListSortByNameDescending();
        return FXCollections.unmodifiableObservableList(list);
    }

    /**
     * Returns an unmodifiable filtered  and reversed ReadOnlyPerson list.
     *
     * @return an unmodifiable view of the list of ReadOnlyPerson that is reversed.
     */
    @Override
    public ObservableList<ReadOnlyPerson> listNameReversed() {
        ObservableList<ReadOnlyPerson> list = addressBook.getPersonListReversed();
        return FXCollections.unmodifiableObservableList(list);
    }

```
