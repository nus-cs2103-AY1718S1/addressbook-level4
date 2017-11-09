# Sri-vatsa
###### \java\seedu\address\logic\commands\DeleteTagCommand.java
``` java
/**
 *
 * Deletes all tags identified from the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteTag";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a particular tag from everyone.\n"
            + "Parameters: Tag1(text) Tag2(text)\n"
            + "Example: " + COMMAND_WORD + " friends" + " family";

    public static final String MESSAGE_SUCCESS = "Tag(s) successfully deleted";
    public static final String MESSAGE_NO_TAGS_DELETED = "Tag(s) not in address book; Nothing to delete";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " TAG 1" + " TAG2" + " ...";

    private final String[] mTagsArgs;
    private Tag[] mTagsToDelete;

    public DeleteTagCommand(String[] tag) throws NullPointerException {
        if (tag == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        mTagsArgs = tag;
    }

    /***
     * Helper method that converts array of arguments (string type) to array of tags (Tag class)
     * @param tag array of arguments in String
     * @throws IllegalValueException
     */
    private Tag[] stringToTag (String[] tag) throws IllegalValueException {
        int numOfArgs = tag.length;
        Tag[] tagsToDelete = new Tag[numOfArgs];

        try {
            for (int i = 0; i < numOfArgs; i++) {
                tagsToDelete[i] = new Tag(tag[i]);
            }
        } catch (IllegalValueException ive) {
            throw new IllegalValueException("Illegal tag value.");
        } catch (IndexOutOfBoundsException ibe) {
            throw new IndexOutOfBoundsException("Accessing tags that do not exist.");
        }
        return tagsToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        boolean hasOneOrMoreDeletion = false;
        try {
            mTagsToDelete = stringToTag(mTagsArgs);
            hasOneOrMoreDeletion = model.deleteTag(mTagsToDelete);

        } catch (IllegalValueException ive) {
            assert false : "The tag is not a proper value";
        } catch (PersonNotFoundException pnfe) {
            assert false : "The person associated with the tag cannot be missing";
        }

        if (hasOneOrMoreDeletion) {
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_TAGS_DELETED));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && Arrays.equals(this.mTagsArgs, ((DeleteTagCommand) other).mTagsArgs)); // state check
    }

}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {

        model.updateFilteredPersonList(predicate);
        int searchResultsCount = model.getFilteredPersonList().size();

        if (searchResultsCount != NO_RESULTS) {
            model.recordSearchHistory();
        }
        return new CommandResult(getMessageForPersonListShownSummary(searchResultsCount));
    }
```
###### \java\seedu\address\logic\commands\ListByMostSearchedCommand.java
``` java
package seedu.address.logic.commands;

/***
 * Lists all users in the addressbook based on how frequently they are searched
 * Sorts by search frequency
 *
 */

public class ListByMostSearchedCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "listMostSearched";
    public static final String COMMAND_ALIAS = "lms";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all persons sorted by frequency of search";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPersonListBySearchCount();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}

```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPersonListLexicographically();
        return new CommandResult(MESSAGE_SUCCESS);
    }
```
###### \java\seedu\address\logic\parser\DeleteTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteTagCommand;

import seedu.address.logic.parser.exceptions.ParseException;


/***
 * Parses the given arguments in the context of the DeleteTagCommand to faciliate execution of method
 * @throws ParseException if the user input does not conform the expected format
 */

public class DeleteTagCommandParser implements Parser<DeleteTagCommand>  {

    /***
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        String[] deleteKeywords = trimmedArgs.split("\\s+");

        return new DeleteTagCommand(deleteKeywords);
    }
}



```
###### \java\seedu\address\model\AddressBook.java
``` java
    /***
     * sorts persons in the addressbook by number of times they were previously searched
     */
    public void sortBySearchCount() {
        persons.sortBySearchCount();
    }


    /***
     * sorts persons in the addressbook alphabetically
     */
    public void sortLexicographically() {
        persons.sortLexicographically();
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes given tag from everyone in the addressbook */
    boolean deleteTag(Tag [] tags) throws PersonNotFoundException, DuplicatePersonException;
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Updates search count for each person who is searched using {@code FindCommand}
     * Assumes filtered List of persons contains search results
     */
    void recordSearchHistory() throws CommandException;

    /**
     * Sort everyone in addressbook by searchCount
     */
    void sortPersonListBySearchCount();

    /**
     * Sort everyone in addressbook lexicographically
     */
    void sortPersonListLexicographically();

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /***
     * Records how many times each person in addressbook is searched for
     * @throws CommandException
     */
    @Override
    public void recordSearchHistory() throws CommandException {

        int searchResultsCount = filteredPersons.size();

        for (int i = 0; i < searchResultsCount; i++) {
            ReadOnlyPerson searchedPerson = filteredPersons.get(i);
            SearchData updatedSearchData = searchedPerson.getSearchData();
            updatedSearchData.incrementSearchCount();
            Person modifiedPerson = new Person(searchedPerson.getInternalId(), searchedPerson.getName(),
                    searchedPerson.getPhone(), searchedPerson.getEmail(), searchedPerson.getAddress(),
                    searchedPerson.getTags(), updatedSearchData);
            try {
                updatePerson(searchedPerson, modifiedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
    }

    //=========== Sort addressBook methods =============================================================
    /***
     * Sorts persons in address book by searchCount
     */
    @Override
    public void sortPersonListBySearchCount() {
        addressBook.sortBySearchCount();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    /***
     * Sorts persons in Address book alphabetically
     */
    @Override
    public void sortPersonListLexicographically() {
        addressBook.sortLexicographically();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public SearchData getSearchData() {
        return searchCount.get();
    }
```
###### \java\seedu\address\model\person\SearchData.java
``` java
package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/***
 * Represents number of times a person is searched for
 * Guarantees: Not editable through user interface; auto updates after each search
 */


public class SearchData {

    private String searchCount;

    /**
     *
     * @throws IllegalValueException if searchCount string is invalid.
     */
    public SearchData(String searchCount) throws IllegalValueException {
        this.searchCount = searchCount;
    }


    public void setSearchCount(String searchCount) {
        this.searchCount = searchCount;
    }

    public String getSearchCount() {
        return this.searchCount;
    }

    /**
     * Increases search count by 1 each time it is called
     */
    public void incrementSearchCount() {
        int searchCountInt = Integer.parseInt(this.searchCount);
        searchCountInt++;
        this.searchCount = Integer.toString(searchCountInt);
    }

    @Override
    public String toString() {
        return searchCount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && this.searchCount.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return searchCount.hashCode();
    }


}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /***
     * sort addressbook persons by number of times they were searched for
     */
    public void sortBySearchCount () {
        internalList.sort(new SearchCountComparator());
    }

    /**
     * Custom Comparator class to compare two ReadOnlyPerson Objects by their search Count
     */
    public class SearchCountComparator implements Comparator<ReadOnlyPerson> {

        /**
         * Basis of comparison between ReadOnlyPerson
         * Compares two persons by Search Count
         *
         * @param o1 is an instance of ReadOnlyPerson
         * @param o2 is another instance of ReadOnlyPerson
         * @return Result of Comparison
         */
        public int compare (ReadOnlyPerson o1, ReadOnlyPerson o2) {

            int personASearchCount = Integer.parseInt(o1.getSearchData().getSearchCount());
            int personBSearchCount = Integer.parseInt(o2.getSearchData().getSearchCount());

            if (personASearchCount > personBSearchCount) {
                return -1;
            } else if (personASearchCount < personBSearchCount) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /***
     * sort address book persons in alphabetical order
     */
    public void sortLexicographically () {
        internalList.sort(new LexicographicComparator());
    }

    /**
     * Custom Comparator class to compare two ReadOnlyPerson Objects lexicographically
     */
    public class LexicographicComparator implements Comparator<ReadOnlyPerson> {

        /**
         * Basis of comparison between ReadOnlyPerson
         * Compares two persons lexicographically
         *
         * @param o1 is an instance of ReadOnlyPerson
         * @param o2 is another instance of ReadOnlyPerson
         * @return Result of Comparison
         */
        public int compare (ReadOnlyPerson o1, ReadOnlyPerson o2) {

            String personAFullName = o1.getName().fullName;
            String personBFullName = o2.getName().fullName;

            return personAFullName.compareTo(personBFullName);
        }

    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    public static final String DEFAULT_PAGE = "default.html";
    public static final String LINKEDIN_SEARCH_URL_PREFIX = "https://www.linkedin.com/search/results/";
    public static final String LINKEDIN_SEARCH_PEOPLE = "people/";
    public static final String LINKEDIN_SEARCH_PARAM_LOCATION = "?facetGeoRegion=%5B%22sg%3A0%22%5D";
    public static final String LINKEDIN_SEARCH_PARAM_FIRST_NAME = "&firstName=";
    public static final String LINKEDIN_SEARCH_PARAM_LAST_NAME = "&lastName=";
    public static final String LINKEDIN_URL_SUFFIX = "&origin=FACETED_SEARCH";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    public static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com.sg/maps?safe=off&q=";
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java

    /***
     * Loads pages based on choose command selection
     * @param page
     */
    private void loadOtherPages(String page) {
        if (page == "linkedin") {
            String[] name = personSelected.getName().fullName.split(" ");

            loadPage(LINKEDIN_SEARCH_URL_PREFIX + LINKEDIN_SEARCH_PEOPLE + LINKEDIN_SEARCH_PARAM_LOCATION
                    + LINKEDIN_SEARCH_PARAM_FIRST_NAME + name[0] + LINKEDIN_SEARCH_PARAM_LAST_NAME + name[1]
                    + LINKEDIN_URL_SUFFIX);
        }
    }
```
