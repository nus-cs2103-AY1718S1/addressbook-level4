package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

//@@author Jeremy

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the tag keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean containsAndOr = keywords.toString().toLowerCase().contains("and")
                || keywords.toString().toLowerCase().contains("or");
        if (containsAndOr) {
            return predicateWithAndOr(person);
        } else {
            return predicateWithoutAndOr(person);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }


    /**
     * Returns the predicate for the case where the input text does not contain "AND" / "OR"
     * Default Logic: Treated as "AND"
     * Filters users who has ALL input tags
     */
    private boolean predicateWithoutAndOr(ReadOnlyPerson person) {
        int foundTags = 0;
        for (String keyword : keywords) {
            for (Tag tags : person.getTags()) {
                if (tags.tagName.equalsIgnoreCase(keyword)) {
                    foundTags += 1;
                    break;
                }
            }
        }
        return (foundTags == keywords.size());
    }

    /**
     * Returns the predicate for the case where the input text contains and / or
     * If "AND" or "OR" is not specified, value treated as "AND"
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
     * Returns true if person contains all tags in list
     */
    private boolean evaluateListOfTags(List<String> listOfTags, ReadOnlyPerson person) {
        int foundTags = 0;
        for (String tag : listOfTags) {
            for (Tag tags : person.getTags()) {
                if (tags.tagName.equalsIgnoreCase(tag)) {
                    foundTags += 1;
                    break;
                }
            }
        }
        return foundTags == listOfTags.size();
    }

    /**
     * Returns a nestled list, containing all the list of tags which are seperated by "OR"
     * or joined by "AND"
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
     * Instantiates an empty stack and pushes a list of keywords into the stack
     */
    private Stack<String> createStack(List<String> keywords) {
        Stack<String> myStack = new Stack<>();
        for (String keyword : keywords) {
            myStack.push(keyword);
        }
        return myStack;
    }
}
