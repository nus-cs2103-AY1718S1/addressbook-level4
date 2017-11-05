# willxujun
###### /java/seedu/address/logic/parser/SearchParser.java
``` java
/**
 * Represents a parser that parses input from the search bar
 */
public class SearchParser {

    /**
     * returns a Command as parsed
     * @param args
     * @return a FindCommand of the search word args if search bar input is not empty, a ListCommand if empty search bar
     * @throws ParseException
     */
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListCommand();
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### /java/seedu/address/ui/SearchBox.java
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

    /**
     * Handles the Key typed event
     */
    @FXML
    private void handleKeyTyped(KeyEvent keyEvent) {
        String s = keyEvent.getCharacter();
        if (s.equals("\u0008") || s.equals("\u007F")) {
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

}
```
