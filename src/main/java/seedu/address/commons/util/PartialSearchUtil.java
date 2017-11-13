package seedu.address.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

//@@author jianglingshuo
/** Helper functions to  produce for name examination predicates*/
public class PartialSearchUtil {

    private final ArrayList<String> baseList; // the list of words that is going to be find about
    private final ArrayList<String> targetList; // the list of search words

    //constructor
    public PartialSearchUtil(List<String> baseListOri, List<String> targetListOri) {
        baseList = new ArrayList<>(baseListOri);
        targetList = new ArrayList<>(targetListOri);
    }

    /** Compare two list and see whether any string in baseList contains(partially) any string in targetList*/
    public boolean compare() {
        boolean flag = false;
        ListIterator<String> baseListItr = baseList.listIterator();
        while (baseListItr.hasNext()) {
            String baseString = baseListItr.next();
            ListIterator<String> targetListItr = targetList.listIterator();
            while (targetListItr.hasNext()) {
                if (targetListItr.next().toLowerCase().contains(baseString.toLowerCase())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

}
