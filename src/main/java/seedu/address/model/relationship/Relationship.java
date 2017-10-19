package seedu.address.model.relationship;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Relationship {

    public static final String RELATIONSHIPID_VALIDATION_REGEX = "\\d";
    public static final String MESSAGE_RELATIONSHIPID_CONSTRAINTS = "Relationship ID should be a string of digits.";

    public final String relationshipID;
    private ReadOnlyPerson fromPerson;
    private ReadOnlyPerson toPerson;
    private RelationshipDirection direction;

    /*
    for undirected relationships only; when relationship is directed or direction == null, relationshipID2 == null
    */
    private String relationshipID2;

    public Relationship(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson, RelationshipDirection direction) {
        requireAllNonNull(fromPerson, toPerson, direction);
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.direction = direction;
        this.relationshipID = computeRelationshipID(fromPerson, toPerson);
        if (isUndirected()) {
            this.relationshipID2 = computeRelationshipID(toPerson, fromPerson);
        }
    }

    public Relationship(String relationshipID) throws IllegalValueException {
        requireNonNull(relationshipID);
        String trimmedID = relationshipID.trim();
        if (!isValidRelationshipID(trimmedID)) {
            throw new IllegalValueException(MESSAGE_RELATIONSHIPID_CONSTRAINTS);
        }
        this.relationshipID = relationshipID;
    }

    public RelationshipDirection getDirection() {
        return direction;
    }

    public String getRelationshipID() {
        return relationshipID;
    }

    public String getRelationshipID2() {
        return relationshipID2;
    }

    public String computeRelationshipID(ReadOnlyPerson fPerson, ReadOnlyPerson tPerson) {
        requireAllNonNull(fPerson, tPerson);
        int fPersonHashCode = fPerson.hashCode();
        int tPersonHashCode = tPerson.hashCode();
        return Integer.toString(fPersonHashCode) + Integer.toString(tPersonHashCode);
    }

    public static boolean isValidRelationshipID(String ID) {
        return ID.matches(RELATIONSHIPID_VALIDATION_REGEX);
    }

    public boolean isDirected() {
        return (direction != null) && direction.isDirected();
    }

    public boolean isUndirected() {
        return (direction != null) && !direction.isDirected();
    }


    @Override
    public boolean equals(Object other) {
        boolean IDCheck = this.relationshipID.equals(((Relationship) other).getRelationshipID())
                || this.relationshipID.equals(((Relationship) other).getRelationshipID2());
        if (isUndirected()) {
            IDCheck = this.relationshipID.equals(((Relationship) other).getRelationshipID())
                    || this.relationshipID2.equals(((Relationship) other).getRelationshipID())
                    || this.relationshipID.equals(((Relationship) other).getRelationshipID2())
                    || this.relationshipID2.equals(((Relationship) other).getRelationshipID2());
        }

        return other == this // short circuit if same object
                || (other instanceof Relationship // instanceof handles nulls
                && IDCheck);
    }
}
