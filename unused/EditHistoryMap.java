package seedu.address.model.edithistory;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;

import seedu.address.commons.core.index.Index;

//@@author sofarsophie
public class EditHistoryMap {

    private final HashMap<Index, ArrayList<Pair<String, String>>> internalMap = new HashMap<>();

    public EditHistoryMap() {}

    public EditHistoryMap(HashMap<Index, ArrayList<Pair<String, String>>> editHistoryMap) {
        internalMap.putAll(editHistoryMap);
    }

    public void setEditHistories(HashMap<Index, ArrayList<Pair<String, String>>> editHistoryMap) {
        internalMap.putAll(editHistoryMap);
    }

    public void addEditHistory(Index index, String before, String after) {
        if(internalMap.get(index)==null)
            internalMap.put(index, new ArrayList<>());

        internalMap.get(index).add(new Pair<>(before, after));
    }

    public HashMap<Index, ArrayList<Pair<String, String>>> getMap() {
        return internalMap;
    }
}
//@@author
