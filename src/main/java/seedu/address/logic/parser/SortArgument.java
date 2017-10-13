package seedu.address.logic.parser;

/**
 * A sort argument that marks the way a list should be sorted.
 * E.g. 'n/desc' in 'list n/desc' displays a list sorted by name in descending order
 */
public class SortArgument {
    private final String argument;

    public SortArgument(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }

    @Override
    public String toString() {
        return getArgument();
    }

    @Override
    public int hashCode() {
        return argument == null ? 0 : argument.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SortArgument)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        SortArgument otherArgument = (SortArgument) obj;
        return otherArgument.getArgument().equals(getArgument());
    }
}
