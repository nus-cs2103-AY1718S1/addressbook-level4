# Chng-Zhi-Xuan-reused
###### \java\seedu\address\logic\commands\ShowBookmarkCommand.java
``` java
    @Override
    public CommandResult execute() {

        List<String> keyword = Arrays.asList(bookmarkString);

        model.updateFilteredPlaceList(new TagContainsKeywordsPredicate(keyword));

        return new CommandResult(MESSAGE_SHOW_BOOKMARK_SUCCESS);
    }
```
