package seedu.address.model;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.graph.GraphWrapper;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.RelationshipDirection;

import java.util.List;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Synchronizes the actions between GraphWrapper and the relationships in model.
 */
public class GraphAndRelationshipManager {
    private Model model;
    private GraphWrapper graphWrapper;

    public GraphAndRelationshipManager() {}

    public GraphAndRelationshipManager setData(Model model, GraphWrapper graphWrapper) {
        this.model = model;
        this.graphWrapper = graphWrapper;
        return this;
    }

    /**
     * Adds a relationship between two persons in the address book and updates the graph at the same time.
     * This method is implemented in a similar way as EditCommand. This means that adding a relationship to a person
     * does not change his index in the most recent listing. Hence, the graph can add an edge based on the original
     * ordering of persons and does not need a rebuild.
     * @param indexFromPerson
     * @param indexToPerson
     * @param direction
     * @throws IllegalValueException
     */
    public Relationship addRelationship(Index indexFromPerson, Index indexToPerson, RelationshipDirection direction)
            throws IllegalValueException{
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (indexFromPerson.getZeroBased() >= lastShownList.size()
                || indexToPerson.getZeroBased() >= lastShownList.size()) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson fromPerson = lastShownList.get(indexFromPerson.getZeroBased());
        ReadOnlyPerson toPerson = lastShownList.get(indexToPerson.getZeroBased());
        ReadOnlyPerson fromPersonCopy = fromPerson.copy();
        ReadOnlyPerson toPersonCopy = toPerson.copy();
        Relationship relationshipForFromPerson = new Relationship(fromPersonCopy, toPersonCopy, direction);
        Relationship relationshipForToPerson = relationshipForFromPerson;
        if (!direction.isDirected()) {
            relationshipForToPerson = new Relationship(toPersonCopy, fromPersonCopy, direction);
        }

        /*
         Updating the model
         */
        try {
            ((Person) fromPersonCopy).addRelationship(relationshipForFromPerson);
            ((Person) toPersonCopy).addRelationship(relationshipForToPerson);
            model.updatePerson(fromPerson, fromPersonCopy);
            model.updatePerson(toPerson, toPersonCopy);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("the person's relationship is unmodified. IMPOSSIBLE.");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        /*
         Updating the graph
         */
        graphWrapper.addEdge(fromPersonCopy, toPersonCopy, direction);

        return relationshipForFromPerson;
    }
}
