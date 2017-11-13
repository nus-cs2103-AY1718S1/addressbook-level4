package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private static final String INVALID_TAG_1 = "invalid";
    private static final String INVALID_TAG_2 = "wrongTag";
    private static final String VALID_TAG_1 = "friends";
    private static final String VALID_TAG_2 = "owesMoney";
    private static final String VALID_TAG_3 = "family";

    private static final String TAG_DESC_FAMILY = "[" + VALID_TAG_3 + "] ";
    private static final String TAG_DESC_FRIENDS = "[" + VALID_TAG_1 + "] ";
    private static final String TAG_DESC_OWESMONEY = "[" + VALID_TAG_2 + "] ";

    private ListCommand listCommand;
    private Model expectedModel;
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listCommand = new ListCommand();
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_FULLLIST, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_FULLLIST, expectedModel);
    }

    //@@author aggarwalRuchir
    @Test
    public void execute_listIsFiltered_showOnlyNecessaryPersons() throws Exception {
        PersonContainsKeywordsPredicate firstPredicate = createNewPersonPredicateForSingleTag(new Tag(VALID_TAG_1));

        PersonContainsKeywordsPredicate secondPredicate = createNewPersonPredicateForMultipleTags(Arrays.asList(new
                Tag(VALID_TAG_2), new Tag(VALID_TAG_3)));

        // test command with single valid tag argument
        ListCommand firstListCommand = new ListCommand(firstPredicate);
        firstListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(firstListCommand, model, ListCommand.MESSAGE_SUCCESS_FILTEREDLIST
                + TAG_DESC_FRIENDS, expectedModel);

        firstPredicate = createNewPersonPredicateForSingleTag(new Tag(VALID_TAG_2));
        ListCommand secondListCommand = new ListCommand(firstPredicate);
        secondListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList("Benson")));
        assertCommandSuccess(secondListCommand, model, ListCommand.MESSAGE_SUCCESS_FILTEREDLIST
                + TAG_DESC_OWESMONEY, expectedModel);

        // test command with multiple valid tag arguments
        ListCommand thirdListCommand = new ListCommand(secondPredicate);
        reInitializeModels();
        thirdListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList("Benson", "Fiona",
                "George")));
        assertCommandSuccess(thirdListCommand, model, ListCommand.MESSAGE_SUCCESS_FILTEREDLIST
                + TAG_DESC_OWESMONEY + TAG_DESC_FAMILY, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showNoPersons() throws Exception {
        PersonContainsKeywordsPredicate firstPredicate = createNewPersonPredicateForSingleTag(new Tag(INVALID_TAG_1));
        PersonContainsKeywordsPredicate secondPredicate = createNewPersonPredicateForMultipleTags(Arrays.asList(new
                Tag(INVALID_TAG_1), new Tag(INVALID_TAG_2)));

        reInitializeModels();

        // test command with single invalid tag argument
        ListCommand firstListCommand = new ListCommand(firstPredicate);
        firstListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList()));
        assertCommandSuccess(firstListCommand, model, ListCommand.MESSAGE_NOENTRIESFOUND, expectedModel);

        reInitializeModels();

        // test command with multiple invalid tag arguments
        ListCommand secondListCommand = new ListCommand(secondPredicate);
        secondListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList()));
        assertCommandSuccess(secondListCommand, model, ListCommand.MESSAGE_NOENTRIESFOUND, expectedModel);
    }


    /**
     * re initializes the models to values given at Set up time
     */
    public void reInitializeModels () {
        this.model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        this.expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    /**
     * return a new PersonContainsKeywordsPredicate with input tag
     */
    private PersonContainsKeywordsPredicate createNewPersonPredicateForSingleTag(Tag tag) throws Exception {
        Set<Tag> singleTagSet = new HashSet<Tag>(Arrays.asList(tag));
        PersonContainsKeywordsPredicate newPredicate = new PersonContainsKeywordsPredicate(new
                ArrayList<>(singleTagSet));
        return newPredicate;
    }

    /**
     * return a new PersonContainsKeywordsPredicate with list of input tags
     */
    private PersonContainsKeywordsPredicate createNewPersonPredicateForMultipleTags(List<Tag> tagList) {
        Set<Tag> multipleTagSet = new HashSet<Tag>(tagList);
        PersonContainsKeywordsPredicate newPredicate = new PersonContainsKeywordsPredicate(new
                ArrayList<>(multipleTagSet));
        return newPredicate;
    }
}
