# sofarsophie
###### \EditHistoryCommand.java
``` java
public class EditHistoryCommand extends Command {

    public static final String COMMAND_WORD = "edit_history";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all edit history of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_RESULT = "Edit history: ";
    public static final String MESSAGE_SUCCESS = "Listed edit history";

    private final Index index;

    public EditHistoryCommand(Index index) {
        this.index = requireNonNull(index);
    }

    @Override
    public CommandResult execute() throws CommandException {
        // Temporary print for debugging.
        System.out.println(MESSAGE_RESULT);
        for(int i=0; i<model.getAddressBook().getEditHistoryMap().getMap().get(index).size(); i++) {
            System.out.println(model.getAddressBook().getEditHistoryMap().getMap().get(index).get(i).getKey() +
                    " changed to " + model.getAddressBook().getEditHistoryMap().getMap().get(index).get(i).getValue() + "\n");
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \EditHistoryCommandParser.java
``` java
public class EditHistoryCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditHistoryCommand
     * and returns an EditHistoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public EditHistoryCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EditHistoryCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditHistoryCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \EditHistoryMap.java
``` java
public class EditHistoryMap {

    private final HashMap<Index, ArrayList<Pair<String, String>>> internalMap = new HashMap<>();

    public EditHistoryMap() {}

    public EditHistoryMap(HashMap<Index, ArrayList<Pair<String, String>>> editHistoryMap) {
        internalMap.putAll(editHistoryMap);
    }

    public void setEditHistories(HashMap<Index, ArrayList<Pair<String, String>>> editHistoryMap) {
        internalMap.putAll(editHistoryMap);
    }

    public void addEditHistory(Index index, String before, String after) {
        if(internalMap.get(index)==null)
            internalMap.put(index, new ArrayList<>());

        internalMap.get(index).add(new Pair<>(before, after));
    }

    public HashMap<Index, ArrayList<Pair<String, String>>> getMap() {
        return internalMap;
    }
}
```
