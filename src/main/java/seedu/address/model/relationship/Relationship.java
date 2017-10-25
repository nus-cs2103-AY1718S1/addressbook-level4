package seedu.address.model.relationship;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * This class defines the relationship between two ReadOnlyPersons
 */
public class Relationship {

    private ReadOnlyPerson fromPerson;
    private ReadOnlyPerson toPerson;
    private RelationshipDirection direction;

    public Relationship(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson, RelationshipDirection direction) {
        requireAllNonNull(fromPerson, toPerson, direction);
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.direction = direction;
    }

    public ReadOnlyPerson getFromPerson() {
        return fromPerson;
    }

    public ReadOnlyPerson getToPerson() {
        return toPerson;
    }

    public RelationshipDirection getDirection() {
        return direction;
    }

    public boolean isUndirected() {
        return !direction.isDirected();
    }


    @Override
    public boolean equals(Object other) {
        boolean personsCheck = this.fromPerson.equals(((Relationship) other).getFromPerson())
                && this.toPerson.equals(((Relationship) other).getToPerson());
        if (isUndirected()) {
            personsCheck = (this.fromPerson.equals(((Relationship) other).getFromPerson())
                    && this.toPerson.equals(((Relationship) other).getToPerson()))
                    || (this.fromPerson.equals(((Relationship) other).getToPerson())
                    && this.toPerson.equals(((Relationship) other).getFromPerson()));
        }

        return other == this // short circuit if same object
                || (other instanceof Relationship // instanceof handles nulls
                && personsCheck);
    }
}
