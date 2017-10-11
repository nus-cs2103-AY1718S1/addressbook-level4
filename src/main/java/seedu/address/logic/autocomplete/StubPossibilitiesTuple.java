package seedu.address.logic.autocomplete;

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
