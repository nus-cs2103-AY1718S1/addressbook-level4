package seedu.address.logic.autocomplete;

/**
 * Stores a pair of stub, and its respective autocomplete possibilities.
 */
public class StubPossibilitiesTuple {

    private final String stub;
    private final AutoCompletePossibilities possibilities;

    public StubPossibilitiesTuple(String stub, AutoCompletePossibilities possibilities) {
        this.stub = stub;
        this.possibilities = possibilities;
    }

    public String getStub() {
        return stub;
    }

    public AutoCompletePossibilities getPossibilities() {
        return possibilities;
    }

}
