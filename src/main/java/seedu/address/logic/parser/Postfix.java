package seedu.address.logic.parser;

/**
 * A postfix that is appended to the end of an argument in an arguments string.
 * E.g. 'desc' in 'find James t/desc'.
 */
public class Postfix {
    private final String postfix;

    public Postfix(String postfix) {
        this.postfix = postfix;
    }

    public String getPostfix() {
        return postfix;
    }

    public String toString() {
        return getPostfix();
    }

    @Override
    public int hashCode() {
        return postfix == null ? 0 : postfix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Postfix)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Postfix otherPostfix = (Postfix) obj;
        return otherPostfix.getPostfix().equals(getPostfix());
    }
}
