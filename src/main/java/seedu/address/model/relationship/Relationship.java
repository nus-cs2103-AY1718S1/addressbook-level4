package seedu.address.model.relationship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;

//@@author wenmogu
/**
 * This class defines the relationship between two ReadOnlyPersons
 */
public class Relationship {

    private ReadOnlyPerson fromPerson;
    private ReadOnlyPerson toPerson;
    private RelationshipDirection direction;

    private Name name;
    private ConfidenceEstimate confidenceEstimate;

    public Relationship(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson, RelationshipDirection direction) {
        requireAllNonNull(fromPerson, toPerson, direction);
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.direction = direction;
        this.name = Name.UNSPECIFIED;
        this.confidenceEstimate = ConfidenceEstimate.UNSPECIFIED;
    }

    public Relationship(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson, RelationshipDirection direction,
                        Name name, ConfidenceEstimate confidenceEstimate) {
        this(fromPerson, toPerson, direction);
        this.setConfidenceEstimate(confidenceEstimate);
        this.setName(name);
    }

    public Relationship(Relationship re) {
        this.fromPerson = re.getFromPerson();
        this.toPerson = re.getToPerson();
        this.direction = re.getDirection();
        this.name = re.getName();
        this.confidenceEstimate = re.getConfidenceEstimate();
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

    /**
     * This is to make the relationship's person entries always point to the existing persons in the address book
     * used when a person is updated
     */
    public Relationship replacePerson(ReadOnlyPerson previousPerson, ReadOnlyPerson currentPerson) {
        if (this.fromPerson.equals(previousPerson)) {
            this.fromPerson = currentPerson;
        } else if (this.toPerson.equals(previousPerson)) {
            this.toPerson = currentPerson;
        }
        return this;
    }

    public Name getName() {
        return name;
    }

    public ConfidenceEstimate getConfidenceEstimate() {
        return confidenceEstimate;
    }

    public boolean isUndirected() {
        return !direction.isDirected();
    }

    /**
     * This is to find the opposite relationships of the one that the user is trying to add.
     * An opposite relationship of one relationship is defined as a relationship involving the same two persons
     * but with a different direction (DIRECTED <=> UNDIRECTED).
     * If the opposite relationship exists, remove the opposite relationship before adding the intended relationship.
     * @return an ArrayList containing two opposite relationships of this one.
     */
    public ArrayList<Relationship> oppositeRelationships() {
        ReadOnlyPerson fromPerson = getFromPerson();
        ReadOnlyPerson toPerson = getToPerson();
        ArrayList<Relationship> oppoRelationships = new ArrayList<>(2);
        if (this.isUndirected()) {
            oppoRelationships.add(new Relationship(fromPerson, toPerson, RelationshipDirection.DIRECTED));
            oppoRelationships.add(new Relationship(toPerson, fromPerson, RelationshipDirection.DIRECTED));
        } else {
            oppoRelationships.add(new Relationship(fromPerson, toPerson, RelationshipDirection.UNDIRECTED));
            oppoRelationships.add(new Relationship(toPerson, fromPerson, RelationshipDirection.UNDIRECTED));
        }

        return oppoRelationships;
    }

    /**
     * Look for the counterpart of @param person in this relationship.
     */
    public ReadOnlyPerson counterpartOf(ReadOnlyPerson person) {
        requireNonNull(person);
        if (person.equals(this.fromPerson)) {
            return this.toPerson;
        } else if (person.equals(this.toPerson)) {
            return this.fromPerson;
        } else {
            return null;
        }
    }

    public void setName(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    public void setConfidenceEstimate(ConfidenceEstimate confidenceEstimate) {
        requireNonNull(confidenceEstimate);
        this.confidenceEstimate = confidenceEstimate;
    }

    /**
     * A toString method for Relationship
     */
    public String toString() {
        String nameAndConfidenceEstimate = this.name.toString() + " " + this.confidenceEstimate.toString();
        if (isUndirected()) {
            return fromPerson.toString() + " <-> " + toPerson.toString() + " " + nameAndConfidenceEstimate;
        } else {
            return fromPerson.toString() + " -> " + toPerson.toString() + " " + nameAndConfidenceEstimate;
        }
    }


    @Override
    public boolean equals(Object other) {

        if (other == this) { // short circuit if same object
            return true;
        } else if (other instanceof Relationship) { //instance of handles null

            boolean correspondingPersonCheck = true;
            if (this.getDirection() != ((Relationship) other).getDirection()) {
                return false;
            } else {
                if (this.isUndirected() && ((Relationship) other).isUndirected()) {
                    correspondingPersonCheck = (this.getFromPerson().equals(((Relationship) other).getFromPerson())
                            && this.getToPerson().equals(((Relationship) other).getToPerson()))
                            || (this.getFromPerson().equals(((Relationship) other).getToPerson())
                            && this.getToPerson().equals(((Relationship) other).getFromPerson()));
                } else if (!this.isUndirected() && !((Relationship) other).isUndirected()) {
                    correspondingPersonCheck = this.getFromPerson().equals(((Relationship) other).getFromPerson())
                            && this.getToPerson().equals(((Relationship) other).getToPerson());
                }
            }
            return correspondingPersonCheck;
        } else {
            return false;
        }
    }
}
