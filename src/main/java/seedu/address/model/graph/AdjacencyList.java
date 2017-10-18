package seedu.address.model.graph;

import javafx.util.Pair;
import org.graphstream.util.set.FixedArrayList;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.ArrayList;

public class AdjacencyList {

    private FixedArrayList<ArrayList<Pair<Integer, Integer>>> adjList;
    private final Model model;
    private final FixedArrayList<ReadOnlyPerson> indexRecord;

    public AdjacencyList(Model model) {
        this.model = model;
        this.indexRecord = model.getIndexRecord();
        this.adjList = new FixedArrayList<>();
    }

    public  FixedArrayList<ArrayList<Pair<Integer, Integer>>> addDirectedRelationship(ReadOnlyPerson person1,
                                                                                 ReadOnlyPerson person2) {

        return adjList;
    }

}
