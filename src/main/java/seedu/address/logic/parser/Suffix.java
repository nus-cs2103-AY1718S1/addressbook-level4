package seedu.address.logic.parser;

/**
 * A suffix that marks the end of an argument in an arguments string.
 * E.g. weekly in 'addtask do this by monday weekly'.
 */
public class Suffix {
    private final String suffix;

    public Suffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String toString() {
        return getSuffix();
    }

    @Override
    public int hashCode() {
        return suffix == null ? 0 : suffix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Suffix)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Suffix otherSuffix = (Suffix) obj;
        return otherSuffix.getSuffix().equals(getSuffix());
    }
}
